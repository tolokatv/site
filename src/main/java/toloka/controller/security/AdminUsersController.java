package toloka.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toloka.Repository.PrivilegeRepository;
import toloka.Repository.RoleRepository;
import toloka.model.security.SiteUser;
import toloka.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//import java.util.Map;
//import toloka.model.front.FrontContacts;



@Controller
public class AdminUsersController {
//    private UserRepository userrepository;
//    private UserCRUDRepositore userCRUDRepository;
    private RoleRepository rolerepositore;
    private PrivilegeRepository privilegerepositore;

//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    //Save the uploaded file to this folder
    @Value("${site.tmp.directory}")
    private String UPLOADED_FOLDER;
    @Value("${site.user.directory}")
    private String USER_FOLDER;

    @Transactional
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/users", method = RequestMethod.GET)
    public ModelAndView GetAdminUser(ModelAndView modelAndView) {

        List<SiteUser> users;
        System.out.println("========= AdminUsersController  LIST GET");
        modelAndView.setViewName("/admin/listusers");
        users = userService.GetUserList();
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/users", method = RequestMethod.POST)
    public ModelAndView PostAdminUser(ModelAndView modelAndView) {

        System.out.println("========= AdminUsersController  LIST POST");
        modelAndView.setViewName("/admin/listusers");
        return modelAndView;
    }


    @RequestMapping(value="/user/edit/{ID}",  method = RequestMethod.GET)
    public ModelAndView GetUserEdit(ModelAndView modelAndView,
                                    @PathVariable("ID") Long ID) {

        SiteUser user;
        System.out.println("========= AdminUsersController  GetUserEdit GET. ID="+
                Long.toString(ID));
        modelAndView.setViewName("/user/edituser");

        // перевіряємо чи цей користувач може редагувати профайл з цим ID
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String currentusername = null;
        if (authentication != null){
            if (authentication.getPrincipal() instanceof UserDetails) {
                currentusername = ((UserDetails) authentication.getPrincipal()).getUsername();
            }
            else if (authentication.getPrincipal() instanceof String) {
                currentusername = (String) authentication.getPrincipal();
            }
        }

//        System.out.println("========= AdminUsersController  GetUserEdit GET. Current user:" + currentusername);

        SiteUser entityuser = userService.findByEmail(currentusername);
        if (entityuser != null) {

            if ((entityuser.getId() != null)
                    && (entityuser.getId().equals(ID))) {
                // Цей користувач, хоче редагувати свій профайл. Можемо редагувати користувача
                modelAndView.addObject("user", userService.findUserById(ID));
                modelAndView.addObject("avatar", userService.findUserById(ID).getAvatar());
                if (authentication.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                    //Поточний користувач є АДМІНОМ. Він може робити все, що йому заманеться.
                    modelAndView.addObject("admin", true);
                };
            } else { // Це інший користувач. Перевіряємо на належність до групи адміністраторів.
                if (authentication.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                    //Поточний користувач є АДМІНОМ. Він може робити все, що йому заманеться.
                    modelAndView.addObject("user", userService.findUserById(ID));
                    modelAndView.addObject("admin", true);
                    modelAndView.addObject("avatar", userService.findUserById(ID).getAvatar());
                } else {
                    modelAndView.addObject("errorMessage", "Ви не маєте прав для редагування цього користувача.");
                }
            }

        } else {
            modelAndView.addObject("errorMessage", "Ви не маєте прав для редагування цього користувача.");
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                //Поточний користувач є АДМІНОМ. Він може робити все, що йому заманеться.
                modelAndView.addObject("user", userService.findUserById(ID));
                modelAndView.addObject("admin", true);
                modelAndView.addObject("avatar", userService.findUserById(ID).getAvatar());
            }
        }
        return modelAndView;
    }
// Зберігаємо користувача
    @Secured ({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value="/user/edit/{ID}",  method = RequestMethod.POST)
    public ModelAndView PostUserEdit(ModelAndView modelAndView, @PathVariable("ID") Long ID, @Valid SiteUser user) {
        modelAndView.setViewName("/user/edituser");
        SiteUser tuser = userService.findUserById(ID);
        System.out.println("========= AdminUsersController  PostUserEdit POST. Current user.id in form:" + user.getId());
        tuser.setFirstName(user.getFirstName());
        tuser.setLastName(user.getLastName());
        tuser.setPhone(user.getPhone());
        userService.saveUser(tuser);
        System.out.println(user.getPhone());
//        System.out.println(tuser.getPhone());
        modelAndView.addObject("errorMessage", "Всі данні користувача збережено.");
        modelAndView.addObject("user", tuser);
        return modelAndView;
    }

    // Зберігаємо аватар користувача
    @Secured ({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value="/user/edit/avatar/{ID}",  method = RequestMethod.POST)
    public ModelAndView SaveUserAvatar(@RequestParam("file") MultipartFile file,
//    public String SaveUserAvatar(@RequestParam("file") MultipartFile file,
                                       ModelAndView modelAndView,
                                       @PathVariable("ID") Long ID,
                                       Model model,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("/user/edituser");
        if (file.isEmpty()) {
//            model.addAttribute("errorMessage", "Ви забули обрати файл. Будь ласка, виберіть файл.");
            return modelAndView;
//            return "forward:/user/edit/" + ID.toString();
        }
        // формуємо випадкове імʼя та відокремлюємо розширення файлу
        String FileToken = UUID.randomUUID().toString();
        String extension = "";
        int i = file.getOriginalFilename().lastIndexOf('.');
        if (i > 0) {
            extension = file.getOriginalFilename().toString().substring(i+1);
        }

        // Зберігаємо аватар користувача
        //TODO переносимо файл ававтару в каталог користувача
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SiteUser user = userService.findByEmail(loginedUser.getUsername());
        String UserID = user.getId().toString();


        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get((UPLOADED_FOLDER + file.getOriginalFilename()));
            Files.write(path, bytes);
            // Записали зображення в тимчасовий файл.

            // перевіряємо існування каталогу користувача
            Path move = Paths.get(USER_FOLDER + ID.toString());
            if (!Files.exists(move)) {
                Files.createDirectory(move);
            }

            File gfile = new File(UPLOADED_FOLDER + file.getOriginalFilename());
            BufferedImage image = null;
            try
            {
                image = ImageIO.read(gfile);
                String tmpString = USER_FOLDER+ID.toString()+"/avatar.png";
                move = Paths.get(tmpString);
                if (Files.exists(move)) {
                    Files.delete(move);
                }
                ImageIO.write(getScaledImage(image,100,100),
                        "png",
                        new File(tmpString));
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.println("==== IMAGE WRITE ERROR!!!!");
            }


//            move = Paths.get(USER_FOLDER+ID.toString()+"/avatar.png");
//            if (Files.exists(move)) {
//                Files.delete(move);
//            }
//            Files.move( path, move);
            //
            redirectAttributes.addFlashAttribute("message",
                    "Файл успішно завантажено як Ваш аватар. '"
                            + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }


        SiteUser tuser = userService.findUserById(ID);
        System.out.println("========= AdminUsersController  SaveUserAvatar POST. ID="+ID.toString());
        // TODO Опрацювати розмір файлу з аватаром

        // Зберегли зміни щодо аватару
        tuser.setAvatar(true);
        userService.saveUser(tuser);
        modelAndView.addObject("errorMessage", "Ваш Аватар збережено.");
        modelAndView.addObject("user", tuser);
        return modelAndView;
    }

        @RequestMapping(value="/user/edit/avatar/{ID}",  method = RequestMethod.GET)
        public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model,
                                                            @PathVariable("ID") Long ID) {
            model.addAttribute("attribute", "redirectWithRedirectPrefix");
            return new ModelAndView("forward:/user/edit/" + ID.toString(), model);
        }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     * https://stackoverflow.com/questions/16497853/scale-a-bufferedimage-the-fastest-and-easiest-way
     */
    private BufferedImage getScaledImage(BufferedImage src, int w, int h){
        int finalw = w;
        int finalh = h;
        double factor = 1.0d;
        if(src.getWidth() > src.getHeight()){
            factor = ((double)src.getHeight()/(double)src.getWidth());
            finalh = (int)(finalw * factor);
        }else{
            factor = ((double)src.getWidth()/(double)src.getHeight());
            finalw = (int)(finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }


}

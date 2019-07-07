package toloka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import toloka.Repository.UploadFileRepositore;
import toloka.Repository.UserRepository;
import toloka.model.back.UserUploadModel;
import toloka.model.security.SiteUser;
import toloka.service.UserService;
//import toloka.model.front.FrontContacts;

@Controller
public class UploadController {

    @Autowired
    UploadFileRepositore uploadfilerepositore;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepositore;

    //Save the uploaded file to this folder
    @Value("${site.tmp.directory}")
    private String UPLOADED_FOLDER;
    @Value("${site.rot.directory}")
    private String ROT_FOLDER;



    @GetMapping("/upload")
    public String GetFileuploadPage(Model model) {
        model.addAttribute("message", "");
//        model.addAttribute("file", "");
//        model.addAttribute("subject", "");
//        model.addAttribute("item", "");
//        model.addAttribute("upload", new UserUploadModel());
        System.out.println("GET /upload");
        System.out.println(UUID.randomUUID().toString());
        return "upload/upload";
    }

    @PostMapping("/upload") // //new annotation since 4.ckeditor_4.11.3.1
        public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("subject") String subject,
                                       @RequestParam("item") String item,
                                       @ModelAttribute UserUploadModel userupload,
                                       Model model,
                                       Principal principal,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Ви забули обрати файл. Будь ласка, виберіть файл.");
            model.addAttribute("subject", subject);
            return "upload/upload";
        }
        // формуємо випадкове імʼя та відокремлюємо розширення файлу
        String FileToken = UUID.randomUUID().toString();
        String extension = "";
        int i = file.getOriginalFilename().lastIndexOf('.');
        if (i > 0) {
            extension = file.getOriginalFilename().toString().substring(i+1);
        }

        //TODO переносимо файл в каталог користувача з випадковою назвою
        // знаходимо ID користувача
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        System.out.println("===== USER GetUserName");
        System.out.println(loginedUser.getUsername());
        SiteUser user = userService.findByEmail(loginedUser.getUsername());
//        user = userService.findByEmail(loginedUser.getUsername());
        String UserID = user.getId().toString();
        System.out.println("====== UserID: "+ UserID);


        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get((UPLOADED_FOLDER + file.getOriginalFilename()));
            Files.write(path, bytes);

//            Path move = Paths.get((ROT_FOLDER + FileToken + "." + extension));
            Path move = Paths.get((ROT_FOLDER + UserID));
            System.out.println("====== ROT directory =====");
            System.out.println(move.toString());
            if (!Files.exists(move)) {
                Files.createDirectory(move);
            }
            move = Paths.get(ROT_FOLDER+UserID+"/"+FileToken+"."+extension);
            System.out.println("====== ROT directory with file =====");
            System.out.println(move.toString());
            Files.move( path, move);
//            System.out.println(move.toString());
// ======================================================================

            redirectAttributes.addFlashAttribute("message",
                    "Файл успішно завантажено. '"
                            + file.getOriginalFilename() + "'");
            System.out.println("=================== Subject: " + subject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO перевіряємо дозволи користувача щодо публікації матеріалів без цензури.

        //TODO Пишемо в базу заголовок та випадкову назву файлу, прапорець цензури
        UserUploadModel UploadRec = new UserUploadModel();
        UploadRec.setFilename(file.getOriginalFilename());
        UploadRec.setFileext(extension);
        UploadRec.setFiletoken(FileToken);
        UploadRec.setSubject(subject);
        UploadRec.setItem(item);
        UploadRec.setEnable(true);
        UploadRec.setUser(user);
        uploadfilerepositore.save(UploadRec);

        return "redirect:/upload/uploadStatus";
    }

    @GetMapping("/upload/uploadStatus")
    public String uploadStatus() {
        return "/upload/uploadStatus";
    }

}

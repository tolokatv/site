package toloka.controller.blog;

//import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import toloka.model.blog.*;
import toloka.model.front.FrontContacts;
import toloka.model.security.SiteUser;
import toloka.service.*;
import toloka.utils.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static toloka.utils.service.check_user_privelidge;


@Controller
public class ItemController {

    private ItemService itemservice;
    private TextBodyService textbodyservice;
    private UserService userService;
    private BlogCategoryService blogCategoryService;
    private ItemStatusBlogService itemStatusBlogService;
    private ItemTypeService itemTypeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ItemController(ItemService itemService,
                          TextBodyService textbodyService,
                          UserService userService,
                          BlogCategoryService blogCategoryService,
                          ItemStatusBlogService itemStatusBlogService,
                          ItemTypeService itemTypeService
    ) {
        this.userService = userService;
        this.textbodyservice = textbodyService;
        this.itemservice = itemService;
        this.blogCategoryService = blogCategoryService;
        this.itemStatusBlogService = itemStatusBlogService;
        this.itemTypeService = itemTypeService;
    }

    // Клас для заповнення форми

    private class FormEdit {
        private Long id;
        // обовʼязкові параметри
        private ItemStatusBlog tItemstatus;
        private ItemCategory tItemCategory;
        private ItemType tItemType;
        private SiteUser tUser;
        // необовʼязкові параметри
        private String tTitle;
        private String tIntro;
        private String tBody;
        private String tMainMedia;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public ItemStatusBlog gettItemstatus() {
            return tItemstatus;
        }

        public void settItemstatus(ItemStatusBlog tItemstatus) {
            this.tItemstatus = tItemstatus;
        }

        public ItemCategory gettItemCategory() {
            return tItemCategory;
        }

        public void settItemCategory(ItemCategory tItemCategory) {
            this.tItemCategory = tItemCategory;
        }

        public ItemType gettItemType() {
            return tItemType;
        }

        public void settItemType(ItemType tItemType) {
            this.tItemType = tItemType;
        }

        public SiteUser gettUser() {
            return tUser;
        }

        public void settUser(SiteUser tUser) {
            this.tUser = tUser;
        }

        public String gettTitle() {
            return tTitle;
        }

        public void settTitle(String tTitle) {
            this.tTitle = tTitle;
        }

        public String gettIntro() {
            return tIntro;
        }

        public void settIntro(String tIntro) {
            this.tIntro = tIntro;
        }

        public String gettBody() {
            return tBody;
        }

        public void settBody(String tBody) {
            this.tBody = tBody;
        }

        public String gettMainMedia() {
            return tMainMedia;
        }

        public void settMainMedia(String tMainMedia) {
            this.tMainMedia = tMainMedia;
        }
    }

    // Пробуємо використати клас для форми
    // користувач працює зі своїм дописом.
    // редагування
    // адміністратор також може редагувати чужий допис
    // редактор може тільки змінити статус допису
    @RequestMapping(value="/user/item/{ID}/{itemtype}", method = RequestMethod.GET)
    public ModelAndView ItemForm (ModelAndView modelAndView,
                                  @PathVariable("ID") Long tid,
                                  @PathVariable("itemtype") String patchitemtype) {

        SiteUser currUser; // користувач в системі
        SiteUser itemUser; // власник допису
        Item currItem; // допис з яким будемо працювати

        currItem = itemservice.GetOneItem(tid);
        // TODO опрацювати створення запису (currItem != null)

        modelAndView.setViewName("/blog/user/itemedit");
        FormEdit tFormEdit = new FormEdit();
        // заповнили поточного користувача
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            // витягли користувача який увійшов до системи
            currUser = userService.findByEmail(((UserDetails) principal).getUsername());

            if ( currUser != null)  {
                if (currUser.getId().equals(currItem.getAuthor()) ) { // користувач є автором
                    // користувач є автором
                    // TODO потрібно якийсь флаг викинути
                    tFormEdit.settUser(currUser);
                } else if (
                        (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
                    ||
                        (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_EDITOR")))
                    )
                { // admin or editor
                    tFormEdit.settUser(currUser);
                    // TODO потрібно якийсь флаг викинути для опрацювання в шаблоні
                } else {
                    // таке враження що це хтось сторонній намагається редактувати чужу запис
                    // TODO опрацювати ситуацію, занести в журнал і вийти на головну.

                }

            }
        } else {
            // Користувач не увійшов до системи
            // TODO опрацювати з перенаправленням на головну сторінку.
        }

        // TODO перевірити співпадіння користувача та автора в запису.
        // Врахувати права адміна
        // якщо автор не співпадає з користувачем та не адмін, то виходимо на головну
        // і напевно виконуємо Logout



        if (tid == 0L ) {
            // створюємо пусту
            tFormEdit.setId(0L);
            tFormEdit.settItemType(itemTypeService.FindByType(patchitemtype));

        } else {
            // Є реальна запис для посту. Заповнюємо з неї
            Item item = itemservice.GetOneItem(tid);
            // TODO перевірити на результат пошуку

            tFormEdit.setId(item.getId());
            tFormEdit.settItemCategory(item.getCategory());
            tFormEdit.settItemstatus(item.getStatus());
            tFormEdit.settItemType(item.getTyperecord());
            // field tUser fill up

            tFormEdit.settIntro(item.getIntro().getBody());
            tFormEdit.settBody(item.getBody().getBody());
            tFormEdit.settMainMedia(item.getMainmedia());
        }
        //передали до форми сторінки
        // передаємо до форми категорії
        modelAndView.addObject("categorys", blogCategoryService.FindAll() );
        modelAndView.addObject("itemstatus", itemStatusBlogService.GetAllItemStatusBlog() );
        modelAndView.addObject("titemtype", patchitemtype);
        modelAndView.addObject("tMainMedia", tFormEdit.gettMainMedia());
        modelAndView.addObject("items", tFormEdit);
        return modelAndView;
    }

    //Зберігаємо запис після редагування
    @RequestMapping(value="/user/item/save", method = RequestMethod.POST)
    public String ItemSave (ModelAndView modelAndView,
                            @ModelAttribute Item item,
                             @RequestParam Map<String, String> requestParams
//                            @RequestParam("item") Item item,
//                            @RequestParam("sTitle") String title,
//                            @RequestParam("sIntroBody") String intro // , @RequestParam("title") String body
                            ) {
//        System.out.println("=========== Item id: "+item.getId());
        System.out.println("=========== Title: ");
        System.out.println("=========== Intro: ");
        item.setMainmedia(requestParams.get("myFileField"));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (userService.findByEmail(((UserDetails) principal).getUsername()) != null) {
                item.setAuthor(userService.findByEmail(((UserDetails) principal).getUsername()));
                // TODO Тут щось не зрозуміле
                modelAndView.addObject("items", item.getAuthor());
            }
            if (item.getIntro() == null) {
                TextBody tt = new TextBody();
                tt.setBody(requestParams.get("introeditor"));
                item.setIntro(tt);
            } else {
                item.getIntro().setBody(requestParams.get("introeditor"));
            }
        }
//        item.setTitle();
        // TODO Виправити тимчасове використання констант
        item.setCategory(blogCategoryService.FindByID(2L));
        item.setStatus(itemStatusBlogService.GetItemStatusBlogByID(217L));
        ItemType itemType = itemTypeService.FindByType(requestParams.get("titemtype"));
        item.setTyperecord(itemType);
        itemservice.SaveItem(item);
        return "redirect:/user/items";
    }

    @RequestMapping(value="/user/create/item/{itemtype}", method = RequestMethod.GET)
    public ModelAndView ItemCreate (ModelAndView modelAndView, @PathVariable("itemtype") String patchitemtype) {
        Item item;
        TextBody shortbody;
        TextBody body;
        ItemType itemType;
//        List<Item> listItems;
        //=================================

        modelAndView.setViewName("/blog/user/itemedit");
        item = new Item();

        // Перевіряємо користувача та структуру каталогів для цього користувача і якщо немає директорій, то створюємо їх.
        // Получаем ИД пользователя
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Якщо це користувач, який залогинівся, то перевіряємо структуру каталогів
        if (principal instanceof UserDetails) {
            item.setAuthor(userService.findByEmail(((UserDetails) principal).getUsername()));
            modelAndView.addObject("itemtype", patchitemtype);
            item.setTyperecord(itemTypeService.FindByType(patchitemtype));
            modelAndView.addObject("items", item);
            modelAndView.addObject("sid", 0L);
            modelAndView.addObject("sTitle", "");
            modelAndView.addObject("sIntroBody", "");
            modelAndView.addObject("tMainMedia", "");
            modelAndView.addObject("itemtype", patchitemtype);
        }

        return modelAndView;

    }



    // щось зберыгаэмо Навіщо?
    @ResponseBody
    @RequestMapping(value="/userupload", method = RequestMethod.POST)
    public ResponseEntity<String> rotUpload(@RequestParam("upload") MultipartFile file) {
        String myresp;
        // TODO використати шляхи для зберігання файлів із файлу конфігурації
        String userPatch = "/tmp/";
        String userFullPatch = "/tmp/";
        String fileName;
        SiteUser user;

        logger.info("======= ItemController rotimageUpload ");
        logger.info("=================== Filename:" + file.getOriginalFilename());
//        System.out.println();
//        System.out.println("=================== File length:" + file.getBytes());

//        Path currentRelativePath = Paths.get("");
//        String s = Paths.get("").toAbsolutePath().toString();
        String curPaths = Paths.get("").toAbsolutePath().toString();

        if (file.isEmpty()) {
            myresp = "{ \"uploaded\" : 0 , \"error\": {\"message\": \"Схоже, що ви не обрали файл\"}";
            logger.error(myresp);
            return new ResponseEntity(myresp, HttpStatus.OK);
        }

        // Перевіряємо користувача та структуру каталогів для цього користувача і якщо немає директорій, то створюємо їх.
        // Получаем ИД пользователя
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Якщо це користувач, який залогинівся, то перевіряємо структуру каталогів
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            user = userService.findByEmail(username);
            // создаем каталог пользователя для images
            userPatch = "/upload/"+user.getId().toString()+"/"; //+"/images/";
            userFullPatch = curPaths+"/src/main/resources/static/upload/"+user.getId().toString()+"/"; //+"/images/";
            File files = new File(userFullPatch);
            if (!files.exists()) {
                if (!files.mkdirs()) {
                    System.out.println("Щось пішло не так. Ми не змогли знайти шлях для збереження файлу. Спробуйте увійти ще раз.");
                    myresp = "{ \"uploaded\" : 0 , \"error\": {\"message\": \"Щось пішло не так. Ми не змогли знайти користувача. Спробуйте увійти ще раз.\"}";
                    return new ResponseEntity(myresp, HttpStatus.OK);
                }
            }
        }
        else {
            // схоже, що користувая не увійшов до системи.
            myresp = "{ \"uploaded\" : 0 , \"error\": {\"message\": \"Щось пішло не так. Ми не змогли знайти користувача. Спробуйте увійти ще раз.\"}";
            return new ResponseEntity(myresp, HttpStatus.OK);
        }

        // пробуємо записати файл
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
//            fileName = new String(file.getOriginalFilename());
//            fileName = fileName.replace(' ','_');
            Path path = Paths.get(userFullPatch + file.getOriginalFilename());
            Files.write(path, bytes);
            myresp = "{ \"uploaded\" : 1 , \"fileName\" : \""+file.getOriginalFilename()+ "\" , \"url\" : \""+userPatch+file.getOriginalFilename()+"\"}";
            System.out.println("Начебто все пройшло нормально. "+myresp);
            return new ResponseEntity(myresp, HttpStatus.OK);
        } catch (IOException e) {
            // обробляємо помилку збереження файлу.
            //TODO потрібно таки опрацювати помилку.
            e.printStackTrace();
            System.out.println("Щось пішло не так. Ми не змогли зберегти файл. Спробуйте увійти ще раз до системи.");
            myresp = "{ \"uploaded\" : 0 , \"error\": {\"message\": \"Щось пішло не так. Ми не змогли зберегти файл. Спробуйте увійти ще раз до системи.\"}";
            return new ResponseEntity(myresp, HttpStatus.OK);
        }
        // TODO
    }

    // Перелык постів користувача
    //    @Transactional
    @RequestMapping(value="/user/items", method = RequestMethod.GET)
    public ModelAndView ItemsList (ModelAndView modelAndView) {
        Item item;
        TextBody shortbody;
        TextBody body;
        SiteUser user = null;
        String username;
        ItemType itemtype;
//        List<Item> listItems;
        //=================================

        modelAndView.setViewName("/blog/user/useritemslist");


        // створюємо нову запис посту
//        item = new Item();
        // поточний користувач
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            user = userService.findByEmail(username);

            // тестуємо виклик сервісної функції
            if (check_user_privelidge('W',"ROT",user)) {
                System.out.println("========== test check_user_privelidge ===========");
            }
            //

            if (user != null ) {
//                item.setAuthor(user);
                modelAndView.addObject("items", itemservice.GetByAutor(user));
            } else {
                //TODO
                // А точно можна нулл передавати?
                // опрацювати параметр, коли користувача не знайдено
                modelAndView.addObject("items", null);
                return modelAndView;
            }

        }
//        else {
//            username = principal.toString();
//            System.out.println("========= ItemController  ItemsList principal username: null");
//            return modelAndView;
//        }
//
        // Створюємо тестові записи
//        body = new TextBody();
//        body.setBody("====== ROT body =========");
//        item.setBody(body);
//        shortbody = new TextBody();
//        shortbody.setBody("====== ROT shortbody =========");
//        item.setIntro(shortbody);
//        item.setTitle("РОТ title");
//        // обовʼязкові атрибути
//        item.setCategory(blogCategoryService.FindByID(2L));
//        item.setStatus(itemStatusBlogService.GetItemStatusBlogByID(217L));
//        itemtype = itemTypeService.FindByType("ROT");
//        item.setTyperecord(itemtype);
//        itemservice.SaveItem(item);

        return modelAndView;
    }

    @Transactional
    @RequestMapping(value="/user/it", method = RequestMethod.GET)
    public ModelAndView ItemsListtest (ModelAndView modelAndView) {

        System.out.println("========= ItemController  ItemsListtest GET");
        modelAndView.setViewName("/blog/test");

        System.out.println("========= ItemController  ItemsListtest END" );

        return modelAndView;
    }

    @RequestMapping(value="/user/item", method = RequestMethod.POST)
    public ModelAndView ItemEdit (ModelAndView modelAndView) {

        System.out.println("========= ItemController  ItemsListtest GET");
        modelAndView.setViewName("/blog/itemedit");
        System.out.println("========= ItemController  ItemsListtest END" );

        return modelAndView;
    }
}

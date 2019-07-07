package toloka.controller.api;

import com.google.gson.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import toloka.model.blog.Item;
import toloka.model.security.SiteUser;
import toloka.service.ItemService;
import toloka.service.UserService;

import java.util.Iterator;
import java.util.List;

@RestController
public class ApiBlogItem {

    private ItemService itemService;
    private UserService userService;

    public ApiBlogItem(ItemService itemService,UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    // структура для построения таблицы
    private class ItemData {
        private String status;
        private String title;
//        private String type;
        private String category;
        private String autor;
        private String datecreate;
        private String hits;
        private String votes;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getDatecreate() {
            return datecreate;
        }

        public void setDatecreate(String datecreate) {
            this.datecreate = datecreate;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getVotes() {
            return votes;
        }

        public void setVotes(String votes) {
            this.votes = votes;
        }
    };


    @Transactional
    @RequestMapping(value = "/api/item", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> ApiItemGET() {

        SiteUser user = null;
        Gson gson = new GsonBuilder().create();
        JsonArray jsonarray = new JsonArray();

        String username;
        List<Item> listItems = null;
        Iterator<Item> iterator;
        String result;

        System.out.println("========= ApiItemBlog  ApiItemGET GET");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
//            user = (SiteUser) principal;
//            System.out.println("========= ApiItemBlog  ApiItemGET username: "+user.getFirstName());
//             username = ((UserDetails)principal).getUsername();
            username = ((UserDetails)principal).getUsername();
            user = userService.findByEmail(username);

            System.out.println("========= ApiItemBlog  ApiItemGET login username: "+username);
        } else {
            username = principal.toString();
            System.out.println("========= ApiItemBlog  ApiItemGET principal username: null");
            System.out.println("========= ApiItemBlog  ApiItemGET GET END with user null");
            return new ResponseEntity("[]", HttpStatus.OK);
//            return new ResponseEntity(result, HttpStatus.OK);
        }

//        System.out.println("========= ApiItemBlog  ApiItemGET username: "+username+" FirstName "+user.getFirstName());

        if (user != null ) {
            listItems = itemService.GetByAutor(user);
        }


//
//        if (listItems == null) {
//            return new ResponseEntity("[]", HttpStatus.OK);
//        }
//        if (listItems.isEmpty()) {
//            return new ResponseEntity("[]", HttpStatus.OK);
//        }
//
        // є пости. Строим JSON
        ItemData itemData = new ItemData();
        System.out.println("========= ApiItemBlog  ApiItemGET build json responce");
        for (int x = 0; x < 40; x = x + 1) {
//        iterator = listItems.iterator();
//        while (iterator.hasNext()) {
//            Item item = iterator.next();
            // заполняем JSON
            itemData.setAutor(user.getFirstName()+" "+user.getLastName());
            itemData.setCategory("Категория");
            itemData.setDatecreate("Дата создания");
            itemData.setHits("1111");
            itemData.setStatus("Статус");
            itemData.setTitle ("Заголовок");
            itemData.setVotes("909090");
            JsonElement jsonElement = gson.toJsonTree(itemData);
            jsonarray.add(jsonElement);

        }
        result = gson.toJson(jsonarray);
        System.out.println("========= ApiItemBlog  ApiItemGET GET END");

        return new ResponseEntity(result, HttpStatus.OK);
    }




}

package toloka.controller.api;
import com.google.gson.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import toloka.model.Other.GsonCategoryBody;
import toloka.model.blog.ItemStatusBlog;
import toloka.model.virtual.TreeAbstract;
import toloka.service.BlogCategoryService;
import toloka.service.ItemStatusBlogService;

import java.util.Iterator;
import java.util.List;

@RestController
public class ApiItemStatusBlog {

    private ItemStatusBlogService itemStatusBlogService;

    private class ItemWeb {
        private String action;
        private String value;
        private String key;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    };

    public ApiItemStatusBlog(ItemStatusBlogService itemStatusBlogService) {
    this.itemStatusBlogService = itemStatusBlogService;
    }

    @Transactional
    @RequestMapping(value = "/api/admin/status", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> ApiItemStatusBlogDELETE(@RequestBody String jsonbody) {

        Gson gson ;
        ItemWeb gsonItemBody = new ItemWeb();

        //System.out.println("========= ApiItemStatusBlog ApiItemStatusBlogDELETE DELETE");

        gson = new Gson();
        gsonItemBody = gson.fromJson(jsonbody, ItemWeb.class);

        ItemStatusBlog itemStatusBlog = new ItemStatusBlog();
        List<ItemStatusBlog> itemStatusBloglist = itemStatusBlogService.GetOneItemStatusBlogByName(gsonItemBody.getKey());
//        System.out.println(" number elements in list: " + itemStatusBloglist.size());
        if (gsonItemBody.getValue().length() > 0 && !itemStatusBloglist.isEmpty()) {
            //List<ItemStatusBlog> itemStatusBloglist = itemStatusBlogService.GetOneItemStatusBlogByName(gsonItemBody.getKey());
            if (itemStatusBloglist.size() > 1) {
                return new ResponseEntity("[]", HttpStatus.OK);
            } else {
                Iterator<ItemStatusBlog> iterator = itemStatusBloglist.iterator();
                if (iterator.hasNext()) {
                    itemStatusBlog = iterator.next();
                    itemStatusBlogService.DeleteItemStatusBlog(itemStatusBlog);
                }
            }
            //System.out.println("========= ApiItemStatusBlog ApiItemStatusBlogDELETE DELETE END");
            return new ResponseEntity("[]", HttpStatus.OK);
        }
        return new ResponseEntity("[]", HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(value = "/api/admin/status", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> ApiItemStatusBlogChange(@RequestBody String jsonbody) {

        Gson gson ;
        ItemWeb gsonItemBody = new ItemWeb();

        //System.out.println("========= ApiItemStatusBlog ApiItemStatusBlogChange PUT");

        gson = new Gson();
        gsonItemBody = gson.fromJson(jsonbody, ItemWeb.class);

        ItemStatusBlog itemStatusBlog = new ItemStatusBlog();
        List<ItemStatusBlog> itemStatusBloglist = itemStatusBlogService.GetOneItemStatusBlogByName(gsonItemBody.getKey());
        //System.out.println(" number elements in list: " + itemStatusBloglist.size());
        if (gsonItemBody.getValue().length() > 0 && !itemStatusBloglist.isEmpty()) {
            //List<ItemStatusBlog> itemStatusBloglist = itemStatusBlogService.GetOneItemStatusBlogByName(gsonItemBody.getKey());
            if (itemStatusBloglist.size() > 1) {
                return new ResponseEntity("[]", HttpStatus.OK);
            } else {
                Iterator<ItemStatusBlog> iterator = itemStatusBloglist.iterator();
                if (iterator.hasNext()) {
                    //System.out.println(" next item");
                    itemStatusBlog = iterator.next();
                    itemStatusBlog.setName(gsonItemBody.getValue());
                    itemStatusBlogService.SaveItemStatusBlog(itemStatusBlog);
                }
            }
            //System.out.println("========= ApiItemStatusBlog  ApiItemStatusBlogChange PUT END");
            return new ResponseEntity("[]", HttpStatus.OK);
        }
        return new ResponseEntity("[]", HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/api/admin/status", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> ApiItemStatusBlogInsert (@RequestBody String jsonbody) {

        Gson gson ;
        ItemWeb gsonItemBody = new ItemWeb();

        //System.out.println("========= ApiItemStatusBlog ApiItemStatusBlogInsert POST");
        //System.out.println("Json String= " + jsonbody);

        gson = new Gson();
        gsonItemBody = gson.fromJson(jsonbody, ItemWeb.class);
        ItemStatusBlog itemStatusBlog = new ItemStatusBlog();
        if (gsonItemBody.getValue().length() > 0){
            itemStatusBlog.setName(gsonItemBody.getValue());
            itemStatusBlogService.SaveItemStatusBlog(itemStatusBlog);
        }
        //System.out.println("========= ApiItemStatusBlog  ApiItemStatusBlogInsert POST END");
        return new ResponseEntity("[]", HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/api/status", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> ApiItemStatusBlogGET() {

        //System.out.println("========= ApiItemStatusBlogGET  ApiItemStatusBlogGET GET");
        Iterator<ItemStatusBlog> iterator;
//        List<ItemStatusBlog> listCategory;
        JsonArray jsonarray = new JsonArray();
        String result;

        Gson gson = new GsonBuilder().create();
        List<ItemStatusBlog> listStatuses = itemStatusBlogService.GetAllItemStatusBlog();
        if (listStatuses.isEmpty()) {
            return new ResponseEntity("[]", HttpStatus.OK);
        }
        // есть категории. Строим JSON
        iterator = listStatuses.iterator();
        while (iterator.hasNext()) {
            JsonPrimitive element = new JsonPrimitive(iterator.next().getName());
            jsonarray.add(element);
        }
        result = gson.toJson(jsonarray);
        //System.out.println("========= ApiItemStatusBlogGET  ApiItemStatusBlogGET GET END");
        //System.out.println(result);

        return new ResponseEntity(result, HttpStatus.OK);
    }


}

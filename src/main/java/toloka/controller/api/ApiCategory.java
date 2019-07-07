package toloka.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import toloka.model.Other.GsonCategoryBody;
import toloka.model.Other.GsonRowCategory;
import toloka.model.Other.GsonRowCategoryJQW;
import toloka.model.blog.ItemCategory;
import toloka.model.blog.ItemCategoryLite;
import toloka.model.virtual.TreeAbstract;
import toloka.service.BlogCategoryService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
// GSON
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;


@RestController
public class ApiCategory {

    private class test {
        private String sss;
    }

    private BlogCategoryService categoryService;
    private String prfCategory = "jqw";

    public ApiCategory(BlogCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Transactional
    @RequestMapping(value = "/api/admin/category/move", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    //    public ResponseEntity<String> CategoryBlogChange(@RequestBody String jsonbody, @PathVariable String id) {
    public ResponseEntity<String> CategoryBlogMove(@RequestBody String jsonbody) {

        Gson gson ;
        GsonCategoryBody gsonCategoryBody = new GsonCategoryBody();
        ItemCategory what = null;
        ItemCategory where = null;
        Iterator<TreeAbstract> iterator;

        System.out.println("========= ApiCategory CategoryBlogMove POST");
        System.out.println("Json String= " + jsonbody);

        gson = new Gson();
        gsonCategoryBody = gson.fromJson(jsonbody, GsonCategoryBody.class);

        if (gsonCategoryBody.getFirstid().length() > 0) {
            what = new ItemCategory();
            what = categoryService.FindByID(Long.parseLong(gsonCategoryBody.getFirstid().replaceAll(prfCategory, "")));
            System.out.println("Что: " + what.getId().toString());
        }

        if (gsonCategoryBody.getSecondid().length() > 0) {
            where = new ItemCategory();
            where = categoryService.FindByID(Long.parseLong(gsonCategoryBody.getSecondid().replaceAll(prfCategory, "")));
            System.out.println("Куда: " + where.getId().toString());
        }

        // Перевіряємо куди переносимо - в корінь чи в інший елемент.
        if (what != null) {
            // є що переносити
            // чистимо перелік нащадків в батьківському елементі
            if (what.getParent() != null) {
                Set<TreeAbstract> childs = what.getParent().getChilds();
                iterator = childs.iterator();
                while (iterator.hasNext()) {
                    TreeAbstract setElement = iterator.next();
                    if (setElement.getId().equals(what.getId())) {
                        iterator.remove();
                        // Після видалення із переліку нащадків зберегли батьківський елемент
                    }
                }
                what.getParent().setChilds(childs);
                categoryService.SaveItem((ItemCategory) what);
                categoryService.SaveItem((ItemCategory) what.getParent());
            }
            // чистимо посилання на батьківський елемент
            // видаляємо звʼязок з батьками
            what.setParent(null);
            // зберігаємо
            categoryService.SaveItem(what);
        }

        // якщо не існує куди, то переносимо в корінь
        if (where == null) {
            // вже все готово:
            // - посилання на батьківський елемент очищено;
            // - в батьківському елементі видалили з переліку нащадків
            // зберігаємо

            // завершуємо роботу
            return new ResponseEntity("[]", HttpStatus.OK);
        } else {
            // додаємо елемент в перелік нащадків попереднього елементу
            // прописуємо посилання на попередній елемент
            categoryService.AddChild(where,what);
            // зберігаємо
//            categoryService.SaveItem(where);
        }
        categoryService.SaveItem(what);
        categoryService.SaveItem(where);


        return new ResponseEntity("[]", HttpStatus.OK);
    }



    @Transactional
    @RequestMapping(value = "/api/admin/category", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<String> CategoryBlogChange(@RequestBody String jsonbody, @PathVariable String id) {
    public ResponseEntity<String> CategoryBlogChange(@RequestBody String jsonbody) {

        Gson gson ;
        GsonCategoryBody gsonCategoryBody = new GsonCategoryBody();

        System.out.println("========= ApiCategory CategoryBlogDelete POST");
        System.out.println("Json String= " + jsonbody);

        gson = new Gson();
        //gson.toJson(jsonbody);
        gsonCategoryBody = gson.fromJson(jsonbody, GsonCategoryBody.class);


//        categoryService.RemoveCategory(categoryService.FindByID(Long.parseLong(gsonCategoryBody.replaceAll(prfCategory, ""))));

        ItemCategory r1 = categoryService.FindByID(Long.parseLong(gsonCategoryBody.getFirstid().replaceAll(prfCategory, "")));
//                new ItemCategory();
        r1.setName(gsonCategoryBody.getValue());
//        r1.setEnable(true);

        categoryService.SaveItem(r1);
        System.out.println("========= BlogCategoryController  1");


        System.out.println("========= ApiCategory CategoryBlogDelete END");
        return new ResponseEntity("[]", HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(value = "/api/admin/category", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<String> CategoryBlogDelete(@RequestBody String jsonbody, @PathVariable String id) {
    public ResponseEntity<String> CategoryBlogCreate(@RequestBody String jsonbody) {

        Gson gson ;
        GsonCategoryBody gsonCategoryBody = new GsonCategoryBody();

        System.out.println("========= ApiCategory CategoryBlogDelete POST");
        System.out.println("Json String= " + jsonbody);

        gson = new Gson();
        //gson.toJson(jsonbody);
        gsonCategoryBody = gson.fromJson(jsonbody, GsonCategoryBody.class);

        ItemCategory r1 = new ItemCategory();
        r1.setName(gsonCategoryBody.getValue());
        r1.setEnable(true);
        categoryService.SaveItem(r1);
        System.out.println("========= BlogCategoryController  1");


        System.out.println("========= ApiCategory CategoryBlogDelete END");
        return new ResponseEntity("[]", HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(value = "/api/admin/category/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> CategoryBlogDelete(@RequestBody String jsonbody, @PathVariable String id) {

        Gson gson ;

        System.out.println("========= ApiCategory CategoryBlogDelete DELETE");
        System.out.println("Json String= " + jsonbody);

        gson = new Gson();
        gson.toJson(jsonbody);

        categoryService.RemoveCategory(categoryService.FindByID(Long.parseLong(id.replaceAll(prfCategory, ""))));

        System.out.println("========= ApiCategory CategoryBlogDelete END");

        return new ResponseEntity("{\"===================\"}", HttpStatus.OK);
    }
    // преобразуем набор подчиненных в json
    public JsonArray GetGsonArrJQW (Set<TreeAbstract> itemCategory) {
        GsonRowCategoryJQW gsonRowCategory = new GsonRowCategoryJQW();
        Iterator<TreeAbstract> iterator;
        Gson gson ;
        JsonArray jsonarray = new JsonArray();

        if (itemCategory.isEmpty()) {
            return null;
        } else {
            gson = new Gson();
            // есть элементы в наборе
            iterator = itemCategory.iterator();
            while (iterator.hasNext()) {
                TreeAbstract curCategoryElement = iterator.next();
//                gsonRowCategory.setId("jqw700"+Long.toString(curCategoryElement.getId()));
                gsonRowCategory.setId(prfCategory+Long.toString(curCategoryElement.getId()));
                gsonRowCategory.setLabel(curCategoryElement.getName());
                gsonRowCategory.setItems(gson.fromJson(
                        gson.toJson(GetGsonArrJQW((Set<TreeAbstract>)categoryService.FindByID(curCategoryElement.getId()).getChilds()))
                                , GsonRowCategoryJQW[].class));
                JsonElement jsonElement = gson.toJsonTree(gsonRowCategory);
                jsonarray.add(jsonElement);
            }
        }
        return jsonarray;
    }
    @Transactional
    @RequestMapping(value = "/api/category", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> AdminGetCategoryTreeJQW() {

        System.out.println("========= ApiCategory  AdminGetCategoryRoot GET");
        Iterator<ItemCategory> iterator;
        List<ItemCategory> listCategory;
        GsonRowCategoryJQW gsrow;
        JsonArray jsonarray = new JsonArray();
        String result;

        // Create GSON
        Gson gson = new GsonBuilder().create();
//        ObjectMapper mapper = new ObjectMapper();
        listCategory = categoryService.GetRoot();
        if (listCategory.isEmpty()) {
            return new ResponseEntity("[]", HttpStatus.OK);
        }
        // есть категории. Строим JSON
        iterator = listCategory.iterator();
        while (iterator.hasNext()) {
            ItemCategory setElement = iterator.next();

            if (setElement.getChilds().isEmpty()) {
                gsrow = new GsonRowCategoryJQW(prfCategory+Long.toString(setElement.getId()),setElement .getName(),null);
            } else {
                JsonArray aaa = new JsonArray();
                gsrow = new GsonRowCategoryJQW(
                        prfCategory+Long.toString(setElement.getId()),
                        setElement.getName(),
                        gson.fromJson(gson.toJson(GetGsonArrJQW((Set<TreeAbstract>)categoryService.FindByID(setElement.getId()).getChilds())), GsonRowCategoryJQW[].class));
            }
            JsonElement jsonElement = gson.toJsonTree(gsrow);
            jsonarray.add(jsonElement);
        }
        System.out.println("========= ApiCategory  AdminGetCategory END");
        result = gson.toJson(jsonarray);
//        System.out.println(result);

        return new ResponseEntity(result, HttpStatus.OK);
    }

} // END CLASS

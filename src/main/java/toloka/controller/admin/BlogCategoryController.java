// https://www.jstree.com
// https://www.jqwidgets.com/populating-jquery-tree-with-json-data/?fbclid=IwAR0VKgbrUNMXl8oSy5df1R8Ec_7CFNxE9coto9IyPgN7sqohcowcBST0lPo

package toloka.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import toloka.model.blog.ItemCategory;
import toloka.model.security.SiteUser;
import toloka.service.BlogCategoryService;

import java.util.List;
import java.util.Set;

@Controller
public class BlogCategoryController {

    BlogCategoryService categoryService;

    public BlogCategoryController(BlogCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Transactional
    @RequestMapping(value="/admin/base", method = RequestMethod.GET)
    public ModelAndView GetAdminCategory (ModelAndView modelAndView) {
        // Заповнюємо структуру категорій для відображення.
//        Set<ItemCategory> rootcatgory = categoryService.GetRootCategory();
//        List<SiteUser> users;
        System.out.println("========= BlogCategoryController  GetAdminCategory GET");
//        categoryService = new BlogCategoryService();

        ItemCategory r1 = new ItemCategory();
        r1.setName("r1");
        r1.setEnable(true);
        System.out.println("========= BlogCategoryController  1");

        ItemCategory r2 = new ItemCategory();
        r2.setName("r2");
        r2.setEnable(true);
        System.out.println("========= BlogCategoryController  2");

        ItemCategory c1 = new ItemCategory();
        c1.setName("c1");
        c1.setEnable(true);
        System.out.println("========= BlogCategoryController  ckeditor_4.11.3.1");

        ItemCategory c2 = new ItemCategory();
        c2.setName("c2");
        c2.setEnable(true);
        System.out.println("========= BlogCategoryController  4");

        ItemCategory c3 = new ItemCategory();
        c3.setName("c3");
        c3.setEnable(true);
        System.out.println("========= BlogCategoryController  4");

        categoryService.AddChild(r1,c1);
        categoryService.AddChild(r1,c2);
        categoryService.AddChild(r2,c3);
        System.out.println("========= BlogCategoryController  5");
        ItemCategory c4 = categoryService.AddChild(r2,"Long chain",true);
        ItemCategory c5 = categoryService.AddChild(c4,"Long chain. End",true);
        categoryService.SaveItem(r1);
        categoryService.SaveItem(r2);
        System.out.println("========= BlogCategoryController  6");
        categoryService.SaveItem(c1);
        categoryService.SaveItem(c2);
        categoryService.SaveItem(c3);
        System.out.println("========= BlogCategoryController  7 "+c3.getId() + " parent " + c3.getParent().getId());
//        categoryService.RemoveCategory(r2);
        System.out.println("========= BlogCategoryController  8");

        modelAndView.setViewName("/admin/test");
//        users = userService.GetUserList();
//        modelAndView.addObject("users", users);


    return modelAndView;
    }

    @Transactional
    @RequestMapping(value="/admin/categoryblog", method = RequestMethod.GET)
    public ModelAndView AdminCategoryCreate (ModelAndView modelAndView) {

//        modelAndView.setViewName("/admin/adminitems");
        modelAndView.setViewName("/admin/test");
//        modelAndView.setViewName("/admin/admincategoryblog");

        System.out.println("========= BlogCategoryController  AdminCategoryCreate GET");
//
        System.out.println("========= BlogCategoryController  AdminCategoryCreate END" );

        return modelAndView;
    }




}


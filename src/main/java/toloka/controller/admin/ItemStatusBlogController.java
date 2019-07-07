package toloka.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import toloka.model.blog.ItemCategory;
import toloka.model.security.SiteUser;
import toloka.service.BlogCategoryService;

@Controller
public class ItemStatusBlogController {

    @Transactional
    @RequestMapping(value="/admin/itemstatusblog", method = RequestMethod.GET)
    public ModelAndView AdminItem (ModelAndView modelAndView) {
        modelAndView.setViewName("/admin/itemstatusblogadmin");
        return modelAndView;
    }
}

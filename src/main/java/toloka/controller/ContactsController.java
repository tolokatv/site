package toloka.controller;

/* template https://spring.io/guides/gs/handling-form-submission/ */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toloka.Repository.FrontContactRepositore;
import toloka.model.front.FrontContacts;

import java.time.LocalDateTime;

@Controller
public class ContactsController {
//    public FrontContacts frontcontacts;

    @Autowired
    FrontContactRepositore front_contactRepositore;

    @GetMapping("/contact")
    public String GetContactPage(Model model) {
        model.addAttribute("message", "");
        model.addAttribute("contact", new FrontContacts());
        return "/contact";
    }

    @PostMapping("/contact")
    public String ContactSubmit(@ModelAttribute FrontContacts frontcontacts, Model model) {
        frontcontacts.setDateTime(LocalDateTime.now());
        front_contactRepositore.save(frontcontacts);
        model.addAttribute("message", "Шановний, "+ frontcontacts.getName()
            + "!<br/>Ваше повідомлення доставлено і найближчим часом ми його розглянемо.<br/>"
            + "<br/>З Повагою,<br/>Студія \"Толока Оболонь\"<br/>");
        model.addAttribute("contact", new FrontContacts());
        return "/contact";
    }
}

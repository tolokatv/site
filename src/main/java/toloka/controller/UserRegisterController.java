package toloka.controller;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toloka.Repository.PrivilegeRepository;
import toloka.Repository.RoleRepository;
import toloka.Repository.UserRepository;
import toloka.model.security.Privilege;
import toloka.model.security.SiteUser;
import toloka.model.security.Role;
import toloka.service.EmailService;
import toloka.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
//import org.hibernate.validator.constraints.NotEmpty;
import java.util.*;

//import javax.validation.Valid;

@Controller
public class UserRegisterController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public UserRegisterController(BCryptPasswordEncoder bCryptPasswordEncoder,
                                  UserService userService,
                                  EmailService emailService) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    // Return registration form template
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, SiteUser user){
        System.out.println("==================== UserRegisterController Register GET");
//        user = new SiteUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("security/register");
        return modelAndView;
    }

    // Process form input data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView,
                                                @Valid SiteUser user,
                                                BindingResult bindingResult,
                                                HttpServletRequest request) {
//        public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid SiteUser user, BindingResult bindingResult, HttpServletRequest request) {
        modelAndView.setViewName("security/register");

        // Lookup user in database by e-mail
        SiteUser userExists = userService.findByEmail(user.getUsermail());

//        System.out.println("==== 1 =====");

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage",
                    "Користувач з е-mail " + user.getUsermail()
                            + " вже зареєстрований."
                            + "Спробуйте іншу пошту, або відновить пароль.");
            modelAndView.setViewName("security/register");
            bindingResult.reject("usermail");
//            modelAndView.addObject("alreadyRegisteredMessage", "Упс! Ця поштова адреса вже використовується.");
//            System.out.println("==== 2 ===== завершили опрацювання пошти: є в базі.");
        }

        if (bindingResult.hasErrors()) {
//            System.out.println("==== 3 ====");
            bindingResult.reject("errorMessage");
            modelAndView.setViewName("security/register");

        } else { // new user so we create user and send confirmation e-mail
//            System.out.println("==================== UserRegisterController Register Post: Увійшли до збереження користувача.");
            // Disable user until they click on confirmation link in email
            user.setEnabled(false);
            // Generate random 36-character string token for confirmation link
            user.setConfirmationToken(UUID.randomUUID().toString());
            user.setPassword(null);
            userService.saveUser(user);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getUsermail());
            registrationEmail.setSubject("Підтвердження реєстрації на сайті toloka.kiev.ua");
            registrationEmail.setText("Підтвердіть реєстрацію на сайті за посиланням:\n"
                    + appUrl + "/confirm?token=" + user.getConfirmationToken());
            registrationEmail.setFrom("noreply@toloka.kiev.ua");
            emailService.sendEmail(registrationEmail);
            modelAndView.addObject("confirmationMessage", "Поштове повідомлення на підтвердження реєстрації надіслано на " + user.getUsermail());
//            System.out.println("==== 5 ===== End send mail.");
        }
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    //--------------------------------------------------------
    // Process confirmation link
    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        SiteUser user = userService.findByConfirmationToken(token);
        System.out.println("==== Увійшли до showConfirmationPage ===== ");
        if (user == null) { // No token found in DB
            modelAndView.addObject("invalidToken",
                    "Упс! Це посилання застаріло :(");
        } else { // Token found
            modelAndView.addObject("confirmationToken",
                    user.getConfirmationToken());
        }
        modelAndView.setViewName("security/confirm");
        return modelAndView;
    }

    // Process confirmation link
//    @Override
    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    @Transactional
    public ModelAndView processConfirmationForm(ModelAndView modelAndView,
                                                BindingResult bindingResult,
                                                @RequestParam Map<String, String> requestParams,
                                                RedirectAttributes redir) {
//    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {
//        System.out.println("==== Увійшли до processConfirmationForm ===== ");
        modelAndView.setViewName("security/confirm");
//        System.out.println("==== 1 =====");
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(requestParams.get("password"));
        if (strength.getScore() < 3) {
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage",
                    "Ваш пароль занадто простий. Спробуйте ще раз.");
            modelAndView.setViewName("redirect:confirm?token="
                    + requestParams.get("token"));
            return modelAndView;
        }
        // Find the user associated with the reset token
//        System.out.println("==== 2 =====");
        SiteUser user = userService.findByConfirmationToken(
                requestParams.get("token"));
        // Set new password
//        System.out.println("==== 3 =====");
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
        // Set user to enabled
        System.out.println("==== 4 =====");
        user.setEnabled(true);
        System.out.println("==== 5 =====");
        user.setConfirmationToken("");

//        Privilege readPrivilege     = createPrivilegeIfNotFound("READ_PRIVILEGE");
        System.out.println("==== 6 =====");
//        Role roles = createRoleIfNotFound("ROLE_USER", Arrays.asList(createPrivilegeIfNotFound("READ_PRIVILEGE")));
        System.out.println("==== 7 =====");
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
//        System.out.println("====== Roles name:" + rolesuser.getName());
//        setUserRole (user, Arrays.asList(rolesuser));
//        setUserRole(user, Collections.singleton(rolesuser));
        // set role for user
        System.out.println("==== 8 =====");
        Role rolesuser = roleRepository.findByName("ROLE_USER");
        Collection<Role> r1 = user.getRoles();
        r1.add(rolesuser);
        user.setRoles(r1);
        // Save user
        userService.saveUser(user);
        System.out.println("==== 9 =====");
//        SiteUser u1 = userService.findByEmail(uem);
//        Role rolesuser = roleRepository.findByName("ROLE_USER");
//        Collection<Role> r1 = u1.getRoles();
//        r1.add(rolesuser);
//        u1.setRoles(r1);
//        System.out.println("==== 10 =====");
//        userService.saveUser(u1);
//        System.out.println("==== 11 =====");
        modelAndView.addObject("successMessage", "Ваш пароль встановлено!");
        System.out.println("==== Return from processConfirmationForm =====");
        return modelAndView;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    public void setUserRole(
            SiteUser ruser, Collection<Role> ru) {
        System.out.println("====== Roles Collection:" + ru);
        ruser.setRoles(ru);
//        return;
    }
    @RequestMapping(value="/user/gen/{psw}", method = RequestMethod.GET )
    public ModelAndView ShowPsw(ModelAndView modelAndView, @PathVariable("psw") String psw) {
        System.out.println("==== PSW: "+ psw);
        System.out.println(bCryptPasswordEncoder.encode(psw));
        System.out.println("==== PSW: END");
        return modelAndView;
    }
}

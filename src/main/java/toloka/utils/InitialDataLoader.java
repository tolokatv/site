package toloka.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toloka.Repository.PrivilegeRepository;
import toloka.Repository.RoleRepository;
import toloka.Repository.UserRepository;
import toloka.model.security.Privilege;
import toloka.model.security.Role;
import toloka.model.security.SiteUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege     = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege blogPrivilege   = createPrivilegeIfNotFound("BLOG_PRIVILEGE");
        Privilege writePrivilege    = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege uploadPrivilege   = createPrivilegeIfNotFound("UPLOAD_PRIVILEGE");
        Privilege downloadPrivilege = createPrivilegeIfNotFound("DOWNLOAD_PRIVILEGE");
        Privilege configPrivilege   = createPrivilegeIfNotFound("CONFIG_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList (readPrivilege, writePrivilege, uploadPrivilege, downloadPrivilege, configPrivilege);
        List<Privilege> editorPrivileges = Arrays.asList(readPrivilege, writePrivilege, downloadPrivilege);
        List<Privilege> userPrivileges = Arrays.asList(readPrivilege, blogPrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_EDITOR", editorPrivileges);
//        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        Role userRole = roleRepository.findByName("ROLE_USER");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role editorRole = roleRepository.findByName("ROLE_EDITOR");

        createUserifNotFound("admin@toloka.kiev.ua", "Admin Admin", "admin", Arrays.asList(adminRole), true);
        createUserifNotFound("user@toloka.kiev.ua", "User User", "user", Arrays.asList(userRole), false);
        createUserifNotFound("editor@toloka.kiev.ua", "Editor Editor", "editor", Arrays.asList(editorRole), true);

        alreadySetup = true;
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
    public SiteUser createUserifNotFound(
            String email,
            String username,
            String psw,
            Collection<Role> roles,
            Boolean en){

        SiteUser user = userRepository.findByUsermail(email);
        if (user == null) {
            user = new SiteUser();
            user.setUsermail(email);
//            user.getUsermail(username);
            user.setPassword(passwordEncoder.encode(psw));
            user.setRoles(roles);
            user.setEnabled(en);
            user.setFirstName("FN");
            user.setLastName("SN");
            userRepository.save(user);
        }
         return user;
    }
}

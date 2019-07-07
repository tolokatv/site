package toloka.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import toloka.Repository.PrivilegeRepository;
import toloka.Repository.RoleRepository;
import toloka.Repository.UserRepository;
import toloka.model.security.Role;
import toloka.model.security.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository appUserRepository;

    @Autowired
    private RoleRepository appRoleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginusermail) throws UsernameNotFoundException {
//        User appUser = this.appUserRepository.findByUsermail(usermail);
//        System.out.println("====== class UserDetailsServiceImpl loadUserByUsername: User input mail " + loginusermail);
        SiteUser appUser = appUserRepository.findByUsermail(loginusermail);
        if (appUser == null) {
            System.out.println("User not found! " + loginusermail);
            throw new UsernameNotFoundException("=== UserDetailsServiceImpl loadUserByUsername " + loginusermail + " Користувача не знайдено в базі.");
        }

//        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : appUser.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(appUser.getUsermail(), appUser.getPassword(), grantedAuthorities);

//
//        List<String> roleNames = this.appRoleRepository.findAll(); // берем роли пользователя
//
//        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//        if (roleNames != null) {
//            for (String role : roleNames) {
//                // ROLE_USER, ROLE_ADMIN,..
//                GrantedAuthority authority = new SimpleGrantedAuthority(role);
//                grantList.add(authority);
//            }
    }

//        UserDetails userDetails = (UserDetails) new User(SiteUser.getUsermail(), //
//                appUser.getEncrytedPassword(), grantList);
//
//        return userDetails;
//    }
}

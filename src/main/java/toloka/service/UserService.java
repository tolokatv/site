package toloka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toloka.model.security.SiteUser;
import toloka.Repository.UserRepository;

import java.util.List;


@Service("UserService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SiteUser findByEmail(String un) {

        return userRepository.findByUsermail(un);
    }

    public SiteUser findUserById(Long uid) {
        return userRepository.getOne(uid);
    }

    public SiteUser findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    public void saveUser(SiteUser user) {
        userRepository.save(user);
    }

    public List<SiteUser> GetUserList() {
        return userRepository.findAll();
    }

}

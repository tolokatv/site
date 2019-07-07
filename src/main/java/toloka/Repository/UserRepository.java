package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import toloka.model.security.SiteUser;

import java.util.List;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUsermail(String usermail);
    SiteUser findByConfirmationToken(String confirmationToken);
//    SiteUser findById(Long id);

    @Override
    @Transactional
    List<SiteUser> findAll();

}

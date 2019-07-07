package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toloka.model.security.Privilege;
import toloka.model.security.SiteUser;
//import toloka.model.security.Role;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
    List<Privilege> findAll();
}

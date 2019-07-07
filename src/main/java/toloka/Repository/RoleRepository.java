package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toloka.model.security.Role;
//import toloka.model.security.SiteUser;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
    List<Role> findAll();
}
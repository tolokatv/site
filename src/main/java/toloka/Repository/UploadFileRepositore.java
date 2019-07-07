package toloka.Repository;

import java.util.List;
import toloka.model.back.UserUploadModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import toloka.model.security.SiteUser;

@Repository
public interface UploadFileRepositore extends CrudRepository<UserUploadModel, Long>  {
    public List<UserUploadModel> findAll();
    public UserUploadModel findByid(Long Id);
//    public List<UserUploadModel> findByowner(SiteUser user);
}

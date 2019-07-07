package toloka.Repository;

import java.util.List;
import toloka.model.front.FrontContacts;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.jpa.repository.CrudRepository;

@Repository
//@Repository("contactRepository")
//public interface FrontContactRepositore extends CrudRepository<FrontContacts, Long> {
public interface FrontContactRepositore extends CrudRepository<FrontContacts, Long> {

//        @Override
        public List<FrontContacts> findAll();
        public FrontContacts findByid(Long Id);

}



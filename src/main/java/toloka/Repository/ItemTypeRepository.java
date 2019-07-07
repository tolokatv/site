package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toloka.model.blog.ItemType;

import java.util.List;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    ItemType findByName(String name);
    List<ItemType> findAll();
}

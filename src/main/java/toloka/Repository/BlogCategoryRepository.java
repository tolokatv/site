package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toloka.model.blog.ItemCategory;
//import toloka.model.blog.ItemCategoryLite;

import java.util.List;

//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import java.util.Set;

public interface BlogCategoryRepository extends JpaRepository<ItemCategory, Long> {

    public static final String FIND_PROJECTS = "select * from getcategoryroot";

    @Query(value = FIND_PROJECTS, nativeQuery = true)
    public List<ItemCategory> FindRoot();
    public List<ItemCategory> findByParentIsNull();

}

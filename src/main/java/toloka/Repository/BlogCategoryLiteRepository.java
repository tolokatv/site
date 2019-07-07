package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toloka.model.blog.ItemCategoryLite;

import java.util.List;

public interface BlogCategoryLiteRepository  extends JpaRepository<ItemCategoryLite, Long> {


//    SELECT id, name,parent_id FROM itemcategorys ORDER BY parent_id DESC ;
//    public static final String FIND_PROJECTS = "SELECT id, name, parent_id FROM itemcategorys ORDER BY parent_id DESC";
    public static final String FIND_PROJECTS = "select id,name,parent AS parent from getcategoryroot";

    @Query(value = FIND_PROJECTS, nativeQuery = true)
    public List<ItemCategoryLite> FindLiteRoot();

}

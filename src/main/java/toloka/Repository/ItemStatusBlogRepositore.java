package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toloka.model.blog.ItemCategory;
import toloka.model.blog.ItemStatusBlog;

import java.util.List;

public interface ItemStatusBlogRepositore extends JpaRepository<ItemStatusBlog, Long> {
    public static final String FIND_ITEMS = "select * from itemstatus WHERE name LIKE ? ;";

    @Query(value = FIND_ITEMS, nativeQuery = true)
    public List<ItemStatusBlog> findAllByName(String name);

}

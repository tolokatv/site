package toloka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import toloka.model.blog.Item;
import toloka.model.blog.ItemCategory;
import toloka.model.blog.ItemStatusBlog;
import toloka.model.security.SiteUser;

import java.util.List;

public interface ItemRepositore extends JpaRepository<Item, Long> {
//    @Override
    @Transactional
    List<Item> findByAuthor(SiteUser user);
//    List<Item> findByCategory(ItemCategory category);
//    List<Item> findByStatus(ItemStatusBlog status);
    List<Item> findByTop(boolean top);
    List<Item> findAll();
//    List<Item> findByAutorByCategory(SiteUser user, ItemCategory category);

}

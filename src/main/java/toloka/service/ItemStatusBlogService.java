package toloka.service;
import org.springframework.stereotype.Service;
import toloka.Repository.ItemStatusBlogRepositore;
import toloka.model.blog.ItemStatusBlog;

import java.util.List;

@Service("ItemStatusBlogService")
public class ItemStatusBlogService {

    private ItemStatusBlogRepositore itemStatisBlogRepositore;

    public ItemStatusBlogService(ItemStatusBlogRepositore itemStatisBlogRepositore) {
        this.itemStatisBlogRepositore = itemStatisBlogRepositore;
    }

    public void SaveItemStatusBlog (ItemStatusBlog itemStatusBlog) {
        itemStatisBlogRepositore.save(itemStatusBlog);
        return;
    }

    public ItemStatusBlog GetItemStatusBlogByID (Long id) {
        return itemStatisBlogRepositore.getOne(id);
    }

    public List<ItemStatusBlog> GetAllItemStatusBlog () {
        return itemStatisBlogRepositore.findAll();
    }

    public List<ItemStatusBlog> GetOneItemStatusBlogByName (String name) {
        return itemStatisBlogRepositore.findAllByName(name);
    }

    public void DeleteItemStatusBlog (ItemStatusBlog isb) {
        itemStatisBlogRepositore.delete(isb);
    }
}

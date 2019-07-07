package toloka.service;

import org.springframework.stereotype.Service;
import toloka.Repository.ItemRepositore;
import toloka.model.blog.Item;
import toloka.model.security.SiteUser;

import java.util.List;

@Service("ItemService")
public class ItemService {

    private ItemRepositore itemRepositore;


    public ItemService(ItemRepositore ir) {
        this.itemRepositore = ir;
    }

    public void SaveItem(Item item) {
        itemRepositore.save(item);
        return;
    }

    public Item GetOneItem( Long id ) {
            return itemRepositore.getOne(id );
    }

    public List<Item> GetByAutor(SiteUser user){
        return itemRepositore.findByAuthor(user);
    }


}

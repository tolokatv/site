package toloka.service;

import org.springframework.stereotype.Service;
import toloka.Repository.ItemTypeRepository;
import toloka.model.blog.ItemType;

@Service("ItemTypeService")
public class ItemTypeService {
    ItemTypeRepository itemTypeRepository;

    public ItemTypeService (ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    public ItemType FindByType(String name) { return itemTypeRepository.findByName(name); }

}

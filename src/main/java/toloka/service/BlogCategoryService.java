package toloka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toloka.Repository.BlogCategoryLiteRepository;
import toloka.Repository.BlogCategoryRepository;
import toloka.model.blog.ItemCategory;
import toloka.model.blog.ItemCategoryLite;
import toloka.model.virtual.TreeAbstract;

import java.util.Iterator;
import java.util.List;
import java.util.Set;



@Service("BlogCategoryService")
public class BlogCategoryService {

    private BlogCategoryRepository categoryRepository;
    private BlogCategoryLiteRepository categoryLiteRepository;


    public BlogCategoryService(BlogCategoryRepository cr, BlogCategoryLiteRepository clr) {
        this.categoryRepository = cr;
        this.categoryLiteRepository = clr;
    }

    public void SaveItem(ItemCategory c1) {
//        c1.setParent(this);
        categoryRepository.save(c1);
        return;
    }

    public void AddChild(ItemCategory parent, ItemCategory child) {
        child.setParent(parent);
        parent.getChilds().add(child);
    }

    public ItemCategory AddChild(ItemCategory parent, String name, boolean enable) {
        ItemCategory child = new ItemCategory();
        child.setName(name);
        child.setParent(parent);
        child.setEnable(enable);
        categoryRepository.save(child);
        parent.getChilds().add(child);
        return child;
    }

    public void RemoveCategory(ItemCategory curr) {

        Iterator<TreeAbstract> iterator;

        System.out.println("========= BlogCategoryService RemoveCategory  1");
        int sizeList = curr.getChilds().size();
        if (sizeList == 0) {
            System.out.println("========= BlogCategoryService RemoveCategory Пустий список");
        } else {
            System.out.println("========= BlogCategoryService RemoveCategory Є елементи: " + sizeList);
        }
        //Видаляємо всіх нащадків
        System.out.println("========= BlogCategoryService RemoveCategory  2");
        System.out.println("========= BlogCategoryService RemoveCategory size curr:" + sizeList + " curr ID:" + curr.getId());
        if (curr.getChilds().size() != 0) {
            Set<TreeAbstract> setChild = curr.getChilds();
            iterator = setChild.iterator();
            while (iterator.hasNext()) {
//                System.out.println("Service RemoveCategory");
                ItemCategory ttt = (ItemCategory) iterator.next();
                iterator.remove();
//                System.out.println("Service RemoveCategory 1");
                RemoveCategory(ttt);
//                System.out.println("Service RemoveCategory 2");
            }
        }
        System.out.println("========= BlogCategoryService RemoveCategory  ckeditor_4.11.3.1");
        // видаляємо звʼязок з батьками
        if (curr.getParent() != null) {
            Set<TreeAbstract> childs = curr.getParent().getChilds();
            iterator = curr.getParent().getChilds().iterator();
            while (iterator.hasNext()) {
                TreeAbstract setElement = iterator.next();
                if (setElement.getId().equals(curr.getId())) {
                    iterator.remove();
                }
            }
            categoryRepository.delete(curr);
        } else {
            categoryRepository.delete(curr);
        }
//        categoryRepository.delete(curr);
        System.out.println("========= BlogCategoryService RemoveCategory  END");
    }


    public ItemCategory FindByID(Long ID) {
        return categoryRepository.getOne(ID);
    }

    public List<ItemCategoryLite> FindRootLite () {
        return categoryLiteRepository.FindLiteRoot();
//        return categoryRepository.findAll();
    }

    public List<ItemCategory> FindAll () {
        return categoryRepository.findAll();
//        return categoryRepository.findAll();
    }

    public ItemCategory GetOne(Long id) {
        return categoryRepository.getOne(id);
    }

    public ItemCategoryLite GetOneLite(Long id) {
        return categoryLiteRepository.getOne(id);
    }

    public List<ItemCategory> GetRoot() {return categoryRepository.findByParentIsNull(); }
}

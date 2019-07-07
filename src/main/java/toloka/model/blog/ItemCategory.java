package toloka.model.blog;


import toloka.model.virtual.TreeAbstract;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CATEGORY")
@Table(name = "itemcategorys")
public class ItemCategory extends TreeAbstract {

    public boolean enable;

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

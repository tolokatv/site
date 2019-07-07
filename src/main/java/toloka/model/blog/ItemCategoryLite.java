

package toloka.model.blog;

import toloka.model.virtual.TreeAbstract;

import javax.persistence.*;

@Entity
@Table(name = "itemcategorys")
public class ItemCategoryLite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String text = null;

//    @ManyToOne(targetEntity = TreeAbstract.class, optional=true)
//    @JoinColumn(name="PARENT_ID", nullable=true)
    private Long parent = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return text;
    }

    public void setName(String name) {
        this.text = name;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}

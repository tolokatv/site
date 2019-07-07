package toloka.model.virtual;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="REC_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("TreeAbstract")
@Table(name = "itemcategorys")
public abstract class TreeAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="OWN_ID")
//    private Long id = null;
    private Long id;

//  @Column(name="NAME")
    private String name = null;

    @ManyToOne(targetEntity = TreeAbstract.class, optional=true)
    @JoinColumn(nullable=true)
    private TreeAbstract parent = null;

    @OneToMany
    private Set<TreeAbstract> childs = new HashSet<TreeAbstract>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeAbstract getParent() {
        return parent;
    }

    public void setParent(TreeAbstract parent) {
        this.parent = parent;
    }

    public Set<TreeAbstract> getChilds() {
        return childs;
    }

    public void setChilds(Set<TreeAbstract> childs) {
        this.childs = childs;
    }
}

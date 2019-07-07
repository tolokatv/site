package toloka.model.blog;

import javax.persistence.*;

@Entity
@Table(name = "itemstatus")
public class ItemStatusBlog {
    // Стан матеріалу (чернетка, готовий, Відхилено, дозволено показати на сайті, заблоковано
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

//    public ItemStatusBlog(String name) {
//
//    }
//
//    public ItemStatusBlog(String name) {
//        this.name = name;
//    }

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
}

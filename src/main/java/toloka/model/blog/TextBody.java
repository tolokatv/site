package toloka.model.blog;

import javax.persistence.*;

@Entity //(name = "TextBody")
@Table(name = "textbody")
public class TextBody {
    // Текстова частина будь якого матеріалу - посту, статті, коментаря, тощо.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @JoinColumn(name = "item_id")
//    @OneToOne //(mappedBy = "textbody")
//    private Item item;
    @Column(columnDefinition = "text", nullable = false)
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Item getItem() {
//        return item;
//    }
//
//    public void setItem(Item item) {
//        this.item = item;
//    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

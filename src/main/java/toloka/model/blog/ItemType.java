package toloka.model.blog;
// Тип запису:
// пост в блозі
// пост на сайті
// реальне оболонське телебачення
// коментар


import javax.persistence.*;

@Entity (name = "ItemType")
@Table(name = "itemtype")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean interective;    // true - можна обирати в діалозі (стаття, блог, РОТ).
                                    // false - призначається автоматично (коментар, голосування)


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

    public boolean isInterective() {
        return interective;
    }

    public void setInterective(boolean interective) {
        this.interective = interective;
    }
}

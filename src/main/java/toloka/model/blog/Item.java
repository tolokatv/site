package toloka.model.blog;

import toloka.model.security.SiteUser;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Items")
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;                   // Назва матеріалу
    //@OneToOne(cascade = CascadeType.ALL)
//    @OneToOne(optional = false, cascade = CascadeType.ALL)
//    @JoinColumn(name = "textbody_id")
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_intro")
    private TextBody intro;                 // короткий зміст матеріалу підготовлений для показу на сайті
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_body")
    private TextBody body;                  // Матеріал підготовлений для показу на сайті
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_author")
    private SiteUser author;                // Хто автор матеріалу
    private String mainmedia;               // посилання на головну ілюстрацію матеріалу для відображення
                // це може бути шлях до ілюстрації матеріалу, що відображається на головній або в категорії
                // чи в категорії, чи посилання для відео для РОТ
    private boolean top;                    // матеріал завжди показується на головній сторінці
    @JoinColumn(name = "createdate")
    private Date createdate = new Date();   //дата створення матеріалу
    private Date startdate = new Date();    // дата початку показу матеріала на сайті
    private Date lasteditdate = null;      //дата останнього редагування матеріалу
    private Date enddate = null;            // дата закінчення показу матеріалу на сайті

    // необхідні атрибути запису
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    private ItemCategory category;          // одна категорія, до якої належить матеріал
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status")
    private ItemStatusBlog status;          // Стан матеріалу (чернетка, готовий, дозволено показати на сайті, заблоковано
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_itemtype")       // тип матеріалу: стаття на сайті, блог, РОТ, коментар
    private ItemType typerecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TextBody getIntro() {
        return intro;
    }

    public void setIntro(TextBody intro) {
        this.intro = intro;
    }

    public TextBody getBody() {
        return body;
    }

    public void setBody(TextBody body) {
        this.body = body;
    }

    public SiteUser getAuthor() {
        return author;
    }

    public void setAuthor(SiteUser author) {
        this.author = author;
    }

    public String getMainmedia() {
        return mainmedia;
    }

    public void setMainmedia(String mainmedia) {
        this.mainmedia = mainmedia;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getLasteditdate() {
        return lasteditdate;
    }

    public void setLasteditdate(Date lasteditdate) {
        this.lasteditdate = lasteditdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public ItemStatusBlog getStatus() {
        return status;
    }

    public void setStatus(ItemStatusBlog status) {
        this.status = status;
    }

    public ItemType getTyperecord() {return typerecord;}

    public void setTyperecord(ItemType typerecord) {this.typerecord = typerecord;}
}

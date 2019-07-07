package toloka.model.back;

//import toloka.model.security.Role;
import toloka.model.security.SiteUser;
import javax.persistence.*;
import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//import toloka.model.security.Role;

@Entity
@Table(name = "files_upload")
public class UserUploadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filename;
    private String fileext;
    private String filetoken;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "item", columnDefinition = "text", nullable = false)
    private String item;
    private boolean enable;

//    @ManyToMany
    @ManyToOne
    @JoinTable(
            name = "filesupload_users",
            joinColumns = @JoinColumn(
                    name = "file_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private SiteUser user;
//    private Collection<SiteUser> user;

//    private SiteUser owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileext() {
        return fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }

    public String getFiletoken() {
        return filetoken;
    }

    public void setFiletoken(String filetoken) {
        this.filetoken = filetoken;
    }

//    public Collection<SiteUser> getUser() {
//        return user;
//    }
//
//    public void setUser(Collection<SiteUser> user) {
//        this.user = user;
//    }


    public SiteUser getUser() {
        return user;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isEnable() { return enable; }

    public void setEnable(boolean enable) { this.enable = enable; }

//    public SiteUser getOwner() {
//        return owner;
//    }
//
//    public void setOwner(SiteUser owner) {
//        this.owner = owner;
//    }
}

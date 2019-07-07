package toloka.model.front;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "front_contact", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "front_contacts_uk", columnNames = "id") })
public class FrontContacts {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "dt", nullable = false)
    private LocalDateTime dateTime;

    @Column
    private Boolean penable;

    @Column
    private Boolean pview;

    @Column(name = "message", columnDefinition = "text", nullable = false)
    private String message;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getPenable() {
        return penable;
    }

    public void setPenable(Boolean penable) {
        this.penable = penable;
    }

    public Boolean getPview() {
        return pview;
    }

    public void setPview(Boolean pview) {
        this.pview = pview;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FrontContacts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public FrontContacts() {
        setPenable(Boolean.FALSE);
        setPview(Boolean.FALSE);
    }
}

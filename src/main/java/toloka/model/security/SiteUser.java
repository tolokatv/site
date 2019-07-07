package toloka.model.security;

import toloka.model.back.UserUploadModel;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.*;

//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.springframework.data.annotation.Transient;
//import java.util.List;
//import java.util.Set;
//import toloka.model.security.Role;

@Entity
@Table(name = "users")
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_id", nullable = false)
    private Long id;
    @Email(message = "Будьласка, введіть коректну поштову адресу")
    @NotEmpty(message = "Будьласка, заповніть поле з поштовою адресою.")
    private String usermail;
    @NotEmpty(message = "Будьласка, заповніть поле з Вашим імʼям.")
//    @Size(min=2, max=40, message = "Імʼя повинно містити від двох до сорока літер.")
    private String firstName;
    @NotEmpty(message = "Будьласка, заповніть поле з Вашим призвищем.")
//    @Size(min=2, max=40, message = "Призвище повинно містити від двох до сорока літер.")
    private String lastName;
    
    private String password;
    private String phone;
    private Boolean enabled;
    private String confirmationToken;
    private Boolean avatar;

    @ManyToMany //(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;



//    private HashSet<UserUploadModel> UploadFiles;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() { return phone;    }

    public void setPhone(String phone) {this.phone = phone;    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Boolean getAvatar() {
        return avatar;
    }

    public void setAvatar(Boolean avatar) {
        this.avatar = avatar;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public SiteUser() {
        this.enabled = false;
        this.avatar = false;
    }
}

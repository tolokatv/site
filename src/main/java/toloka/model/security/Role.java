package toloka.model.security;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import toloka.model.security.SiteUser;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles") //, fetch = FetchType.EAGER)
    private Collection<SiteUser> users;
    @ManyToMany // (fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

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

    public Collection<SiteUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<SiteUser> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

}
package cmahy.webapp.resource.impl.domain.user;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "role_app")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;

    @ManyToMany(mappedBy = "roles")
    Collection<User> users;

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

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}

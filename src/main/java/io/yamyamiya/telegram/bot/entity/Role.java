package io.yamyamiya.telegram.bot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
/**
 * class Role represent the Role entity, which is an element in {@link io.yamyamiya.telegram.bot.repository.RoleRepository}
 * contains id, name, users parameters.
 * Objects of this class with corresponding properties represent table "role" in DB,
 * linked with table "user_role" (using Foreign key role_id).
 * Implements methods of GrantedAuthority interface (for implementing Security)
 */
@Entity
@Table(name="role")
public class Role implements GrantedAuthority {
    /**
     * id of the role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    /**
     * role's name (ROLE_USER, ROLE_ADMIN), ROLE_USER is added by default
     */
    @Column(name="name")
    @NotNull
    private String name;
    /**
     * set of users having the role
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )

    private Set<User> users;

    public Role() {
    }

    public Role(int id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}

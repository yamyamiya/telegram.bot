package io.yamyamiya.telegram.bot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
/**
 * class User represent the User entity, which is an element in {@link io.yamyamiya.telegram.bot.repository.UserRepository}
 * contains id, name, password, chatId, addedAt, roles and cities parameters.
 * Objects of this class with corresponding properties represent table "user" in DB,
 * linked with table "user_city" (using Foreign key user_id) and with table "user_role" (using Foreign key user_id).
 * Implements methods of UserDetails interface (for implementing Security)
 */
@Entity
@Table(name = "user", indexes = @Index(columnList = "id,chat_id"))
public class User implements UserDetails {
    /**
     * id of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * name of the user
     */
    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;
    /**
     * password of the user {@link io.yamyamiya.telegram.bot.service.password.RandomPasswordGenerator}
     * @see io.yamyamiya.telegram.bot.TelegramBot
     */
    @Column(name = "password")
    @NotBlank
    private String password;
    /**
     * chatId of user (provided by TelegramBot)
     */
    @Column(name = "chat_id")
    @NotBlank
    private long chatId;
    /**
     * time of creation of user in TelegramBot
     */
    @Column(name = "added_at")
    @NotBlank
    private Date addedAt;

    /**
     * set of roles of user {@link Role} (ROLE_USER, ROLE_ADMIN)
     */

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    /**
     * set of cities of user, contains cities, that were requested by user
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(
            name="user_city",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="city_id")
    )
    private Set<City> cities;

    public User() {
    }

    public User(int id, String name, String password, long chatId, Date addedAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.chatId = chatId;
        this.addedAt = Objects.requireNonNullElseGet(addedAt, Date::new);
    }

    public User(int id, String name, long chatId, Date addedAt) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
        this.addedAt = Objects.requireNonNullElseGet(addedAt, Date::new);
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(chatId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatId == user.chatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", chatId=" + chatId +
                ", addedAt=" + addedAt +
                ", roles=" + roles +
                ", cities=" + cities +
                '}';
    }
}

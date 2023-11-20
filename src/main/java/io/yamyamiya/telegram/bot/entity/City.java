package io.yamyamiya.telegram.bot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * class City represent the City entity, which is an element in {@link io.yamyamiya.telegram.bot.repository.CityRepository}
 * contains id, name, latitude, longitude parameters.
 * Objects of this class with corresponding properties represent table "city" in DB,
 * linked with table "user_city" (using Foreign key city_id),
 */
@Entity
@Table(name="city")
public class City {
    /**
     * id of the city
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    /**
     * name of the city
     */
    @Column(name="name")
    @NotNull
    private String name;

    /**
     * latitude of the city
     */
    @Column(name="latitude")
    @NotNull
    private double latitude;

    /**
     * longitude of the city
     */
    @Column(name="longitude")
    @NotNull
    private double longitude;

    /**
     * users {@link User}) having link to the city are contained in this param
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JoinTable(
            name="user_city",
            joinColumns = @JoinColumn(name="city_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> usersForCities = new HashSet<>();

    public City() {
    }

    public City(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Set<User> getUsersForCities() {
        return usersForCities;
    }

    public void setUsersForCities(Set<User> usersForCities) {
        this.usersForCities = usersForCities;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", usersForCities=" + usersForCities +
                '}';
    }


}

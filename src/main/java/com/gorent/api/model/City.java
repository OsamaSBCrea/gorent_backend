package com.gorent.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gorent.api.dto.CityDTO;

import javax.persistence.*;

@Entity(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    public City() {
    }

    public City(Long id) {
        this.id = id;
    }

    public City(CityDTO cityDTO) {
        this.name = cityDTO.getName();
        this.country = new Country(cityDTO.getCountryId());
    }

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}

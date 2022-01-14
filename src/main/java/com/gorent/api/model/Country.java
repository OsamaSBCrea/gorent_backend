package com.gorent.api.model;

import com.gorent.api.dto.CountryDTO;
import com.gorent.api.model.enums.Currency;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(mappedBy = "country", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<City> cities = new HashSet<>();

    public Country() {
    }

    public Country(Long id) {
        this.id = id;
    }

    public Country(CountryDTO countryDTO) {
        this.name = countryDTO.getName();
        this.countryCode = countryDTO.getCountryCode();
        this.currency = countryDTO.getCurrency();
    }

    public Country(Long id, String name, String countryCode, Currency currency) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.currency = currency;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", currency=" + currency +
                '}';
    }
}

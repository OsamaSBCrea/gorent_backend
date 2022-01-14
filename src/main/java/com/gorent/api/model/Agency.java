package com.gorent.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gorent.api.dto.AgencyDTORegister;

import javax.persistence.*;

@Entity(name = "agency")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String fcmToken;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties(value = {"cities"})
    private Country country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Agency() {
    }

    public Agency(Long id) {
        this.id = id;
    }

    public Agency(AgencyDTORegister agencyDTO) {
        this.name = agencyDTO.getName();
        this.phoneNumber = agencyDTO.getPhoneNumber();
        this.country = new Country(agencyDTO.getCountryId());
        this.city = new City(agencyDTO.getCityId());
    }

    public Agency(Long id, String name, String phoneNumber, String fcmToken) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fcmToken = fcmToken;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                ", country=" + country +
                ", city=" + city +
                ", user=" + user +
                '}';
    }
}

package com.gorent.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gorent.api.dto.TenantDTORegister;
import com.gorent.api.model.enums.Gender;

import javax.persistence.*;

@Entity(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String nationality;

    private Double grossMonthlySalary;

    private String occupation;

    private Integer familySize;

    private String phoneNumber;

    private Long currentRentId;

    private String fcmToken;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties(value = {"cities"})
    private Country country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties(value = {"country"})
    private City city;

    public Tenant() {
    }

    public Tenant(Long id) {
        this.id = id;
    }

    public Tenant(TenantDTORegister tenantDTO) {
        this.firstName = tenantDTO.getFirstName();
        this.lastName = tenantDTO.getLastName();
        this.gender = tenantDTO.getGender();
        this.grossMonthlySalary = tenantDTO.getGrossMonthlySalary();
        this.familySize = tenantDTO.getFamilySize();
        this.nationality = tenantDTO.getNationality();
        this.occupation = tenantDTO.getOccupation();
        this.phoneNumber = tenantDTO.getPhoneNumber();
        this.city = new City(tenantDTO.getCityId());
        this.country = new Country(tenantDTO.getCountryId());
    }

    public Tenant(Long id, String firstName, String lastName, Gender gender, String nationality, Double grossMonthlySalary, String occupation, Integer familySize, String phoneNumber, String fcmToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.grossMonthlySalary = grossMonthlySalary;
        this.occupation = occupation;
        this.familySize = familySize;
        this.phoneNumber = phoneNumber;
        this.fcmToken = fcmToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Double getGrossMonthlySalary() {
        return grossMonthlySalary;
    }

    public void setGrossMonthlySalary(Double grossMonthlySalary) {
        this.grossMonthlySalary = grossMonthlySalary;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getFamilySize() {
        return familySize;
    }

    public void setFamilySize(Integer familySize) {
        this.familySize = familySize;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getCurrentRentId() {
        return currentRentId;
    }

    public void setCurrentRentId(Long currentRentId) {
        this.currentRentId = currentRentId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", grossMonthlySalary=" + grossMonthlySalary +
                ", occupation='" + occupation + '\'' +
                ", familySize=" + familySize +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", user=" + user +
                ", country=" + country +
                ", city=" + city +
                ", currentRentId=" + currentRentId +
                ", fcmToken=" + fcmToken +
                '}';
    }
}

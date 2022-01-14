package com.gorent.api.dto;

import com.gorent.api.model.enums.Gender;

public class TenantDTORegister extends RegisterUserDTO {

    private String firstName;

    private String lastName;

    private Gender gender;

    private String nationality;

    private String phoneNumber;

    private Double grossMonthlySalary;

    private String occupation;

    private Integer familySize;

    private Long countryId;

    private Long cityId;

    public TenantDTORegister() {
    }

    public TenantDTORegister(String firstName, String lastName, Gender gender, String nationality, String phoneNumber, Double grossMonthlySalary, String occupation, Integer familySize, Long countryId, Long cityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.grossMonthlySalary = grossMonthlySalary;
        this.occupation = occupation;
        this.familySize = familySize;
        this.countryId = countryId;
        this.cityId = cityId;
    }

    public TenantDTORegister(String email, String password, String confirmPassword, String firstName, String lastName, Gender gender, String nationality, String phoneNumber, Double grossMonthlySalary, String occupation, Integer familySize, Long countryId, Long cityId) {
        super(email, password, confirmPassword);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.grossMonthlySalary = grossMonthlySalary;
        this.occupation = occupation;
        this.familySize = familySize;
        this.countryId = countryId;
        this.cityId = cityId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}

package com.gorent.api.dto;

public class AgencyDTORegister extends RegisterUserDTO {

    private String name;

    private String phoneNumber;

    private Long countryId;

    private Long cityId;

    public AgencyDTORegister() {
    }

    public AgencyDTORegister(String name, String phoneNumber, Long countryId, Long cityId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.countryId = countryId;
        this.cityId = cityId;
    }

    public AgencyDTORegister(String email, String password, String confirmPassword, String name, String phoneNumber, Long countryId, Long cityId) {
        super(email, password, confirmPassword);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.countryId = countryId;
        this.cityId = cityId;
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

    @Override
    public String toString() {
        return "AgencyDTO{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryId=" + countryId +
                ", cityId=" + cityId +
                '}';
    }
}

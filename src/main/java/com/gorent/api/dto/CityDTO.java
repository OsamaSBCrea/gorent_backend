package com.gorent.api.dto;

public class CityDTO {

    private String name;

    private Long countryId;

    public CityDTO() {
    }

    public CityDTO(String name, Long countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}

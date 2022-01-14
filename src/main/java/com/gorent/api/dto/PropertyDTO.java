package com.gorent.api.dto;

import com.gorent.api.model.enums.PropertyStatus;

import java.time.Instant;

public class PropertyDTO {

    private String postalAddress;

    private Double surfaceArea;

    private Integer bedrooms;

    private Boolean hasBalcony;

    private Boolean hasGarden;

    private String constructionYear;

    private Double rentalPrice;

    private String description;

    private Instant availabilityDate;

    private Long cityId;

    private PropertyStatus status;

    public PropertyDTO() {
    }

    public PropertyDTO(String postalAddress, Double surfaceArea, Integer bedrooms, Boolean hasBalcony,
                       Boolean hasGarden, String constructionYear, Double rentalPrice, String description,
                       Instant availabilityDate, Long cityId, PropertyStatus status) {
        this.postalAddress = postalAddress;
        this.surfaceArea = surfaceArea;
        this.bedrooms = bedrooms;
        this.hasBalcony = hasBalcony;
        this.hasGarden = hasGarden;
        this.constructionYear = constructionYear;
        this.rentalPrice = rentalPrice;
        this.description = description;
        this.availabilityDate = availabilityDate;
        this.cityId = cityId;
        this.status = status;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public String getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(String constructionYear) {
        this.constructionYear = constructionYear;
    }

    public Double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(Instant availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }
}

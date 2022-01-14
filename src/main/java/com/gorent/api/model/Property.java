package com.gorent.api.model;

import com.gorent.api.dto.PropertyDTO;
import com.gorent.api.model.enums.PropertyStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postalAddress;

    private Double surfaceArea;

    private Integer bedrooms;

    private Boolean hasBalcony;

    private Boolean hasGarden;

    private String constructionYear;

    private Double rentalPrice;

    private String description;

    private Instant availabilityDate;

    private Instant createdDate;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    public Property() {
    }

    public Property(Long id) {
        this.id = id;
    }

    public Property(PropertyDTO propertyDTO) {
        this.postalAddress = propertyDTO.getPostalAddress();
        this.surfaceArea = propertyDTO.getSurfaceArea();
        this.bedrooms = propertyDTO.getBedrooms();
        this.hasBalcony = propertyDTO.getHasBalcony();
        this.hasGarden = propertyDTO.getHasGarden();
        this.constructionYear = propertyDTO.getConstructionYear();
        this.rentalPrice = propertyDTO.getRentalPrice();
        this.description = propertyDTO.getDescription();
        this.availabilityDate = propertyDTO.getAvailabilityDate();
        this.city = new City(propertyDTO.getCityId());
        this.status = propertyDTO.getStatus();
    }

    public Property(Long id, String postalAddress, Double surfaceArea, Integer bedrooms, Boolean hasBalcony,
                    Boolean hasGarden, String constructionYear, Double rentalPrice, String description,
                    Instant availabilityDate, PropertyStatus status, Instant createdDate) {
        this.id = id;
        this.postalAddress = postalAddress;
        this.surfaceArea = surfaceArea;
        this.bedrooms = bedrooms;
        this.hasBalcony = hasBalcony;
        this.hasGarden = hasGarden;
        this.constructionYear = constructionYear;
        this.rentalPrice = rentalPrice;
        this.description = description;
        this.availabilityDate = availabilityDate;
        this.status = status;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", postalAddress='" + postalAddress + '\'' +
                ", surfaceArea=" + surfaceArea +
                ", bedrooms=" + bedrooms +
                ", hasBalcony=" + hasBalcony +
                ", hasGarden=" + hasGarden +
                ", constructionYear='" + constructionYear + '\'' +
                ", rentalPrice=" + rentalPrice +
                ", description='" + description + '\'' +
                ", availabilityDate=" + availabilityDate +
                ", status=" + status +
                ", agency=" + agency +
                ", city=" + city +
                '}';
    }
}

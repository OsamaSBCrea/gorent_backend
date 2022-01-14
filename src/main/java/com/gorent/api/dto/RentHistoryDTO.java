package com.gorent.api.dto;

import com.gorent.api.model.City;
import com.gorent.api.model.Rent;
import com.gorent.api.model.enums.RentStatus;

import java.time.Instant;

public class RentHistoryDTO {

    private Long rentId;

    private String tenantName;

    private String agencyName;

    private City propertyCity;

    private String propertyAddress;

    private Instant applicationDate;

    private Instant rentDate;

    private Instant endDate;

    private RentStatus status;

    public RentHistoryDTO() {
    }

    public RentHistoryDTO(Rent rent) {
        this.rentId = rent.getId();
        this.agencyName = rent.getAgency().getName();
        this.tenantName = rent.getTenant().getFullName();
        this.propertyCity = rent.getProperty().getCity();
        this.propertyAddress = rent.getProperty().getPostalAddress();
        this.status = rent.getRentStatus();
        this.applicationDate = rent.getApplicationDate();
    }

    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public City getPropertyCity() {
        return propertyCity;
    }

    public void setPropertyCity(City propertyCity) {
        this.propertyCity = propertyCity;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Instant getRentDate() {
        return rentDate;
    }

    public void setRentDate(Instant rentDate) {
        this.rentDate = rentDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public RentStatus getStatus() {
        return status;
    }

    public void setStatus(RentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RentHistory{" +
                "rentId=" + rentId +
                ", tenantName='" + tenantName + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", propertyCity=" + propertyCity +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", rentDate=" + rentDate +
                ", endDate=" + endDate +
                '}';
    }

}

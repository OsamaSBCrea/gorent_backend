package com.gorent.api.dto;

import com.gorent.api.model.Rent;
import com.gorent.api.model.enums.RentStatus;

import java.time.Instant;

public class RentApplicationDTO {

    private Long propertyId;

    private Long tenantId;

    private Long rentId;

    private String tenantName;

    private String tenantAddress;

    private RentStatus status;

    private Instant applicationDate;

    public RentApplicationDTO() {
    }

    public RentApplicationDTO(Rent rent) {
        this.propertyId = rent.getProperty().getId();
        this.tenantId = rent.getTenant().getId();
        this.tenantName = rent.getTenant().getFullName();
        this.tenantAddress = rent.getTenant().getCity().getName();
        this.rentId = rent.getId();
        this.status = rent.getRentStatus();
        this.applicationDate = rent.getApplicationDate();
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }

    public RentStatus getStatus() {
        return status;
    }

    public void setStatus(RentStatus status) {
        this.status = status;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }
}

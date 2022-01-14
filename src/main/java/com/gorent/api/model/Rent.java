package com.gorent.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gorent.api.model.enums.RentStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    @JsonIgnoreProperties(value = {"agency"})
    private Property property;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id")
    @JsonIgnoreProperties(value = {"user"})
    private Tenant tenant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agency_id")
    @JsonIgnore
    private Agency agency;

    private Instant applicationDate;

    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;

    public Rent() {
    }

    public Rent(Long id) {
        this.id = id;
    }

    public Rent(Long id, Instant applicationDate, RentStatus rentStatus) {
        this.id = id;
        this.applicationDate = applicationDate;
        this.rentStatus = rentStatus;
    }

    public Rent(Long id, Property property, Tenant tenant, Agency agency, Instant applicationDate, RentStatus rentStatus) {
        this.id = id;
        this.property = property;
        this.tenant = tenant;
        this.agency = agency;
        this.applicationDate = applicationDate;
        this.rentStatus = rentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public RentStatus getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(RentStatus rentStatus) {
        this.rentStatus = rentStatus;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", property=" + property +
                ", tenant=" + tenant +
                ", agency=" + agency +
                ", applicationDate=" + applicationDate +
                ", rentStatus=" + rentStatus +
                '}';
    }
}

package com.gorent.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gorent.api.model.enums.UserRole;

import javax.persistence.*;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(name = "agency_id", referencedColumnName = "id")
    @JsonIgnore
    private Agency agency;

    @OneToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @JsonIgnore
    private Tenant tenant;

    public User() {
    }

    public User(Long id, String email, String passwordHash, UserRole role, Agency agency, Tenant tenant) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.agency = agency;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                ", agency=" + agency +
                ", tenant=" + tenant +
                '}';
    }
}

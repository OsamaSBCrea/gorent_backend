package com.gorent.api.dto;

import com.gorent.api.model.enums.UserRole;

public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private UserRole role;

    private Long agencyId;

    private Long tenantId;

    private Long countryId;

    public UserDTO() {
    }

    public UserDTO(Long id, String email, String name, UserRole role, Long agencyId, Long tenantId, Long countryId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.agencyId = agencyId;
        this.tenantId = tenantId;
        this.countryId = countryId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}

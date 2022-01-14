package com.gorent.api.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DomainUserDetails implements UserDetails {

    private final User user;
    private Long agencyId;
    private Long tenantId;
    private Long countryId;

    public DomainUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user = new User(username, password, authorities);
    }

    public DomainUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long countryId, Long agencyOrTenantId, boolean isAgency) {
        this.user = new User(username, password, authorities);
        if (isAgency) {
            this.agencyId = agencyOrTenantId;
        } else {
            this.tenantId = agencyOrTenantId;
        }
        this.countryId = countryId;
    }

    public DomainUserDetails(
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getAgencyOrTenantId() {
        return agencyId != null ? agencyId : tenantId;
    }

    public Long getCountryId() {
        return countryId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public User getUser() {
        return user;
    }
}

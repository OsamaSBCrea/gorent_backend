package com.gorent.api.security;

import com.gorent.api.model.User;
import com.gorent.api.repository.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository
                .findByEmailIgnoreCase(login)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        } else {
            throw new UsernameNotFoundException("Invalid user email");
        }
    }

    private DomainUserDetails createSpringSecurityUser(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getLabel()));
        if (user.getAgency() != null) {
            return new DomainUserDetails(user.getEmail(), user.getPasswordHash(), grantedAuthorities,
                    user.getAgency().getCountry().getId(), user.getAgency().getId(), true);
        } else {
            return new DomainUserDetails(user.getEmail(), user.getPasswordHash(), grantedAuthorities,
                    user.getTenant().getCountry().getId(), user.getTenant().getId(), false);
        }
    }
}

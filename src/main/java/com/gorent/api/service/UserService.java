package com.gorent.api.service;

import com.gorent.api.dto.AgencyDTORegister;
import com.gorent.api.dto.TenantDTORegister;
import com.gorent.api.dto.RegisterUserDTO;
import com.gorent.api.errors.BadRequestAlertException;
import com.gorent.api.errors.EmailAlreadyUsedException;
import com.gorent.api.model.Agency;
import com.gorent.api.model.Tenant;
import com.gorent.api.model.User;
import com.gorent.api.model.enums.UserRole;
import com.gorent.api.repository.AgencyRepository;
import com.gorent.api.repository.TenantRepository;
import com.gorent.api.repository.UserRepository;
import com.gorent.api.security.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AgencyRepository agencyRepository;

    private final TenantRepository tenantRepository;

    private final AgencyService agencyService;

    private final TenantService tenantService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AgencyRepository agencyRepository,
                       TenantRepository tenantRepository,
                       AgencyService agencyService,
                       TenantService tenantService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.agencyRepository = agencyRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
        this.agencyService = agencyService;
        this.tenantService = tenantService;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findByEmailIgnoreCase);
    }

    public User registerUser(RegisterUserDTO registerUserDTO) {
        userRepository
                .findByEmailIgnoreCase(registerUserDTO.getEmail())
                .ifPresent(
                        existingUser -> {
                            throw new EmailAlreadyUsedException();
                        }
                );

        String encryptedString = passwordEncoder.encode(registerUserDTO.getPassword());
        User user = new User();

        user.setEmail(registerUserDTO.getEmail().toLowerCase());
        user.setPasswordHash(encryptedString);
        User newUser;
        if (registerUserDTO instanceof AgencyDTORegister) {
            user.setRole(UserRole.AGENCY);
            newUser = userRepository.save(user);
            Agency agency = agencyService.createAgency((AgencyDTORegister) registerUserDTO, newUser);
            user.setAgency(agency);
            userRepository.save(newUser);
        } else if (registerUserDTO instanceof TenantDTORegister) {
            user.setRole(UserRole.TENANT);
            newUser = userRepository.save(user);
            Tenant tenant = tenantService.createTenant((TenantDTORegister) registerUserDTO, newUser);
            newUser.setTenant(tenant);
            userRepository.save(newUser);
        } else {
            throw new BadRequestAlertException("Invalid user form", "user", "invalidForm");
        }

        return newUser;
    }

    public void refreshFCMToken(String fcmToken) {
        if (SecurityUtils.getCurrentAgencyId() != null) {
            Agency agency = agencyRepository.findById(SecurityUtils.getCurrentAgencyId()).orElseThrow(null);
            agency.setFcmToken(fcmToken);
            agencyRepository.save(agency);
        } else if(SecurityUtils.getCurrentTenantId() != null) {
            Tenant tenant = tenantRepository.findById(SecurityUtils.getCurrentTenantId()).orElse(null);
            tenant.setFcmToken(fcmToken);
            tenantRepository.save(tenant);
        } else {
            throw new BadRequestAlertException("Invalid User", "user", "invalid_token");
        }
    }
}

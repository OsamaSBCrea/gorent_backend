package com.gorent.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gorent.api.dto.*;
import com.gorent.api.errors.InvalidPasswordException;
import com.gorent.api.mapper.UserMapper;
import com.gorent.api.model.Rent;
import com.gorent.api.model.User;
import com.gorent.api.security.jwt.JWTFilter;
import com.gorent.api.security.jwt.TokenProvider;
import com.gorent.api.service.RentService;
import com.gorent.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AuthenticationResource {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationResource.class);

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserMapper userMapper;

    private final RentService rentService;

    private static class UserResourceException extends RuntimeException {

        private UserResourceException(String message) {
            super(message);
        }
    }

    public AuthenticationResource(UserService userService,
                                  RentService rentService,
                                  TokenProvider tokenProvider,
                                  AuthenticationManagerBuilder authenticationManagerBuilder,
                                  UserMapper userMapper) {
        this.userService = userService;
        this.rentService = rentService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userMapper = userMapper;
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/account")
    public UserDTO getCurrentUser() {
        UserDTO userDTO = userService
                .getUserWithAuthorities()
                .map(userMapper::userToUserDTO)
                .orElseThrow(() -> new UserResourceException("User could not be found"));

        if (userDTO.getTenantId() != null) {
            TenantDTO tenantDTO = new TenantDTO(userDTO);
            Rent currentRent = this.rentService.getCurrentRentByTenantId(tenantDTO.getTenantId());
            if (currentRent != null) {
                tenantDTO.setCurrentRentedPropertyId(currentRent.getProperty().getId());
                tenantDTO.setCurrentRentStatus(currentRent.getRentStatus());
            }
            return tenantDTO;
        } else if (userDTO.getAgencyId() != null) {
            AgencyDTO agencyDTO = new AgencyDTO(userDTO);
            agencyDTO.setRents(this.rentService.getAllNewRents(userDTO.getAgencyId()));
            return agencyDTO;
        }

        return userDTO;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticate(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginDTO.getEmail(), loginDTO.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginDTO.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/agency/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAgency(@RequestBody AgencyDTORegister agencyDTO) {
        if (isPasswordLengthInvalid(agencyDTO.getPassword())) {
            throw new InvalidPasswordException();
        }

        User user = userService.registerUser(agencyDTO);
    }

    @PostMapping("/tenant/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerTenant(@RequestBody TenantDTORegister tenantDTO) {
        if (isPasswordLengthInvalid(tenantDTO.getPassword())) {
            throw new InvalidPasswordException();
        }

        User user = userService.registerUser(tenantDTO);
    }

    @PostMapping("/fcm-token")
    @ResponseStatus(HttpStatus.CREATED)
    public void refreshFCMToken(@RequestParam("token") String fcmToken) {
        this.userService.refreshFCMToken(fcmToken);
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < RegisterUserDTO.PASSWORD_MIN_LENGTH ||
                        password.length() > RegisterUserDTO.PASSWORD_MAX_LENGTH
        );
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

}

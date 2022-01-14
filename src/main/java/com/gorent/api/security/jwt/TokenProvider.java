package com.gorent.api.security.jwt;

import com.gorent.api.config.GorentProperties;
import com.gorent.api.model.enums.UserRole;
import com.gorent.api.security.DomainUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component("tokenProvider")
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private static final String AGENCY_OR_TENANT_KEY = "agency-or-tenant";

    private static final String COUNTRY_ID = "country-id";

    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    private final long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider(GorentProperties gorentProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(gorentProperties.getJwtBase64Secret());
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * gorentProperties.getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * gorentProperties.getTokenValidityInSecondsRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Long agencyOrTenantId = ((DomainUserDetails) authentication.getPrincipal()).getAgencyOrTenantId();
        Long countryId = ((DomainUserDetails) authentication.getPrincipal()).getCountryId();

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(AGENCY_OR_TENANT_KEY, agencyOrTenantId)
            .claim(COUNTRY_ID, countryId)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String[] authoritiesString = claims.get(AUTHORITIES_KEY).toString().split(",");
        boolean isAgency = false;
        for (String authority : authoritiesString) {
            if (authority.equals(UserRole.AGENCY.getLabel())) {
                isAgency = true;
                break;
            } else {
                isAgency = false;
            }
        }


        Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(authoritiesString)
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        Long agencyOrTenantId = ((Integer) claims.get(AGENCY_OR_TENANT_KEY)).longValue();
        Long countryId = ((Integer) claims.get(COUNTRY_ID)).longValue();

        DomainUserDetails principal = new DomainUserDetails(claims.getSubject(), "", authorities, countryId, agencyOrTenantId, isAgency);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}

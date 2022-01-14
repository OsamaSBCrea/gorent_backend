package com.gorent.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "gorent")
public class GorentProperties {

    private String jwtBase64Secret;

    private Long tokenValidityInSeconds;

    private Long tokenValidityInSecondsRememberMe;

    private String contentSecurityPolicy;

    private String firebaseConfigFile;

    private final CorsConfiguration cors = new CorsConfiguration();

    public String getJwtBase64Secret() {
        return jwtBase64Secret;
    }

    public void setJwtBase64Secret(String jwtBase64Secret) {
        this.jwtBase64Secret = jwtBase64Secret;
    }

    public Long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    public void setTokenValidityInSeconds(Long tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    public Long getTokenValidityInSecondsRememberMe() {
        return tokenValidityInSecondsRememberMe;
    }

    public void setTokenValidityInSecondsRememberMe(Long tokenValidityInSecondsRememberMe) {
        this.tokenValidityInSecondsRememberMe = tokenValidityInSecondsRememberMe;
    }

    public CorsConfiguration getCors() {
        return cors;
    }

    public String getContentSecurityPolicy() {
        return contentSecurityPolicy;
    }

    public void setContentSecurityPolicy(String contentSecurityPolicy) {
        this.contentSecurityPolicy = contentSecurityPolicy;
    }

    public String getFirebaseConfigFile() {
        return firebaseConfigFile;
    }

    public void setFirebaseConfigFile(String firebaseConfigFile) {
        this.firebaseConfigFile = firebaseConfigFile;
    }
}

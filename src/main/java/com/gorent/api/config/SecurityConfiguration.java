package com.gorent.api.config;

import com.gorent.api.security.DomainUserDetailsService;
import com.gorent.api.security.jwt.JWTConfigurer;
import com.gorent.api.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final DomainUserDetailsService domainUserDetailsService;

    private final SecurityProblemSupport securityProblemSupport;

    private final CorsFilter corsFilter;

    private final GorentProperties gorentProperties;

    public SecurityConfiguration(TokenProvider tokenProvider,
                                 DomainUserDetailsService domainUserDetailsService,
                                 SecurityProblemSupport securityProblemSupport,
                                 CorsFilter corsFilter,
                                 GorentProperties gorentProperties) {
        this.tokenProvider = tokenProvider;
        this.domainUserDetailsService = domainUserDetailsService;
        this.securityProblemSupport = securityProblemSupport;
        this.corsFilter = corsFilter;
        this.gorentProperties = gorentProperties;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/swagger-ui/**").antMatchers("/test/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(domainUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(securityProblemSupport)
                .accessDeniedHandler(securityProblemSupport)
                .and()
                .headers()
                .contentSecurityPolicy(gorentProperties.getContentSecurityPolicy())
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions()
                .deny()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/country/**").permitAll()
                .antMatchers("/api/city/**").permitAll()
                .antMatchers("/api/agency/register").permitAll()
                .antMatchers("/api/tenant/register").permitAll()
                .antMatchers("/api/reset-password").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}

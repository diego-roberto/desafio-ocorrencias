package com.bitbucket.diegoroberto.ocorrenciasapi.infra.config.security;

import com.bitbucket.diegoroberto.ocorrenciasapi.infra.config.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String[] PUBLIC_ENDPOINTS = { "/auth/login" };
    public static final String[] USER_ENDPOINTS = { "/ocorrencias/**", "/clientes/**", "/enderecos/**" };
    public static final String[] ADMIN_ENDPOINTS = { "/admin/**" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                .requestMatchers(USER_ENDPOINTS).hasAnyRole("USER", "ADMIN")
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
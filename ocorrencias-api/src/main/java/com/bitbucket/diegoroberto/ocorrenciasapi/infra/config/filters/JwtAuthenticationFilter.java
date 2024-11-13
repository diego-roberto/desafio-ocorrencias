package com.bitbucket.diegoroberto.ocorrenciasapi.infra.config.filters;

import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.CustomUserDetailsService;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.config.security.JwtTokenProvider;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.config.security.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (!checkIfEndpointIsPublic(request)) {
            String token = getTokenFromRequest(request);
            if (token != null ) {
                String subject = jwtTokenProvider.getUsername(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(subject);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
            chain.doFilter(request, response);
    }


    private boolean checkIfEndpointIsPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.asList(SecurityConfig.PUBLIC_ENDPOINTS).contains(requestURI);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

package com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();

    public CustomUserDetailsService() {
        users.put("user", User.withDefaultPasswordEncoder()
                .username("user")
                .password("secreta")
                .roles("USER")
                .build());

        users.put("admin", User.withDefaultPasswordEncoder()
                .username("admin")
                .password("secretona")
                .roles("USER", "ADMIN")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Uuário não encontrado");
        }
        return user;
    }
}

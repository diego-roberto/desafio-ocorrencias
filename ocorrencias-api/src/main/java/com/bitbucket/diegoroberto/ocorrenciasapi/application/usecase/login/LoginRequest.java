package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.login;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class LoginRequest {
    private String username;
    private String password;
}

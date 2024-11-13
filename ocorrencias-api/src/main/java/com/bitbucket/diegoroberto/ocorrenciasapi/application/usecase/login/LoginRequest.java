package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.login;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}

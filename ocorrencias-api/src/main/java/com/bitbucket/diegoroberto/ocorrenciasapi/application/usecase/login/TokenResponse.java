package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    public String token;

}

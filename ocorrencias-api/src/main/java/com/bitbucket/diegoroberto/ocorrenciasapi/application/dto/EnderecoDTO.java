package com.bitbucket.diegoroberto.ocorrenciasapi.application.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    private UUID codEndereco;
    private String nomeLogradouro;
    private String nomeBairro;
    private String numeroCep;
    private String nomeCidade;
    private String nomeEstado;
}

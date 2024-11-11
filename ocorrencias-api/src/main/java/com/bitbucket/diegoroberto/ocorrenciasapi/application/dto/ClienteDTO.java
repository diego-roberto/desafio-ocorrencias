package com.bitbucket.diegoroberto.ocorrenciasapi.application.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private UUID codCliente;
    private String nomeCliente;
    private String nroCpf;
    private Date dataNascimento;
    private Date dataCriacao;
}

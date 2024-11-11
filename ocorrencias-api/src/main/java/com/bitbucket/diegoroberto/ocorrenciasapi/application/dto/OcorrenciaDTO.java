package com.bitbucket.diegoroberto.ocorrenciasapi.application.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaDTO {
    private UUID codOcorrencia;
    private UUID codCliente;
    private UUID codEndereco;
    private Date dataOcorrencia;
    private String statusOcorrencia;
}

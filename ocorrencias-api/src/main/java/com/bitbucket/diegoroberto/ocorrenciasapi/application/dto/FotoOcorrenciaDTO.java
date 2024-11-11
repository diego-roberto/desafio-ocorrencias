package com.bitbucket.diegoroberto.ocorrenciasapi.application.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FotoOcorrenciaDTO {
    private UUID codFotoOcorrencia;
    private UUID codOcorrencia;
    private Date dataCriacao;
    private String pathBucket;
    private String hash;
}

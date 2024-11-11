package com.bitbucket.diegoroberto.ocorrenciasapi.application.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaDetalhadaDTO {
    private UUID codOcorrencia;
    private Date dataOcorrencia;
    private String statusOcorrencia;
    private ClienteDTO cliente;
    private EnderecoDTO endereco;
    private List<FotoOcorrenciaDTO> fotos;
}

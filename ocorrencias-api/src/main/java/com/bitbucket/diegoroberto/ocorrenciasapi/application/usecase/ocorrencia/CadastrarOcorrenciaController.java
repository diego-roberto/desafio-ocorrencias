package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ocorrencias/cadastrar")
public class CadastrarOcorrenciaController {

    @Autowired
    private CadastrarOcorrencia cadastrarOcorrencia;

    @PostMapping
    public ResponseEntity<OcorrenciaDTO> cadastrar(@RequestBody OcorrenciaDTO ocorrenciaDTO) {
        try {
            OcorrenciaDTO novaOcorrencia = cadastrarOcorrencia.executar(ocorrenciaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaOcorrencia);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/ocorrencias/{codOcorrencia}/finalizar")
public class FinalizarOcorrenciaController {

    @Autowired
    private FinalizarOcorrencia finalizarOcorrencia;

    @PutMapping
    public ResponseEntity<?> finalizar(@PathVariable UUID codOcorrencia) {
        try {
            OcorrenciaDTO ocorrenciaFinalizada = finalizarOcorrencia.executar(codOcorrencia);
            if (ocorrenciaFinalizada == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ocorrência já finalizada");
            }
            return ResponseEntity.ok(ocorrenciaFinalizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


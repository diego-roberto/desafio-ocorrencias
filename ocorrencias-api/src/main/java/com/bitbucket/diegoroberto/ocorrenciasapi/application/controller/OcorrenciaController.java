package com.bitbucket.diegoroberto.ocorrenciasapi.application.controller;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.service.OcorrenciaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @GetMapping("/{codOcorrencia}")
    public ResponseEntity<OcorrenciaDTO> buscarOcorrencia(@PathVariable UUID codOcorrencia) {
        Optional<OcorrenciaDTO> ocorrencia = ocorrenciaService.buscarOcorrencia(codOcorrencia);
        return ocorrencia.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<Page<OcorrenciaDTO>> listarOcorrencias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OcorrenciaDTO> ocorrencias = ocorrenciaService.listarOcorrencias(page, size);
        return ocorrencias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ocorrencias);
    }

    @PutMapping("/{codOcorrencia}")
    public ResponseEntity<OcorrenciaDTO> atualizarOcorrencia(
            @PathVariable UUID codOcorrencia, @RequestBody OcorrenciaDTO ocorrenciaDTO) {
        try {
            OcorrenciaDTO ocorrenciaAtualizada = ocorrenciaService.atualizarOcorrencia(codOcorrencia, ocorrenciaDTO);
            return ResponseEntity.ok(ocorrenciaAtualizada);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}


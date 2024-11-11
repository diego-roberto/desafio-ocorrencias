package com.bitbucket.diegoroberto.ocorrenciasapi.application.controller;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.FotoOcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.service.FotoOcorrenciaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/ocorrencias/{codOcorrencia}/fotos")
public class FotoOcorrenciaController {

    @Autowired
    private FotoOcorrenciaService fotoOcorrenciaService;

    @PostMapping
    public ResponseEntity<?> uploadFotos(
            @PathVariable UUID codOcorrencia,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            List<FotoOcorrenciaDTO> fotos = fotoOcorrenciaService.uploadFotos(codOcorrencia, files);
            if (fotos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(fotos);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
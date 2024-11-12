package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDetalhadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/ocorrencias/consultar")
public class ConsultarOcorrenciaController {

    @Autowired
    private ConsultarOcorrencia consultarOcorrencia;

    @GetMapping()
    public ResponseEntity<Page<OcorrenciaDetalhadaDTO>> consultarOcorrencias(
            @RequestParam(value = "nomeCliente", required = false) String nomeCliente,
            @RequestParam(value = "cpfCliente", required = false) String cpfCliente,
            @RequestParam(value = "dataOcorrencia", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataOcorrencia,
            @RequestParam(value = "cidadeOcorrencia", required = false) String cidadeOcorrencia,
            @RequestParam(value = "sortBy", defaultValue = "dataOcorrencia") String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<OcorrenciaDetalhadaDTO> ocorrencias = consultarOcorrencia.consultarOcorrencias(
                nomeCliente, cpfCliente, dataOcorrencia, cidadeOcorrencia, sortBy, order, page, size);

        return ocorrencias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ocorrencias);
    }
}

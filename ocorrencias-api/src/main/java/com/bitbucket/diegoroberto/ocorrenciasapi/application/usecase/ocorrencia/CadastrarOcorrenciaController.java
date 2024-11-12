package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDetalhadaDTO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/ocorrencias/cadastrar")
public class CadastrarOcorrenciaController {

    @Autowired
    private CadastrarOcorrencia cadastrarOcorrencia;

    @PostMapping
    public ResponseEntity<?> cadastrarOcorrencia(
            @RequestParam("cpfCliente") String cpfCliente,
            @RequestParam("codEndereco") UUID codEndereco,
            @RequestParam("imagem") MultipartFile imagem) {
        try {
            OcorrenciaDetalhadaDTO ocorrenciaCriada = cadastrarOcorrencia.executar(cpfCliente, codEndereco, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaCriada);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

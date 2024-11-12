package com.bitbucket.diegoroberto.ocorrenciasapi.application.controller;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.EnderecoDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.service.EnderecoService;
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
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoDTO> criarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO novoEndereco = enderecoService.criarEndereco(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
    }

    @GetMapping("/{codEndereco}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable UUID codEndereco) {
        Optional<EnderecoDTO> endereco = enderecoService.buscarEndereco(codEndereco);
        return endereco.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> listarEnderecos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<EnderecoDTO> enderecos = enderecoService.listarEnderecos(page, size);
        return enderecos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(enderecos);
    }

    @PutMapping("/{codEndereco}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(
            @PathVariable UUID codEndereco, @RequestBody EnderecoDTO enderecoDTO) {
        try {
            EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(codEndereco, enderecoDTO);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{codEndereco}")
    public ResponseEntity<?> deletarEndereco(@PathVariable UUID codEndereco) {
        try {
            enderecoService.deletarEndereco(codEndereco);
            return ResponseEntity.accepted().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


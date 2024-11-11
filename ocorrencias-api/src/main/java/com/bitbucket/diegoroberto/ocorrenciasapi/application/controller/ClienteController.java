package com.bitbucket.diegoroberto.ocorrenciasapi.application.controller;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.ClienteDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.service.ClienteService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
            ClienteDTO novoCliente = clienteService.criarCliente(clienteDTO);
            if (novoCliente != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(clienteDTO);
    }

    @GetMapping("/{codCliente}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable UUID codCliente) {
        Optional<ClienteDTO> cliente = clienteService.buscarCliente(codCliente);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ClienteDTO> clientes = clienteService.listarClientes(page, size);
        return clientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clientes);
    }

    @PutMapping("/{codCliente}")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @PathVariable UUID codCliente, @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteAtualizado = clienteService.atualizarCliente(codCliente, clienteDTO);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{codCliente}")
    public ResponseEntity<?> deletarCliente(@PathVariable UUID codCliente) {
        try {
            clienteService.deletarCliente(codCliente);
            return ResponseEntity.accepted().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


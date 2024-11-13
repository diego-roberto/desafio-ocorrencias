package com.bitbucket.diegoroberto.ocorrenciasapi.application.service;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.ClienteDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.EntityMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.ClienteRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        clienteDTO.setDataCriacao(new Date());
        Cliente cliente = clienteRepository.save(EntityMapper.toCliente(clienteDTO));
        return DtoMapper.toClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO atualizarCliente(UUID codCliente, ClienteDTO clienteDTO) {
        return buscarCliente(codCliente).map(cliente -> {
            cliente.setNomeCliente(clienteDTO.getNomeCliente());
            cliente.setDataNascimento(clienteDTO.getDataNascimento());
            cliente.setNroCpf(clienteDTO.getNroCpf());
            Cliente clienteAtualizado = clienteRepository.saveAndFlush(EntityMapper.toCliente(cliente));
            return DtoMapper.toClienteDTO(clienteAtualizado);
        }).orElseThrow(() -> new ServiceException("Cliente não encontrado"));
    }

    public Optional<ClienteDTO> buscarCliente(UUID codCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(codCliente);
        return cliente.map(DtoMapper::toClienteDTO);
    }

    @Transactional
    public void deletarCliente(UUID codCliente) {
        Cliente cliente = clienteRepository.findByCodClienteAndAtivoTrue(codCliente)
                .orElseThrow(() -> new ServiceException("Cliente não encontrado ou já inativo"));
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    public Page<ClienteDTO> listarClientes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> clientesPage = clienteRepository.findAllByAtivoTrue(pageable);
        return clientesPage.map(DtoMapper::toClienteDTO);
    }

}

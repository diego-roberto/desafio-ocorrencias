package com.bitbucket.diegoroberto.ocorrenciasapi.application.service;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.EnderecoDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.EntityMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.EnderecoRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public EnderecoDTO criarEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.save(EntityMapper.toEndereco(enderecoDTO));
        return DtoMapper.toEnderecoDTO(endereco);
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(UUID codEndereco, EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(codEndereco)
                .orElseThrow(() -> new ServiceException("Endereço não encontrado"));
        endereco.setNomeLogradouro(enderecoDTO.getNomeLogradouro());
        endereco.setNomeBairro(enderecoDTO.getNomeBairro());
        endereco.setNumeroCep(enderecoDTO.getNumeroCep());
        endereco.setNomeCidade(enderecoDTO.getNomeCidade());
        endereco.setNomeEstado(enderecoDTO.getNomeEstado());
        Endereco enderecoAtualizado = enderecoRepository.saveAndFlush(endereco);
        return DtoMapper.toEnderecoDTO(enderecoAtualizado);
    }

    public Optional<EnderecoDTO> buscarEndereco(UUID codEndereco) {
        Optional<Endereco> endereco = enderecoRepository.findById(codEndereco);
        return endereco.map(DtoMapper::toEnderecoDTO);
    }

    @Transactional
    public void deletarEndereco(UUID codEndereco) {
        Endereco endereco = enderecoRepository.findById(codEndereco)
                .orElseThrow(() -> new ServiceException("Endereço não encontrado"));
        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

    public Page<EnderecoDTO> listarEnderecos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Endereco> enderecosPage = enderecoRepository.findAllByAtivoTrue(pageable);
        return enderecosPage.map(DtoMapper::toEnderecoDTO);
    }
}


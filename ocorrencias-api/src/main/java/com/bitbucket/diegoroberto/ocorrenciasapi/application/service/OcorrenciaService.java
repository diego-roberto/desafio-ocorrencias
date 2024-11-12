package com.bitbucket.diegoroberto.ocorrenciasapi.application.service;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.ClienteRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.EnderecoRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.FotoOcorrenciaRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
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
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    @Transactional
    public OcorrenciaDTO atualizarOcorrencia(UUID codOcorrencia, OcorrenciaDTO ocorrenciaDTO) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(codOcorrencia)
                .orElseThrow(() -> new ServiceException("Ocorrência não encontrada"));

        if (Boolean.FALSE.equals(ocorrencia.getStatusOcorrencia())) {
            throw new ServiceException("Não é possível atualizar uma ocorrência finalizada");
        }

        Cliente cliente = clienteRepository.findById(ocorrenciaDTO.getCodCliente())
                .orElseThrow(() -> new ServiceException("Cliente não encontrado"));

        Endereco endereco = enderecoRepository.findById(ocorrenciaDTO.getCodEndereco())
                .orElseThrow(() -> new ServiceException("Endereço não encontrado"));

        ocorrencia.setCliente(cliente);
        ocorrencia.setEndereco(endereco);
        ocorrencia.setDataOcorrencia(ocorrenciaDTO.getDataOcorrencia());
        Ocorrencia ocorrenciaAtualizada = ocorrenciaRepository.saveAndFlush(ocorrencia);
        return DtoMapper.toOcorrenciaDTO(ocorrenciaAtualizada);
    }


    public Optional<OcorrenciaDTO> buscarOcorrencia(UUID codOcorrencia) {
        try {
            Optional<Ocorrencia> dto = ocorrenciaRepository.findById(codOcorrencia);
            return dto.map(DtoMapper::toOcorrenciaDTO);
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar Ocorrência com Cod: " + codOcorrencia, e);
        }
    }

    public Page<OcorrenciaDTO> listarOcorrencias(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ocorrencia> ocorrenciasPage = ocorrenciaRepository.findAllBystatusOcorrenciaTrue(pageable);
        return ocorrenciasPage.map(DtoMapper::toOcorrenciaDTO);
    }

}

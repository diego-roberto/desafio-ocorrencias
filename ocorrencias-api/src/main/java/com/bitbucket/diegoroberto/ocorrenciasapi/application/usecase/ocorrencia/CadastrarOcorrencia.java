package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.EntityMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.ClienteRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.EnderecoRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CadastrarOcorrencia {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public OcorrenciaDTO executar(OcorrenciaDTO ocorrenciaDTO) {
        Cliente cliente = clienteRepository.findById(ocorrenciaDTO.getCodCliente())
                .orElseThrow(() -> new ServiceException("Cliente não encontrado"));

        Endereco endereco = enderecoRepository.findById(ocorrenciaDTO.getCodEndereco())
                .orElseThrow(() -> new ServiceException("Endereço não encontrado"));

        Ocorrencia ocorrencia = EntityMapper.toOcorrencia(ocorrenciaDTO, cliente, endereco);
        ocorrencia.setDataOcorrencia(new Date());
        ocorrencia.setStatusOcorrencia(true);

        Ocorrencia savedOcorrencia = ocorrenciaRepository.save(ocorrencia);
        return DtoMapper.toOcorrenciaDTO(savedOcorrencia);
    }

}

package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FinalizarOcorrencia {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Transactional
    public OcorrenciaDTO executar(UUID codOcorrencia) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(codOcorrencia)
                .orElseThrow(() -> new NoSuchElementException("Ocorrência não encontrada"));
        if (Boolean.FALSE.equals(ocorrencia.getStatusOcorrencia())) {
            return null;
        }
        ocorrencia.setStatusOcorrencia(false);
        return DtoMapper.toOcorrenciaDTO(ocorrenciaRepository.save(ocorrencia));
    }
}

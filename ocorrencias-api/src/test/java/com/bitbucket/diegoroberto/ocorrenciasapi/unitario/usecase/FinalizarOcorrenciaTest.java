package com.bitbucket.diegoroberto.ocorrenciasapi.unitario.usecase;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia.FinalizarOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinalizarOcorrenciaTest {

    @InjectMocks
    private FinalizarOcorrencia finalizarOcorrencia;

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    DtoMapper dtoMapper;

    private Ocorrencia ocorrencia;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ocorrencia = new Ocorrencia();
        ocorrencia.setCodOcorrencia(UUID.randomUUID());
        ocorrencia.setStatusOcorrencia(true);
        ocorrencia.setDataOcorrencia(new Date());
        ocorrencia.setCliente(new Cliente());
        ocorrencia.setEndereco(new Endereco());
    }

    @Test
    @DisplayName("Deve finalizar ocorrência")
    void testExecutar_FinalizaOcorrencia() {
        when(ocorrenciaRepository.findById(ocorrencia.getCodOcorrencia())).thenReturn(Optional.of(ocorrencia));
        when(ocorrenciaRepository.save(any(Ocorrencia.class))).thenReturn(ocorrencia);
        OcorrenciaDTO result = finalizarOcorrencia.executar(ocorrencia.getCodOcorrencia());

        assertEquals("Finalizada", result.getStatusOcorrencia());
        verify(ocorrenciaRepository, times(1)).save(any(Ocorrencia.class));
    }

    @Test
    @DisplayName("Deve impedir ocorrência finalizada de ser finalizada novamente")
    void testExecutar_OcorrenciaJaFinalizada() {
        ocorrencia.setStatusOcorrencia(false);
        when(ocorrenciaRepository.findById(ocorrencia.getCodOcorrencia())).thenReturn(Optional.of(ocorrencia));
        OcorrenciaDTO result = finalizarOcorrencia.executar(ocorrencia.getCodOcorrencia());

        assertNull(result);
        verify(ocorrenciaRepository, never()).save(any(Ocorrencia.class));
    }
}


package com.bitbucket.diegoroberto.ocorrenciasapi.unitario.usecase;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDetalhadaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia.ConsultarOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.StorageService;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.FotoOcorrenciaRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarOcorrenciaTest {

    @InjectMocks
    private ConsultarOcorrencia consultarOcorrencia;

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Mock
    private StorageService storageService;

    @Mock
    DtoMapper dtoMapper;

    private Cliente cliente;
    private Ocorrencia ocorrencia;
    private FotoOcorrencia fotoOcorrencia;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10, Sort.by("dataOcorrencia").ascending());

        cliente = Cliente.builder()
                .codCliente(UUID.randomUUID())
                .nomeCliente("Cliente Teste")
                .build();

        ocorrencia = Ocorrencia.builder()
                .codOcorrencia(UUID.randomUUID())
                .statusOcorrencia(true)
                .dataOcorrencia(new Date())
                .cliente(cliente)
                .endereco(new Endereco())
                .build();

        fotoOcorrencia = FotoOcorrencia.builder()
                .codFotoOcorrencia(UUID.randomUUID())
                .ocorrencia(ocorrencia)
                .dataCriacao(new Date())
                .pathBucket("path/to/image")
                .hash("fakeHash")
                .build();
    }

    @Test
    @DisplayName("Deve consultar ocorrência")
    void testConsultarOcorrencias_SemFiltros() {
        Page<Ocorrencia> ocorrenciaPage = new PageImpl<>(Collections.singletonList(ocorrencia));

        when(ocorrenciaRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(ocorrenciaPage);
        when(fotoOcorrenciaRepository.findByOcorrencia_CodOcorrencia(ocorrencia.getCodOcorrencia()))
                .thenReturn(Collections.singletonList(fotoOcorrencia));
        when(storageService.generatePresignedUrl(anyString())).thenReturn("presignedUrl");

        Page<OcorrenciaDetalhadaDTO> result = consultarOcorrencia.consultarOcorrencias(
                null, null, null, null, "dataOcorrencia", "asc", 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Cliente Teste", result.getContent().get(0).getCliente().getNomeCliente());
        verify(ocorrenciaRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(fotoOcorrenciaRepository, times(1)).findByOcorrencia_CodOcorrencia(ocorrencia.getCodOcorrencia());
        verify(storageService, times(1)).generatePresignedUrl(fotoOcorrencia.getPathBucket());
    }

    @Test
    @DisplayName("Deve consultar ocorrências com filtro por nome do cliente")
    void testConsultarOcorrencias_ComFiltroNomeCliente() {
        Page<Ocorrencia> ocorrenciaPage = new PageImpl<>(Collections.singletonList(ocorrencia));

        when(ocorrenciaRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(ocorrenciaPage);
        when(fotoOcorrenciaRepository.findByOcorrencia_CodOcorrencia(ocorrencia.getCodOcorrencia()))
                .thenReturn(Collections.singletonList(fotoOcorrencia));
        when(storageService.generatePresignedUrl(anyString())).thenReturn("presignedUrl");

        Page<OcorrenciaDetalhadaDTO> result = consultarOcorrencia.consultarOcorrencias(
                "Cliente Teste", null, null, null, "dataOcorrencia", "asc", 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Cliente Teste", result.getContent().get(0).getCliente().getNomeCliente());
        verify(ocorrenciaRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(fotoOcorrenciaRepository, times(1)).findByOcorrencia_CodOcorrencia(ocorrencia.getCodOcorrencia());
        verify(storageService, times(1)).generatePresignedUrl(fotoOcorrencia.getPathBucket());
    }

}


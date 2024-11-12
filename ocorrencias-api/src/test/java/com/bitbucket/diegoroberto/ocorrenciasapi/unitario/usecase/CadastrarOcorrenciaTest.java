package com.bitbucket.diegoroberto.ocorrenciasapi.unitario.usecase;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDetalhadaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia.CadastrarOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.StorageService;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.ClienteRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.EnderecoRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.FotoOcorrenciaRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarOcorrenciaTest {

    @InjectMocks
    private CadastrarOcorrencia cadastrarOcorrencia;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Mock
    private StorageService storageService;

    @Mock
    private DtoMapper dtoMapper;

    private Cliente cliente;
    private Endereco endereco;
    private Ocorrencia ocorrencia;
    private FotoOcorrencia fotoOcorrencia;
    private MockMultipartFile imagem;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        cliente = Cliente.builder()
                .codCliente(UUID.randomUUID())
                .nroCpf("12345678901")
                .nomeCliente("Cliente Teste")
                .build();

        endereco = Endereco.builder()
                .codEndereco(UUID.randomUUID())
                .nomeLogradouro("Rua Teste")
                .nomeCidade("Cidade Teste")
                .nomeBairro("Bairro Teste")
                .numeroCep("12345678")
                .nomeEstado("Estado Teste")
                .build();

        ocorrencia = Ocorrencia.builder()
                .codOcorrencia(UUID.randomUUID())
                .cliente(cliente)
                .endereco(endereco)
                .dataOcorrencia(new Date())
                .statusOcorrencia(true)
                .build();

        fotoOcorrencia = FotoOcorrencia.builder()
                .codFotoOcorrencia(UUID.randomUUID())
                .ocorrencia(ocorrencia)
                .pathBucket("path/to/image")
                .hash("fakeHash")
                .dataCriacao(new Date())
                .build();

        imagem = new MockMultipartFile("imagem", "imagem.jpg", "image/jpeg", "fake_image_content".getBytes());
        ReflectionTestUtils.setField(cadastrarOcorrencia, "bucketName", "test-bucket");
    }

    @Test
    @DisplayName("Deve cadastrar uma ocorrência com imagem")
    void testCadastrarOcorrenciaComImagem() throws Exception {
        when(clienteRepository.findByNroCpf(cliente.getNroCpf())).thenReturn(Optional.of(cliente));
        when(enderecoRepository.findById(endereco.getCodEndereco())).thenReturn(Optional.of(endereco));
        when(ocorrenciaRepository.save(any(Ocorrencia.class))).thenReturn(ocorrencia);
        when(fotoOcorrenciaRepository.save(any(FotoOcorrencia.class))).thenReturn(fotoOcorrencia);
        when(fotoOcorrenciaRepository.findByOcorrencia_CodOcorrencia(ocorrencia.getCodOcorrencia()))
                .thenReturn(Collections.singletonList(fotoOcorrencia));
        when(storageService.generateHash(any())).thenReturn("fakeHash");
        doNothing().when(storageService).uploadFile(anyString(), anyString(), any(InputStream.class), anyString());

        OcorrenciaDetalhadaDTO result = cadastrarOcorrencia.executar(cliente.getNroCpf(), endereco.getCodEndereco(), imagem);

        assertNotNull(result);
        assertEquals(cliente.getNomeCliente(), result.getCliente().getNomeCliente());
        assertEquals(endereco.getNomeCidade(), result.getEndereco().getNomeCidade());
        assertEquals(1, result.getFotos().size());
        assertEquals("fakeHash", result.getFotos().get(0).getHash());

        verify(clienteRepository, times(1)).findByNroCpf(cliente.getNroCpf());
        verify(enderecoRepository, times(1)).findById(endereco.getCodEndereco());
        verify(ocorrenciaRepository, times(1)).save(any(Ocorrencia.class));
        verify(storageService, times(1)).generateHash(any());
        verify(storageService, times(1)).uploadFile(anyString(), anyString(), any(InputStream.class), anyString());
    }
}


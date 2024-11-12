package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDetalhadaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.StorageService;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.ClienteRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.EnderecoRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.FotoOcorrenciaRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CadastrarOcorrencia {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Autowired
    private StorageService storageService;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Transactional
    public OcorrenciaDetalhadaDTO executar(String cpfCliente, UUID codEndereco, MultipartFile imagem) throws Exception {
        Cliente cliente = clienteRepository.findByNroCpf(cpfCliente)
                .orElseThrow(() -> new ServiceException("Cliente não encontrado"));

        Endereco endereco = enderecoRepository.findById(codEndereco)
                .orElseThrow(() -> new ServiceException("Endereço não encontrado"));

        Ocorrencia ocorrencia = Ocorrencia.builder()
                .cliente(cliente)
                .endereco(endereco)
                .dataOcorrencia(new Date())
                .statusOcorrencia(true)
                .build();

        Ocorrencia ocorrenciaSalva = ocorrenciaRepository.save(ocorrencia);

        String objectName = "ocorrencia-" + ocorrenciaSalva.getCodOcorrencia() + "/" + imagem.getOriginalFilename();
        storageService.uploadFile(bucketName, objectName, imagem.getInputStream(), imagem.getContentType());

        String hashImagem = storageService.generateHash(imagem.getBytes());

        FotoOcorrencia fotoOcorrencia = FotoOcorrencia.builder()
                .ocorrencia(ocorrenciaSalva)
                .pathBucket(objectName)
                .hash(hashImagem)
                .dataCriacao(new Date())
                .build();

        fotoOcorrenciaRepository.save(fotoOcorrencia);
        List<FotoOcorrencia> fotos = fotoOcorrenciaRepository.findByOcorrencia_CodOcorrencia(ocorrenciaSalva.getCodOcorrencia());

        return DtoMapper.toOcorrenciaDetalhadaDTO(ocorrenciaSalva, fotos);
    }

}

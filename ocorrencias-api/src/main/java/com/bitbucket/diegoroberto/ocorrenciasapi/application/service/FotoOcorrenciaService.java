package com.bitbucket.diegoroberto.ocorrenciasapi.application.service;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.FotoOcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.StorageService;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.FotoOcorrenciaRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FotoOcorrenciaService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public List<FotoOcorrenciaDTO> uploadFotos(UUID codOcorrencia, List<MultipartFile> files) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(codOcorrencia)
                .orElseThrow(() -> new ServiceException("Ocorrência não encontrada"));

        return files.stream().map(file -> {
            try {
                String objectName = "ocorrencia-" + codOcorrencia + "/" + file.getOriginalFilename();
                InputStream inputStream = file.getInputStream();

                    /* upload para o MinIO */
                storageService.uploadFile(bucketName, objectName, inputStream, file.getContentType());

                FotoOcorrencia foto = FotoOcorrencia.builder()
                        .ocorrencia(ocorrencia)
                        .pathBucket(objectName)
                        .dataCriacao(new Date())
                        .hash(storageService.generateHash(file.getBytes()))
                        .build();
                FotoOcorrencia savedFoto = fotoOcorrenciaRepository.save(foto);
                return DtoMapper.toFotoOcorrenciaDTO(savedFoto);

            } catch (Exception e) {
                throw new RuntimeException("Erro ao fazer upload da imagem: " + file.getOriginalFilename(), e);
            }
        }).collect(Collectors.toList());
    }

}

package com.bitbucket.diegoroberto.ocorrenciasapi.application.usecase.ocorrencia;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.FotoOcorrenciaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.OcorrenciaDetalhadaDTO;
import com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper.DtoMapper;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.specification.OcorrenciaSpecifications;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.StorageService;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.FotoOcorrenciaRepository;
import com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultarOcorrencia {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private FotoOcorrenciaRepository fotoOcorrenciaRepository;

    @Autowired
    private StorageService storageService;

    public Page<OcorrenciaDetalhadaDTO> consultarOcorrencias(
            String nomeCliente, String cpfCliente, LocalDate dataOcorrencia, String cidadeOcorrencia,
            String sortBy, String order, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));

        Specification<Ocorrencia> spec = Specification
                .where(nomeCliente != null ? OcorrenciaSpecifications.hasNomeCliente(nomeCliente) : null)
                .and(cpfCliente != null ? OcorrenciaSpecifications.hasCpfCliente(cpfCliente) : null)
                .and(dataOcorrencia != null ? OcorrenciaSpecifications.hasDataOcorrencia(dataOcorrencia) : null)
                .and(cidadeOcorrencia != null ? OcorrenciaSpecifications.hasCidadeOcorrencia(cidadeOcorrencia) : null);

        Page<Ocorrencia> ocorrencias = ocorrenciaRepository.findAll(spec, pageable);

        return ocorrencias.map(ocorrencia -> {
            List<FotoOcorrencia> fotos = fotoOcorrenciaRepository.findByOcorrencia_CodOcorrencia(ocorrencia.getCodOcorrencia());
            List<FotoOcorrenciaDTO> fotoDTOs = fotos.stream()
                    .map(foto -> {
                        String presignedUrl = storageService.generatePresignedUrl(foto.getPathBucket());
                        FotoOcorrenciaDTO fotoDTO = DtoMapper.toFotoOcorrenciaDTO(foto);
                        fotoDTO.setPathBucket(presignedUrl);
                        return fotoDTO;
                    })
                    .collect(Collectors.toList());

            return DtoMapper.toOcorrenciaDetalhadaDTOWithFotosDTOList(ocorrencia, fotoDTOs);
        });
    }
}

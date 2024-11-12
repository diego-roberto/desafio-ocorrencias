package com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository;

import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FotoOcorrenciaRepository extends JpaRepository<FotoOcorrencia, UUID> {
    List<FotoOcorrencia> findByOcorrencia_CodOcorrencia(UUID codOcorrencia);

    Optional<FotoOcorrencia> findBycodFotoOcorrenciaAndOcorrencia_CodOcorrencia(UUID codFotoOcorrencia, UUID codOcorrencia);
}


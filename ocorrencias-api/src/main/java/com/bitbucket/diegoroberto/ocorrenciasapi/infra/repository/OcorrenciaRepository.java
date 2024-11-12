package com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository;

import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, UUID> {
    
    Page<Ocorrencia> findAllBystatusOcorrenciaTrue(Pageable pageable);

}


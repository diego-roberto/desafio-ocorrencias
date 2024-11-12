package com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository;

import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

    Page<Endereco> findAllByAtivoTrue(Pageable pageable);

}


package com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository;

import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Page<Cliente> findAll(Pageable pageable);

}

package com.bitbucket.diegoroberto.ocorrenciasapi.infra.repository;

import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, UUID> {

    List<Ocorrencia> findByClienteNomeClienteContainingAndClienteNroCpfContainingAndDataOcorrenciaAndEnderecoNomeCidade(
            String nomeCliente,
            String nroCpf,
            Date dataOcorrencia,
            String nomeCidade
    );

    List<Ocorrencia> findByOrderByDataOcorrenciaAscEnderecoNomeCidadeAsc();
    List<Ocorrencia> findByOrderByDataOcorrenciaDescEnderecoNomeCidadeDesc();
}


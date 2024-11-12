package com.bitbucket.diegoroberto.ocorrenciasapi.domain.specification;

import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class OcorrenciaSpecifications {

    public static Specification<Ocorrencia> hasNomeCliente(String nomeCliente) {
        return (Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("cliente").get("nomeCliente"), "%" + nomeCliente + "%");
    }

    public static Specification<Ocorrencia> hasCpfCliente(String cpfCliente) {
        return (Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cliente").get("nroCpf"), cpfCliente);
    }

    public static Specification<Ocorrencia> hasDataOcorrencia(LocalDate dataOcorrencia) {
        return (Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Date startOfDay = Date.from(dataOcorrencia.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endOfDay = Date.from(dataOcorrencia.atTime(23, 59, 59, 999999999).atZone(ZoneId.systemDefault()).toInstant());
            return criteriaBuilder.between(root.get("dataOcorrencia"), startOfDay, endOfDay);
        };
    }

    public static Specification<Ocorrencia> hasCidadeOcorrencia(String cidadeOcorrencia) {
        return (Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(root.get("endereco").get("nomeCidade"), "%" + cidadeOcorrencia + "%");
    }
}


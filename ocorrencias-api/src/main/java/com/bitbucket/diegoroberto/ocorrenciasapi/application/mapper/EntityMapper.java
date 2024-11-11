package com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.*;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public static Cliente toCliente(ClienteDTO dto) {
        return Cliente.builder()
            .codCliente(dto.getCodCliente())
            .nomeCliente(dto.getNomeCliente())
            .nroCpf(dto.getNroCpf())
            .dataNascimento(dto.getDataNascimento())
            .dataCriacao(dto.getDataCriacao())
            .build();
    }

    public static Endereco toEndereco(EnderecoDTO dto) {
        return Endereco.builder()
            .codEndereco(dto.getCodEndereco())
            .nomeLogradouro(dto.getNomeLogradouro())
            .nomeBairro(dto.getNomeBairro())
            .numeroCep(dto.getNumeroCep())
            .nomeCidade(dto.getNomeCidade())
            .nomeEstado(dto.getNomeEstado())
            .build();
    }

    public static Ocorrencia toOcorrencia(OcorrenciaDTO dto, Cliente cliente, Endereco endereco) {
        return Ocorrencia.builder()
            .codOcorrencia(dto.getCodOcorrencia())
            .cliente(cliente)
            .endereco(endereco)
            .dataOcorrencia(dto.getDataOcorrencia())
            .statusOcorrencia("Ativo".equals(dto.getStatusOcorrencia()))
            .build();
    }

    public static FotoOcorrencia toFotoOcorrencia(FotoOcorrenciaDTO dto, Ocorrencia ocorrencia) {
        return FotoOcorrencia.builder()
            .codFotoOcorrencia(dto.getCodFotoOcorrencia())
            .ocorrencia(ocorrencia)
            .dataCriacao(dto.getDataCriacao())
            .pathBucket(dto.getPathBucket())
            .hash(dto.getHash())
            .build();
    }
}

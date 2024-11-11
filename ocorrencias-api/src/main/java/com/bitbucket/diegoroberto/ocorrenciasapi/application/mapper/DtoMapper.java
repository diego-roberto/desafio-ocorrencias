package com.bitbucket.diegoroberto.ocorrenciasapi.application.mapper;

import com.bitbucket.diegoroberto.ocorrenciasapi.application.dto.*;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Cliente;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Endereco;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.FotoOcorrencia;
import com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity.Ocorrencia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public static ClienteDTO toClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
            .codCliente(cliente.getCodCliente())
            .nomeCliente(cliente.getNomeCliente())
            .nroCpf(cliente.getNroCpf())
            .dataNascimento(cliente.getDataNascimento())
            .dataCriacao(cliente.getDataCriacao())
            .build();
    }

    public static EnderecoDTO toEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
            .codEndereco(endereco.getCodEndereco())
            .nomeLogradouro(endereco.getNomeLogradouro())
            .nomeBairro(endereco.getNomeBairro())
            .numeroCep(endereco.getNumeroCep())
            .nomeCidade(endereco.getNomeCidade())
            .nomeEstado(endereco.getNomeEstado())
            .build();
    }

    public static OcorrenciaDTO toOcorrenciaDTO(Ocorrencia ocorrencia) {
        return OcorrenciaDTO.builder()
            .codOcorrencia(ocorrencia.getCodOcorrencia())
            .codCliente(ocorrencia.getCliente().getCodCliente())
            .codEndereco(ocorrencia.getEndereco().getCodEndereco())
            .dataOcorrencia(ocorrencia.getDataOcorrencia())
            .statusOcorrencia(Boolean.TRUE.equals(ocorrencia.getStatusOcorrencia()) ? "Ativa" : "Finalizada")
            .build();
    }

    public static FotoOcorrenciaDTO toFotoOcorrenciaDTO(FotoOcorrencia foto) {
        return FotoOcorrenciaDTO.builder()
            .codFotoOcorrencia(foto.getCodFotoOcorrencia())
            .codOcorrencia(foto.getOcorrencia().getCodOcorrencia())
            .dataCriacao(foto.getDataCriacao())
            .pathBucket(foto.getPathBucket())
            .hash(foto.getHash())
            .build();
    }

    public static OcorrenciaDetalhadaDTO toOcorrenciaDetalhadaDTO(Ocorrencia ocorrencia, List<FotoOcorrencia> fotos) {
        return OcorrenciaDetalhadaDTO.builder()
            .codOcorrencia(ocorrencia.getCodOcorrencia())
            .dataOcorrencia(ocorrencia.getDataOcorrencia())
            .cliente(toClienteDTO(ocorrencia.getCliente()))
            .endereco(toEnderecoDTO(ocorrencia.getEndereco()))
            .statusOcorrencia(Boolean.TRUE.equals(ocorrencia.getStatusOcorrencia()) ? "Ativa" : "Finalizada")
            .fotos(mapeiaListaFotos(fotos))
            .build();
    }

    public static List<FotoOcorrenciaDTO> mapeiaListaFotos(List<FotoOcorrencia> fotos) {
        return fotos.stream()
                .map(DtoMapper::toFotoOcorrenciaDTO)
                .collect(Collectors.toList());
    }
}

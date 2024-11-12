package com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "endereco")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue
    private UUID codEndereco;

    @Column(name = "nme_logradouro", nullable = false)
    private String nomeLogradouro;

    @Column(name = "dta_bairro")
    private String nomeBairro;

    @Column(name = "nro_cep", nullable = false)
    private String numeroCep;

    @Column(name = "nme_cidade", nullable = false)
    private String nomeCidade;

    @Column(name = "nme_estado", nullable = false)
    private String nomeEstado;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

}

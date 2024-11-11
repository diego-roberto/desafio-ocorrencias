package com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue
    private UUID codCliente;

    @Column(name = "nme_cliente", nullable = false)
    private String nomeCliente;

    @Column(name = "dta_nascimento")
    private Date dataNascimento;

    @Column(name = "nro_cpf", unique = true, nullable = false)
    private String nroCpf;

    @Column(name = "dta_criacao")
    private Date dataCriacao;

}
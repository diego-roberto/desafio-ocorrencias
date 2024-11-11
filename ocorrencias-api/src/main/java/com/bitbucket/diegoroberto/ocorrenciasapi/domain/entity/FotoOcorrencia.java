package com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "foto_ocorrencia")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FotoOcorrencia {

    @Id
    @GeneratedValue
    private UUID codFotoOcorrencia;

    @ManyToOne
    @JoinColumn(name = "cod_ocorrencia", nullable = false)
    private Ocorrencia ocorrencia;

    @Column(name = "dta_criacao")
    private Date dataCriacao;

    @Column(name = "dsc_path_bucket", nullable = false)
    private String pathBucket;

    @Column(name = "dsc_hash", nullable = false)
    private String hash;

}

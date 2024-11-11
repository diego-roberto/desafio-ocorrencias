package com.bitbucket.diegoroberto.ocorrenciasapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "ocorrencia")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue
    private UUID codOcorrencia;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "cod_endereco", nullable = false)
    private Endereco endereco;

    @Column(name = "dta_ocorrencia")
    private Date dataOcorrencia;

    @Column(name = "sta_ocorrencia", nullable = false)
    private Boolean statusOcorrencia;

}

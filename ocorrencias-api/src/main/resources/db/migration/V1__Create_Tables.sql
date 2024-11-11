CREATE TABLE cliente (
     cod_cliente UUID PRIMARY KEY,
     nme_cliente VARCHAR(255) NOT NULL,
     dta_nascimento DATE,
     nro_cpf VARCHAR(11) UNIQUE NOT NULL,
     dta_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE endereco (
      cod_endereco UUID PRIMARY KEY,
      nme_logradouro VARCHAR(255) NOT NULL,
      dta_bairro VARCHAR(255),
      nro_cep VARCHAR(8) NOT NULL,
      nme_cidade VARCHAR(255) NOT NULL,
      nme_estado VARCHAR(255) NOT NULL
);

CREATE TABLE ocorrencia (
    cod_ocorrencia UUID PRIMARY KEY,
    cod_cliente UUID NOT NULL,
    cod_endereco UUID NOT NULL,
    dta_ocorrencia TIMESTAMP,
    sta_ocorrencia BOOLEAN NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cod_cliente) REFERENCES cliente (cod_cliente),
    CONSTRAINT fk_endereco FOREIGN KEY (cod_endereco) REFERENCES endereco (cod_endereco)
);

CREATE TABLE foto_ocorrencia (
     cod_foto_ocorrencia UUID PRIMARY KEY,
     cod_ocorrencia UUID NOT NULL,
     dta_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     dsc_path_bucket VARCHAR(255) NOT NULL,
     dsc_hash VARCHAR(255) NOT NULL,
     CONSTRAINT fk_ocorrencia FOREIGN KEY (cod_ocorrencia) REFERENCES ocorrencia (cod_ocorrencia)
);

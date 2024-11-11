-- inserindo clientes
INSERT INTO cliente (cod_cliente, nme_cliente, dta_nascimento, nro_cpf) VALUES
    (gen_random_uuid(), 'João da Silva', '1985-10-12', '12345678901'),
    (gen_random_uuid(), 'Maria Oliveira', '1990-05-25', '98765432100');

-- inserindo endereços
INSERT INTO endereco (cod_endereco, nme_logradouro, dta_bairro, nro_cep, nme_cidade, nme_estado) VALUES
    (gen_random_uuid(), 'Rua A', 'Bairro X', '12345000', 'Cidade A', 'Estado A'),
    (gen_random_uuid(), 'Avenida B', 'Bairro Y', '54321000', 'Cidade B', 'Estado B');

-- inserindo ocorrências
INSERT INTO ocorrencia (cod_ocorrencia, cod_cliente, cod_endereco, dta_ocorrencia, sta_ocorrencia) VALUES
    (gen_random_uuid(), (SELECT cod_cliente FROM cliente LIMIT 1), (SELECT cod_endereco FROM endereco LIMIT 1), CURRENT_TIMESTAMP, TRUE),
    (gen_random_uuid(), (SELECT cod_cliente FROM cliente LIMIT 1 OFFSET 1), (SELECT cod_endereco FROM endereco LIMIT 1 OFFSET 1), CURRENT_TIMESTAMP, TRUE);

-- inserindo fotos de ocorrências
INSERT INTO foto_ocorrencia (cod_foto_ocorrencia, cod_ocorrencia, dta_criacao, dsc_path_bucket, dsc_hash) VALUES
    (gen_random_uuid(), (SELECT cod_ocorrencia FROM ocorrencia LIMIT 1), CURRENT_TIMESTAMP, 'path/to/photo1.jpg', 'hash1'),
    (gen_random_uuid(), (SELECT cod_ocorrencia FROM ocorrencia LIMIT 1 OFFSET 1), CURRENT_TIMESTAMP, 'path/to/photo2.jpg', 'hash2');

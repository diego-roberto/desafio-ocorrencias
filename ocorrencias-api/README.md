# Desafio Técnico | Back-end | Java

<h3>
<img src="https://img.shields.io/badge/Java-C71A00?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
<img src="https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=JUnit5&logoColor=white"/>
<img src="https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white"/>
<img src="https://img.shields.io/badge/MinIO-C72E49.svg?style=for-the-badge&logo=MinIO&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white"/>
</h3>

API em Java 17 com spring-boot 3.3.5</br>
Base de dados PostgreSQL 13 </br>
Storage em MinIO </br>
Spring Security 6.3.4 com JWT </br>
Versionamento de DB com Flyway 10 </br>
Testes com JUnit 5.10.2 e Mockito </br>
API, DB e Storage containerizados com Docker e orquestrados com docker-compose
</br>

## Executando em ambiente local com Docker 🐋
A partir da pasta raiz do projeto, onde se encontra o arquivo docker-compose.yml, execute o comando para iniciar o container:
> docker-compose up --build
>

### Requisições:
<a href="https://postman.com" target="_blank" rel="noreferrer">
  <img src="https://www.vectorlogo.zone/logos/getpostman/getpostman-icon.svg" alt="postman" width="28"
    height="28" />
</a> </br>
Há um arquivo de collections para importação das requisições em <br> ocorrencias-api/src/main/resources/assets/<b>desafio-ocorrencias.postman_collection.json</b> </br>
Foi configurado um script para copiar o valor do token para uma variável no postman:
<p align="center">
  <img width="800" height="320" src="src/main/resources/assets/postman_collection.png">
</p>

Feito isso, verifique se os containers estão em execução e faça uma requisição na API 
utilizando Postman, ou outro software de sua preferência:
> localhost:8080/auth/login
`{
    "username": "user",
    "password": "secreta"
}`
</br>

# Modelagem de dados

<p align="center">
  <img width="460" height="400" src="src/main/resources/assets/db.png">
</p>
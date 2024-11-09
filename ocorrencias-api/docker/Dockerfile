FROM openjdk:17-jdk-slim

RUN apt update && apt upgrade -y && apt install dos2unix && \
    apt-get update

COPY . .
RUN dos2unix ./mvnw
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests=true

FROM openjdk:17-jdk-slim
RUN apt update && apt upgrade -y && apt install -y locales && \
    apt-get update
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY --from=0 /target/*.jar  /ocorrencias-api.jar

EXPOSE 8080
CMD ["java", "-Xms256m", "-Xmx768m", "-jar", "/ocorrencias-api.jar"]
# Documentação Técnica - Backend Itaú

## Visão Geral
Este é um projeto backend desenvolvido em Java com Spring Boot que implementa uma API REST para gerenciamento de transações financeiras. O sistema permite criar transações, obter estatísticas e realizar verificações de saúde da aplicação. 

Link do desafio: https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior/tree/main?tab=readme-ov-file

## Requisitos Técnicos
- Java 17
- Spring Boot 3.4.3
- Maven
- Docker (opcional)

## Estrutura do Projeto
```
src/
├── main/
│   ├── java/com/bernardomerlo/
│   │   ├── controllers/
│   │   ├── entities/
│   │   ├── exceptions/
│   │   └── services/
│   └── resources/
└── test/
```

## Endpoints da API

### 1. Saúde da Aplicação
```
GET /health
```
Retorna informações sobre o status da aplicação:
- Status atual
- Tempo de execução
- Ambiente atual

### 2. Gerenciamento de Transações
```
POST /transacao
```
Cria uma nova transação.

Corpo da requisição:
```json
{
    "valor": 100.50,
    "dataHora": "2024-03-06T10:30:00-03:00"
}
```

```
DELETE /transacao
```
Remove todas as transações armazenadas.

### 3. Estatísticas
```
GET /estatistica?seconds=60
```
Retorna estatísticas das transações dos últimos X segundos (padrão: 60 segundos):
- count: quantidade de transações
- sum: soma total
- avg: média dos valores
- min: valor mínimo
- max: valor máximo

## Regras de Negócio

1. **Validação de Transações**:
   - Valores não podem ser negativos
   - Data/hora não pode ser futura
   - Campos não podem ser nulos

2. **Estatísticas**:
   - Calculadas apenas com transações dos últimos X segundos
   - Período de tempo deve ser positivo

## Como Executar

### Localmente com Maven
```bash
# Compilar o projeto
mvn clean package

# Executar
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Com Docker
```bash
# Construir a imagem
docker build -t backend-itau .

# Executar o container
docker run -p 8080:8080 backend-itau
```

## Configurações
O arquivo `application.properties` contém as seguintes configurações:
```properties
spring.application.name=backend-itau
management.endpoints.web.exposure.include=health,info
spring.profiles.active=development
logging.level.root=DEBUG
logging.level.com.bernardomerlo=DEBUG
logging.file.name=app.log
```

## Testes
O projeto inclui testes unitários para:
- Validação de transações
- Cálculo de estatísticas
- Gerenciamento de transações

Para executar os testes:
```bash
mvn test
```

## Logs
- Os logs são gravados no arquivo `app.log`
- Nível de log configurado como DEBUG para desenvolvimento
- Inclui logs detalhados das operações de transação e estatísticas

## Tratamento de Erros
O sistema possui tratamento para os seguintes erros:
- `InvalidTimeException`: Quando a data da transação é futura
- `InvalidValueException`: Quando o valor é negativo
- `NullValuesException`: Quando campos obrigatórios estão nulos
- `InvalidTimeRangeException`: Quando o período para estatísticas é inválido

## Monitoramento
A aplicação expõe endpoints de monitoramento através do Spring Actuator:
- `/actuator/health`: Status da aplicação
- `/actuator/info`: Informações da aplicação

## Ambiente de Desenvolvimento
O projeto está configurado com:
- Spring DevTools para hot reload
- Perfil de desenvolvimento ativo por padrão
- Logging detalhado para debugging

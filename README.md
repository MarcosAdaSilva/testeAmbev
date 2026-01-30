# Sistema de Gerenciamento de Pedidos - Teste Técnico Ambev

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Sistema de gerenciamento de pedidos desenvolvido como teste técnico para vaga de **Java Sênior** na Ambev. A aplicação gerencia pedidos, calcula valores de produtos e integra-se com sistemas externos de produtos.

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)

## Sobre o Projeto

Este projeto implementa um serviço de gerenciamento de pedidos (`order`) que:

- Recebe pedidos de produtos de diferentes fontes (interno, externo A, externo B)
- Calcula o valor total dos pedidos somando os valores de cada produto
- Disponibiliza consultas de pedidos com status para sistemas externos
- Suporta alta volumetria (150-200 mil pedidos/dia)

1. **Seção de Testes completa** com:
   - Como executar testes
   - Comandos Maven
   - Cobertura de testes
   - Tecnologias utilizadas
   - Exemplo de teste

2.  **Tecnologias de teste** adicionadas na seção de tecnologias:
   - JUnit 5
   - Mockito
   - AssertJ

3.  **Instruções claras** para executar no IntelliJ, via Maven e para isso precisa subir um banco de dados Postgres e criar a conexão.
4.  A forma mais fácil de executar a aplicação é usando Docker Compose, que sobe automaticamente a aplicação e o PostgreSQL.
   ## 🐳 Docker
### Executar com Docker Compose (Recomendado)
#### Pré-requisitos
- Docker Desktop instalado
#### Comandos
**Subir a aplicação:**
```bash`````
docker-compose up -d
**Parar a aplicação:**
docker-compose down

### Requisitos Atendidos

 Integração com Produto Externo A  
 Integração com Produto Externo B  
 Cálculo de valores por produto e total do pedido  
 Consulta de pedidos com status  
 Armazenamento em banco de dados PostgreSQL  
 Verificação de duplicação de pedidos  
 Otimizações para alta volumetria  
 Garantia de consistência e concorrência  

##  Funcionalidades

### Principais

- **Criação de Pedidos**: Cria pedidos com múltiplos produtos de diferentes fontes
- **Cálculo Automático**: Calcula subtotal por item e total do pedido
- **Consulta de Pedidos**: Busca por ID, número do pedido ou lista todos
- **Validação**: Valida duplicação e dados de entrada
- **Tratamento de Erros**: Respostas padronizadas para erros

### Produtos Disponíveis

**Produtos Internos (PROD-)**
- PROD-001: Cerveja Brahma 350ml - R$ 3,50
- PROD-002: Cerveja Skol 350ml - R$ 3,20
- PROD-003: Cerveja Antarctica 350ml - R$ 3,30

**Produtos Externos A (EXT-A-)**
- EXT-A-001: Refrigerante Coca-Cola 2L - R$ 8,50
- EXT-A-002: Refrigerante Guaraná 2L - R$ 7,80

**Produtos Externos B (EXT-B-)**
- EXT-B-001: Água Mineral 500ml - R$ 2,50
- EXT-B-002: Suco Natural 1L - R$ 6,90

##  Arquitetura

O projeto segue os princípios **SOLID** e utiliza **Design Patterns** para garantir código limpo e manutenível.
Desenho da solução proposta:
┌─────────────────────────────────────────┐
│         OrderController (REST)          │
│    POST /api/orders                     │
│    GET  /api/orders                     │
│    GET  /api/orders/{id}                │
│    GET  /api/orders/number/{number}     │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│           OrderService                  │
│  - createOrder()                        │
│  - getOrder()                           │
│  - getAllOrders()                       │
│  - validateOrderUniqueness()            │
└──────┬──────────────────────┬───────────┘
       │                      │
       ▼                      ▼
┌─────────────────┐  ┌──────────────────────┐
│ OrderRepository │  │ ProductServiceResolver│
│   (JPA)         │  │   (Strategy Pattern)  │
└─────────────────┘  └──────┬───────────────┘
                            │
              ┌─────────────┼─────────────┐
              ▼             ▼             ▼
       ┌──────────┐  ┌──────────┐  ┌──────────┐
       │ Internal │  │External A│  │External B│
       │ Product  │  │ Product  │  │ Product  │
       │ Service  │  │ Service  │  │ Service  │
       └──────────┘  └──────────┘  └──────────┘
              │             │             │
              └─────────────┴─────────────┘
                          │
                          ▼
                  ┌───────────────┐
                  │  PostgreSQL   │
                  │   Database    │
                  └───────────────┘



 ## Tecnologias Utilizadas
- **Docker**
- **Java 17**
- **Spring Boot 3.2.1**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Cache
- **PostgreSQL** (banco de dados)
- **Lombok** (redução de boilerplate)
- **Hikari** (connection pool)
- **Maven** (gerenciamento de dependências)
- **JUnit 5** (testes unitários)
- **Mockito** (mocks para testes)
- **AssertJ** (assertions fluentes)
 
## Pré-requisitos
 
- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+
- Git                 

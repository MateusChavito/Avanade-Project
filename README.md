# Projeto Avanade - Sistema de Gerenciamento de Dados Financeiros

Este projeto foi desenvolvido como parte de um desafio técnico para backend, utilizando **Java com Spring Boot**.

## Funcionalidades

- **Cadastro e consulta de usuários**, com informações sobre **conta bancária** e **cartões de crédito**.
- **Gestão de dados financeiros**, como saldo e limite das contas.
- **EndPoints RESTful** para **CRUD (Create, Read, Update, Delete)** de usuários, contas e cartões.
- **Deploy no Railway** para hospedagem e escalabilidade da aplicação.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.2**
- **JPA (Hibernate)** com **PostgreSQL** para persistência de dados
- **Swagger (springdoc-openapi)** para documentação da API
- **Railway** para deploy e hospedagem

## Estrutura do Banco de Dados

O projeto utiliza as seguintes entidades para armazenar dados financeiros:

- **User**: Representa o usuário, com informações sobre conta bancária e cartão.
- **Account**: Representa a conta bancária do usuário, incluindo saldo e limite.
- **Card**: Representa o cartão de crédito do usuário, com limite disponível.
- **Feature**: Representa funcionalidades adicionais relacionadas aos usuários (ex: serviços extras).
- **News**: Representa notícias e alertas financeiros que são associadas ao usuário.

## Endpoints da API

### Usuários

- `GET /users/{id}`: Retorna os detalhes de um usuário.
- `POST /users`: Cria um novo usuário.




# 📘 EventHub - Sistema de Gerenciamento de Eventos e Inscrições

**EventHub** é uma aplicação Java/Spring Boot monolítica (inicialmente) que permite o cadastro de eventos, inscrição de usuários e
envio de e-mails de confirmação. A arquitetura é pensada para ser evoluída para microsserviços, utilizando **RabbitMQ** para
comunicação assíncrona via **Spring Cloud Stream**.

---

## 🔧 Tecnologias Utilizadas

- Java 17
- Spring Boot
- MongoDB
- Redis
- Java Mail Sender
- Docker / Docker Compose
- RabbitMQ
- Spring Cloud Stream
- Lombok

---

## 📂 Estrutura Inicial (Monolito)

````
event-hub/
│   event-hub/
│   ├── src/
│   │ ├── main/
│   │ │ ├── java/com/lucasmoraist/event_hub/
│   │ │ │ ├── application/
│   │ │ │ │   ├── email
│   │ │ │ │   ├── service
│   │ │ │ │   ├── usecases
│   │ │ │ │   └── utils
│   │ │ │ ├── domain/
│   │ │ │ │   ├── entity
│   │ │ │ │   ├── enums
│   │ │ │ │   ├── exception
│   │ │ │ │   ├── model
│   │ │ │ │   ├── request
│   │ │ │ │   └── response
│   │ │ │ ├── infra/
│   │ │ │ │   ├── controller
│   │ │ │ │   ├── documentation (swagger)
│   │ │ │ │   └── repository
│   │ │ │ └── EventHubApplication.java
│   │ │ ├── resources/
│   │ │ │   └── application.yml
├── docker-compose.yml
└── README.md
````

---

## 📌 Endpoints da API

### 🎉 Events

| Método | Rota                  | Descrição                    |
|--------|-----------------------|------------------------------|
| POST   | `/events`             | Cria um novo evento          |
| GET    | `/events/{id}`        | Busca evento por ID          |
| PATCH  | `/events/{id}`        | Atualiza um evento existente |
| DELETE | `/events/{id}/delete` | Exclui um evento             |
| GET    | `/events/all`         | Lista todos os eventos       |
| GET    | `/events/upcoming`    | Lista eventos futuros        |
| GET    | `/events/available`   | Lista eventos disponíveis    |

### 📝 Inscriptions

| Método | Rota                                         | Descrição                           |
|--------|----------------------------------------------|-------------------------------------|
| POST   | `/inscriptions/subscribe/{userId}/{eventId}` | Inscreve um usuário em um evento    |
| PATCH  | `/inscriptions/confirm/{inscriptionId}`      | Confirma uma inscrição              |
| PATCH  | `/inscriptions/cancel/{inscriptionId}`       | Cancela uma inscrição               |
| GET    | `/inscriptions/user/{userId}`                | Lista inscrições por usuário        |
| GET    | `/inscriptions/event/{eventId}`              | Lista inscrições por evento         |
| GET    | `/inscriptions/waiting-list/{eventId}`       | Lista de espera para um evento      |
| PATCH  | `/inscriptions/check-in/{inscriptionId}`     | Realiza o check-in de uma inscrição |


### 👤 Users

| Método | Rota           | Descrição                                        |
|--------|----------------|--------------------------------------------------|
| POST   | `/users`       | Cria um novo usuário                             |
| GET    | `/users`       | Lista todos os usuários                          |
| GET    | `/users/{id}`  | Busca usuário por ID                             |
| GET    | `/users/email` | Busca usuário por e-mail (email via query param) |
| PATCH  | `/users/{id}`  | Atualiza informações de um usuário               |
| DELETE | `/users/{id}`  | Exclui um usuário (com senha via query param)    |


### 📄 Documentation
| Método | Rota | Descrição                                                                       |
|--------|------|---------------------------------------------------------------------------------|
| GET    | `/`  | Documentação da API com Swagger, para mais informações acesse a rota do Swagger |

---

## ⚙️ Configurações (application.yml)

```yml
spring:
  data:
    # MongoDB
    mongodb:
      host: mongo
      port: 27017
      database: eventhub
    # Redis
    redis:
      host: redis
      port: 6379
  # E-mail
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USER}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

---

## 🐳 Docker Compose

```yaml
version: '3.8'

services:
  mongo:
    image: mongo:6.0
    container_name: mongo
    ports:
      - "27017:27017"
    volumes:
      - mongo-data:/data/db
    networks:
      - backend

  redis:
    image: redis:7.2
    container_name: redis
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
    networks:
      - backend

volumes:
  mongo-data:
  redis-data:

networks:
  backend:
    driver: bridge
```

---

## 📌 Funcionalidades

- ✅ Cadastro de eventos
- ✅ Cadastro de usuários
- ✅ Inscrição em eventos
- ✅ Envio de e-mails de confirmação
- ✅ Cache de eventos populares com Redis

---

## 🔁 Roadmap para Microsserviços

| Serviço             | Função                                         | Comunicação      |
|---------------------|------------------------------------------------|------------------|
| event-service       | CRUD de eventos                                | MongoDB          |
| user-service        | Cadastro/autenticação de usuários              | MongoDB          |
| inscription-service | Registro de inscrição + publicação no RabbitMQ | MongoDB + Stream |
| mail-service        | Recebe eventos e envia e-mails                 | RabbitMQ + SMTP  |
| cache-service       | Mantém dados em cache com Redis                | Redis            |
| gateway             | Roteamento/API gateway                         | --               |

---

## ✅ Execução Local

1. Suba os containers:
   ```bash
   docker-compose up -d
   ```
2. Execute a aplicação Spring Boot (IDE ou terminal).
3. Teste os endpoints via Postman ou Swagger.

---

## 📫 Contato

Desenvolvido por Lucas de Morais Nascimento 🧠💪
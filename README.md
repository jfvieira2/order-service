# 🚀 Order Service

Este é um serviço para gerenciar pedidos, integrando-se a sistemas externos para receber, processar e disponibilizar pedidos via API RESTful.

## 📌 Tecnologias Utilizadas

- **Java 17** + **Spring Boot**
- **PostgreSQL** (Banco de dados relacional)
- **Redis** (Cache para otimizar consultas)
- **Apache Kafka** (Mensageria para processamento assíncrono)
- **MapStruct** (Conversão automática entre DTOs e entidades)
- **Feign Client** (Consumo de APIs externas)
- **Docker Compose** (Ambiente completo com banco, cache e mensageria)

---

## ✅ Pré-requisitos

Certifique-se de ter instalado:

- [Docker](https://www.docker.com/get-started) e [Docker Compose](https://docs.docker.com/compose/install/)
- [Java 17+](https://adoptopenjdk.net/) e [Maven](https://maven.apache.org/download.cgi)

---

## 🚀 Rodando o Projeto

### 📌 1️⃣ Subir os serviços necessários (Banco, Redis, Kafka)
```sh
docker-compose up -d
```

### 📌 2️⃣ Compilar e executar o projeto
```sh
mvn clean install
mvn spring-boot:run
```

### 📌 3️⃣ Testar a API no Swagger UI
Acesse: **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## 📌 Endpoints da API

### 📍 Criar um Pedido
**POST** `/orders`  
📥 **Body (JSON)**:
```json
{
  "orderId": "123ABC",
  "status": "PENDENTE",
  "totalValue": 150.00
}
```
📤 **Resposta (200 OK)**:
```json
"Pedido enviado para processamento!"
```

---

### 📍 Consultar um Pedido
**GET** `/orders/{orderId}`  
📥 **Exemplo**: `/orders/123ABC`  
📤 **Resposta**:
```json
{
  "orderId": "123ABC",
  "status": "PENDENTE",
  "totalValue": 150.00,
  "products": []
}
```

---

## 📌 Testes

Para rodar os testes automatizados:
```sh
mvn test
```

---

## 📌 Estrutura do Projeto

```
order-service/
│── src/
│   ├── main/java/com/order/
│   │   ├── config/        # Configurações do Redis, Kafka e Feign
│   │   ├── controller/    # OrderController (exposição de endpoints)
│   │   ├── service/       # OrderService, OrderProducerService, OrderConsumerService
│   │   ├── repository/    # OrderRepository
│   │   ├── model/         # Entidades JPA (Order, Product)
│   │   ├── dto/           # DTOs para comunicação de dados
│   │   ├── mapper/        # MapStruct para conversão DTO <-> Entity
│   ├── test/java/com/order/ # Testes unitários
│── docker-compose.yml     # Serviços do PostgreSQL, Redis e Kafka
│── pom.xml                # Dependências do projeto
│── README.md              # Documentação do projeto
```

---

## 📌 Melhorias Futuras
- 📌 Implementar autenticação JWT para proteger os endpoints.
- 📌 Criar notificações via WebSocket para novos pedidos.
- 📌 Criar um painel de monitoramento dos pedidos.

---

## 📌 Autor
👨‍💻 **Desenvolvido por [Seu Nome]**  
📧 Contato: seuemail@exemplo.com  
🌎 LinkedIn: [linkedin.com/in/seunome](https://linkedin.com/in/seunome)

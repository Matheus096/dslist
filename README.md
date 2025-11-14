# DSList - API de Gerenciamento de Jogos

Uma aplicação backend desenvolvida com **Spring Boot 3.4.5** e **Java 21** para gerenciamento de uma lista de jogos, com autenticação segura via JWT e integração com banco de dados.

## ✨ Sumário

- [Visão geral](#visao-geral)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)
- [Endpoints Principais](#endpoints-principais)
- [Autenticação e Segurança](#autenticação-e-segurança)
- [Estrutura de Dados](#estrutura-de-dados)
- [Testes](#testes)
- [Boas Práticas Implementadas](#boas-práticas-implementadas)
- [Troubleshooting](#troubleshooting)
- [Estrutura do Frontend (Angular)](#estrutura-do-frontend-angular)

---

## 📋 Visão Geral

O **DSList** é um projeto que demonstra boas práticas de desenvolvimento de APIs REST usando o ecossistema Spring. A aplicação permite:

- ✅ Listar jogos e criar listas de jogos personalizadas
- ✅ Autenticação segura de usuários com **Spring Security** e **JWT**
- ✅ Criptografia de senhas com **BCrypt**
- ✅ Consultas otimizadas com **JPA/Hibernate**
- ✅ Suporte a múltiplos perfis (test, dev, prod)
- ✅ CORS configurado para conexão com front-ends

---

## 🏗️ Arquitetura do Projeto

```
dslist/
├── src/
│   ├── main/
│   │   ├── java/com/devsuperior/dslist/
│   │   │   ├── config/           # Configurações (Security, CORS, etc)
│   │   │   ├── controllers/      # Controllers REST (@RestController)
│   │   │   ├── dto/              # Data Transfer Objects (DTOs)
│   │   │   ├── entities/         # Entidades JPA (@Entity)
│   │   │   ├── projections/      # Projeções para queries personalizadas
│   │   │   ├── repositories/     # Interfaces de acesso a dados
│   │   │   ├── services/         # Lógica de negócio
│   │   │   ├── security/         # Configuração de segurança JWT
│   │   │   └── DslistApplication.java  # Classe principal
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-test.properties
│   │       └── import.sql        # Scripts de inicialização do BD
│   └── test/
│       └── java/...              # Testes unitários
└── pom.xml                        # Dependências do Maven
```

### Padrão de Camadas

O projeto segue a arquitetura de **camadas**:

1. **Controller**: Recebe requisições HTTP e retorna respostas
2. **Service**: Contém a lógica de negócio e validações
3. **Repository**: Responsável pela persistência de dados (acesso ao BD)
4. **Entity**: Representa as tabelas do banco de dados
5. **DTO**: Objetos usados para transferir dados entre camadas

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 3.4.5 | Framework web e injeção de dependências |
| **Spring Data JPA** | - | ORM para acesso a dados |
| **Spring Security** | - | Autenticação e autorização |
| **JWT** | 0.11.5 | Tokens para autenticação stateless |
| **Spring Security Crypto** | - | Criptografia BCrypt de senhas |
| **Lombok** | 1.18.30 | Redução de boilerplate (@Getter, @Setter) |
| **PostgreSQL** | - | Banco de dados em produção |
| **H2** | - | Banco de dados em memória para testes |
| **Maven** | - | Gerenciador de dependências |

---

## 🚀 Como Executar

### Pré-requisitos

- Java 21 instalado
- Maven instalado (ou use o `mvnw` incluído no projeto)
- PostgreSQL instalado (opcional, para produção)

### 1. Clone ou abra o projeto

```bash
cd dslist
```

### 2. Configure o banco de dados (opcional)

Para usar **PostgreSQL em produção**, configure as variáveis de ambiente:

```bash
export APP_PROFILE=prod
export DB_HOST=localhost
export DB_NAME=dslist_db
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha
```

Ou edite o arquivo `application.properties`:

```properties
spring.profiles.active=test  # test (H2) ou prod (PostgreSQL)
```

### 3. Execute a aplicação

Com Maven:
```bash
mvn spring-boot:run
```

Ou compile e rode o JAR:
```bash
mvn clean package
java -jar target/dslist-0.0.1-SNAPSHOT.jar
```

### 4. Acesse a aplicação

A API estará disponível em: **http://localhost:8080**

---

## 📡 Endpoints Principais

### Jogos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/games` | Listar todos os jogos |
| `GET` | `/games/{id}` | Obter detalhes de um jogo |
| `GET` | `/games/list/{listId}` | Listar jogos de uma lista específica |

### Listas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/lists` | Listar todas as listas |
| `GET` | `/lists/{id}` | Obter detalhes de uma lista |

### Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/auth/login` | Fazer login (retorna JWT) |
| `POST` | `/auth/register` | Registrar novo usuário |

---

## 🔐 Autenticação e Segurança

### JWT (JSON Web Token)

A aplicação usa JWT para autenticação stateless:

1. **Login**: O usuário envia credenciais
2. **Token**: O servidor retorna um JWT válido
3. **Requisições**: O cliente envia o token no header `Authorization: Bearer <token>`
4. **Validação**: O servidor valida o token antes de processar

### Criptografia de Senha

As senhas são criptografadas com **BCrypt** antes de serem armazenadas no banco de dados.

### CORS

A aplicação está configurada para aceitar requisições de:
- `http://localhost:5173` (Vite)
- `http://localhost:3000` (React/Node)

Configure outras origens no arquivo `application.properties`:

```properties
cors.origins=http://localhost:5173,http://localhost:3000,http://seu-dominio.com
```

---

## 📦 Estrutura de Dados

### Entidades Principais

#### Game (Jogo)
```java
- id: Long (PK)
- title: String
- year: Integer
- genre: String
- platforms: String
- score: Double
- imgUrl: String
- shortDescription: String
- longDescription: String
```

#### GameList (Lista de Jogos)
```java
- id: Long (PK)
- name: String
```

#### GameBelonging (Pertencimento de Jogo a Lista)
```java
- id: GameBelongingPK (FK: game_id, list_id)
- position: Integer
```

#### User (Usuário)
```java
- id: Long (PK)
- username: String (UNIQUE)
- password: String (criptografado)
- email: String
```

---

## 🧪 Testes

Execute os testes com Maven:

```bash
mvn test
```

Ou teste específico:

```bash
mvn test -Dtest=DslistApplicationTests
```

---

## 📝 Boas Práticas Implementadas

- ✅ Injeção de Dependência: Uso de `@Autowired` e construtores
- ✅ DTOs: Separação entre modelo de dados (Entity) e dados transferidos
- ✅ Camadas: Separação clara entre controllers, services e repositories
- ✅ Tratamento de Erros: Exceptions customizadas e tratamento global
- ✅ Queries Otimizadas: Uso de `@Query` nativa com JPA para joins complexos
- ✅ Segurança: Spring Security, JWT e criptografia BCrypt
- ✅ CORS: Configuração para múltiplas origens
- ✅ Perfis de Aplicação: test (H2), dev e prod (PostgreSQL)

---

## 🐛 Troubleshooting

### CORS Block
**Solução**: Configure a origem no `application.properties`:
```properties
cors.origins=http://seu-dominio.com
```

### Problema com JWT expirado
**Solução**: Faça login novamente para obter um novo token.

---

## 📚 Estrutura do Frontend (Angular)

Se estiver usando Angular, a estrutura esperada é:

```
dslist-frontend/
├── src/app/
│   ├── components/
│   │   └── game-list/
│   ├── services/
│   │   └── game.service.ts
│   └── app.component.ts
├── environment.ts
└── package.json
```

Configure a URL da API no `environment.ts`:

```typescript
export const environment = {
  apiUrl: 'http://localhost:8080'
};
```

---

## 📖 Recursos Adicionais

- [Documentação Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security com JWT](https://spring.io/guides/gs/securing-web/)
- [JWT.io](https://jwt.io/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

## 👨‍💻 Autor

**Matheus096** - Projeto melhorado e atualizado por mim após o intensivão de Spring Boot do professor Nélio

---

## 🤝 Contribuindo

Para contribuir com melhorias:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

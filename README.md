# Workshop MongoDB — API REST com Spring Boot

> Projeto de **estudo** focado em Java, MongoDB e arquitetura em camadas.  
> Objetivo: praticar modelagem NoSQL, APIs REST e organização de código — **não** é um produto em produção.

---

## Sobre o projeto

API REST de uma rede social simplificada (usuários, posts e comentários), construída com **Spring Boot** e **MongoDB**.

O foco é exercitar:

- Camadas (Resource → Service → Repository)
- DTOs e desacoplamento da API em relação ao domínio
- Persistência NoSQL (documentos, `@DBRef`, seed de dados)
- Tratamento centralizado de exceções
- Consultas por título (`ContainingIgnoreCase`)

Inspirado em workshops de Java/Spring + MongoDB, com estrutura pensada para clareza e evolução didática.

---

## Stack

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem |
| Spring Boot 4.1 | Framework |
| Spring Data MongoDB | Persistência |
| Spring Web MVC | REST |
| Maven | Build |
| MongoDB | Banco NoSQL |

---

## Arquitetura

Organização em camadas (estilo *layered / clean-ish* para estudo):

```
com.workshop.mongodb
├── resources/          # Controllers REST (entrada HTTP)
│   ├── exception/      # Handler + erro padronizado
│   └── util/           # Utilitários (decode de query params)
├── services/           # Regras de negócio
│   └── exception/      # Exceções de domínio
├── repository/         # Acesso a dados (Spring Data)
├── domains/            # Entidades (@Document)
├── dto/                # Objetos de transferência
└── config/             # Seed (CommandLineRunner)
```

**Fluxo típico de uma requisição**

```
HTTP → Resource → Service → Repository → MongoDB
                      ↓
                   DTO / Domain
```

| Camada | Responsabilidade |
|---|---|
| **Resource** | Contrato HTTP, status codes, conversão DTO |
| **Service** | Orquestra regras e validações |
| **Repository** | Queries e CRUD via Spring Data |
| **Domain** | Modelo persistido (`User`, `Post`) |
| **DTO** | Shape da API (`UserDTO`, `AuthorDTO`, `CommentDTO`) |

**Decisões de modelagem NoSQL**

- `User` e `Post` como documentos
- Posts do usuário via `@DBRef(lazy = true)`
- Autor e comentários embutidos no post (DTOs) — leitura eficiente no documento do post

---

## Domínio

```
User ──<DBRef>──► Post
                    ├── AuthorDTO
                    └── CommentDTO[]
```

Dados iniciais (seed em `Instantiation`):

- Usuários: Maria, Alex, Bob  
- Posts: *"Partiu viagem"*, *"Bom dia"* (+ comentários)

---

## Pré-requisitos

- JDK 21+
- Maven 3.9+ (ou `./mvnw`)
- MongoDB rodando em `localhost:27017`

URI padrão (`application.properties`):

```properties
spring.mongodb.uri=mongodb://localhost:27017/workshop_mongo
```

---

## Como executar

```bash
# 1. Suba o MongoDB localmente

# 2. Na raiz do projeto
./mvnw spring-boot:run
```

API em: `http://localhost:8080`

Ao iniciar, o seed **apaga e recria** as collections `user` e `post`.

---

## Endpoints

### Users

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/users` | Lista usuários (DTO) |
| `GET` | `/users/{id}` | Busca por id |
| `GET` | `/users/{id}/posts` | Posts do usuário |
| `POST` | `/users` | Cria usuário |
| `PUT` | `/users/{id}` | Atualiza usuário |
| `DELETE` | `/users/{id}` | Remove usuário |

### Posts

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/posts/{id}` | Busca post por id |
| `GET` | `/posts/titlesearch?text=` | Busca por título (case-insensitive) |

**Exemplo**

```http
GET /posts/titlesearch?text=viagem
```

---

## Conceitos em prática

- [x] API REST com Spring MVC  
- [x] Arquitetura em camadas  
- [x] DTOs na borda da API  
- [x] MongoDB + Spring Data (`MongoRepository`)  
- [x] Relacionamento com `@DBRef`  
- [x] Documentos embutidos (autor/comentários)  
- [x] Exception handler (`@ControllerAdvice`)  
- [x] Seed com `CommandLineRunner`  
- [ ] Testes automatizados *(próximo passo de estudo)*  
- [ ] Validação (`@Valid`) *(próximo passo)*  
- [ ] Paginação / filtros compostos *(próximo passo)*  

---

## Estrutura do repositório

```
src/main/java/com/workshop/mongodb/
src/main/resources/application.properties
pom.xml
```

---

## Aviso

Projeto **educacional**. Sem autenticação, autorização, hardening ou padrões de produção. Serve para aprender e documentar evolução de arquitetura.

---

## Autor

**Fernando** — estudos em Java, Spring e MongoDB.

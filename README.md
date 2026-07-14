# 📚 Library Management System

Sistema web de gerenciamento de biblioteca desenvolvido em **Java** utilizando **Servlets**, **JSP/JSTL** e **MySQL**, seguindo o padrão arquitetural **MVC** em camadas (Controller, DAO, Model, Util, Exception).

O sistema permite que usuários se cadastrem, façam login, consultem o catálogo de livros e realizem empréstimos/devoluções, enquanto administradores têm acesso a um painel para gerenciar o acervo.

---

## ✨ Funcionalidades

### Usuário comum
- Cadastro e login com senha criptografada (BCrypt)
- Visualização do catálogo de livros
- Consulta de detalhes de um livro específico
- Empréstimo (reserva) de livros disponíveis
- Devolução de livros emprestados
- Visualização e edição do próprio perfil

### Administrador
- Dashboard com estatísticas gerais (total de livros, disponíveis, emprestados, usuários, empréstimos ativos)
- Listagem de empréstimos recentes
- Cadastro, edição e listagem de livros

### Sistema
- Controle de sessão via `HttpSession`
- Filtro global de exceções (`GlobalExceptionHandler`) para tratamento centralizado de erros
- Exceções de negócio customizadas (`BusinessException`, `NotFoundException`, `UnauthorizedException`)
- Transações no banco de dados para operações de empréstimo/devolução (commit/rollback)

---

## 🛠️ Tecnologias utilizadas

- **Java** (Servlets + JSP)
- **JSTL** (Core) para lógica em views
- **MySQL** (via JDBC)
- **BCrypt** (jBCrypt) para hash de senhas
- **HTML/CSS** para o front-end
- Arquitetura em camadas: `Controller` → `DAO` → `Model`

---

## 📁 Estrutura do projeto

```
src/
├── controller/
│   ├── AdminServlet.java        # Rotas administrativas (/admin/*)
│   ├── BookServlet.java         # Listagem de livros (/books)
│   ├── BookDetailsServlet.java  # Detalhes de um livro (/books/details)
│   ├── HomeServlet.java         # Página inicial (/home)
│   ├── LoansServlet.java        # Empréstimos e devoluções (/loans/*)
│   ├── ProfileServlet.java      # Perfil do usuário (/profile/*)
│   └── UserServlet.java         # Registro, login e logout (/users/*)
│
├── dao/
│   ├── BookDAO.java             # Acesso a dados de livros
│   ├── LoansDAO.java            # Acesso a dados de empréstimos
│   └── UserDAO.java             # Acesso a dados de usuários
│
├── model/
│   ├── Book.java
│   ├── Loan.java
│   └── User.java
│
├── exception/
│   ├── BusinessException.java
│   ├── NotFoundException.java
│   ├── UnauthorizedException.java
│   └── GlobalExceptionHandler.java  # Filtro global (@WebFilter("/*"))
│
└── util/
    ├── ConnectionFactory.java   # Conexão JDBC com MySQL
    └── PasswordUtil.java        # Hash e verificação de senha (BCrypt)

WebContent/
└── WEB-INF/
    └── views/
        ├── admin/       (dashboard, book-form, book-list)
        ├── auth/        (login, register)
        ├── books/       (list, book-details)
        ├── loans/       (my-loans)
        ├── users/       (profile, edit-profile)
        ├── errors/      (error.jsp)
        └── includes/    (header, nav-bar, footer)
```

---

## 🗄️ Modelo de dados

### `users`
| Campo | Tipo | Descrição |
|---|---|---|
| id | INT UNSIGNED (PK) | Identificador único |
| full_name | VARCHAR(150) | Nome completo |
| nick_name | VARCHAR(100) | Apelido/usuário |
| email | VARCHAR(255) UNIQUE | E-mail (login) |
| password_hash | VARCHAR(65) | Senha criptografada |
| type_user | ENUM('User','Admin') | Papel do usuário |

### `books`
| Campo | Tipo | Descrição |
|---|---|---|
| id | INT UNSIGNED (PK) | Identificador único |
| title | VARCHAR(150) | Título |
| author | VARCHAR(150) | Autor |
| isbn | VARCHAR(150) UNIQUE | ISBN |
| available | BOOLEAN | Disponível para empréstimo |
| active | BOOLEAN | Ativo no acervo |

### `loans`
| Campo | Tipo | Descrição |
|---|---|---|
| id | INT UNSIGNED (PK) | Identificador único |
| book_id | INT UNSIGNED (FK) | Referência ao livro |
| user_id | INT UNSIGNED (FK) | Referência ao usuário |
| loan_date | DATE | Data do empréstimo |
| return_date | DATE | Data de devolução |
| active | BOOLEAN | Empréstimo em andamento |

> ⚠️ Os scripts SQL de criação das tabelas estão disponíveis na pasta do projeto. Ajuste o nome do schema (`library_db`) conforme necessário antes de executar.

---

## 🚀 Como executar o projeto

### Pré-requisitos
- JDK 11+ (ou versão compatível com Servlet 4.0)
- Apache Tomcat 9+
- MySQL 8+
- Driver JDBC do MySQL (`mysql-connector-j`)
- Biblioteca `jBCrypt`

### Passos

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Kauapereira10/library-management-system-java
   ```

2. **Crie o banco de dados**
   ```sql
   CREATE DATABASE library_db;
   ```
   Em seguida, execute os scripts SQL de criação das tabelas `users`, `books` e `loans` (na ordem: `users` → `books` → `loans`, por causa das chaves estrangeiras).

3. **Configure a conexão com o banco**

   Edite o arquivo `util/ConnectionFactory.java` com suas credenciais:
   ```java
   private static final String URL =
       "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
   private static final String USER = "root";
   private static final String PASSWORD = "sua_senha";
   ```

4. **Importe o projeto** em sua IDE (Eclipse/IntelliJ) como um projeto **Dynamic Web Project** ou **Maven**, adicionando as dependências:
   - `mysql-connector-j`
   - `jbcrypt`
   - `javax.servlet-api`

5. **Faça o deploy no Tomcat** e acesse:
   ```
   http://localhost:8080/library-management-system-java/home
   ```

---

## 🔗 Principais rotas

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/home` | Página inicial | Público |
| GET/POST | `/users/register` | Cadastro de usuário | Público |
| GET/POST | `/users/login` | Login | Público |
| GET | `/users/logout` | Logout | Autenticado |
| GET | `/users/profile` | Perfil do usuário | Autenticado |
| GET | `/books` | Catálogo de livros | Público |
| GET | `/books/details?id=` | Detalhes de um livro | Público |
| GET/POST | `/loans` | Meus empréstimos | Autenticado |
| POST | `/loans/borrow` | Realizar empréstimo | Autenticado |
| POST | `/loans/return` | Devolver livro | Autenticado |
| GET | `/profile/edit` | Editar perfil | Autenticado |
| GET | `/admin/dashboard` | Painel administrativo | Admin |
| GET | `/admin/books` | Listar livros (admin) | Admin |
| GET/POST | `/admin/books/new` | Cadastrar livro | Admin |
| POST | `/admin/books/update` | Atualizar livro | Admin |

---

## 👨‍💻 Autor

**Kauã Pereira da Silva Borges**
Estudante de Análise e Desenvolvimento de Sistemas — foco em backend com Java, Spring Boot, MySQL e boas práticas de arquitetura em camadas.

---

## 📄 Licença

Este projeto está disponível para fins de estudo e aprendizado. Sinta-se à vontade para utilizá-lo como referência.

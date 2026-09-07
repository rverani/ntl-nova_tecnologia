# Teste de Implementação — API Java (Spring Boot) + Angular

Projeto atendendo ao escopo do PDF: API com Basic Auth e Swagger, cadastro/consulta de
usuários, consulta por origem via **procedure** Oracle, e front-end Angular exibindo a lista.

## Estrutura

```
teste-api-java/
├── backend/     -> API Spring Boot (Java 17)
│   └── src/main/resources/sql/  -> scripts para criar tabelas, procedure e dados de exemplo
└── frontend/    -> Aplicação Angular (standalone)
```

## 1. Banco de dados (Oracle)

Rode os scripts, na ordem, conectado como um usuário com permissão de criar objetos
(schema da aplicação):

1. `backend/src/main/resources/sql/01_create_tables.sql` — cria `TIPO_USUARIO`, `USUARIO` e a sequence.
2. `backend/src/main/resources/sql/02_create_procedure.sql` — cria `PRC_LISTA_USUARIOS_POR_ORIGEM`.
3. `backend/src/main/resources/sql/03_insert_data.sql` — insere os dados de exemplo do PDF.

## 2. Backend (Spring Boot)

Requisitos: Java 17+ e Maven.

Configure a conexão em `backend/src/main/resources/application.yml` (ou via variáveis de
ambiente `DB_USER`, `DB_PASS`), ajustando host/porta/SID conforme seu ambiente Oracle:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:xe
    username: ${DB_USER:SYSTEM}
    password: ${DB_PASS:121291}
```

Rodar a API:

```bash
cd backend
mvn spring-boot:run
```

A API sobe em `http://localhost:8081`.

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **Basic Auth**: usuário/senha padrão `SYSTEM` / `121291`
  (configuráveis via `API_USER` / `API_PASS`, em `application.yml`)

### Endpoints

| Método | Endpoint                          | Descrição                                  |
|--------|------------------------------------|---------------------------------------------|
| POST   | `/api/usuarios`                    | Cadastra um novo usuário                    |
| GET    | `/api/usuarios/{id}`               | Busca usuário cadastrado por id             |
| GET    | `/api/usuarios?origem=E`           | Lista usuários de uma origem (via procedure)|

Exemplo de payload para `POST /api/usuarios`:

```json
{
  "nomeUsuario": "Maria da Silva",
  "matriculaUsuario": "RJ123456",
  "dataNascimento": "1990-05-10",
  "email": "maria.silva@teste.com.br",
  "origem": "F"
}
```

Códigos de origem válidos: `M` (Magistrado), `F` (Funcionário), `T` (Terceirizado),
`A` (Aposentado), `P` (Pensionista), `C` (Cotista), `E` (Externo).

## 3. Frontend (Angular)

Requisitos: Node.js 18+ e Angular CLI (`npm install -g @angular/cli`).

```bash
cd frontend
npm install
npm start
```

Acesse `http://localhost:4200`. A tela permite escolher o tipo de usuário (origem) em um
select e exibe a lista retornada pela API em uma tabela.

> As credenciais de Basic Auth usadas pelo front (`frontend/src/app/core/usuario.service.ts`)
> devem bater com as configuradas no backend (`SYSTEM` / `121291` por padrão).

## Observações de arquitetura

- A relação entre `USUARIO` e `TIPO_USUARIO` é feita pela coluna `ORIGEM` (chave única em
  `TIPO_USUARIO` e chave estrangeira em `USUARIO`), conforme os exemplos do PDF.
- A busca por origem é feita inteiramente através da procedure Oracle
  `PRC_LISTA_USUARIOS_POR_ORIGEM`, chamada via `SimpleJdbcCall` (Spring JDBC).
- Cadastro e busca por id usam Spring Data JPA normalmente.
- CORS: se o Angular (`localhost:4200`) e a API (`localhost:8081`) rodarem em portas
  diferentes, pode ser necessário liberar CORS no backend (`@CrossOrigin` no controller
  ou uma configuração global) — não incluído aqui para manter o exemplo enxuto.

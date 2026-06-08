Segue o **README.md único**, já sem validação de nome duplicado do Pokémon:

````md
# Projeto Java Web — CRUD de Pokémon

Sistema web desenvolvido em Java utilizando JSP, Servlets, DAO e PostgreSQL.

O projeto realiza a manutenção de um cadastro de Pokémons, permitindo incluir, alterar, excluir e listar registros.

## Funcionalidades

- Cadastrar Pokémon
- Listar Pokémons
- Alterar Pokémon
- Excluir Pokémon
- Validar campos obrigatórios
- Validar nível maior que zero
- Validar data de captura

## Classe Pokémon

A classe `Pokemon` possui os seguintes atributos:

- `id` — inteiro
- `nome` — texto
- `tipo` — texto
- `nivel` — inteiro
- `dataCaptura` — data

## Estrutura do Projeto

```text
src/java/br/com/aplcurso/
├── controller/
│   └── pokemon/
│       ├── PokemonCadastrar.java
│       ├── PokemonCarregar.java
│       ├── PokemonExcluir.java
│       ├── PokemonListar.java
│       └── PokemonNovo.java
├── dao/
│   ├── GenericDAO.java
│   └── PokemonDAO.java
├── filter/
│   └── FilterAutenticacao.java
├── model/
│   └── Pokemon.java
└── utils/
    └── SingleConnection.java

web/
├── cadastros/
│   └── pokemon/
│       ├── pokemon.jsp
│       └── pokemonCadastrar.jsp
├── header.jsp
├── menu.jsp
├── footer.jsp
├── home.jsp
└── index.jsp
````

## Banco de Dados

Execute o script abaixo no PostgreSQL:

```sql
CREATE TABLE pokemon (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    nivel INTEGER NOT NULL,
    data_captura DATE NOT NULL
);
```

## Servlets

| Servlet            | Função                              |
| ------------------ | ----------------------------------- |
| `PokemonNovo`      | Abre o formulário de cadastro       |
| `PokemonListar`    | Lista todos os Pokémons cadastrados |
| `PokemonCarregar`  | Carrega um Pokémon para alteração   |
| `PokemonCadastrar` | Insere ou altera um Pokémon         |
| `PokemonExcluir`   | Exclui um Pokémon                   |

## Validações

O sistema realiza validações no frontend e no backend.

### Frontend

* Verifica se o nome está vazio
* Verifica se o tipo está vazio
* Verifica se o nível foi informado
* Verifica se o nível é maior que zero
* Verifica se a data de captura foi informada

### Backend

* Verifica campos obrigatórios
* Verifica nível maior que zero
* Verifica data de captura válida
* Impede gravação de dados inválidos

## Retornos AJAX

| Código | Significado                   |
| ------ | ----------------------------- |
| `1`    | Pokémon salvo com sucesso     |
| `0`    | Erro ao salvar                |
| `5`    | Campos inválidos ou em branco |

## Tecnologias Utilizadas

* Java
* Java Web
* JSP
* Servlets
* DAO
* PostgreSQL
* NetBeans
* Apache Tomcat ou GlassFish
* Git e GitHub

## Como Executar

1. Clone o repositório.
2. Abra o projeto no NetBeans.
3. Configure o servidor Tomcat ou GlassFish.
4. Crie o banco de dados no PostgreSQL.
5. Execute o script da tabela `pokemon`.
6. Configure a conexão na classe `SingleConnection`.
7. Execute o projeto.
8. Acesse o módulo de Pokémons pelo menu.

## Comandos Git

```bash
git init
git add .
git commit -m "Implementa CRUD de Pokemon"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/NOME_REPOSITORIO.git
git push -u origin main
```

## Autor

Projeto desenvolvido para a disciplina de Laboratório de Programação.

Aluno: Dan Silva

```
```

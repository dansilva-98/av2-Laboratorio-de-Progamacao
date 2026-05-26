# Projeto Java Web — CRUD de Usuário e Estado

Sistema web desenvolvido em Java utilizando JSP, Servlets, DAO, PostgreSQL e validações no frontend e backend.

O projeto possui cadastros com operações de inclusão, listagem, alteração e exclusão, além de validações para evitar dados inválidos e duplicados.


## Tecnologias utilizadas

* Java
* JSP
* Servlets
* JDBC
* PostgreSQL
* HTML
* CSS
* JavaScript
* jQuery
* AJAX
* SweetAlert2
* DataTables
* Apache Tomcat ou servidor compatível com Java Web
* NetBeans ou IDE compatível

---

## Estrutura principal do projeto

```text
br.com.aplcurso.controller.usuario
    UsuarioCadastrar.java
    UsuarioCarregar.java
    UsuarioExcluir.java
    UsuarioListar.java
    UsuarioNovo.java
    UsuarioVerificarCPF.java
    UsuarioVerificarEmail.java

br.com.aplcurso.controller.estado
    EstadoCadastrar.java
    EstadoCarregar.java
    EstadoExcluir.java
    EstadoListar.java
    EstadoNovo.java
    EstadoVerificarSigla.java

br.com.aplcurso.dao
    GenericDAO.java
    UsuarioDAO.java
    EstadoDAO.java

br.com.aplcurso.filter
    FilterAutenticacao.java

br.com.aplcurso.model
    Usuario.java
    Estado.java

br.com.aplcurso.utils
    DocumentoValidador.java
    SingleConnection.java

web/cadastros/usuario
    usuario.jsp
    usuarioCadastrar.jsp

web/cadastros/estado
    estado.jsp
    estadoCadastrar.jsp

web
    index.jsp
    home.jsp
    header.jsp
    footer.jsp
    menu.jsp
    banco.sql
```

---

## Funcionalidades

### Cadastro de Usuário

O módulo de usuário permite:

* cadastrar usuário;
* listar usuários;
* alterar usuário;
* excluir usuário;
* validar CPF;
* validar CPF duplicado;
* validar e-mail duplicado;
* validar formato de e-mail;
* validar campos obrigatórios.

Campos principais:

* `id`
* `nome`
* `dataNascimento`
* `cpf`
* `email`
* `senha`
* `salario`

---

### Cadastro de Estado

O módulo de estado permite:

* cadastrar estado;
* listar estados;
* alterar estado;
* excluir estado;
* validar sigla duplicada no frontend;
* validar sigla duplicada no backend;
* impedir duplicidade da sigla no banco de dados;
* validar campos obrigatórios.

Campos principais:

* `id`
* `nomeEstado`
* `siglaEstado`

Regras do cadastro de estado:

* `nomeEstado` deve ser obrigatório;
* `siglaEstado` deve ser obrigatória;
* `siglaEstado` deve possuir exatamente 2 caracteres;
* `siglaEstado` não pode ser duplicada;
* a sigla é salva em letras maiúsculas.

---

## Banco de dados

O projeto utiliza PostgreSQL.

### Configuração da conexão

A conexão fica centralizada na classe:

```text
br.com.aplcurso.utils.SingleConnection
```

Exemplo de configuração:

```java
private static String servidor = "jdbc:postgresql://localhost:5432/bdaplcurso?autoReconnect=true";
private static String usuario = "postgres";
private static String senha = "admin";
```

Altere essas informações conforme o banco instalado na sua máquina.

---

## Script de criação das tabelas

### Tabela `usuario`

```sql
create table usuario (
    id serial primary key,
    nome varchar(100) not null,
    datanascimento date not null,
    cpf varchar(11) not null,
    email varchar(100) not null,
    senha varchar(20) not null,
    salario decimal(15,2) not null
);

create unique index if not exists uk_usuario_cpf
on usuario (cpf);

create unique index if not exists uk_usuario_email_lower
on usuario (lower(email));
```

---

### Tabela `estado`

```sql
create table estado (
    id serial primary key,
    nomeEstado varchar(100) not null,
    siglaEstado varchar(2) not null
);

create unique index if not exists uk_estado_siglaEstado
on estado (upper(siglaEstado));
```

O índice `uk_estado_siglaEstado` impede que siglas duplicadas sejam cadastradas, inclusive quando digitadas com letras maiúsculas ou minúsculas.

Exemplo:

```text
SP
sp
Sp
sP
```

Todas serão consideradas a mesma sigla.

---

## Validações implementadas

### Validações no frontend

As validações no frontend são feitas com JavaScript, jQuery, AJAX e SweetAlert2.

No cadastro de usuário:

* verifica campos vazios;
* valida formato de e-mail;
* verifica CPF duplicado via AJAX;
* verifica e-mail duplicado via AJAX;
* exibe mensagens de erro com SweetAlert2.

No cadastro de estado:

* verifica se o nome do estado está vazio;
* verifica se a sigla está vazia;
* valida se a sigla possui exatamente 2 caracteres;
* converte a sigla para maiúsculo;
* verifica sigla duplicada via AJAX;
* exibe mensagens de erro com SweetAlert2.

---

### Validações no backend

As validações no backend são feitas nas Servlets.

No cadastro de usuário:

* valida campos obrigatórios;
* valida CPF;
* valida CPF duplicado;
* valida e-mail duplicado;
* valida formato do e-mail;
* trata data e salário antes de gravar no banco.

No cadastro de estado:

* valida campos obrigatórios;
* valida tamanho da sigla;
* converte nome e sigla para maiúsculo;
* valida duplicidade da sigla antes de salvar.

---

### Validação no banco de dados

Além das validações no frontend e backend, o banco também possui índices únicos para impedir duplicidade.

```sql
create unique index if not exists uk_usuario_email_lower
on usuario (lower(email));

create unique index if not exists uk_usuario_cpf
on usuario (cpf);

create unique index if not exists uk_estado_siglaEstado
on estado (upper(siglaEstado));
```

Isso protege o sistema mesmo que alguém tente burlar o formulário.

---

## Servlets principais

### Usuário

| Servlet                 | Função                             |
| ----------------------- | ---------------------------------- |
| `UsuarioNovo`           | Abre o formulário de novo usuário  |
| `UsuarioListar`         | Lista os usuários cadastrados      |
| `UsuarioCarregar`       | Carrega usuário para edição        |
| `UsuarioCadastrar`      | Cadastra ou altera usuário         |
| `UsuarioExcluir`        | Exclui usuário                     |
| `UsuarioVerificarCPF`   | Verifica CPF duplicado via AJAX    |
| `UsuarioVerificarEmail` | Verifica e-mail duplicado via AJAX |

---

### Estado

| Servlet                | Função                            |
| ---------------------- | --------------------------------- |
| `EstadoNovo`           | Abre o formulário de novo estado  |
| `EstadoListar`         | Lista os estados cadastrados      |
| `EstadoCarregar`       | Carrega estado para edição        |
| `EstadoCadastrar`      | Cadastra ou altera estado         |
| `EstadoExcluir`        | Exclui estado                     |
| `EstadoVerificarSigla` | Verifica sigla duplicada via AJAX |

---

## Retornos utilizados nas requisições AJAX

### Cadastro de usuário

| Código | Significado                   |
| ------ | ----------------------------- |
| `1`    | Usuário salvo com sucesso     |
| `0`    | Erro geral ao salvar          |
| `3`    | CPF inválido                  |
| `4`    | CPF já cadastrado             |
| `5`    | Campos em branco ou inválidos |
| `6`    | E-mail já cadastrado          |
| `7`    | Formato de e-mail inválido    |

---

### Cadastro de estado

| Código | Significado              |
| ------ | ------------------------ |
| `1`    | Estado salvo com sucesso |
| `0`    | Erro geral ao salvar     |
| `5`    | Campos em branco         |
| `6`    | Sigla já cadastrada      |
| `7`    | Sigla inválida           |

---

## Como executar o projeto

1. Clone ou copie o projeto para sua máquina.
2. Abra o projeto no NetBeans ou IDE compatível.
3. Crie o banco de dados PostgreSQL.
4. Execute o script `banco.sql`.
5. Confira as configurações da classe `SingleConnection.java`.
6. Configure o servidor Apache Tomcat ou servidor compatível.
7. Execute `Clean and Build`.
8. Execute o projeto.
9. Acesse o sistema pelo navegador.

---

## Exemplo de criação do banco

```sql
create database bdaplcurso;
```

Depois conecte no banco `bdaplcurso` e execute os comandos de criação das tabelas.

---

## Fluxo do cadastro de Estado

1. Usuário acessa o menu `Estado`.
2. O sistema chama `EstadoListar`.
3. A listagem é carregada em `estado.jsp`.
4. Ao clicar em `Novo`, o sistema chama `EstadoNovo`.
5. O formulário `estadoCadastrar.jsp` é aberto.
6. O usuário informa nome e sigla.
7. Ao sair do campo sigla, o frontend chama `EstadoVerificarSigla` via AJAX.
8. Se a sigla já existir, o sistema exibe uma mensagem de erro.
9. Ao salvar, o frontend valida os campos novamente.
10. O backend recebe os dados em `EstadoCadastrar`.
11. O backend valida campos e duplicidade.
12. O DAO salva os dados no banco.
13. O banco impede duplicidade usando índice único.

---

## Fluxo do cadastro de Usuário

1. Usuário acessa o menu `Usuário`.
2. O sistema chama `UsuarioListar`.
3. A listagem é carregada em `usuario.jsp`.
4. Ao clicar em `Novo`, o sistema chama `UsuarioNovo`.
5. O formulário `usuarioCadastrar.jsp` é aberto.
6. O usuário informa os dados.
7. O frontend valida campos obrigatórios.
8. O frontend verifica CPF e e-mail via AJAX.
9. Ao salvar, o backend valida novamente os dados.
10. O DAO grava as informações no banco.
11. O banco impede CPF e e-mail duplicados.

---

## Possíveis problemas e soluções

### Erro: `validarCampos is not defined`

Esse erro normalmente acontece quando existe algum erro de sintaxe no JavaScript antes da função `validarCampos`.

Verifique:

* `else if` fora de ordem;
* chaves `{}` faltando;
* parênteses `()` faltando;
* ponto e vírgula depois de `</script>`;
* funções duplicadas.

---

### Erro: `Unexpected token 'else'`

Esse erro ocorre quando existe um `else` em lugar inválido.

Exemplo incorreto:

```javascript
if (data == 1) {
    // sucesso
} else {
    // erro geral
} else if (data == 6) {
    // inválido
}
```

Exemplo correto:

```javascript
if (data == 1) {
    // sucesso
} else if (data == 6) {
    // sigla duplicada
} else {
    // erro geral
}
```

---

### Erro ao salvar data

O Java pode exigir a data no formato:

```text
yyyy-MM-dd
```

Exemplo:

```text
1999-10-05
```

Se o frontend enviar:

```text
05/10/1999
```

é necessário converter a data no backend antes de salvar.

---

### Erro ao salvar salário

Se o salário vier formatado como:

```text
R$ 3.000,00
```

é necessário remover `R$`, pontos e trocar vírgula por ponto antes de converter para `double`.

---

## Boas práticas aplicadas

* Separação entre Model, DAO, Controller e JSP.
* Uso de `PreparedStatement` para evitar SQL Injection.
* Uso de validação no frontend para melhorar a experiência do usuário.
* Uso de validação no backend para segurança.
* Uso de índice único no banco para garantir integridade dos dados.
* Uso de AJAX para validações sem recarregar a página.
* Uso de mensagens amigáveis com SweetAlert2.

---

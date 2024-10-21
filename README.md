# Cajuzeiro Payment

Web application trata de autorizações de beneficios e dinheiro.

## Overview

Este projeto segue o padrão Hexagonal Architecture, também conhecido como Ports and Adapters. O objetivo deste estilo arquitetônico é desacoplar a lógica de negócios principal dos componentes externos.

## Estrutura

A Arquitetura Hexagonal gira em torno de três conceitos principais:

### 1. Lógica de Domínio

O coração do aplicativo reside aqui. Ele contém entidades, casos de uso e lógica de negócios que são independentes de preocupações externas. Esta camada permanece agnóstica de quaisquer detalhes de implementação específicos.

### 2. Portas

Portas são interfaces ou contratos que definem as interações entre o domínio e o mundo externo. Essas são interfaces que representam os casos de uso fornecidos pelo aplicativo. Por exemplo, uma interface de repositório representa os meios para acessar o banco de dados sem nenhuma implementação real.

### 3. Adaptadores

Adaptadores implementam as portas. Eles servem como a ponte entre o núcleo do aplicativo (lógica de domínio) e o mundo externo (frameworks, bancos de dados, UI, etc.). Os adaptadores são responsáveis ​​por traduzir entradas externas para o aplicativo e vice-versa.
### Migrations e Flyway Integration
#### Migrations

Os arquivos de migração contêm um conjunto de scripts SQL que representam alterações incrementais no esquema do banco de dados. Eles desempenham um papel crucial no gerenciamento de versões do esquema do banco de dados de forma sistemática. Cada script de migração representa uma única alteração no banco de dados, permitindo o controle de versão do esquema do banco de dados.
Já existe nas migrations as inserções iniciais para que a aplicação suba com dados no banco e não seja necessario criar tudo o tempo todo.
### Controllers

#### AuthorizerController
O AuthorizerController manipula solicitações HTTP relacionadas a autorizações. Ele interage com o AuthorizerService para manipular informações de pagamentos.

#### AdmController
Esse controller manipula solicitações que estariam alem dos usuarios normais, a ideia é que apenas usuarios ADM poderiam acessar e realizar os requests, porém por motivos de praticidade
ele está liberado, ele funcionará para criar novas contas e novas "wallets".

## Pré requisitos

Para executar o projeto, os seguintes softwares devem ser instalados no sistema:

- [Java](https://www.oracle.com/java/) (v17)
- [Maven](https://maven.apache.org/) (v3.9.0 or higher)
- [Docker](https://www.docker.com)
- [Postman](https://www.postman.com/)

### Building com Maven

Para buildar o projeto, navegue até o diretório raiz do projeto e execute os seguintes comandos no seu terminal:

#### Com Tests

```bash
$ mvn clean install
```

Este comando irá limpar o projeto, compilar o código-fonte, executar os testes e empacotar a aplicação.

#### Sem Tests

Para buildar o projeto sem executar os testes:

```bash
$ mvn clean install -DskipTests
```

## Para Rodar Docker

```bash
$ docker-compose up
```

Nota:

- Este processo pode levar um tempo significativo.
- Se [Docker](https://www.docker.com/) não está instalado, por favor, instale.

### Postman Collection

Encontre a Postman collection no diretório `request-example`, que contém exemplos de requisições para os endpoints da API. Importe esta coleção no Postman para interagir com a API.
A coleção inclui o seguinte:

- Exemplos de requisições para vários endpoints como GET, POST.
- Headers de requisição, body e parâmetros configurados para testes.

### Usando o Postman

1. Baixe e instale o Postman (se ainda não o fez).
2. Importe a request-example Postman collection no Postman.
3. Modifique as URLs das requisições ou payloads conforme necessário para os testes.
4. Envie requisições para interagir com os endpoints da API.

Certifique-se de atualizar os detalhes das requisições (ex.: URLs, headers, body) no Postman de acordo com o seu ambiente ou configurações específicas.
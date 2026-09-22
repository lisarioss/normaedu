# NormaEdu

**Sistema de gestão e análise preliminar de normas jurídicas**

O NormaEdu é uma API REST desenvolvida em Java e Spring Boot para organizar normas jurídicas, consultar seus vínculos e apoiar a identificação de possíveis relações entre documentos normativos.

O projeto foi concebido a partir de uma necessidade de organização e consulta de leis, decretos, portarias e outros atos normativos, com foco inicial na gestão pública municipal e na área da educação.

A aplicação permite cadastrar normas, associá-las a órgãos e assuntos, pesquisar documentos e registrar relações como alteração, revogação, regulamentação e complementação.

Também oferece processamento de arquivos PDF, identificação de referências normativas por regras textuais e criação de relações candidatas para revisão humana.

> **Importante:** a identificação automática de referências é uma análise preliminar. O sistema não determina, por conta própria, a validade jurídica de uma norma nem substitui a avaliação de um profissional responsável.

## Tecnologias

* Java 21
* Spring Boot 4.1.1
* Spring Data JPA e Hibernate
* Oracle Database Free 26ai
* Maven
* Apache PDFBox
* JUnit 5 e Mockito
* Git e GitHub

## Funcionalidades

### Gestão de normas

* Cadastro e consulta de normas jurídicas.
* Associação de normas a órgãos, assuntos e objetos normativos.
* Pesquisa por termo, ano, tipo, status e órgão.
* Registro de informações como número, ano, ementa, datas, fonte e texto integral.

### Relações normativas

* Registro de relações entre normas.
* Identificação dos tipos: `REVOGA`, `REVOGA_PARCIALMENTE`, `ALTERA`, `REGULAMENTA`, `COMPLEMENTA` e `SUBSTITUI`.
* Consulta das relações de origem e destino de uma norma.
* Visualização do histórico de relações normativas.
* Prevenção de relações duplicadas e de relações de uma norma consigo mesma.
* Confirmação humana das relações identificadas.

### Processamento de PDF

* Extração de texto de arquivos PDF.
* Identificação do tipo, número e ano da norma no documento.
* Verificação da identidade do documento antes de associá-lo à norma cadastrada.
* Detecção de referências a leis por meio de expressões regulares.
* Criação de relações candidatas quando a norma referenciada é encontrada no cadastro do mesmo órgão.

A extração utiliza texto presente no PDF. O reconhecimento de texto em documentos digitalizados como imagem (OCR) não faz parte da implementação atual.

### Alertas e pré-análise

* Registro e consulta de alertas normativos.
* Fluxo de análise de alertas.
* Pré-análise baseada em regras para sinalizar situações que exigem avaliação humana.

Os alertas e as relações candidatas são indicativos para revisão, não conclusões jurídicas automáticas.

## Requisitos

Para executar o projeto localmente, é necessário ter:

* JDK 21;
* Oracle Database Free 26ai, com um usuário e um banco configurados;
* acesso ao terminal PowerShell no Windows.

O projeto inclui o Maven Wrapper, portanto não é necessário instalar o Maven separadamente.

## Configuração do banco de dados

A configuração utilizada no desenvolvimento está no arquivo `src/main/resources/application.properties`:

```properties
spring.application.name=normaedu

spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/FREEPDB1
spring.datasource.username=NORMAEDU
spring.datasource.password=${NORMAEDU_DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

Crie o usuário `NORMAEDU` no Oracle e conceda as permissões necessárias para a aplicação. Ajuste a URL e o nome do usuário caso sua instalação seja diferente.

A senha é fornecida por uma variável de ambiente, evitando seu armazenamento no código-fonte.

No PowerShell, configure a variável para a sessão atual:

```powershell
$senhaSegura = Read-Host "Senha do usuario NORMAEDU" -AsSecureString
$ponteiro = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($senhaSegura)

try {
    $env:NORMAEDU_DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($ponteiro)
}
finally {
    [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($ponteiro)
    Remove-Variable senhaSegura, ponteiro
}
```

Não inclua senhas ou outros dados sensíveis no repositório.

## Como executar

Clone o repositório:

```bash
git clone https://github.com/lisarioss/normaedu.git
cd normaedu
```

Com o Oracle em execução e a variável `NORMAEDU_DB_PASSWORD` configurada, inicie a aplicação no PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Endpoints principais

A API utiliza o prefixo `/api/v1`.

| Método  | Endpoint                                           | Descrição                                       |
| ------- | -------------------------------------------------- | ----------------------------------------------- |
| `GET`   | `/api/v1/normas/busca`                             | Pesquisa normas com filtros                     |
| `POST`  | `/api/v1/normas/{normaId}/pdf`                     | Processa o PDF de uma norma cadastrada          |
| `POST`  | `/api/v1/documentos/pdf`                           | Extrai texto e identifica referências de um PDF |
| `POST`  | `/api/v1/relacoes-normas/norma/{normaOrigemId}`    | Cadastra uma relação normativa                  |
| `GET`   | `/api/v1/relacoes-normas/{id}`                     | Consulta uma relação pelo ID                    |
| `GET`   | `/api/v1/relacoes-normas/origem/{normaOrigemId}`   | Lista relações de origem                        |
| `GET`   | `/api/v1/relacoes-normas/destino/{normaDestinoId}` | Lista relações de destino                       |
| `GET`   | `/api/v1/relacoes-normas/norma/{normaId}`          | Lista relações associadas a uma norma           |
| `PATCH` | `/api/v1/relacoes-normas/{id}/confirmacao`         | Confirma uma relação normativa                  |

Os endpoints de processamento de PDF recebem um arquivo por requisição multipart.

### Exemplo: pesquisar normas

```http
GET /api/v1/normas/busca?ano=2026&tipo=LEI
```

### Exemplo: confirmar uma relação

```http
PATCH /api/v1/relacoes-normas/21/confirmacao
```

O identificador `21` é ilustrativo: utilize o ID de uma relação existente no seu banco de dados.

## Testes

O projeto possui testes de inicialização da aplicação e testes unitários para regras de identificação de referências e gerenciamento de relações normativas.

Para executar a suíte de testes no Windows:

```powershell
.\mvnw.cmd clean test
```

O teste de inicialização carrega o contexto do Spring Boot e, na configuração atual, depende de uma conexão válida com o Oracle. Os testes unitários dos serviços utilizam JUnit 5 e Mockito.

Na execução de validação realizada durante o desenvolvimento, os nove testes existentes passaram, sem falhas ou erros.

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/lisarios/normaedu/
│   │   ├── controller/
│   │   ├── domain/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── repository/
│   │   ├── service/
│   │   └── NormaeduApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/lisarios/normaedu/
        ├── NormaeduApplicationTests.java
        └── service/
            ├── ReferenciaNormativaServiceTest.java
            └── RelacaoNormaServiceTest.java
```

## Limitações e evolução

A identificação de referências utiliza padrões textuais e atualmente contempla referências a leis no formato número/ano. Documentos com redação diferente, referências indiretas ou texto não extraível podem exigir análise manual.

Entre as possibilidades de evolução estão o suporte a outros formatos de referência, OCR para documentos digitalizados, ampliação da cobertura de testes e uma interface para consulta e revisão das relações normativas.

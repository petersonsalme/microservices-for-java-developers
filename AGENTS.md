# AGENTS.md — microservices-for-java-developers

Diretrizes para agentes de IA trabalhando no repositório `microservices-for-java-developers`.

## Contexto do Projeto

Projeto prático baseado no livro *Microservices for Java Developers* (Rafael Benevides & Christian Posta), demonstrando composição de serviços heterogêneos (Java Spring Boot, Thorntail/MicroProfile e Node.js).

- Para o diagrama Mermaid do fluxo de chamadas e descrição de cada endpoint, consulte o [README.md](README.md).

## Estrutura do Repositório

```
microservices-for-java-developers/
├── api-gateway/         # Gateway agregador (Spring Boot + Apache Camel)
├── hello-springboot/    # Microsserviço Spring Boot (porta 8080)
├── hello-microprofile/  # Microsserviço Thorntail / MicroProfile (porta 8081)
├── node-backend/        # Serviço base em Node.js (porta 5000)
└── README.md            # Diagrama do fluxo e documentação dos serviços
```

## Regras e Comandos

- Prefix shell commands with `rtk` (see `.agents/rules/rtk.md`).

1. **Build e Execução por Serviço:**
   - **Java / Spring Boot / Thorntail:** Cada módulo Java possui seu próprio `pom.xml`.
     - Build: `mvn clean package` dentro do diretório do módulo.
   - **Node Backend:** Módulo JavaScript com npm.
     - Instalar: `npm install`
     - Executar: `node app.js`
2. **Padrões de Desenvolvimento:**
   - Manter as assinaturas de API esperadas pelo `api-gateway` e pelos clientes HTTP.
   - Commits seguindo Conventional Commits (`feat:`, `fix:`, `refactor:`, `docs:`).
   - Respostas ao usuário em Português do Brasil; código e documentação técnica em Inglês.

@.agents/rules/rtk.md

# Elobooks

# Descrição:

Este projeto consiste em um sistema de Gestão de Biblioteca que permite o controle completo de usuários e livros, além de funcionalidades de empréstimo, devolução e recomendação de livros.

O sistema permite cadastrar usuários, livros e gerenciar empréstimos, garantindo o acompanhamento da disponibilidade de cada livro. A funcionalidade de recomendação tem como objetivo sugerir títulos ao usuário com base nos livros que já pegou emprestado, tornando a experiência mais personalizada e eficiente.

Principais funcionalidades do sistema:
- Cadastro e gerenciamento de usuários;
- Cadastro e gerenciamento de livros;
- Empréstimo e devolução de livros;
- Sistema de recomendação de livros;

# Como rodar o projeto:

Este projeto foi desenvolvido em Java 21, utilizando SpringBoot, Gradle, PostgreSQL e Docker.

Toda a infraestrutura de backend, incluindo banco de dados, migrações com Flyway e a aplicação Spring Boot, é executada via Docker, não sendo necessária nenhuma configuração manual de banco de dados no ambiente local.

A aplicação roda localmente via Docker. Cada execução cria um ambiente isolado, incluindo banco de dados, sem dependências externas ou compartilhamento de dados.

Passos para rodar o backend:

1. Você precisará do GIT para fazer o clone do projeto: 
git clone https://github.com/leandrolbnu/elobooks

2. Abra um cmd e digite:
cd <caminho do projeto clonado>
docker compose up --build

3. Aguarde inicializar os containers, que irá iniciar o PostgreSQL, executará as migrations do Flyway e por fim subirá a aplicação SpringBoot. 

4. Depois que estiver rodando, a aplicação estará acessível em:
http://localhost:8080

# Documentação da API (Swagger)

O projeto conta com documentação interativa da API via Swagger. Pelo Swagger é possível: 
- Visualizar todos os endpoints implementados na aplicação; 
- Ver os modelos de requisição e respostas;
- Chamar os endpoints direto pelo navegador e obter as respostas;
- Testar as principais funcionalidades do sistema como cadastrar, buscar, alterar e deletar registros.

Após subir a aplicação com o Docker, o Swagger estará disponível em:

http://localhost:8080/swagger-ui/index.html


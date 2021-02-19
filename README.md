

# Validador de CPFs e CNPJs

Esse projeto tem por finalidade criar um backend em que seja possível fazer a validação
de CPFs e CNPJs. Para isso, foi utilizado o Spring Boot para a criação das Entidades,
e o consumo da API pelo cliente final utilizando REST.

## Instalação
  - Baixe o arquivo ZIP do repositório ou faça um clone deste em sua máquina.
  - Descompacte o arquivo em sua máquina e abra-o em sua IDE de preferência(para desenvolvimento, utilizei o Spring Tools)
  - Crie um banco de dados de nome datadb em sua máquina (Postgres)

### Detalhes

A Aplicação foi montada utilizando o Spring Boot, com o Padrão MVC de organização de código.
Foram criadas duas Entidades:
  - Customer - Consumidor da Aplicação, que vai fazer as requisições de consulta da API.
  - Solicitation - Solicitação da Aplicação, que recebe o Valor do CPF/CNPJ e verifica se é valido.
  
Foram criados os Repositories para flexibilizar a criação dos métodos do CRUD das entidades.
Por último, foram criados os Controllers, com as Anotações necessárias para que seja possível realizar requisições REST.

Foram criadas também uma classe para fazer um tratamento de erros nas requisições(NotFoundException) e a configuração do Banco de Dados para
ser enviado ao Heroku.

Seguem abaixo as Dependências que foram utilizadas:

  - ``` Spring Web ```
  - ``` Spring Data JPA ``` 
  - ``` PostgreSQL ```

### Demonstração

Para ilustrar a utilização do aplicativo, você pode fazer o download do repositório e rodar em sua máquina.
Foi feito também um deploy para o Heroku, onde é possível fazer as requisições utilizando a sua ferramenta
de envio de requisições REST de preferência.

URL de requisição da API: [Clique aqui](https://stark-tor-87979.herokuapp.com/api/)





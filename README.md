

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

Foram criadas também uma classe para fazer a configuração do Banco de Dados para ser enviado ao Heroku.

Seguem abaixo as Dependências que foram utilizadas:

  - ``` Spring Web ```
  - ``` Spring Data JPA ``` 
  - ``` PostgreSQL ```

### Modo de Utilização

Para utilização do aplicativo, você pode fazer o download do repositório e rodar em sua máquina.
Foi feito também um deploy da aplicação para o Heroku, para que não seja necessário baixar o projeto,
dessa forma, é apenas necessário fazer as requsições utilizando o seu REST Service de preferência

URL de requisição Local: ```http://localhost:8080/api/```
URL de requisição de Produção: ```https://stark-tor-87979.herokuapp.com/api/```

Endpoints importantes da aplicação:
```
 - Customers
 
	- GET ALL- /customers
 
 	- GET BY ID - /api/customers/{id}
	
	- POST - /customers/
			{
				"email": "email@gmail.com",
				"password": "12345678"
			}
	
	- PUT - /customers/{id}
			{
				"email": "email@gmail.com",
				"password": "12345678"
			}
	
	- DELETE BY ID - /api/customers/{id}
	
	- DELETE ALL  - /api/customers/
	
 - Solicitations
 
	- GET ALL FROM A CUSTOMER - /customers/{customerId}/solicitations
	
	- GET BY ID = /customers/{customerId}/solicitations/{id}
	
	- POST - /customers/{customerId}/solicitations
			{
				"value": "email@gmail.com",
				"description": ""
			}
	
	- PUT - /customers/{customerId}/solicitations/{id}
			{
				"value": "email@gmail.com",
				"description": ""
			}

	- DELETE BY ID - /customers/{customerId}/solicitations/{id}
	
 - Payments
 
	- GET THE TOTAL PAYMENT FROM A CUSTOMER - /api/customers/{customerId}/payment
	
	- GET THE MONTH PAYMENT FROM A CUSTOMER - /api/customers/{customerId}/payment/{month}
```




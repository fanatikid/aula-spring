# Aula 8 - Spring Boot e Thymeleaf

As alterações a realizar durante a aula consistem na implementação de uma interface web para clientes. Para isso deve 

1- Alterar a vista bankView.html de forma a que os clientes sejam apresentados numa tabela. Note que o controlador adiciona dois cliente para facilitar o desenvolvimento

2- Defina uma nova classe controlador que resolve os caminhos com prefixo /banks/bank/{code}/clients

3- Defina também os métodos que necessita no controlador, um GET e um POST para /banks/bank/{code}/clients, para criar um formulário de cliente e submeter um formulário de cliente, respetivamente

4- Adicionar à vista bankView.html um formulário de criação de clientes

5- Criar um novo método no controlador de cliente para o caminho /banks/bank/{code}/clients/client/{id} que procura no banco com o código indicado o utilizador com o identificador id, e apresenta uma vista com toda a informação sobre esse cliente

6- Alterar a vista bankView.html para incluir os links para se aceder às vistas de cliente definidas no passo anterior

7- Colocar uma validação na classe cliente que impede a existência de dois clientes com o mesmo id. Altere a interface, vista e controlador, de forma a que uma mensagem de erro seja apresentada ao utilizador

## Instrucções

Para executar o código dê o comando

```
mvn clean spring-boot:run
```

podendo então aceder no browser em http://localhost:8080/banks

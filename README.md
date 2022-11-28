<h1 >  Valex-api  🔗  &nbsp  </h1><br>


##  :link: Sobre

Valex é uma aplicação feita em java e postgres que após se cadastrar e cessar a conta terá como solicitar criação de cartões de crédito e débito para fazer compras, sendo que, os cartões de crédito, quando utilizado, pode-se resgatar suas faturas mensais pelo mes e ano.

## :hammer: Features

:ballot_box_with_check: POST `/users` - Recebe name, 'cpf', 'email' e 'password'.

:ballot_box_with_check: GET `/users/:userId` - Busca usuários pelo id.

:ballot_box_with_check: POST `/login` - Recebe 'email' e 'password'.

:ballot_box_with_check: POST `/cards` - Recebe 'userId', 'type' (CREDIT ou DEBIT), 'limit'.

:ballot_box_with_check: GET `/cards/users/:userId` - Busca os cards do usuário.

:ballot_box_with_check: POST `/cards/activate/:cardId` - Recebe 'passcode'. Ativa cartões criados para utilização.

:ballot_box_with_check: POST `/orders` - Recebe 'purchaseType', 'cardId', 'shopName', 'passcode', 'products': [{'title': '', 'description': '', 'price': 0}].

:ballot_box_with_check: DELETE `/orders/:orderId` - Deleta compras realizadas polo id.

:ballot_box_with_check: GET `/invoices/cards/:cardId/months/:monthNumber(0-11)/years/:yearNumber` - Busca a fatura mensal da data indicada.

## :woman_technologist: Tecnologias

<p align="center">
  <img style='margin: 5px;' src="https://img.shields.io/badge/Junit5%20-%2320232a.svg?&style=for-the-badge&color=25A162&logo=Junit5&logoColor=000000"/>
  <img style='margin: 5px;' src="https://img.shields.io/badge/Spring Security%20-%2320232a.svg?&style=for-the-badge&color=6DB33F&logo=SpringSecurity&logoColor=000000"/>
  <img style='margin: 5px;' src="https://img.shields.io/badge/Java%20-%2320232a.svg?&style=for-the-badge&color=E11C23&logo=OpenJDK&logoColor=000000"/>
  <img style='margin: 5px;' src="https://img.shields.io/badge/Spring%20-%2320232a.svg?&style=for-the-badge&color=6DB33F&logo=Spring&logoColor=000000"/>
  <img style='margin: 5px;' src="https://img.shields.io/badge/PostgreSQL%20-%2320232a.svg?&style=for-the-badge&color=4169E1&logo=PostgreSQL&logoColor=ffffff"/>
</p>

# General description

I've studied the principles of vending machine functionality and decided to split it into 3 main controllers: admin, wallet, buyer.

1. The admin acts as a data loader into the device.
1. The wallet acts as a storehouse of money that the buyer pays in before purchasing the product.
1. The buyer can get a list of products and purchase them.

# Dependencies

To solve this problem, I used the following libraries and frameworks:

**Spring boot starter web** - includes the minimum required set of classes to create and run a RESTful application.

**Spring boot starter data JPA** - includes components for working with databases according to the ORM approach.

**PostgreSQL** - allows you to connect to the DBMS of the same name, which we need to store information (product name, quantity, unit price, location and amount of money contributed by the buyer).

**Lombok** - is a plugin that allows you to reduce the amount of boilerplate code (setters, getters, constructors, etc.)

**Spring boot starter test** - required for testing (includes JUnit, Mockito, etc.)

**H2 Database** - in-memory database which is used for tests.

# Executing Instructions

Before running the application, we need to create a PostgreSQL database called "vending\_machine". At startup, the program automatically connects to it and creates 3 tables: "cell", "item" and "wallet". Be careful, with the current implementation after restarting, our application will erase all the data that was previously in these tables, so if necessary, make sure that they are saved. After starting the program, we can open our [API](http://localhost:8080/swagger-ui.html) and start using it.

## **Admin Controller**

**importData** - loads the list of products, their quantity and price into our database in JSON format:
```json
     {

       "config": {

         "columns": "string",

         "rows": 0

       },

       "items": [

         {

           "amount": 0,

           "name": "string",

           "price": "string"

         }

       ]

     }
```
## **Wallet Controller**

**add** - adds the buyer's money to the internal account, which he can use to buy the necessary product (you need to specify the value in cents).

**getChange** - returns change after purchase, or if the user changes his mind about buying something and wants to get the money back.

## **Buyer Controller**

**getAll** - shows the list of products, their quantity, unit price, location.

**buy**- buys the selected product, according to the specified row and column (row is case-sensitive, so make sure you are writing it in upper-case).

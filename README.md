# Cinema-Room-REST-Service

This project represents a simple Spring REST service that will help you manage a small movie theatre. 
A more detailed description can be found at https://hyperskill.org/projects/189?track=12

## Objectives

The **/purchase** endpoint handles **POST** requests and marks a booked ticket as purchased.

A request contains the following data:

- **row** — the row number;
- **column** — the column number.

We also add the ability to refund a ticket if a customer can't come and watch a movie. We will use tokens to secure the ticket refund process.
Check if the specified ticket is available. If the ticket is booked, mark the seat as purchased and don't show it in the list.

If the purchase is successful, the response body should be as follows:
    
    {
      "token": "00ae15f2-1ab6-4a02-a01f-07810b42c0ee",
      "ticket": {
          "row": 1,
          "column": 1,
          "price": 10
      }
    }
    
We are using the **randomUUID()** method of the **UUID** class to generate tokens. 
    
The ticket price is determined by a row number. If the row number is less or equal to 4, set the price at 10. All other rows cost 8 per seat.

If the seat is taken, respond with a **400 (Bad Request)** status code. The response body should contain the following:
  
    {
      "error": "The ticket has been already purchased!"
    }
  
If users pass a wrong row/column number, respond with a **400** status code and the following line:
  
    {
      "error": "The number of a row or a column is out of bounds!"
    }
  
### Examples

Example 1: a **GET /seats** request

Response body:

    {
      "total_rows": 5,
      "total_columns": 7,
      "available_seats": [
        {
           "row":1,
           "column":1,
           "price":10
        },
        {
           "row":1,
           "column":2,
           "price":10
        },
        {
           "row":1,
           "column":3,
           "price":10
        },

        ........
      ]
    }
    

Example 2: a **POST /purchase** correct request

Request body:

    {
      "row": 3,
      "column": 4
    }
    
Response body:

    {
      "row": 3,
      "column": 4,
      "price": 10
    }
  
Example 3: a **POST /purchase** request, the ticket is already booked

Request body:
  
    {
      "row": 3,
      "column": 4
    }
  
Response body:
  
    {
      "error": "The ticket has been already purchased!"
    }
  
Example 4: a **POST /purchase** request, a wrong row number

Request body:
  
    {
      "row": 15,
      "column": 4
    }
  
Response body:
  
    {
      "error": "The number of a row or a column is out of bounds!"
    }
  
The **/return** endpoint will handle **POST** requests and allow customers to refund their tickets.

The request should have the token feature that identifies the ticket in the request body. 
Once you have the token, you need to identify the ticket it relates to and mark it as available. 
The response body should be as follows:

    {
        "returned_ticket": {
            "row": 1,
            "column": 1,
            "price": 10
        }
    }
  
The returned_ticket should contain the information about the returned ticket.

If you cannot identify the ticket by the token, make your program respond with a 400 status code and the following response body:

    {
        "error": "Wrong token!"
    }
  
### Examples

Example 1: a correct **POST /purchase** request

Request body:

    {
        "row": 3,
        "column": 4
    }
  
Response body:

    {
        "token": "e739267a-7031-4eed-a49c-65d8ac11f556",
        "ticket": {
            "row": 3,
            "column": 4,
            "price": 10
        }
    }
  
Example 2: **POST /return** with the correct token

Request body:

    {
        "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
    }
  
Response body:

    {
        "returned_ticket": {
            "row": 1,
            "column": 2,
            "price": 10
        }
    }
  
Example 3: **POST /return** with an expired token

Request body:

    {
        "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
    }
  
Response body:

    {
        "error": "Wrong token!"
    }  

The /stats endpoint will handle **POST** requests with URL parameters. 
If the URL parameters contain a password key with a super_secret value, return the movie theatre statistics in the following format:

    {
        "current_income": 0,
        "number_of_available_seats": 81,
        "number_of_purchased_tickets": 0
    }
    
Take a look at the description of keys:

- **current_income** — shows the total income of sold tickets.
- **number_of_available_seats** — shows how many seats are available.
- **number_of_purchased_tickets** — shows how many tickets were purchased.

If the parameters don't contain a password key or a wrong value has been passed, respond with a **401** status code. The response body should contain the following:

    {
        "error": "The password is wrong!"
    }
    
### Examples

Example 1: a **POST /stats** request with no parameters

Response body:

    {
        "error": "The password is wrong!"
    }
    
Example 2: a **POST /stats** request with the correct password

Response body:

    {
        "current_income": 30,
        "number_of_available_seats": 78,
        "number_of_purchased_tickets": 3
    }

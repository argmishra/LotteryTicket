Lottery Ticket API
==========  
        
## Problem

We are looking for a REST interface to a simple lottery system. The rules of the game are described below.

## Lottery Rules

You have a series of lines on a ticket with 3 numbers, each of which has a value of 0, 1, or 2. For each ticket if the sum of the values is 2, the result is 10. Otherwise if they are all the same, the result is 5. Otherwise so long as both 2nd and 3rd numbers are different from the 1st, the result is 1. Otherwise the result is 0.

## Implementation

Implement a REST interface to generate a ticket with n lines. Additionally we will need to have the ability to check the status of lines on a ticket. We would like the lines sorted into outcomes before being returned. It should be possible for a ticket to be amended with n additional lines. Once the status of a ticket has been checked it should not be possible to amend.

We would like tested, clean code to be returned.
        		
## Solution
1. Create a ticket - Implemented 
2. Return list of tickets - Implemented
3. Get individual ticket - Implemented
4. Retrieve status of ticket -  Implemented
5. Amend ticket lines - Implemented
6. Actuator end point - Implemented (http://localhost:8080/actuator)
7. Swagger Integration - Implemented (http://localhost:8080/swagger-ui.html)
8. Generic Exception Handling and Validations - Implemented (Refer to java classes)
9. In Memory Database - Implemented (Refer properties file) 
10. Run Script - Implemented
11. Unit Testing - Implemented
12. Integration Testing - Implemented
13. Code Coverage - Implemented


```curl
curl -X POST "http://localhost:8080/ticket?size=2" -H "Content-Type: application/json"

curl -X GET "http://localhost:8080/ticket/1" -H "Content-Type: application/json"

curl -X GET "http://localhost:8080/ticket" -H "Content-Type: application/json"

curl -X GET "http://localhost:8080/ticket/1/status" -H "Content-Type: application/json"

curl -X PUT "http://localhost:8080/ticket/1?size=2" -H "Content-Type: application/json"
```

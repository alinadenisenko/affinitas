# affinitas

A RESTful web service that can validate successive moves on a Sudoku board. It can also recognise and 
indicate if the Sudoku is finished with the current move.

##Building and running

mvn clean spring-boot:run

### To generate a new sudoku game send a GET Request:

http://localhost:8080/sudoku/generate

Response: 

{"id":"c095c3c1-0ec5-4e7f-a5f1-5386ba2fcbb0","data":[[7,0,0,0,4,0,5,3,0],[0,0,5,0,0,8,0,1,0],[0,0,8,5,0,9,0,3,0],[5,3,9,0,6,0,0,0,1],[0,0,0,0,1,0,0,0,5],[8,0,0,7,2,0,9,0,0],[9,0,7,4,0,0,0,0,0],[0,0,0,0,5,7,0,0,0],[6,0,0,0,0,0,0,5,0]]}

### To check a new move for the game send a POST Request:

http://localhost:8080/sudoku/validate_move/{id}

id - unique id for the game, that was returned by generate request

{"row":2,"col":3,"digit":7}

row, col, digit possible values are in 1..9 

Response: 

{"result":"VALID"}

result - result of move. Possible values:
VALID,
INVALID,
FINISH

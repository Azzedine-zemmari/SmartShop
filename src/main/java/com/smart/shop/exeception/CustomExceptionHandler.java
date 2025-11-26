package com.smart.shop.exeception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ApiErreur> UserNotFoundException(UserNotFound e){
        ApiErreur apiErreur = new ApiErreur();
        apiErreur.setStatus(404);
        apiErreur.setMessage(e.getMessage());
        apiErreur.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(apiErreur, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExiste.class)
    public ResponseEntity<ApiErreur> userAlreadyExists(UserAlreadyExiste e) {
        ApiErreur apiErreur = new ApiErreur();
        apiErreur.setMessage(e.getMessage());
        apiErreur.setLocalDateTime(LocalDateTime.now());
        apiErreur.setStatus(422);
        return new ResponseEntity<>(apiErreur, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiErreur> IncorrectPassword(IncorrectPasswordException e) {
        ApiErreur apiErreur = new ApiErreur();
        apiErreur.setMessage(e.getMessage());
        apiErreur.setLocalDateTime(LocalDateTime.now());
        apiErreur.setStatus(401);
        return new ResponseEntity<>(apiErreur, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErreur> ProductNotFound(ProductNotFoundException e){
        ApiErreur apiErreur = new ApiErreur(e.getMessage(),LocalDateTime.now(),404);
        return new ResponseEntity<>(apiErreur,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ApiErreur> StockNotEnough(NotEnoughStockException e){
        ApiErreur apiErreur = new ApiErreur(e.getMessage() , LocalDateTime.now(),422);
        return new ResponseEntity<>(apiErreur,HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

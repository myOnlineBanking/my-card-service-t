package jee.ensas.cardservice.controllers;

import jee.ensas.cardservice.dtos.ErrorClass;
import jee.ensas.cardservice.exceptions.CardException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CardException.class)
    public ResponseEntity<ErrorClass> handleChequeException(CardException ex, HttpServletRequest request) {
        logger.error("Card Service Error" , ex);
        return ResponseEntity.status(ex.getStatus()).body(new ErrorClass(ex.getStatus(), ex.getMessage(), request.getRequestURI()));
    }

}

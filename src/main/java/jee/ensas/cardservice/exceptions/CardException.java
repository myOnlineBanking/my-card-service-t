package jee.ensas.cardservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardException extends RuntimeException{

    private final int status;

    public CardException(int status , String message){
        super(message);
        this.status = status;
    }
}
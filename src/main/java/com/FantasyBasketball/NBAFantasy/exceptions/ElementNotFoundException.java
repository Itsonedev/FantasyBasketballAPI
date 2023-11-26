package com.FantasyBasketball.NBAFantasy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ElementNotFoundException(){}

    public ElementNotFoundException(String message){
        super(message);
    }

    public ElementNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

package com.FantasyBasketball.NBAFantasy.exceptions;

import java.io.Serial;

public class PlayerAlreadyOnATeamException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public PlayerAlreadyOnATeamException() {
    }

    public PlayerAlreadyOnATeamException(String message) {
        super(message);
    }

}

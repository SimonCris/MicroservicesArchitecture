package com.cartmicroservice.project.exceptions;

/**
 * Eccezione custom per il caso di token non creato correttamente
 * */
public class TokenErrorException extends Exception{

    public TokenErrorException(String messageError) {
        super(messageError);
    }
}

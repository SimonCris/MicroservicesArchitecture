package com.springopenid.project.exceptions;

/**
 * Eccezione custom per il caso di utente non loggato.
 * Passiamo nel costruttore il messaggio di errore che vogliamo visualizzare.
 */
public class TokenErrorException extends Exception{

    public TokenErrorException(String messageError) {
        super(messageError);
    }
}

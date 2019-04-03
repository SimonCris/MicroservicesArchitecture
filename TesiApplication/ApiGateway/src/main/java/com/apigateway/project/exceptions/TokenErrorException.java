package com.apigateway.project.exceptions;

/**Eccezione custom che viene invocata quando si genera un errore relativo al JWT */
public class TokenErrorException extends Exception{

    public TokenErrorException(String messageError) {
        super(messageError);
    }

}

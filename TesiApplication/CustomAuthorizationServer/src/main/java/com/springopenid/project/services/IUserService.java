package com.springopenid.project.services;

import com.springopenid.project.entities.User;
import com.springopenid.project.exceptions.TokenErrorException;

import java.util.Map;

public interface IUserService {

    /**Metodo che restituisce un user dal DB ricevuto come parametro un userId
     *
     * @param jwtParametersMap
     * @return
     */
    User getUserFromUserEmail(Map<String, Object> jwtParametersMap) throws Exception;

    /**Metodo che richiama il dao e imposta il valore del consenso dell'utente nel db e riceve in input
     * una Map proveniente da una richiesta da parte di altri servizi*/
    Boolean setUserConsent(Map<String, Object> jwtParametersMap) throws TokenErrorException;

}

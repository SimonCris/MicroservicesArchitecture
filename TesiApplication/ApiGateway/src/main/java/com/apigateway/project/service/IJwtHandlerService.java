package com.apigateway.project.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public interface IJwtHandlerService {

    /** Crea il JWT con i parametri passati in input al metodo */
    String createRoleInfoJwt(String subject, Date date) throws UnsupportedEncodingException;

    /** Verifica il JWT tramite la request e recupera lo userRole restituendolo in una String */
    String verifyJwtAndGetUserRole(String jwt) throws UnsupportedEncodingException;

    /**Metodo che permette di creare un JWT relativo al consenso dato dall'utente.
     *
     * @param subject
     * @param date
     * @param userConsent
     * @return
     * @throws UnsupportedEncodingException
     */
    String createConsentUserJwt(String subject, Date date, Map<String, String> userConsent) throws UnsupportedEncodingException;

    /** Verifica il JWT tramite la request e recupera i dati sul consenso dell'utente */
    Map<String, Object> verifyJwtAndGetUserConsentInfo(String jwt) throws UnsupportedEncodingException;

}

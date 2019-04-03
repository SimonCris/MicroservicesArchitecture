package com.apigateway.project.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public interface IUserInfoService {

    /**
     * Metodo che effettua una richiesta all'endpoint di google per ottenere le informazioni
     * relative all'utente autenticato mediante l'invio dell'accessToken restituito dal server di
     * google al momento dell'autenticazione/autorizzazione
     *
     * @param googleAccessToken
     * @return
     */
    Map<String, Object> getUserInfoFromGoogle(OAuth2AccessToken googleAccessToken);

    /**
     * Metodo che Riceve dal server di autorizzazione il ruolo relativo all'utente autenticato con google
     * mediante l'invio dell'indirizzo email dell'utente stesso
     *
     * @param userEmail
     * @return
     */
    String getUserRole(String userEmail) throws UnsupportedEncodingException;

    /**
     * Metodo che ricevuto un Principal estrae e restituisce il googleAccessToken ad esso riferito
     *
     * @param principal
     * @return
     */
    OAuth2AccessToken saveGoogleAccessToken(Object principal);

    /**
     * Metodo che restituisce un Jwt contenente i dati di consenso dell'utente
     *
     * @param subject
     * @param date
     * @param userConsent
     * @return
     * @throws UnsupportedEncodingException
     */
    String getUserConsentJwt(String subject, Date date, Map<String, String> userConsent) throws UnsupportedEncodingException;

    /**Metodo che restituisce un hashmap con i dati relativi al consenso dell'utente
     *
     * @param jwt
     * @return
     */
    Map<String, Object> getUserConsentData(String jwt) throws UnsupportedEncodingException;

    Boolean setUserConsent(Boolean consentValue);
}
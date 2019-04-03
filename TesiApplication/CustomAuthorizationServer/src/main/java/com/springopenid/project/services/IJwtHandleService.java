package com.springopenid.project.services;

import com.springopenid.project.exceptions.SubjectFieldNotFoundException;
import com.springopenid.project.exceptions.TokenErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public interface IJwtHandleService {

    /**
     * Metodo che permette di creare un JWT relativo ad uno user.
     * I Parametri sono appunto relativi ad un utente
     *
     * @param subject
     * @param date
     * @return
     */

    String createJwt(String subject, String name, String surname, String id, Date date) throws UnsupportedEncodingException;

    /**Metodo che permette di verificare la validità di un jwt, recuperandolo dalla request e
     * recuperare i dati presenti al suo interno.
     *
     * @param request
     * @return dati presenti nel jwt
     */
    String verifyRoleUserJwt(HttpServletRequest request) throws TokenErrorException, UnsupportedEncodingException, SubjectFieldNotFoundException;

    /**
     * Metodo che permette di creare un JWT relativo ad uno roleUser.
     *
     * @param subject
     * @param date
     * @return
     */
    String createRoleUserJwt(String subject, Date date) throws UnsupportedEncodingException;


}

package com.springopenid.project.services;

import com.springopenid.project.exceptions.SubjectFieldNotFoundException;
import com.springopenid.project.exceptions.TokenErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface IUserRoleService {

    /**Metodo che restituisce un'entity user_role relativa all'utente con email userEmail
     *
     * @param request
     * @return
     */
    String getUserRoleFromEmailUser(HttpServletRequest request) throws Exception;


    /**Metodo che restituisce un jwt contenente il role dell'user
     *
     * @param role
     * @return
     */
    String getUserRoleJwt(String role) throws UnsupportedEncodingException;


}

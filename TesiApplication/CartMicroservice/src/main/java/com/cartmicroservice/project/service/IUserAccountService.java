package com.cartmicroservice.project.service;

import com.cartmicroservice.project.entities.User;

import java.util.LinkedHashMap;
import java.util.List;

public interface IUserAccountService {

    /**Metodo che restituisce un utente dal CustomAuthorizationServer */
    User getUserFromUserEmail(String userEmail);

}

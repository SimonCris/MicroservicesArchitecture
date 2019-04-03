package com.cartmicroservice.project.service;

import com.cartmicroservice.project.entities.CartProduct;
import com.cartmicroservice.project.entities.User;
import com.cartmicroservice.project.exceptions.TokenErrorException;

import java.util.List;
import java.util.Map;

public interface ICartProductService {

    /**Metodo che restituisce la lista di tutti gli oggetti presenti nel carrello di un utente specifico */
    List<CartProduct> getAllCartProducts(Map<String, Object> jwtParameters) throws TokenErrorException;

    /**Metodo che salva un nuovo prodotto nel cart di un utente specifico */
    Boolean saveNewCartProduct(Map<String, Object> jwtParameters) throws TokenErrorException;

    /**Metodo che restituisce un prodotto se presente nel cart di un utente specifico */
    CartProduct getCartProductByNameAndUserEmail(String productName, String userEmail);

    /**Metodo che rimuove un prodotto dal cart di un utente specifico */
    Boolean removeProductFromCart(Map<String, Object> jwtParameters) throws TokenErrorException;

    /**Metodo che rimuove tutte le info dell'utente dal DB */
    String removeUserInfo(String userEmail) throws TokenErrorException;

    Boolean setProductToOrder(Map<String, Object> jwtParametersMap) throws TokenErrorException;

    List<CartProduct> getAllOrderProducts(Map<String, Object> jwtParameters) throws TokenErrorException;

    Boolean removeProductToOrder(Map<String, Object> jwtParametersMap) throws TokenErrorException;

    Boolean finishOrder(Map<String, Object> jwtParameters) throws TokenErrorException;

    User getUserFromUserEmail(String userEmail);

    Boolean removeAllOrderProducts(Map<String, Object> jwtParametersMap) throws TokenErrorException;
}

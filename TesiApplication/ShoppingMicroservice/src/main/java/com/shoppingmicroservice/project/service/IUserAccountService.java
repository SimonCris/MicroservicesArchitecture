package com.shoppingmicroservice.project.service;

import com.springopenid.project.entities.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IUserAccountService {

    /**Metodo che recupera tutti i prodotti preferiti presenti nel DB ei prodotti
     *
     * @return
     */
    List<LinkedHashMap> getAllFavoriteProducts();

    /**Metodo che permette di salvare un prodotto fra i preferiti richiamando productMicroservice */
    Boolean addProductToFavorite(String prodName, String prodBrand, String prodImage, String prodPrice);

    /**Metodo che elimina un prodotto dai preferiti tramite una chiamata al ProductMicroservice */
    Boolean removeProductFromFavorites(String productName, String userEmail);

    /**Metodo che restituisce un utente dal CustomAuthorizationServer */
    User getUserFromUserEmail();

    /**Metodo che recupera tutti i prodotti  presenti nel cart di un utente
     *
     * @return
     */
    List<LinkedHashMap> getAllCartProducts();

    /**Metodo che permette di salvare un prodotto nel cart richiamando cartMicroservice */
    Boolean addProductToCart(String prodName, String prodBrand, String prodImage, String prodPrice);

    /**Metodo che elimina un prodotto dal cart chiamando cartMicroservice */
    Boolean removeProductFromCart(String productName, String userEmail);

    /**Metodo che effettua una richiesta all'apigateway affinche vengano rimossi tutti i
     * dati relativi all'utente da tutte le componenti dell'architettura */
    String revokeUserConsentFromAll();

    /**Metodo che permette di aggiungere un prodotto all'ordine*/
    Boolean addProductToOrder(String productId);

    /**Metodo che recupera tutti i prodotti inseriti per un ordine */
    List<LinkedHashMap> getAllOrderProducts();

    /**Metodo che rimuove tutti i prodotti inseriti per un ordine */
    Boolean removeProductToOrder(String productId);

    /**Metodo che rimuove tutti i prodotti dal carrello dopo che è stato effettuato l'ordine */
    Boolean removeAllOrderProduct();

    /**Metodo che permette di completare l'ordine con i prodotti inseriti nell'ordine stesso */
    Boolean completeOrder();
}

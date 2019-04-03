package com.shoppingmicroservice.project.service;

import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.entities.LivingRoomProduct;
import com.springopenid.project.entities.User;

import java.util.LinkedHashMap;
import java.util.List;

public interface IHomePageService {

    /**Metodo che recupera tutti i prodotti di cucina presenti nel DB ei prodotti
     *
     * @return
     */
    List<LinkedHashMap> getAllKitchenProducts();

    /**Metodo che recupera tutti i prodotti da soggiorno presenti nel DB ei prodotti
     *
     * @return
     */
    List<LinkedHashMap> getAllLivingRoomProducts();

    /**Metodo che restituisce un prodotto da cucina da productMicroservice, a cui viene passato
     * l'id del prodotto */
    KitchenProduct getKitchenProduct(Integer idProduct);

    /**Metodo che restituisce un prodotto da soggiorno da productMicroservice, a cui viene passato
     * l'id del prodotto */
    LivingRoomProduct getLivingRoomProduct(Integer idProduct);

}

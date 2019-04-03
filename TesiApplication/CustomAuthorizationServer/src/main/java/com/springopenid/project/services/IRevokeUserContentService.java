package com.springopenid.project.services;

public interface IRevokeUserContentService {

    /**Metodo che effettua una chiamata a product microservice e richiede la cancellazione
     * di tutti i dati relativi all'utente */
    Boolean revokeUserConsentFromProductMicroservice(String userEmail);

    /**Metodo che effettua una chiamata a cart microservice e richiede la cancellazione
     * di tutti i dati relativi all'utente */
    String revokeUserConsentFromCartMicroservice(String userEmail);

    /**Metodo che effettua la cancellazione di tutti i dati relativi ai ruoli dell'utente */
    Boolean revokeUserConsentFromUserRoleTable(String userEmail);

    /**Metodo che effettua la cancellazione di tutti i dati relativi all'utente */
    Boolean revokeUserConsentFromUserTable(String userEmail);

}

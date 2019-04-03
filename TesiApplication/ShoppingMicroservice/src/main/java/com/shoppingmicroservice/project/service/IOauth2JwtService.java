package com.shoppingmicroservice.project.service;

import java.util.Map;

public interface IOauth2JwtService {

    /**Metodo che genera un jwt access token sicuro tramite il JwtAccessTokenConverter
     * custom
     *
     * @param requestParameters
     * @return
     */
    String getJwtAccessToken(Map<String, String> requestParameters);

    /**Metodo che converte un jwt in una Map contenente i parametri del jwt stesso e lo fa
     * tramite il jwt viene letto e decodificato dal tokenStore
     *
     * @param jwt
     * @return
     */
    Map<String, Object> getJwtParameter(String jwt);

}

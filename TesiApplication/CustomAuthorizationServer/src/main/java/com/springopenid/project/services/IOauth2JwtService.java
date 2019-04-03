package com.springopenid.project.services;

import java.util.Map;

public interface IOauth2JwtService {

    /**Metodo che converte un jwt in una Map contenente i parametri del jwt stesso
     *
     * @param jwt
     * @return
     */
    Map<String, Object> getJwtParameter(String jwt);

    String getJwtAccessToken(Map<String, Object> requestParameters);
}

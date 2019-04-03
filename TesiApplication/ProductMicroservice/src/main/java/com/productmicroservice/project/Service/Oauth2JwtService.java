package com.productmicroservice.project.service;

import com.productmicroservice.project.oAuth2jwt.CustomAccessTokenConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Oauth2JwtService implements IOauth2JwtService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    TokenStore tokenStore;

    /**Metodo che converte un jwt in una Map contenente i parametri del jwt stesso e lo fa
     * tramite il jwt viene letto e decodificato dal tokenStore
     *
     * @param jwt
     * @return
     */
    @Override
    public Map<String, Object> getJwtParameter(String jwt) {

        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(jwt);

        Map<String, Object> map = oAuth2AccessToken.getAdditionalInformation();

        return map;

    }

    /**Metodo che genera un jwt access token sicuro tramite il JwtAccessTokenConverter
     * custom e riceve in input i parametri del jwt originale
     *
     * @return
     */
    @Override
    public String getJwtAccessToken(Map<String, Object> requestParameters) {

        /** Converto la map di String,Object in una map di String, String per inviarla successivamente
         * all'accessTokenConverter */
        Map<String, String> reqParam = new HashMap<>();

        requestParameters.forEach((key,value)->{

            reqParam.put(key, String.valueOf(value));

        });

        /** Estraggo dai parametri del jwt, grazie all'accessTokenConverter, prima l'autenticazione
         * oAuth2 passata nel jwt e successivamente l'oauth2AccessToken  */
        OAuth2Authentication auth2Authentication = accessTokenConverter.extractAuthentication(requestParameters);
        OAuth2AccessToken oAuth2AccessToken = accessTokenConverter.extractAccessToken(
                (String) requestParameters.get("id_token"), new HashMap<>());

        /** In questa mappa memorizzo i parametri che sono presenti nel jwt originale */
        Map<String,Object> details = (Map<String, Object>) auth2Authentication.getDetails();

        /** Dalla mappa details inserisco, nella mappa dei parametri aggiuntivi dell'accessToken solo
         * i parametri di interesse e non inserisco gli altri */
        oAuth2AccessToken.getAdditionalInformation().putAll(details);

        /** Tramite il metodo custom enhance dell'AccessTokenConverter creo il jwt tramite
         * l'oauth2AccessToken e l'autenticazione oaut2 creati precedentemente */
        OAuth2AccessToken customAccessToken = null;
        if(accessTokenConverter instanceof CustomAccessTokenConverter){
            customAccessToken = accessTokenConverter.enhance(oAuth2AccessToken, auth2Authentication);
        }

        return customAccessToken.getValue();

    }
}

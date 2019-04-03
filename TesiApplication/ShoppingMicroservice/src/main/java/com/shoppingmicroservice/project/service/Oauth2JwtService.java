package com.shoppingmicroservice.project.service;

import com.apigateway.project.security.OpenIdConnectAdminDetails;
import com.apigateway.project.security.OpenIdConnectUserDetails;
import com.shoppingmicroservice.project.oAuth2Jwt.CustomOAuth2ReqFactory;
import com.shoppingmicroservice.project.oAuth2Jwt.CustomTokenEnhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Oauth2JwtService implements IOauth2JwtService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    CustomOAuth2ReqFactory CustomOAuth2ReqFactory;

    @Autowired
    TokenStore tokenStore;

    /**Metodo che genera un jwt access token sicuro tramite il JwtAccessTokenConverter
     * custom
     *
     * @return
     */
    @Override
    public String getJwtAccessToken(Map<String, String> requestParameters) {

        Authentication authenticationFromGoogle = SecurityContextHolder.getContext().getAuthentication();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OAuth2AccessToken googleAccessToken = null;

        if(principal instanceof OpenIdConnectAdminDetails) {
            googleAccessToken = ((OpenIdConnectAdminDetails) principal).getToken();
        }
        else if(principal instanceof OpenIdConnectUserDetails) {
            googleAccessToken = ((OpenIdConnectUserDetails) principal).getToken();
        }

        OAuth2Request oAuth2Request = CustomOAuth2ReqFactory.generateOAuth2Request(requestParameters);

        /** Creazione di un'autenticazione OAuth2 usando la OAuth2Request creata precedentemente e
         * l'autenticazione di google relativa all'utente che ha appena effettuato l'accesso */
        OAuth2Authentication auth2AuthenticationFromGoogle = new OAuth2Authentication(oAuth2Request, authenticationFromGoogle);

        /** Utilizzo l'accessTokenConverter per generare il jwt richiamando il metodo enhance in cui
         * aggiungo alcuni parametri custom. All'accessTokenConverter passo il token passato direttamente da
         * google a cui aggiungo */

        OAuth2AccessToken customAccessToken = null;

        if(accessTokenConverter instanceof CustomTokenEnhancer){
            customAccessToken = accessTokenConverter.enhance(googleAccessToken, auth2AuthenticationFromGoogle);
        }

        return customAccessToken.getValue();

    }

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


}

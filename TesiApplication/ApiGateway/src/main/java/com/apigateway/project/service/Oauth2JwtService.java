package com.apigateway.project.service;

import com.apigateway.project.oAuth2Jwt.CustomTokenEnhancer;
import com.apigateway.project.security.OpenIdConnectAdminDetails;
import com.apigateway.project.security.OpenIdConnectUserDetails;
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
    com.apigateway.project.oAuth2Jwt.CustomOAuth2ReqFactory CustomOAuth2ReqFactory;

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

    @Override
    public String provaInvioJwt(String jwt) {

        /** Genero l'header e creo la request inserendo il jwt nell'header*/
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);

        HttpEntity<?> userInfoRequest = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare l'endopoint del server di autorizzazione che restituisce
         * i permessi relativi all'utente.*/
        RestTemplate restTemplate = new RestTemplate();

        /**.exchange vuole come parametri l'url del servizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request creato con le varie header e il tipo della risposta
         * (in questo caso String)*/
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("http://localhost:8030/sendProvaJwt",
                        HttpMethod.POST, userInfoRequest, String.class);

        /**La risposta del server di autorizzazione contiene i permessi relativi all'utente autenticato*/
        String jwtResponse = responseEntity.getBody();

        Map<String, Object> jwtResponseParameters = this.getJwtParameter(jwtResponse);

        return "RespParam1 -> " + jwtResponseParameters.get("RespParam1") + " -- " +
                "RespParam2 -> " + jwtResponseParameters.get("RespParam2");
    }

}

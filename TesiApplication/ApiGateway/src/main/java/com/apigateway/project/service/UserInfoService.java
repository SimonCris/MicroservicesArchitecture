package com.apigateway.project.service;

import com.apigateway.project.security.OpenIdConnectAdminDetails;
import com.apigateway.project.security.OpenIdConnectUserDetails;
import com.apigateway.project.utils.JsonResponseBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService implements IUserInfoService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${google.userInfoEndpoint}")
    private String userInfoEndPoint;

    @Value("${userRoleEndPoint}")
    private String userRoleEndPoint;

    @Value("${setUserConsent}")
    private String setUserConsent;

    @Autowired
    IJwtHandlerService JwtHandlerService;

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    /**Metodo che effettua una richiesta all'endpoint di google per ottenere le informazioni
     * relative all'utente autenticato mediante l'invio dell'accessToken restituito dal server di
     * google al momento dell'autenticazione/autorizzazione
     *
     * @param googleAccessToken
     * @return
     */
    @Override
    public Map<String, Object> getUserInfoFromGoogle(OAuth2AccessToken googleAccessToken) {

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        HttpEntity<?> userInfoRequest = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare l'endopoint dell'api gateway inviando la richiesta
         * di autenticazione. Restituisce risposta positiva o negativa*/
        RestTemplate restTemplate = new RestTemplate();

        /**.exchange vuole come parametri l'url del servizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request creato con le varie header e il tipo della risposta
         * (in questo caso String)*/
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(userInfoEndPoint + googleAccessToken.getValue(),
                        HttpMethod.GET, userInfoRequest, String.class);

        /**La risposta del server di autenticazione di google è una stringa contenente il json dei dati.
         * Viene mappata una hashMap convertendo la stringa json in un'hashMap che conterrà tutte le
         * coppie chiave:valore relative ai dati personali dell'utente*/
        String resp = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> userInfoFromGoogle = new HashMap<>();

        try {
            userInfoFromGoogle = mapper.readValue(resp, new TypeReference<Map<String, Object>>() {
            });
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return userInfoFromGoogle;

    }

    /**Metodo che Riceve dal server di autorizzazione il ruolo relativo all'utente autenticato con google
     * mediante l'invio dell'indirizzo email dell'utente stesso
     *
     * @param userEmail
     * @return
     */
    @Override
    public String getUserRole(String userEmail) throws UnsupportedEncodingException {

        String jwt = null;

        try {

            jwt = JwtHandlerService.createRoleInfoJwt(userEmail, new Date());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(userRoleEndPoint,
                        HttpMethod.POST, userInfoRequest, JsonResponseBody.class);

        /**La risposta del server di autorizzazione contiene i permessi relativi all'utente autenticato*/
        String response = (String) responseEntity.getBody().getResponse();

        return JwtHandlerService.verifyJwtAndGetUserRole(response);

    }

    /**Metodo che ricevuto un Principal estrae e restituisce il googleAccessToken ad esso riferito
     *
     * @param principal
     * @return
     */
    @Override
    public OAuth2AccessToken saveGoogleAccessToken(Object principal) {

        OAuth2AccessToken token = null;

        if(principal instanceof OpenIdConnectAdminDetails) {
            token = ((OpenIdConnectAdminDetails) principal).getToken();
        }
        else if(principal instanceof OpenIdConnectUserDetails) {
            token = ((OpenIdConnectUserDetails) principal).getToken();
        }

        return token;

    }

    /**Metodo che restituisce un Jwt contenente i dati di consenso dell'utente
     *
     * @param subject
     * @param date
     * @param userConsent
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getUserConsentJwt(String subject, Date date, Map<String, String> userConsent) throws UnsupportedEncodingException {

        String jwt = JwtHandlerService.createConsentUserJwt(subject, date, userConsent);
        return jwt;

    }

    /**Metodo che restituisce un hashmap con i dati relativi al consenso dell'utente
     *
     * @param jwt
     * @return
     */
    @Override
    public Map<String, Object> getUserConsentData(String jwt) throws UnsupportedEncodingException {

        Map<String, Object> consentData = JwtHandlerService.verifyJwtAndGetUserConsentInfo(jwt);

        return consentData;

    }

    @Override
    public Boolean setUserConsent(Boolean userConsent) {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("Update user consent", String.valueOf(userConsent));

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare customAuthorizationServer e restituisce
         * la risposta all'update*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(setUserConsent,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt */
        List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

        /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
         * vengono inseriti in una lista di LinkedHashMap*/

        Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

        if(responseParameters.get("userConsentUpdated").equals("userUpdated")){

            logger.debug("User Consent Value updated");
            return true;

        }

        logger.debug("User Consent Value not updated");
        return false;

    }

}

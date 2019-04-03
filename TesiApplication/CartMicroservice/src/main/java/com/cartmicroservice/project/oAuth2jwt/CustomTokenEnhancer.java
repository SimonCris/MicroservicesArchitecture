package com.cartmicroservice.project.oAuth2jwt;

import com.cartmicroservice.project.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**Implementazione Custom del converter dei jwt
 *
 */
@Component
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	private Logger logger = LoggerFactory.getLogger(getClass());

    private JsonParser objectMapper = JsonParserFactory.create();

	@Autowired
    JwtUtils JwtUtils;

	/**Metodo che permette di aggiungere e/o rimuovere claim al jwt standard sovrascrivendo il metodo enhance del
	 * JwtAccessTokenConverter.
	 * In questo caso elimino tutti i dati personali dall'accessToken di google passato in input
	 * ed inserisco solamente i parametri per la verifica da parte dei client
	 *
	 * @param accessToken
	 * @param authentication
	 * @return
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

	    /** Dalla OAuth2Request incorporata nella OAuth2Authentication estraggo i parametri che voglio
         * inserire nel jwt */
        OAuth2Request oAuth2Request = authentication.getOAuth2Request();

        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);

        /** Lista dei parametri che voglio aggiungere al jwt che viene settata inizialmente con
         * i parametri del token originale */
        Map<String, Object> additionalInfo = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

       // String tokenId = result.getValue();

        /** Se il value del token non è impostato, viene settato */
       /** if (!additionalInfo.containsKey(TOKEN_ID)) {
            additionalInfo.put(TOKEN_ID, tokenId);
        }
        else {
            tokenId = (String) additionalInfo.get(TOKEN_ID);
        }

        /** Rimuovo dal jwt il JTI che in questo caso coincide con il googleAccessToken relativo
         * all'utente autenticato e l'id_token. Inoltre svuoto lo scope in modo che non siano visibili gli
         * scope provenienti dall'autenticazione di google*/
        additionalInfo.remove("jti");
        additionalInfo.remove("id_token");
        result.getScope().clear();

        oAuth2Request.getRequestParameters().forEach((key,value)-> additionalInfo.put(key, value));

        /** Aggiungo al token la lista di informazioni aggiuntive creata e riempita in precedenza */
        result.setAdditionalInformation(additionalInfo);

        /** Codifica il nuovo valore del token con le additionalInfo aggiuntive */
        result.setValue(encode(result, authentication));

        OAuth2RefreshToken refreshToken = result.getRefreshToken();

        /** Gestione del token di refresh */
        if (refreshToken != null) {

            DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
            encodedRefreshToken.setValue(refreshToken.getValue());
            // Refresh tokens do not expire unless explicitly of the right type
            encodedRefreshToken.setExpiration(null);

            try {

                Map<String, Object> claims = objectMapper
                        .parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());

               /** if (claims.containsKey(TOKEN_ID)) {
                    encodedRefreshToken.setValue(claims.get(TOKEN_ID).toString());
                } */
            }
            catch (IllegalArgumentException e) {
            }

            Map<String, Object> refreshTokenInfo = new LinkedHashMap<String, Object>(
                    accessToken.getAdditionalInformation());
            /**refreshTokenInfo.put(TOKEN_ID, encodedRefreshToken.getValue());
            refreshTokenInfo.put(ACCESS_TOKEN_ID, tokenId); */
            encodedRefreshToken.setAdditionalInformation(refreshTokenInfo);

            DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(
                    encode(encodedRefreshToken, authentication));

            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {

                Date expiration = ((ExpiringOAuth2RefreshToken) refreshToken).getExpiration();
                encodedRefreshToken.setExpiration(expiration);
                token = new DefaultExpiringOAuth2RefreshToken(encode(encodedRefreshToken, authentication), expiration);

            }
            result.setRefreshToken(token);
        }

        logger.debug("Access Token created: " + result.getValue());
        return result;
	}

    /**Metodo che permette di estrarre l'autenticazione oauth2 con i suoi dettagli prendendo in input
     * i parametri del jwt
     *
     * @param claims
     * @return
     */
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {

        OAuth2Authentication authentication
                = super.extractAuthentication(claims);

        authentication.setDetails(claims);

        logger.debug("OAuth2Authentication from the JWT, has been recovered: " + authentication.toString());
        return authentication;
    }

}
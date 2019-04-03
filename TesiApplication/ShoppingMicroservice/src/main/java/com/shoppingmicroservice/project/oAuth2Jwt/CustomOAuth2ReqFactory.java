package com.shoppingmicroservice.project.oAuth2Jwt;

import com.apigateway.project.security.OpenIdConnectAdminDetails;
import com.apigateway.project.security.OpenIdConnectUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
public class CustomOAuth2ReqFactory implements OAuth2RequestFactory {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**Metodo che, ricevuti i parametri aggiuntivi per la request, crea una OAuth2Request
     * usando i metodi dell'interfaccia OAuth2RequestFactory
     *
     * @param requestParameters
     * @return
     */
    public OAuth2Request generateOAuth2Request(Map<String, String> requestParameters){

        AuthorizationRequest authorizationRequest = this.createAuthorizationRequest(requestParameters);

        return createOAuth2Request(authorizationRequest);

    }

    /**Metodo che genera una AuthenticationRequest che verrà usata per creare la OAuth2Request
     *
     * @param requestParameters
     * @return
     */
    @Override
    public AuthorizationRequest createAuthorizationRequest(Map<String, String> requestParameters) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /** Componenti della Request*/
        Map<String, String> approvalParameters = new HashMap<>();
        String clientId = "";
        Set<String> scope = new HashSet<>();
        Set<String> resourceIds = new HashSet<>();
        Collection<? extends GrantedAuthority> authorities = null;
        boolean approved = true;
        String state = "";
        String redirectUri = "";
        Set<String> responseTypes = new HashSet<>();

        if(principal instanceof OpenIdConnectAdminDetails) {
            authorities = ((OpenIdConnectAdminDetails) principal).getAuthorities();
        }
        else if(principal instanceof OpenIdConnectUserDetails) {
            authorities = ((OpenIdConnectUserDetails) principal).getAuthorities();
        }

        return new AuthorizationRequest(requestParameters,approvalParameters,clientId,scope,resourceIds,
                authorities,approved,state,redirectUri,responseTypes);

    }

    /**Metodo che crea e restituisce una OAuth2Request usando tutti i parametri settati nella
     * AuthorizationRequest passata in input
     *
     * @param request
     * @return
     */
    @Override
    public OAuth2Request createOAuth2Request(AuthorizationRequest request) {

        Map<String, Serializable> extensions = new HashMap<>();

        return new OAuth2Request(request.getRequestParameters(), request.getClientId(), request.getAuthorities(),
                request.isApproved(), request.getScope(), request.getResourceIds(), request.getRedirectUri(),
                request.getResponseTypes(), extensions);

    }

    @Override
    public OAuth2Request createOAuth2Request(ClientDetails client, TokenRequest tokenRequest) {
        return null;
    }

    @Override
    public TokenRequest createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient) {
        return null;
    }

    @Override
    public TokenRequest createTokenRequest(AuthorizationRequest authorizationRequest, String grantType) {
        return null;
    }
}

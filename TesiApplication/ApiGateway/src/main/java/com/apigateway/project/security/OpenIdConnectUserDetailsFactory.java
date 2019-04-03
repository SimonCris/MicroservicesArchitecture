package com.apigateway.project.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Map;

/**Factory che crea uno user o un admin in funzione dei permessi relativi all'utente autenticato
 */
@Component
public class OpenIdConnectUserDetailsFactory {

    public UserDetails getOpenIdConnectUserDetails(Map<String, String> userInfo,
                                                   OAuth2AccessToken token, String role){

        if(role.equals("ROLE_USER"))
            return new OpenIdConnectUserDetails(userInfo,token);

        if(role.equals("ROLE_ADMIN"))
            return new OpenIdConnectAdminDetails(userInfo,token);

        return null;

    }

}

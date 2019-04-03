package com.springopenid.project.controllers;

import com.springopenid.project.exceptions.TokenErrorException;
import com.springopenid.project.services.IOauth2JwtService;
import com.springopenid.project.services.IRevokeUserContentService;
import com.springopenid.project.services.IUserService;
import com.springopenid.project.utils.JsonResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RevokeUserConsentController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    @Autowired
    IRevokeUserContentService RevokeUserContentService;

    @Autowired
    IUserService UserService;

    @Value("${revokeUserConsentFromProductMicroservice}")
    private String revokeUserConsentFromProductMicroservice;

    @Value("${revokeUserConsentFromCartMicroservice}")
    private String revokeUserConsentFromCartMicroservice;

    /**Endpoint che elimina tutte le info dell'utente da ogni componente dell'architettura */
    @RequestMapping(value = "/revokeUserConsent", method = POST)
    public ResponseEntity<JsonResponseBody> revokeUserConsent(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);
            String userEmail = (String) jwtParameters.get("user_name");

            logger.debug("userEmail " + userEmail);

            /**Chiamate ai microservizi e alle tabelle che possono contenere le info
             * relative all'utente */
            Boolean userInfoRemovedFromProdMicr =
                    RevokeUserContentService.revokeUserConsentFromProductMicroservice(userEmail);

            String userInfoRemovedFromCartProd =
                    RevokeUserContentService.revokeUserConsentFromCartMicroservice(userEmail);

          /**  Boolean userInfoRemovedFromUserRoleTable =
                    RevokeUserContentService.revokeUserConsentFromUserRoleTable(userEmail);

            Boolean userInfoRemovedFromUserTable =
                    RevokeUserContentService.revokeUserConsentFromUserTable(userEmail); */

          /**Se tutte le info sono state rimosse, allora cambio il flag del consenso a false  */
            if (userInfoRemovedFromProdMicr &&
                    userInfoRemovedFromCartProd.equals("All user info removed")
                                                                /**&&
                    userInfoRemovedFromUserRoleTable &&
                    userInfoRemovedFromUserTable */) {

                Map<String, Object> requestParameters = new HashMap<>();
                requestParameters.put("Update user consent", "false");
                requestParameters.put("user_name", userEmail);

                Boolean userConsentUpdated = UserService.setUserConsent(requestParameters);

                if(userConsentUpdated.equals(true)) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new JsonResponseBody(HttpStatus.OK.value(), "All user info removed"));
                }
                else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User consent not updated"));
                }

            } else if (userInfoRemovedFromProdMicr &&
                    userInfoRemovedFromCartProd.equals("There are already some user info into products")){

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "There are already some user info into products"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

        return null;

    }


}

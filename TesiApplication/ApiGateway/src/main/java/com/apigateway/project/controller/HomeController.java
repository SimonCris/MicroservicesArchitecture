package com.apigateway.project.controller;

import com.apigateway.project.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**Classe Controller che gestisce tutti gli endpoint relativi alle chiamate effettuate durante il
 * processo di autenticazione ed autorizzazione */
@Controller
public class HomeController {

    Map<String, Object> userInfoFromGoogle = null;
    OAuth2AccessToken googleAccessToken = null;

    @Autowired
    IUserInfoService UserInfoService;

    @Value("${consentPageEndPoint}")
    private String consentPageEndPoint;

    @Value("${welcomeClientPage}")
    private String welcomeClientPage;

    @Value("${shoppingHomePage}")
    private String shoppingHomePage;

    /**Endpoint che viene chiamato subito dopo l'autenticazione ed autorizzazione e reindirizza l'utente
     * alla pagina per il consenso al trattamento dei propri dati personali
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/home")
    public void home(HttpServletResponse response) throws IOException {

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        googleAccessToken = UserInfoService.saveGoogleAccessToken(principal);

        userInfoFromGoogle = UserInfoService.getUserInfoFromGoogle(googleAccessToken);

        response.sendRedirect(consentPageEndPoint);

    }

    /**Endpoint che restitusce la jsp di consenso per l'utente
     *
     * @return jsp che mostra all'utente l'informativa sulla privacy al cliente
     */
    @RequestMapping(value = "/goToUserConsentPage")
    public String goToUserConsentPage(){

        return "userConsentPage";

    }

    /**Endpoint che viene chiamato quando si effettua il logout.
     * Viene invalidata la sessione e l'utente viene reindirizzato alla pagina di benvenuto iniziale.
     *
     * @param request
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        /** Viene annullata l'autenticazione/sessione */
        new SecurityContextLogoutHandler().logout(request, null, null);

        try {

            /** Link a cui viene reindirizzato il client dopo il logout*/
            response.sendRedirect(welcomeClientPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**Endpoint che gestisce il consenso dato da parte dell'utente e reindirizza l'utente alla home page.
     * Viene effettuata una chiamata al server di autorizzazione per settare il flag del consenso a true
     * relativo all'utente.
     *
     * @param request
     * @param response
     * @return jsp relativa alla home page o jsp relativa alla pagina di benvenuto
     * @throws IOException
     */
    @RequestMapping(value = "/getUserConsent", method = GET)
    public String getUserConsent(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /** Recupero delle scelte fatte dall'utente nella form di consenso tramite i parametri passati
         * nella request. */

        String emailConsent = request.getParameter("gridRadiosEmail");
        String nameAndSurnameConsent = request.getParameter("gridRadiosNameAndSurname");
        String userIdConsent = request.getParameter("gridRadiosUserId");
        String privacyPolicyConsent = request.getParameter("gridRadiosPrivacy");

        Map<String, String> userConsentMap = new HashMap<>();
        userConsentMap.put("emailConsent", emailConsent);
        userConsentMap.put("nameAndSurnameConsent", nameAndSurnameConsent);
        userConsentMap.put("userIdConsent", userIdConsent);
        userConsentMap.put("privacyConsent", privacyPolicyConsent);

        /**Se il consenso è stato dato setto a true il flag del consenso nel DB*/
        if(emailConsent.equals("yesConsentEmail") && nameAndSurnameConsent.equals("yesConsentNameAndSurname") &&
                userIdConsent.equals("yesConsentUserId") && privacyPolicyConsent.equals("yesConsentPrivacy")){

            /**Effettuo una chiamata al server di autorizzazione e modifico il flag del
             * consenso dell'utente su true*/
            UserInfoService.setUserConsent(true);

            response.sendRedirect(shoppingHomePage);

        }else{

            response.sendRedirect(welcomeClientPage);

        }

        return null;
    }

    /**Endpoint che gestisce il consenso non dato da parte dell'utente.
     * Viene effettuata una chiamata al server di autorizzazione per settare il flag del consenso a false
     * e l'utente viene reindirizzato alla home page.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/negateUserConsent")
    public void negateUserConsent(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /**Effettuo una chiamata al server di autorizzazione e modifico il flag del
         * consenso dell'utente su false */
        UserInfoService.setUserConsent(false);

        response.sendRedirect(shoppingHomePage);

    }

    /**Endpoint che restituisce la pagina della policy sulla privacy
     *
     * @return
     */
    @RequestMapping(value = "/getPolicyPage")
    public String getPolicyPage(){

        return "privacyPolicy";

    }


}

package com.springopenid.project.services;

import com.springopenid.project.dao.UserDao;
import com.springopenid.project.dao.UserRoleDao;
import com.springopenid.project.entities.User;
import com.springopenid.project.entities.UserRole;
import com.springopenid.project.utils.JsonResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RevokeUserConsentService implements IRevokeUserContentService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    @Autowired
    UserRoleDao UserRoleDao;

    @Autowired
    UserDao UserDao;

    @Value("${revokeUserConsentFromProductMicroservice}")
    private String revokeUserConsentFromProductMicroservice;

    @Value("${revokeUserConsentFromCartMicroservice}")
    private String revokeUserConsentFromCartMicroservice;

    @Override
    public Boolean revokeUserConsentFromProductMicroservice(String userEmail) {

        Map<String, Object> parametersToSend = new HashMap<>();
        parametersToSend.put("user_email", userEmail);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productMicroservice e rimuovere tutte le info dell'utente*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(revokeUserConsentFromProductMicroservice,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("All user info removed")) {
            logger.debug("All user info removed from product Microservice");
            return true;
        }

        logger.debug("User Info not removed from product Microservice");
        return false;

    }

    @Override
    public String revokeUserConsentFromCartMicroservice(String userEmail) {

        Map<String, Object> parametersToSend = new HashMap<>();
        parametersToSend.put("user_email", userEmail);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e rimuovere tutte le info dell'utente*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(revokeUserConsentFromCartMicroservice,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("All user info removed")) {
            logger.debug("All user info removed from cart Microservice");
            return "All user info removed";
        }
        else if(responseValue.equals("There are already some user info into products")){
            logger.debug("There are already some user info into products");
            return "There are already some user info into products";
        }

        logger.debug("User Info not removed from cart Microservice");
        return null;

    }

    @Override
    public Boolean revokeUserConsentFromUserRoleTable(String userEmail) {

        if(userEmail != null){

            List<UserRole> userRoleList;
            userRoleList = UserRoleDao.findAllByUserEmail(userEmail);

                    userRoleList.forEach((userRole) -> UserRoleDao.delete(userRole));

            logger.debug("All user info removed from userRole Table");
            return true;

        }

        logger.debug("User Info not removed from userRole Table");
        return false;

    }

    @Override
    public Boolean revokeUserConsentFromUserTable(String userEmail) {

        if(userEmail != null){

            List<User> userList;
            userList = UserDao.findAllByUserEmail(userEmail);

            userList.forEach((user) -> UserDao.delete(user));

            logger.debug("All user info removed from user Table");
            return true;

        }

        logger.debug("User Info not removed from user Table");
        return false;

    }
}

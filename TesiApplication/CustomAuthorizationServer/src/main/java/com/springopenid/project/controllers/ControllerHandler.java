package com.springopenid.project.controllers;

import com.springopenid.project.dao.UserDao;
import com.springopenid.project.dao.UserRoleDao;
import com.springopenid.project.entities.User;
import com.springopenid.project.entities.UserRole;
import com.springopenid.project.exceptions.SubjectFieldNotFoundException;
import com.springopenid.project.exceptions.TokenErrorException;
import com.springopenid.project.services.IOauth2JwtService;
import com.springopenid.project.services.IUserRoleService;
import com.springopenid.project.services.IUserService;
import com.springopenid.project.services.UserService;
import com.springopenid.project.utils.Decryption;
import com.springopenid.project.utils.Encryption;
import com.springopenid.project.utils.JsonResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.time.Instant.parse;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ControllerHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IUserRoleService UserRoleService;

    @Autowired
    IUserService UserService;

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    @Autowired
    UserRoleDao UserRoleDao;

    @Autowired
    Encryption Encryption;

    @Autowired
    Decryption Decryption;

    @RequestMapping(value = "/getUserRole", method = POST)
    public ResponseEntity<JsonResponseBody> getRoleUser(HttpServletRequest request) throws Exception {

        String role = UserRoleService.getUserRoleFromEmailUser(request);

        String jwt = UserRoleService.getUserRoleJwt(role);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new JsonResponseBody(HttpStatus.OK.value(), jwt));

    }

    @RequestMapping(value = "/getUser", method = POST)
    public ResponseEntity<JsonResponseBody> getUser(HttpServletRequest request) throws Exception {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParametersMap = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            User userFromDB = UserService.getUserFromUserEmail(jwtParametersMap);

            if (userFromDB != null) {

                Map<String, Object> parametersToSend = new HashMap<>();
                parametersToSend.put("user_email", userFromDB.getUser_email());
                parametersToSend.put("user_name", userFromDB.getUser_name());
                parametersToSend.put("user_surname", userFromDB.getUser_surname());
                parametersToSend.put("user_address", userFromDB.getUser_address());
                parametersToSend.put("user_domicile_city", userFromDB.getUser_domicile_city());
                parametersToSend.put("user_consent", userFromDB.getUser_consent());

                String date = userFromDB.getUser_birthday_date().toString();
                parametersToSend.put("user_birthday_date", date);

                String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "ok"));
            }
            else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not found into DB!"));

            }
        }
        else {
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @RequestMapping(value = "/setUserConsent", method = POST)
    public ResponseEntity<JsonResponseBody> setUserConsent(HttpServletRequest request) throws TokenErrorException, UnsupportedEncodingException, SubjectFieldNotFoundException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParametersMap = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean userConsentUpdate = UserService.setUserConsent(jwtParametersMap);

            if (userConsentUpdate.equals(true)) {

                Map<String, Object> parametersToSend = new HashMap<>();
                parametersToSend.put("userConsentUpdated", "userUpdated");

                String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "updated"));
            }
            else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not updated!"));

            }
        }
        else {
            throw new TokenErrorException("JWT not found in the request");
        }

    }

}

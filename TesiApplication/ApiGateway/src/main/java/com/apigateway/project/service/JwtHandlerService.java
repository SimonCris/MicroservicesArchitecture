package com.apigateway.project.service;

import com.apigateway.project.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class JwtHandlerService implements IJwtHandlerService {

    @Autowired
    JwtUtils JwtUtils;

    @Override
    public String createRoleInfoJwt(String subject, Date date) throws UnsupportedEncodingException {

        /** Inizializzo una Date, prendendola dalla date passata in input e sommo a questa data il
         * periodo per cui il JWT sarà valido (più o meno il periodo in millisecondi di una sessione */

        Date nowDate = date;
        nowDate.setTime(nowDate.getTime() + (300*1000));

        /** Creo il JWT (che è sottoforma di una stringa JSON), passando i parametri relativi
         * allo user e lo restituisco*/
        String token = JwtUtils.generateRoleInfoJwt(subject, nowDate);

        return token;

    }

    @Override
    public String verifyJwtAndGetUserRole(String jwt) throws UnsupportedEncodingException {

        return JwtUtils.jwtRoleUser2Map(jwt);

    }

    @Override
    public String createConsentUserJwt(String subject, Date date, Map<String, String> userConsent) throws UnsupportedEncodingException {

        String jwt = JwtUtils.generateUserConsentJwt(subject, date, userConsent);

        return jwt;
    }

    @Override
    public Map<String, Object> verifyJwtAndGetUserConsentInfo(String jwt) throws UnsupportedEncodingException {

        Map<String, Object> userConsentInfo = JwtUtils.jwtUserConsent2Map(jwt);

        return userConsentInfo;

    }

}

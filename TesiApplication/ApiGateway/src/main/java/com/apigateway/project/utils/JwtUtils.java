package com.apigateway.project.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe fornisce un metodo per generare e convalidare token Web JSON tramite la
 * io.jsonwebtoken.* .
 */
@Component
public class JwtUtils {

    @Value("${secretKeyJwt}")
    private String secretKeyJwt;

    /**
     * Questo metodo genera il token Jwt da inviare al server di autorizzazione per ottenere
     * il ruolo dell'utente autenticato.
     * Definisco anche una chiave di cifratura con cui verrà creata la string del JWT
     *
     * @param subject userEmail
     * @param date    new Date
     * @return String jwt
     * @throws java.io.UnsupportedEncodingException
     */
    public String generateRoleInfoJwt(String subject, Date date) throws java.io.UnsupportedEncodingException{

        String jwt = Jwts.builder()
                .setHeaderParam("typ","jwt")
                .setSubject(subject)
                .setExpiration(date) /** Tempo di durata del JWT */
                .signWith(
                        SignatureAlgorithm.HS256,
                        secretKeyJwt.getBytes("UTF-8")
                )
                .compact();

        return jwt;
    }

    /**Questo metodo genera il token Jwt contenente le scelte realtive al consenso fatte dall'utente.
     * Definisco anche una chiave di cifratura con cui verrà creata la string del JWT
     *
     * @param subject
     * @param date
     * @param userConsent
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public String generateUserConsentJwt(String subject, Date date, Map<String,
            String> userConsent) throws java.io.UnsupportedEncodingException{

        String jwt = Jwts.builder()
                .setHeaderParam("typ","jwt")
                .setSubject(subject)
                .setExpiration(date) /** Tempo di durata del JWT */
                .claim("emailConsent", userConsent.get("emailConsent"))
                .claim("nameAndSurnameConsent", userConsent.get("nameAndSurnameConsent"))
                .claim("userIdConsent", userConsent.get("userIdConsent"))
                .claim("privacyConsent", userConsent.get("privacyConsent"))
                .signWith(
                        SignatureAlgorithm.HS256,
                        secretKeyJwt.getBytes("UTF-8")
                )
                .compact();

        return jwt;
    }

    /**
     * Questo metodo converte il token in una mappa di dati utente verificandone la validità.
     *
     * @param jwt
     * @return HashMap<String, Object> di dati utente
     * @throws java.io.UnsupportedEncodingException
     */
    public String jwtRoleUser2Map(String jwt) throws java.io.UnsupportedEncodingException, ExpiredJwtException{

        Jws<Claims> claim = Jwts.parser()
                .setSigningKey(secretKeyJwt.getBytes("UTF-8"))
                .parseClaimsJws(jwt);


        Date expDate = claim.getBody().getExpiration();
        String subj = claim.getBody().getSubject();

        Map<String, Object> userData = new HashMap<>();

        userData.put("exp_date", expDate);
        userData.put("subject", subj);

        Date now = new Date();
        if(now.after(expDate)){
            throw new ExpiredJwtException(null, null, "Session expired!");
        }

        return (String) userData.get("subject");
    }

    /**
     * Questo metodo converte il token in una mappa di dati utente verificandone la validità.
     *
     * @param jwt
     * @return HashMap<String, Object> di dati utente
     * @throws java.io.UnsupportedEncodingException
     */
    public Map<String, Object> jwtUserConsent2Map(String jwt) throws java.io.UnsupportedEncodingException, ExpiredJwtException{

        Jws<Claims> claim = Jwts.parser()
                .setSigningKey(secretKeyJwt.getBytes("UTF-8"))
                .parseClaimsJws(jwt);

        Date expDate = claim.getBody().getExpiration();
        String subj = claim.getBody().getSubject();

        String emailConsent = (String) claim.getBody().get("emailConsent");
        String nameAndSurnameConsent = (String) claim.getBody().get("nameAndSurnameConsent");
        String userIdConsent = (String) claim.getBody().get("userIdConsent");
        String privacyConsent = (String) claim.getBody().get("privacyConsent");

        Map<String, Object> userData = new HashMap<>();

        userData.put("exp_date", expDate);
        userData.put("subject", subj);
        userData.put("emailConsent", emailConsent);
        userData.put("nameAndSurnameConsent", nameAndSurnameConsent);
        userData.put("userIdConsent", userIdConsent);
        userData.put("privacyConsent", privacyConsent);

        Date now = new Date();
        if(now.after(expDate)){
            throw new ExpiredJwtException(null, null, "Session expired!");
        }

        return userData;
    }

    /**
     * Questo metodo converte il token in una mappa di dati utente verificandone la validità.
     *
     * @param jwt
     * @return HashMap<String, Object> di dati utente
     * @throws java.io.UnsupportedEncodingException
     */
    public Map<String, Object> jwt2Map(String jwt) throws java.io.UnsupportedEncodingException, ExpiredJwtException{

        Jws<Claims> claim = Jwts.parser()
                .setSigningKey(secretKeyJwt.getBytes("UTF-8"))
                .parseClaimsJws(jwt);

        String name = claim.getBody().get("name", String.class);
        String scope = (String) claim.getBody().get("scope");

        Date expDate = claim.getBody().getExpiration();
        String subj = claim.getBody().getSubject();

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("scope", scope);
        userData.put("exp_date", expDate);
        userData.put("subject", subj);

        Date now = new Date();
        if(now.after(expDate)){
            throw new ExpiredJwtException(null, null, "Session expired!");
        }

        return userData;
    }


    /**
     * Questo metodo estrae il jwt dall'intestazione o dal cookie nella richiesta Http se è presente.
     *
     * @param request
     * @return jwt
     */
    public String getJwtFromHttpRequest(HttpServletRequest request) {
        String jwt = null;
        if (request.getHeader("jwt") != null) {
            jwt = request.getHeader("jwt");     //token presente in header
        } else if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();   //token presente in cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }
        return jwt;
    }


}

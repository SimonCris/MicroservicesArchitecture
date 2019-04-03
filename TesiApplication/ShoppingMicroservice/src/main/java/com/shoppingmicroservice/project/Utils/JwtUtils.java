package com.shoppingmicroservice.project.Utils;

import io.jsonwebtoken.*;
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

    /**
     * Questo metodo genera il token Jwt da inviare al client e lo costruisco con i dati che mi servono
     * in questo caso i 4 parametri relativi all'utente.
     * Definisco anche una chiave di cifratura con cui verrà creata la string del JWT
     *
     * @param subject "RGNLSN87H13D761R"
     * @param date    new Date(1300819380)
     * @param name    "Alessandro Argentieri"
     * @param scope   "user"
     * @return String jwt
     * @throws java.io.UnsupportedEncodingException
     */
    public static String generateJwt(String subject, Date date, String name, String scope) throws java.io.UnsupportedEncodingException{

        String jwt = Jwts.builder()
                .setSubject(subject)
                .setExpiration(date) /** Tempo di durata del JWT */
                .claim("name", name)
                .claim("scope", scope)
                .signWith(
                        SignatureAlgorithm.HS256,
                        "myPersonalSecretKey12345".getBytes("UTF-8")
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
    public static Map<String, Object> jwt2Map(String jwt) throws java.io.UnsupportedEncodingException, ExpiredJwtException {

        Jws<Claims> claim = Jwts.parser()
                .setSigningKey("myPersonalSecretKey12345".getBytes("UTF-8"))
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
    public static String getJwtFromHttpRequest(HttpServletRequest request){
        String jwt = null;
        if(request.getHeader("jwt") != null){
            jwt = request.getHeader("jwt");     //token presente in header
        }else if(request.getCookies() != null){
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

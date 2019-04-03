package com.springopenid.project.services;

import com.springopenid.project.exceptions.SubjectFieldNotFoundException;
import com.springopenid.project.exceptions.TokenErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service(value = "JwtHandleService")
public class JwtHandleService implements IJwtHandleService {

    private Logger log = LoggerFactory.getLogger(JwtHandleService.class);

    @Autowired
    com.springopenid.project.utils.JwtUtils JwtUtils;

    /**
     * Metodo che permette di creare un JWT relativo ad uno user.
     * I Parametri sono appunto relativi ad un utente
     *
     * @param subject
     * @param date
     * @return
     */
    @Override
    public String createJwt(String subject, String name, String surname, String id, Date date) throws UnsupportedEncodingException {

        /** Inizializzo una Date, prendendola dalla date passata in input e sommo a questa data il
         * periodo per cui il JWT sarà valido (più o meno il periodo in millisecondi di una sessione */

        Date nowDate = date;
        nowDate.setTime(nowDate.getTime() + (300*1000));

        /** Creo il JWT (che è sottoforma di una stringa JSON), passando i parametri relativi
         * allo user e lo restituisco*/
        String token = JwtUtils.generateJwt(subject, date/**, name, surname*/);

        return token;

    }

    /**
     * Metodo che permette di verificare la validità di un jwt.
     *
     * @param request
     * @return
     */
    @Override
    public String verifyRoleUserJwt(HttpServletRequest request) throws TokenErrorException, UnsupportedEncodingException, SubjectFieldNotFoundException {

        /** Recuperiamo il jwt dalla request.
         * Se il jwt non c'è nella request, oppure è scaduto e non più valido, lancia un'eccezione;
         * altrimenti recupera i dati dell'utente contenuti nel token e li restituisce in una Map*/
        String jwt = JwtUtils.getJwtFromHttpRequest(request);

        if(jwt == null){
            throw new TokenErrorException("Token not found in the request.");
        }

        /** Nel metodo jwt2Map viene controllata l'expiration date  e il subject del jwt.
         * Se è scaduta lancia un'eccezione */
        String userEmail = JwtUtils.jwt2MapRoleInfoJwt(jwt);

        return userEmail;
    }

    @Override
    public String createRoleUserJwt(String subject, Date date) throws UnsupportedEncodingException {

        /** Inizializzo una Date, prendendola dalla date passata in input e sommo a questa data il
         * periodo per cui il JWT sarà valido (più o meno il periodo in millisecondi di una sessione */

        Date nowDate = date;
        nowDate.setTime(nowDate.getTime() + (300*1000));

        /** Creo il JWT (che è sottoforma di una stringa JSON), passando i parametri relativi
         * allo user e lo restituisco*/
        String token = JwtUtils.generateJwt(subject, date);

        return token;

    }


}

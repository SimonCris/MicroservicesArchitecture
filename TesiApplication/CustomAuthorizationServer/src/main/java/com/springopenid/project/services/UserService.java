package com.springopenid.project.services;

import com.springopenid.project.dao.UserDao;
import com.springopenid.project.entities.User;
import com.springopenid.project.exceptions.TokenErrorException;
import com.springopenid.project.utils.Decryption;
import com.springopenid.project.utils.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "UserService")
@Transactional
public class UserService implements IUserService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserDao userDao;

    @Autowired
    IJwtHandleService JwtHandleService;

    @Autowired
    Encryption Encryption;

    @Autowired
    Decryption Decryption;

    /**Metodo che restituisce un user dal DB. Dall'user recuperato dal DB decripto i campi protetti e
     * creo un nuovo user con questi valori. Creo un nuovo utente perche andando a fare set sull'utente
     * recuperato dal db, va a modificare i campi che invece non devono mutare
     *
     * @param jwtParametersMap
     * @return
     */
    @Override
    public User getUserFromUserEmail(Map<String, Object> jwtParametersMap) throws Exception {

        if (jwtParametersMap.containsKey("user_name") &&
                jwtParametersMap.get("user_name") != null) {

            User userFromDB = userDao.findByUserEmail(String.valueOf(jwtParametersMap.get("user_name")));

            String decryptedName = Decryption.decrypt(userFromDB.getUser_name());
            String decryptedSurname = Decryption.decrypt(userFromDB.getUser_surname());
            String decryptedAddress = Decryption.decrypt(userFromDB.getUser_address());
            String decryptedDomicileCity = Decryption.decrypt(userFromDB.getUser_domicile_city());

            /** Creo un nuovo utente per evitare che vengano cambiati i valori dei campi protetti */
            User newUser = new User(userFromDB.getUser_email(), decryptedName, decryptedSurname,
                    userFromDB.getUser_birthday_date(), decryptedAddress, decryptedDomicileCity,
                    userFromDB.getUser_consent());

            if (userFromDB != null) {
                logger.debug("User from email recovered: " + newUser.toString());
                return newUser;
            }

        }

        return null;

    }

    @Override
    public Boolean setUserConsent(Map<String, Object> requestedParametersMap) throws TokenErrorException {

        /** Verifica che siano presenti i parametri */
        if(requestedParametersMap.containsKey("Update user consent") &&
                requestedParametersMap.containsKey("user_name") &&
                requestedParametersMap.get("user_name") != null){

            User userToUpdate = null;

            /** Imposto il flag del consenso dell'utente a true o false in relazione
             * al valore passato nelal richiesta */
            if(requestedParametersMap.get("Update user consent").equals("true")){

                userToUpdate = userDao.getOne((String) requestedParametersMap.get("user_name"));

                logger.debug("userToUpdate " + userToUpdate.toString());

                if(userToUpdate != null) {
                    userToUpdate.setUser_consent(true);
                    userDao.save(userToUpdate);
                }

                return true;

            }
            else if(requestedParametersMap.get("Update user consent").equals("false"))

                userToUpdate = userDao.getOne((String) requestedParametersMap.get("user_name"));

            if(userToUpdate != null) {
                userToUpdate.setUser_consent(false);
                userDao.save(userToUpdate);
            }

            return true;
        }

        else{
            throw new TokenErrorException("Some request parameter is null");
        }

    }

}

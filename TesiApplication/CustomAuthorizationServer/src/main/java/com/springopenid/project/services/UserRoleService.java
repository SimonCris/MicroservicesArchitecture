package com.springopenid.project.services;

import com.springopenid.project.dao.UserRoleDao;
import com.springopenid.project.entities.UserRole;
import com.springopenid.project.exceptions.SubjectFieldNotFoundException;
import com.springopenid.project.exceptions.TokenErrorException;
import com.springopenid.project.utils.Decryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserRoleService implements IUserRoleService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRoleDao UserRoleDao;

    @Autowired
    IJwtHandleService JwtHandleService;

    @Autowired
    Decryption Decryption;

    /**Metodo che restituisce un'entity user_role relativa all'utente autenticato nell'api gateway.
     * Viene estratto e verificato il jwt dalla request e da questo la userEmail dell'utente
     * che viene ricercato nel db cosi come il role ad esso relativo
     *
     * @param request
     * @return
     */
    @Override
    public String getUserRoleFromEmailUser(HttpServletRequest request) throws Exception {

        String userEmail = JwtHandleService.verifyRoleUserJwt(request);

        Optional<UserRole> userRole = UserRoleDao.findByUserEmail(userEmail);

        if(userRole.isPresent()) {

            String decryptedRole = Decryption.decrypt(userRole.get().getRole_name());

            UserRole newUserRole = new UserRole(userRole.get().getEmail_user(), decryptedRole);

            logger.debug("UserRole for " + newUserRole.getEmail_user() + " found: " + newUserRole.getRole_name());
            return newUserRole.getRole_name();
        }

        logger.debug("UserRole for " + userEmail + " not found: ");
        return "not found";

    }

    /**Metodo che restituisce un jwt contenente il role dell'user
     *
     * @param role
     * @return
     */
    @Override
    public String getUserRoleJwt(String role) throws UnsupportedEncodingException {

        String jwt = JwtHandleService.createRoleUserJwt(role, new Date());

        return jwt;

    }

}

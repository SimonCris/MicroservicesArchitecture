package com.springopenid.project.dao;

import com.springopenid.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**Interfaccia che estende la JPARepository di Spring

In Spring non è necessario creare l'implementazione di questa interfaccia in quanto
Spring in automatico ha gia implementato i metodi base come save, findAll, delete, ecc (guardare docs).
Se vogliamo creare dei metodi con dei nomi specifici usiamo le "named query", metodi che hanno come nome
il comando più il campo da ricercare ad esempio "findByUsername" oppure findByPermission" dove
Username e Permission sono due campi dell'entità User
 */
public interface UserDao extends JpaRepository<User, String> {

    @Override
    <S extends User> S save(S entity);

    @Override
    void delete(User user);

    /**Metodi custom */
    @Query(value = "SELECT * FROM userdb.user WHERE user_email=:mailUser", nativeQuery = true)
    List<User> findAllByUserEmail(@Param("mailUser") String userEmail);

    /**Metodi custom */
    @Query(value = "SELECT * FROM userdb.user WHERE user_email=:mailUser", nativeQuery = true)
    User findByUserEmail(@Param("mailUser") String userEmail);

}

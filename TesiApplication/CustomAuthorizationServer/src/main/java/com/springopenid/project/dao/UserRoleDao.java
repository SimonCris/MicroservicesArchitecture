package com.springopenid.project.dao;

import com.springopenid.project.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**Interfaccia che estende la JPARepository di Spring

 In Spring JPA Repository non è necessario creare l'implementazione di questa interfaccia in quanto
 Spring in automatico ha gia implementato i metodi base come save, findAll, delete, ecc (guardare docs).
 */

public interface UserRoleDao extends JpaRepository<UserRole, Integer> {

    @Override
    Optional<UserRole> findById(Integer userRoleId);

    @Query(value = "SELECT * FROM userdb.user_role WHERE email_user=:userEmail", nativeQuery = true)
    List<UserRole> findAllByUserEmail(@Param("userEmail") String userEmail);

    @Query(value = "SELECT * FROM userdb.user_role WHERE email_user=:userEmail", nativeQuery = true)
    Optional<UserRole> findByUserEmail(@Param("userEmail") String userEmail);

    @Override
    void delete(UserRole userRole);
}

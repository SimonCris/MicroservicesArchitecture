package com.springopenid.project.entities;

/**
 *  1) Settiamo le annotazioni per JPA
 2) Uso la validazione di java JSR-303 -> @NotEmpty, @NotNull. @NotBlank

 Se voglio usare nomi diversi per la tabella e i campi uso @Tabel e @Column
 altrimenti i nomi vengono presi dal nome della classe e dai nomi dei campi
 */

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**Entity che mappa la Table nel DB */

@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_role_id;

    @NotEmpty @NotNull @NotBlank
    private String email_user;

    @NotEmpty @NotNull @NotBlank
    private String role_name;

    public UserRole(String email_user, String role_name) {
        this.email_user = email_user;
        this.role_name = role_name;
    }

    public UserRole() {
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user_role_id=" + user_role_id +
                ", email_user='" + email_user + '\'' +
                ", role_name='" + role_name + '\'' +
                '}';
    }

    public Integer getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(Integer user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}

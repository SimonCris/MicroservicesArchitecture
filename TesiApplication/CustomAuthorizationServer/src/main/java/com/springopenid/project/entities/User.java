package com.springopenid.project.entities;

/**
 *  1) Settiamo le annotazioni per JPA
    2) Uso la validazione di java JSR-303 -> @NotEmpty, @NotNull. @NotBlank

    Se voglio usare nomi diversi per la tabella e i campi uso @Tabel e @Column
    altrimenti i nomi vengono presi dal nome della classe e dai nomi dei campi
 */

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**Entity che mappa la Table nel DB */
@Entity
@Table(name="user")
public class User {

    @Id
    @NotEmpty @NotBlank @NotNull
    @Column(unique = true)
    private String user_email;

  /**  @Column(name = "user_name")
    @ColumnTransformer(
            read = "AES_DECRYPT(user_name, '00')",
            write = "AES_ENCRYPT(?, '00')") */
    private String user_name;

    /**@Column(name = "user_surname")
    @ColumnTransformer(
            read = "AES_DECRYPT(user_surname, '00')",
            write = "AES_ENCRYPT(?, '00')")*/
    private String user_surname;

    private Date user_birthday_date;

    private String user_address;

    private String user_domicile_city;

    private Boolean user_consent;

    public User(@NotEmpty @NotBlank @NotNull String user_email, String user_name, String user_surname,
                Date user_birthday_date, String user_address, String user_domicile_city,
                Boolean user_consent) {
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_birthday_date = user_birthday_date;
        this.user_address = user_address;
        this.user_domicile_city = user_domicile_city;
        this.user_consent = user_consent;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "user_email='" + user_email + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_surname='" + user_surname + '\'' +
                ", user_birthday_date=" + user_birthday_date +
                ", user_address='" + user_address + '\'' +
                ", user_domicile_city='" + user_domicile_city + '\'' +
                ", user_consent='" + user_consent + '\'' +
                '}';
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public Date getUser_birthday_date() {
        return user_birthday_date;
    }

    public void setUser_birthday_date(Date user_birthday_date) {
        this.user_birthday_date = user_birthday_date;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_domicile_city() {
        return user_domicile_city;
    }

    public void setUser_domicile_city(String user_domicile_city) {
        this.user_domicile_city = user_domicile_city;
    }

    public Boolean getUser_consent() {
        return user_consent;
    }

    public void setUser_consent(boolean user_consent) {
        this.user_consent = user_consent;
    }
}

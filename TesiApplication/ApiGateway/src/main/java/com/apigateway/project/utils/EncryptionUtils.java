package com.apigateway.project.utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**Classe bean che utilizza il bean textEncryptor per cifrare e decifrare una password
//Implementiamo i metodi encrypt e decrypt richiamando i rispettivi metodi di BasicTextEncryptor
//presente all'interno del bean textEncryptor */
@Component
public class EncryptionUtils {

    @Autowired
    BasicTextEncryptor textEncryptor;

    public String encrypt(String data){

        return textEncryptor.encrypt(data);

    }

    public String decrypt(String encriptedData){

        return textEncryptor.decrypt(encriptedData);

    }

    @Bean
    public BasicTextEncryptor textEncryptor(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("mySecretEncriptionKeyBlaBla1234");
        return textEncryptor;
    }

}

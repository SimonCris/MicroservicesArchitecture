package com.productmicroservice.project.utils;

/**
 * Classe che è composta da un codice di risposta da parte del server e
 * da un Object response. Verrà usata per gestire l'invio e il recupero dei Json Web Token.
 * la JACKSON LIBRARY automaticamente convertirà questo oggetto JsonResponseBody in una response JSON
 */
public class JsonResponseBody {

    private Integer server;
    private Object response;

    public JsonResponseBody(Integer server, Object response) {
        this.server = server;
        this.response = response;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}

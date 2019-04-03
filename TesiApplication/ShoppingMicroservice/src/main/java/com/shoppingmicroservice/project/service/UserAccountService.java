package com.shoppingmicroservice.project.service;

import com.productmicroservice.project.exceptions.TokenErrorException;
import com.shoppingmicroservice.project.Utils.JsonResponseBody;
import com.springopenid.project.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.*;

@Service
public class UserAccountService implements IUserAccountService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    @Value("${allFavoriteProducts}")
    private String allFavoriteProducts;

    @Value("${addProductToFavorite}")
    private String addProductToFavorite;

    @Value("${removeProductFromFavorite}")
    private String removeProductFromFavorite;

    @Value("${getCartProducts}")
    private String getCartProducts;

    @Value("${addProductToCart}")
    private String addProductToCart;

    @Value("${setProductToOrder}")
    private String setProductToOrder;

    @Value("${removeProductToOrder}")
    private String removeProductToOrder;

    @Value("${getOrderProducts}")
    private String getOrderProducts;

    @Value("${removeProductFromCart}")
    private String removeProductFromCart;

    @Value("${getUser}")
    private String getUser;

    @Value("${revokeUserConsent}")
    private String revokeUserConsent;

    @Value("${completeOrder}")
    private String completeOrder;

    @Value("${removeAllOrderProducts}")
    private String removeAllOrderProducts;

    /**Metodo che recupera tutti i prodotti preferiti presenti nel DB ei prodotti
     *
     * @return
     */
    @Override
    public List<LinkedHashMap> getAllFavoriteProducts() {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("productsType", "Favorite Products");

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e restituisce ResponseEntity
         * di favoriteProducts*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(allFavoriteProducts,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        String resp = (String) responseEntity.getBody().getResponse();

        List<LinkedHashMap> favoriteProducts = new ArrayList<>();

        /** Se la lista dei preferiti non è vuota la restituisce */
        if(resp.equals("Products Found!")) {

            List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

            /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
             * vengono inseriti in una lista di LinkedHashMap*/
            Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

            responseParameters.forEach((productName, product) -> {

                favoriteProducts.add((LinkedHashMap) product);

            });

            logger.debug("All favorite products list recovered!");
            return favoriteProducts;

        }
        /** Altrimenti restituisce una lista vuota */
        else{

            logger.debug("Favorite products list is empty!");
            return favoriteProducts;

        }

    }

    @Override
    public Boolean addProductToFavorite(String prodName, String prodBrand, String prodImage, String prodPrice) {

        String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Name", prodName);
        parametersToSend.put("Product Brand", prodBrand);
        parametersToSend.put("Product Image", prodImage);
        parametersToSend.put("Product Price", prodPrice);
        parametersToSend.put("Email User", emailUser);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e aggiungere il prodotto
         * tra i preferiti*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(addProductToFavorite,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("Product Saved!!")) {
            logger.debug("Product Saved into favorites!");
            return true;
        }

        logger.debug("Product not Saved into favorites!");
        return false;

    }

    @Override
    public Boolean removeProductFromFavorites(String productName, String userEmail) {

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Name", productName);
        parametersToSend.put("User Email", userEmail);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e rimuovere il prodotto
         * dai preferiti*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(removeProductFromFavorite,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("Product Removed!!")) {
            logger.debug("Product removed from favorites!");
            return true;
        }

        logger.debug("Product not removed from favorites!");
        return false;


    }


    @Override
    public User getUserFromUserEmail() {

        Map<String, String> requestParameters = new HashMap<>();

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare CustomAuthorizationServer e restituisce
         * se è presente nel DB l'utente*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(getUser,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

        Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

        String dateFromResponse = (String) responseParameters.get("user_birthday_date");
        java.sql.Date date = Date.valueOf(dateFromResponse);

        Boolean consent = (Boolean) responseParameters.get("user_consent");
        User user = null;

        if(consent.equals(false)) {

            user = new User((String) responseParameters.get("user_email"), (String) responseParameters.get("user_name"),
                    (String) responseParameters.get("user_surname"), date,
                    (String) responseParameters.get("user_address"), (String) responseParameters.get("user_domicile_city"),
                    false);

        }
        else if(consent.equals(true)){

            user = new User((String) responseParameters.get("user_email"), (String) responseParameters.get("user_name"),
                    (String) responseParameters.get("user_surname"), date,
                    (String) responseParameters.get("user_address"), (String) responseParameters.get("user_domicile_city"),
                    true);

        }

        logger.debug("User from emailUser recovered");
        return user;

    }

    @Override
    public List<LinkedHashMap> getAllCartProducts() {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("productsType", "Cart Products");

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e restituisce ResponseEntity
         * di prodotto presenti nel cart*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(getCartProducts,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String resp = (String) responseEntity.getBody().getResponse();

        List<LinkedHashMap> cartProducts = new ArrayList<>();

        /** Se la lista dei prodotti nel cart non è vuota la restituisce */
        if(resp.equals("Products Found!")) {

            List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

            /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
             * vengono inseriti in una lista di LinkedHashMap*/
            Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

            responseParameters.forEach((productName, product) -> cartProducts.add((LinkedHashMap) product));

            logger.debug("Cart products list recovered!");
            return cartProducts;

        }
        /** Altrimenti restituisce una lista vuota */
        else{

            logger.debug("Cart products list is empty!");
            return cartProducts;

        }

    }

    @Override
    public Boolean addProductToCart(String prodName, String prodBrand, String prodImage, String prodPrice) {

        String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Name", prodName);
        parametersToSend.put("Product Brand", prodBrand);
        parametersToSend.put("Product Image", prodImage);
        parametersToSend.put("Product Price", prodPrice);
        parametersToSend.put("Email User", emailUser);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e aggiungere il prodotto
         * nel cart*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(addProductToCart,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("Product Saved!!")) {
            logger.debug("Product Saved into cart!");
            return true;
        }

        logger.debug("Product not Saved into cart!");
        return false;

    }

    @Override
    public Boolean removeProductFromCart(String productName, String userEmail) {

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Name", productName);
        parametersToSend.put("User Email", userEmail);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e rimuovere il prodotto
         * dal cart*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(removeProductFromCart,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("Product Removed!!")) {
            logger.debug("Product removed from cart!");
            return true;
        }

        logger.debug("Product not removed from cart!");
        return false;

    }

    @Override
    public String revokeUserConsentFromAll() {

        Map<String, String> parametersToSend = new HashMap<>();

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare il customAuthorizazionServer e rimuovere tutte le info dell'utente
         * da tutte le componenti dell'architettura*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(revokeUserConsent,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("All user info removed")) {
            logger.debug("All user info removed");
            return "All user info removed";
        }
        else if(responseValue.equals("There are already some user info into products")){
            logger.debug("There are already some user info into products");
            return "There are already some user info into products";
        }

        logger.debug("User Info not removed");
        return null;

    }

    @Override
    public Boolean addProductToOrder(String productId) {

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Id", productId);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e aggiungere il prodotto
         * nell'ordine*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(setProductToOrder,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("Product Added To Order")) {
            logger.debug("Product Added To Order!");
            return true;
        }
        else if(responseValue.equals("Product already Added To Order")){

            logger.debug("Product already Added To Order");
            return false;
        }

        return null;

    }

    @Override
    public List<LinkedHashMap> getAllOrderProducts() {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("productsType", "Order Products");

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e restituisce ResponseEntity
         * di prodotto presenti per l'ordine*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(getOrderProducts,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String resp = (String) responseEntity.getBody().getResponse();

        List<LinkedHashMap> orderProducts = new ArrayList<>();

        /** Se la lista dei prodotti nell'ordine non è vuota la restituisce */
        if(resp.equals("Products Found!")) {

            List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

            /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
             * vengono inseriti in una lista di LinkedHashMap*/
            Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

            responseParameters.forEach((productName, product) -> orderProducts.add((LinkedHashMap) product));

            logger.debug("Order products list recovered!");
            return orderProducts;

        }
        /** Altrimenti restituisce una lista vuota */
        else{

            logger.debug("Order products list is empty!");
            return orderProducts;

        }

    }

    @Override
    public Boolean removeProductToOrder(String productId) {

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Id", productId);

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e rimuove il prodotto
         * dall'ordine*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(removeProductToOrder,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals("Product Removed From Order")) {
            logger.debug("Product Removed From Order!");
            return true;
        }
        else if(responseValue.equals("Product already Removed From Order")){

            logger.debug("Product already Removed From Order");
            return false;
        }

        return null;

    }

    @Override
    public Boolean removeAllOrderProduct() {

        Map<String, String> parametersToSend = new HashMap<>();
        parametersToSend.put("Product Type", "removeOrderProducts");

        String jwtToSend = Oauth2JwtService.getJwtAccessToken(parametersToSend);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwtToSend);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e rimuove i prodotti
         * dall'ordine*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(removeAllOrderProducts,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String responseValue = (String) responseEntity.getBody().getResponse();

        if(responseValue.equals(" All Products Removed From Order")) {

            logger.debug("All Products Removed From Order!");
            return true;

        }
        else if(responseValue.equals("Products already Removed From Order")){

            logger.debug("Products already Removed From Order");
            return false;

        }

        return null;

    }

    @Override
    public Boolean completeOrder() {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("completeOrder", "Complete Order");

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare cartMicroservice e restituisce ResponseEntity
         * con la risposta positiva o negativa relativa all'ordine completato o meno */
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(completeOrder,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt*/
        String resp = (String) responseEntity.getBody().getResponse();

        if(resp.equals("Order complete")) {

            logger.debug("Order Complete!");
            return true;

        }
        else{

            logger.debug("Order not complete");
            return false;

        }

    }
}

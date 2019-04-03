package com.cartmicroservice.project.service;

import com.cartmicroservice.project.dao.CartProductDao;
import com.cartmicroservice.project.entities.CartProduct;
import com.cartmicroservice.project.entities.User;
import com.cartmicroservice.project.exceptions.TokenErrorException;
import com.cartmicroservice.project.utils.JsonResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class CartProductService implements ICartProductService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CartProductDao CartProductDao;

    @Autowired
    MailClientService MailClientService;

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    @Value("${getUser}")
    private String getUser;

    @Override
    public List<CartProduct> getAllCartProducts(Map<String, Object> jwtParameters) throws TokenErrorException {

        List<CartProduct> cartProducts = new ArrayList<>();

        if(jwtParameters.containsKey("productsType") &&
                jwtParameters.get("productsType").equals("Cart Products") &&
                jwtParameters.containsKey("user_name") &&
                jwtParameters.get("user_name") != null){

            cartProducts = CartProductDao.findAllByUserEmail((String) jwtParameters.get("user_name"));

            logger.debug("All cart products list recovered!");
            return cartProducts;

        }
        else if(!jwtParameters.containsKey("productsType")){
            throw new TokenErrorException("The type of products is not present or wrong in the request jwt");
        }
        else
            return cartProducts;

    }

    @Override
    public Boolean saveNewCartProduct(Map<String, Object> requestParameters) throws TokenErrorException {

        /** Verifica che siano presenti i parametri del prodotto da memorizzare */
        if(requestParameters.containsKey("Product Name") &&
                requestParameters.containsKey("Product Brand") &&
                requestParameters.containsKey("Product Image") &&
                requestParameters.containsKey("Product Price") &&
                requestParameters.containsKey("Email User")){

            /** Se il prodotto non è gia presente nel DB lo aggiunge, altrimenti ritorna false */
            CartProduct cartProductFromDb = this.getCartProductByNameAndUserEmail(
                    (String) requestParameters.get("Product Name"), (String) requestParameters.get("Email User"));

            if(cartProductFromDb != null)
                return false;

            CartProduct cartProductToAdd = new CartProduct((String) requestParameters.get("Email User"),
                    (String) requestParameters.get("Product Name"), (String) requestParameters.get("Product Brand"),
                    (String) requestParameters.get("Product Price"), (String) requestParameters.get("Product Image"),
                    false);

            CartProduct productSaved = CartProductDao.save(cartProductToAdd);

            if(productSaved != null) {
                logger.debug("Product saved into cart!");
                return true;
            }
            else
                logger.debug("Product not saved into cart!");
                return false;

        }
        else{
            throw new TokenErrorException("Some request parameter is null");
        }

    }

    @Override
    public CartProduct getCartProductByNameAndUserEmail(String productName, String userEmail) {

        Optional<CartProduct> prod = CartProductDao.findByProductNameAndUserEmail(productName, userEmail);

        if(prod.isPresent())
            return prod.get();
        else
            return null;

    }

    @Override
    public Boolean removeProductFromCart(Map<String, Object> requestParameters) throws TokenErrorException {

    /** Verifica che siano presenti i parametri del prodotto da rimuovere */
        if(requestParameters.containsKey("Product Name") &&
                requestParameters.containsKey("User Email") &&
                requestParameters.get("Product Name") != null &&
                requestParameters.get("User Email") != null){

        /** Se il prodotto è presente fra i preferiti lo rimuove */
        CartProduct cartProductFromDb = this.getCartProductByNameAndUserEmail(
                (String) requestParameters.get("Product Name"), (String) requestParameters.get("User Email"));

        if(cartProductFromDb != null){

            CartProductDao.delete(cartProductFromDb);

            logger.debug("Product removed from cart!");
            return true;

        }
        else{
            logger.debug("Product not removed from cart!");
            return false;
        }

    }
        else{
        throw new TokenErrorException("Some request parameter is null");
    }

    }

    @Override
    public String removeUserInfo(String userEmail) throws TokenErrorException {

        if(userEmail != null){

            List<CartProduct> cartProducts;
            cartProducts = CartProductDao.findAllByUserEmail(userEmail);

            /**Vengono eliminato dal carrello solamente i prodotti che non sono inseriti in
             * un ordine relativo all'utente specifico */
            cartProducts.forEach((product) -> {

                if(product.getCart_product_to_order().equals(false))
                    CartProductDao.delete(product);

            });

            if(cartProducts.isEmpty()) {
                logger.debug("All User Info deleted");
                return "All User Info deleted";
            }
            else{
                logger.debug("There are already some user info into products");
                return "There are already some user info into products";
            }

        }
        else{
            logger.debug("User email field is null");
            throw new TokenErrorException("User email field is null");
        }

    }

    @Override
    public Boolean setProductToOrder(Map<String, Object> jwtParametersMap) throws TokenErrorException {

        /** Verifica che siano presenti i parametri */
        if(jwtParametersMap.containsKey("user_name") &&
                jwtParametersMap.get("user_name") != null &&
                jwtParametersMap.containsKey("Product Id") &&
                jwtParametersMap.get("Product Id") != null){

            CartProduct cartProductToUpdate = CartProductDao
                    .findByProductIdAndUserEmail(Integer.valueOf((String)jwtParametersMap.get("Product Id")),
                    (String)jwtParametersMap.get("user_name"));

            if(cartProductToUpdate != null && cartProductToUpdate.getCart_product_to_order().equals(false)) {
                cartProductToUpdate.setCart_product_to_order(true);
                CartProductDao.save(cartProductToUpdate);

                logger.debug("Product set true to order");
                return true;

            }
            else if(cartProductToUpdate != null && cartProductToUpdate.getCart_product_to_order().equals(true)){

                logger.debug("Product not set true to order");
                return false;

            }

        }

        else{
            throw new TokenErrorException("Some request parameter is null");
        }

        return null;

    }

    @Override
    public List<CartProduct> getAllOrderProducts(Map<String, Object> jwtParameters) throws TokenErrorException {

        List<CartProduct> orderProducts = new ArrayList<>();

        if(jwtParameters.containsKey("productsType") &&
                jwtParameters.get("productsType").equals("Order Products") &&
                jwtParameters.containsKey("user_name") &&
                jwtParameters.get("user_name") != null){

            orderProducts = CartProductDao.findAllOrderProduct((String) jwtParameters.get("user_name"));

            logger.debug("All order products list recovered!");
            return orderProducts;

        }
        else if(!jwtParameters.containsKey("productsType")){
            throw new TokenErrorException("The type of products is not present or wrong in the request jwt");
        }
        else
            return orderProducts;

    }

    @Override
    public Boolean removeProductToOrder(Map<String, Object> jwtParametersMap) throws TokenErrorException {

        /** Verifica che siano presenti i parametri */
        if(jwtParametersMap.containsKey("user_name") &&
                jwtParametersMap.get("user_name") != null &&
                jwtParametersMap.containsKey("Product Id") &&
                jwtParametersMap.get("Product Id") != null){

            CartProduct cartProductToUpdate = CartProductDao
                    .findByProductIdAndUserEmail(Integer.valueOf((String)jwtParametersMap.get("Product Id")),
                            (String)jwtParametersMap.get("user_name"));

            if(cartProductToUpdate != null && cartProductToUpdate.getCart_product_to_order().equals(true)) {
                cartProductToUpdate.setCart_product_to_order(false);
                CartProductDao.save(cartProductToUpdate);

                logger.debug("Product set false to order");
                return true;

            }
            else if(cartProductToUpdate != null && cartProductToUpdate.getCart_product_to_order().equals(false)){

                logger.debug("Product not set false to order");
                return false;

            }

        }

        else{
            throw new TokenErrorException("Some request parameter is null");
        }

        return null;

    }

    @Override
    public Boolean finishOrder(Map<String, Object> jwtParametersMap) throws TokenErrorException {

        /** Verifica che siano presenti i parametri */
        if(jwtParametersMap.containsKey("user_name") &&
                jwtParametersMap.containsKey("completeOrder")){

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("productsType", "Order Products");
            parameters.put("user_name", jwtParametersMap.get("user_name"));

            List<CartProduct> orderProducts = this.getAllOrderProducts(parameters);

            if(orderProducts != null) {

                AtomicReference<Integer> totalPrice = new AtomicReference<>(0);

                orderProducts.forEach(product -> {

                    String[] parts = product.getCart_product_price().split(" ");

                    totalPrice.updateAndGet(v -> v + Integer.valueOf(parts[0]));

                });

                User user = this.getUserFromUserEmail((String) jwtParametersMap.get("user_name"));

                java.util.Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
                String today = formatter.format(date);

                String recipient = user.getUser_email();
                MailClientService.prepareAndSend(recipient, orderProducts, totalPrice, user, today);

                logger.debug("Order Complete");
                return true;

            }
            else{

                logger.debug("Order products list is empty");
                return false;

            }

        }

        else{
            throw new TokenErrorException("Some request parameter is null");
        }

    }

    @Override
    public User getUserFromUserEmail(String userEmail) {

        Map<String, Object> requestParameters = new HashMap<>();
        requestParameters.put("user_name", userEmail);

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
    public Boolean removeAllOrderProducts(Map<String, Object> jwtParametersMap) throws TokenErrorException {

        /** Verifica che siano presenti i parametri */
        if(jwtParametersMap.containsKey("user_name") &&
                jwtParametersMap.containsKey("Product Type")){

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("productsType", "Order Products");
            parameters.put("user_name", jwtParametersMap.get("user_name"));

            List<CartProduct> orderProducts = this.getAllOrderProducts(parameters);

            if(orderProducts != null) {

                orderProducts.forEach(product -> {

                    CartProductDao.delete(product);

                });

                logger.debug("All Order Products removed");
                return true;

            }
            else{

                logger.debug("All Order Products not removed");
                return false;

            }

        }

        else{
            throw new TokenErrorException("Some request parameter is null");
        }

    }

}

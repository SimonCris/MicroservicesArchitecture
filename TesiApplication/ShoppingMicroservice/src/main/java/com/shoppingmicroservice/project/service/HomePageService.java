package com.shoppingmicroservice.project.service;

import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.entities.LivingRoomProduct;
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
public class HomePageService implements IHomePageService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${allKitchenProducts}")
    private String allKitchenProducts;

    @Value("${allLivingRoomProducts}")
    private String allLivingRoomProducts;

    @Value("${getKitchenProduct}")
    private String getKitchenProduct;

    @Value("${getLivingRoomProduct}")
    private String getLivingRoomProduct;

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    /**Metodo che recupera tutti i prodotti di cucina presenti nel DB ei prodotti
     *
     * @return
     */
    public List<LinkedHashMap> getAllKitchenProducts(){

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("productsType", "Kitchen Products");

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e restituisce ResponseEntity
         * di kitchenProducts*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(allKitchenProducts,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

        /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
         * vengono inseriti in una lista di LinkedHashMap*/
        Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));
        List<LinkedHashMap> kitchenProducts = new ArrayList<>();

        responseParameters.forEach((productName, product) -> {

            kitchenProducts.add((LinkedHashMap) product);

        });

        logger.debug("All kitchen products list recovered!");
        return kitchenProducts;
    }

    /**Metodo che recupera tutti i prodotti da soggiorno presenti nel DB ei prodotti
     *
     * @return
     */
    @Override
    public List<LinkedHashMap> getAllLivingRoomProducts() {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("productsType", "Living Room Products");

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e restituisce ResponseEntity
         * di livingRoomProducts*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(allLivingRoomProducts,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

        /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
         * vengono inseriti in una lista di LinkedHashMap*/
        Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));
        List<LinkedHashMap> livingRoomProducts = new ArrayList<>();

        responseParameters.forEach((productName, product) -> {

            livingRoomProducts.add((LinkedHashMap) product);

        });

        logger.debug("All living room products list recovered!");
        return livingRoomProducts;

    }


    @Override
    public KitchenProduct getKitchenProduct(Integer idProduct) {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("Product Id", String.valueOf(idProduct));

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e restituisce
         * se è presente nel DB il prodotto richiesto*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(getKitchenProduct,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

        /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
         * vengono inseriti in una lista di LinkedHashMap*/

        Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

        KitchenProduct kitchenProduct = new KitchenProduct((String) responseParameters.get("ProductName"),
                (String) responseParameters.get("ProductPrice"), (String) responseParameters.get("ProductBrand"),
                (String) responseParameters.get("ProductImage"), (String) responseParameters.get("ProductDescription"));

        kitchenProduct.setIdkitchen_product((Integer) responseParameters.get("ProductID"));

        logger.debug("Kitchen product recovered");
        return kitchenProduct;
    }

    @Override
    public LivingRoomProduct getLivingRoomProduct(Integer idProduct) {

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("Product Id", String.valueOf(idProduct));

        String jwt = Oauth2JwtService.getJwtAccessToken(requestParameters);

        /** Genero l'header e creo la request */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);

        HttpEntity<?> request = new HttpEntity(String.class, headers);

        /** Utilizzo il RestTemplate per chiamare productsMicroservice e restituisce
         * se è presente nel DB il prodotto richiesto*/
        RestTemplate restTemplate = new RestTemplate();

        /** .exchange vuole come parametri l'url del microservizio che si sta chiamando, eventualmente il metodo
         * post o get usato, la request create e il tipo della risposta*/
        ResponseEntity<JsonResponseBody> responseEntity =
                restTemplate.exchange(getLivingRoomProduct,
                        HttpMethod.POST, request, JsonResponseBody.class);

        /** Estraggo dalla header della risposta il jwt contenente la lista di tutti i prodotti */
        List<String> jwtFromResponse = responseEntity.getHeaders().get("jwt");

        /** Recupero i parametri contenuti nel jwt, ossia la lista di tutti i prodotti richiesti, e
         * vengono inseriti in una lista di LinkedHashMap*/

        Map<String, Object> responseParameters = Oauth2JwtService.getJwtParameter(jwtFromResponse.get(0));

        LivingRoomProduct livingRoomProduct = new LivingRoomProduct((String) responseParameters.get("ProductName"),
                (String) responseParameters.get("ProductPrice"), (String) responseParameters.get("ProductBrand"),
                (String) responseParameters.get("ProductImage"), (String) responseParameters.get("ProductDescription"));

        livingRoomProduct.setIdliving_room_product((Integer) responseParameters.get("ProductID"));

        logger.debug("Living Room product recovered");
        return livingRoomProduct;


    }

}

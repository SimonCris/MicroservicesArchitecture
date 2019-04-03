package com.productmicroservice.project.controller;

import com.productmicroservice.project.entities.FavoriteProduct;
import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.entities.LivingRoomProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;
import com.productmicroservice.project.service.IFavoriteProductService;
import com.productmicroservice.project.service.IKitchenProductService;
import com.productmicroservice.project.service.ILivingRoomProductService;
import com.productmicroservice.project.service.IOauth2JwtService;
import com.productmicroservice.project.utils.JsonResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ControllerHandler {

    IKitchenProductService kitchenProductService;
    ILivingRoomProductService livingRoomProductService;
    IFavoriteProductService favoriteProductService;
    IOauth2JwtService Oauth2JwtService;

    private Logger logger = LoggerFactory.getLogger(ControllerHandler.class);

    @Autowired
    public ControllerHandler(IKitchenProductService KitchenProductService,
                             ILivingRoomProductService LivingRoomProductService,
                             IFavoriteProductService FavoriteProductService,
                             IOauth2JwtService Oauth2JwtService) {

        this.kitchenProductService = KitchenProductService;
        this.livingRoomProductService = LivingRoomProductService;
        this.favoriteProductService = FavoriteProductService;
        this.Oauth2JwtService = Oauth2JwtService;

    }

    @CrossOrigin
    @RequestMapping(value = "/getAllKitchenProducts", method = POST)
    public ResponseEntity<JsonResponseBody> getAllKitchenProducts(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            List<KitchenProduct> kitchenProductsList = kitchenProductService.getAllKitchenProducts(jwtParameters);

            final Map productsMap = new HashMap<>();
            for (final KitchenProduct kitchenProduct : kitchenProductsList){
                productsMap.put(kitchenProduct.getKitchen_product_name(),
                        kitchenProduct); }

            String jwtToSend = Oauth2JwtService.getJwtAccessToken(productsMap);

            if (!kitchenProductsList.isEmpty()) {

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "ok"));

            } else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Kitchen Products list is empty!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }


    }

    @CrossOrigin
    @RequestMapping(value = "/getAllLivingRoomProducts", method = POST)
    public ResponseEntity<JsonResponseBody> getAllLivingRoomProducts(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            List<LivingRoomProduct> livingRoomProductsList =
                    livingRoomProductService.getAllLivingRoomProducts(jwtParameters);

            final Map productsMap = new HashMap<>();
            for (final LivingRoomProduct livingRoomProduct : livingRoomProductsList){
                productsMap.put(livingRoomProduct.getLiving_room_product_name(),
                        livingRoomProduct);
            }

            String jwtToSend = Oauth2JwtService.getJwtAccessToken(productsMap);

            if (!livingRoomProductsList.isEmpty()) {

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "ok"));

            } else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Living Room Products list is empty!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/getAllFavoriteProducts", method = POST)
    public ResponseEntity<JsonResponseBody> getAllFavoriteProducts(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            List<FavoriteProduct> favoriteProductsList =
                    favoriteProductService.getAllFavoriteProducts(jwtParameters);

            final Map productsMap = new HashMap<>();
            for (final FavoriteProduct favoriteProduct : favoriteProductsList){
                productsMap.put(favoriteProduct.getFavorite_product_name(),
                        favoriteProduct);
            }

            String jwtToSend = Oauth2JwtService.getJwtAccessToken(productsMap);

            if (!favoriteProductsList.isEmpty()) {

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Products Found!"));

            } else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Favorite Products list is empty!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }
    }

    @RequestMapping("/getKitchenProduct")
    public ResponseEntity<JsonResponseBody> getKitchenProduct(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            KitchenProduct kitchenProductFromDB =
                    kitchenProductService.getKitchenProduct(jwtParameters);

            if (kitchenProductFromDB != null) {

            final Map product = new HashMap<>();
            product.put("ProductID" , kitchenProductFromDB.getIdkitchen_product());
            product.put("ProductName" , kitchenProductFromDB.getKitchen_product_name());
            product.put("ProductBrand" , kitchenProductFromDB.getKitchen_product_brand());
            product.put("ProductPrice" , kitchenProductFromDB.getKitchen_product_price());
            product.put("ProductImage" , kitchenProductFromDB.getKitchen_product_image());
            product.put("ProductDescription" , kitchenProductFromDB.getKitchen_product_description());

            String jwtToSend = Oauth2JwtService.getJwtAccessToken(product);

            return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "ok"));

            } else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Kitchen product not found into DB!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @RequestMapping("/getLivingRoomProduct")
    public ResponseEntity<JsonResponseBody> getLivingRoomProduct(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            LivingRoomProduct livingRoomProductFromDB =
                    livingRoomProductService.getLivingRoomProduct(jwtParameters);

            if (livingRoomProductFromDB != null) {

                final Map product = new HashMap<>();
                product.put("ProductID" , livingRoomProductFromDB.getIdliving_room_product());
                product.put("ProductName" , livingRoomProductFromDB.getLiving_room_product_name());
                product.put("ProductBrand" , livingRoomProductFromDB.getLiving_room_product_brand());
                product.put("ProductPrice" , livingRoomProductFromDB.getLiving_room_product_price());
                product.put("ProductImage" , livingRoomProductFromDB.getLiving_room_product_image());
                product.put("ProductDescription" , livingRoomProductFromDB.getLiving_room_product_description());

                String jwtToSend = Oauth2JwtService.getJwtAccessToken(product);

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "ok"));

            } else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Living Room product not found into DB!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/saveNewFavoriteProduct", method = POST)
    public ResponseEntity<JsonResponseBody> saveNewFavoriteProduct(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean addProduct = favoriteProductService.saveNewFavoriteProduct(jwtParameters);

            if (addProduct) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Saved!!"));

            } else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "The product is already present among the favorites!"));


            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }


    }

    @CrossOrigin
    @RequestMapping(value = "/removeFavoriteProduct", method = POST)
    public ResponseEntity<JsonResponseBody> removeFavoriteProduct(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);
            Boolean removedProduct = favoriteProductService.removeFavoriteProduct(jwtParameters);

            if (removedProduct) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Removed!!"));

            } else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "The product is already removed from the favorites!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    /**Endpoint che elimina tutte le info dell'utente */
    @RequestMapping(value = "/revokeUserConsent", method = POST)
    public ResponseEntity<JsonResponseBody> revokeUserConsent(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);
            String userEmail = (String) jwtParameters.get("user_email");

            /**Chiamate ai microservizi che possono contenere le info relative all'utente */
            Boolean userInfoRemoved = favoriteProductService.removeUserInfo(userEmail);

             if (userInfoRemoved) {

             return ResponseEntity.status(HttpStatus.OK)
                .body(new JsonResponseBody(HttpStatus.OK.value(), "All user info removed"));

             } else {

             return ResponseEntity.status(HttpStatus.OK)
                .body(new JsonResponseBody(HttpStatus.OK.value(), "User Info not removed"));

             }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

}

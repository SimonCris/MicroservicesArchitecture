package com.cartmicroservice.project.controller;

import com.cartmicroservice.project.entities.CartProduct;
import com.cartmicroservice.project.exceptions.TokenErrorException;
import com.cartmicroservice.project.service.ICartProductService;
import com.cartmicroservice.project.service.IOauth2JwtService;
import com.cartmicroservice.project.utils.JsonResponseBody;
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

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ControllerHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IOauth2JwtService Oauth2JwtService;

    @Autowired
    ICartProductService CartProductService;

    @RequestMapping("/getCartFromUserEmail")
    public ResponseEntity<JsonResponseBody> getCartFromUserEmail(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            List<CartProduct> cartProductsList = CartProductService.getAllCartProducts(jwtParameters);

            final Map productsMap = new HashMap<>();
            for (final CartProduct cartProduct : cartProductsList){
                productsMap.put(cartProduct.getCart_product_name(),
                        cartProduct);
            }

            String jwtToSend = Oauth2JwtService.getJwtAccessToken(productsMap);

            if (!cartProductsList.isEmpty()) {

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Products Found!"));

            } else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Cart Products list is empty!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/addProductToCart", method = POST)
    public ResponseEntity<JsonResponseBody> addProductToCart(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean addProduct = CartProductService.saveNewCartProduct(jwtParameters);

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
    @RequestMapping(value = "/removeProductFromCart", method = POST)
    public ResponseEntity<JsonResponseBody> removeProductFromCart(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);
            Boolean removedProduct = CartProductService.removeProductFromCart(jwtParameters);

            if (removedProduct) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Removed!!"));

            } else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "The product is already removed from the cart!"));

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
            String userInfoRemoved = CartProductService.removeUserInfo(userEmail);

            if (userInfoRemoved.equals("All User Info deleted")) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "All user info removed"));

            } else if (userInfoRemoved.equals("There are already some user info into products")){

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "There are already some user info into products"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

        return null;
    }

    @RequestMapping(value = "/setProductToOrder", method = POST)
    public ResponseEntity<JsonResponseBody> setProductToOrder(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParametersMap = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean productToOrderUpdate = CartProductService.setProductToOrder(jwtParametersMap);

            if (productToOrderUpdate.equals(true)) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Added To Order"));
            }
            else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product already Added To Order"));

            }
        }
        else {
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @RequestMapping(value = "/removeProductToOrder", method = POST)
    public ResponseEntity<JsonResponseBody> removeProductToOrder(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParametersMap = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean productToOrderUpdate = CartProductService.removeProductToOrder(jwtParametersMap);

            if (productToOrderUpdate.equals(true)) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Removed From Order"));
            }
            else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Product already Removed From Order"));

            }
        }
        else {
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @RequestMapping("/getOrderProductList")
    public ResponseEntity<JsonResponseBody> getOrderProductList(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            List<CartProduct> orderProductsList = CartProductService.getAllOrderProducts(jwtParameters);

            final Map productsMap = new HashMap<>();
            for (final CartProduct cartProduct : orderProductsList){
                productsMap.put(cartProduct.getCart_product_name(),
                        cartProduct);
            }

            String jwtToSend = Oauth2JwtService.getJwtAccessToken(productsMap);

            if (!orderProductsList.isEmpty()) {

                return ResponseEntity.status(HttpStatus.OK).header("jwt", jwtToSend)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Products Found!"));

            } else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Cart Products list is empty!"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @RequestMapping(value = "/completeOrder", method = POST)
    public ResponseEntity<JsonResponseBody> completeOrder(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParameters = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean orderCompleted = CartProductService.finishOrder(jwtParameters);

            if (orderCompleted.equals(true)) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Order complete"));

            } else if(orderCompleted.equals(false)){

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Order not complete"));

            }
            else{

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Order error"));

            }
        }
        else{
            throw new TokenErrorException("JWT not found in the request");
        }

    }

    @RequestMapping(value = "/removeAllOrderProducts", method = POST)
    public ResponseEntity<JsonResponseBody> removeAllOrderProducts(HttpServletRequest request) throws TokenErrorException {

        String jwtFromRequest = request.getHeader("jwt");

        if(jwtFromRequest != null) {

            Map<String, Object> jwtParametersMap = Oauth2JwtService.getJwtParameter(jwtFromRequest);

            Boolean allOrderProductsRemoved = CartProductService.removeAllOrderProducts(jwtParametersMap);

            if (allOrderProductsRemoved.equals(true)) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "All Products Removed From Order"));
            }
            else {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponseBody(HttpStatus.OK.value(), "Products already Removed From Order"));

            }
        }
        else {
            throw new TokenErrorException("JWT not found in the request");
        }

    }

}

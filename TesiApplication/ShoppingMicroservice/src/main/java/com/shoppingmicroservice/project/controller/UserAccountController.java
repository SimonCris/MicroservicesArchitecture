package com.shoppingmicroservice.project.controller;

import com.shoppingmicroservice.project.Utils.JsonResponseBody;
import com.shoppingmicroservice.project.service.IHomePageService;
import com.shoppingmicroservice.project.service.IUserAccountService;
import com.springopenid.project.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UserAccountController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    IUserAccountService UserAccountService;

    @Autowired
    public UserAccountController(IUserAccountService UserAccountService) {

        this.UserAccountService = UserAccountService;

    }

    /**Endpoint che reindirizza l'utente alla pagina del proprio account */
    @Secured({"ROLE_USER"})
    @RequestMapping("/goToUserAccount")
    public String goToUserAccount(Model model)  {

        User user = UserAccountService.getUserFromUserEmail();
        model.addAttribute("userInfo", user);

        /** Se l'utente ha dato il consenso al trattamento dei dati personali recupero
         * i prodotti, altrimenti restituisco le liste vuote */

        List<LinkedHashMap> favoriteProducts = new ArrayList<>();
        List<LinkedHashMap> cartProducts = new ArrayList<>();
        List<LinkedHashMap> orderProducts = new ArrayList<>();

        if(user.getUser_consent().equals(true)) {

            favoriteProducts = UserAccountService.getAllFavoriteProducts();
            cartProducts = UserAccountService.getAllCartProducts();
            orderProducts = UserAccountService.getAllOrderProducts();

        }

        model.addAttribute("favoriteProducts", favoriteProducts);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("orderProducts", orderProducts);

        return "userAccount";

    }

    /**Endpoint che effettua il salvataggio fra i preferiti di un prodotto specifico.
     * Oltre ai parametri presi dalla richiesta, aggiungo l'email utente nel HomePageService */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/addToFavorite", method = GET)
    public ResponseEntity<JsonResponseBody> addToFavorite(@RequestParam("prodName") String productName,
                                                          @RequestParam("prodBrand") String productBrand,
                                                          @RequestParam("prodImage") String productImage,
                                                          @RequestParam("prodPrice") String productPrice) throws IOException {

        Boolean addProduct = UserAccountService.addProductToFavorite(productName,
                productBrand, productImage, productPrice);

        if(addProduct)
            return ResponseEntity.status(HttpStatus.OK).header("Response", "ProductSaved!!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Saved!"));
        else
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product not Saved!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product not Saved!"));

    }

    /**Endpoint che effettua la rimozione di un prodotto dai preferiti */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/removeFromFavorite", method = GET)
    public ResponseEntity<JsonResponseBody> removeFromFavorite(@RequestParam("prodName") String productName,
                                                               @RequestParam("userEmail") String userEmail){

        Boolean removedProduct = UserAccountService.removeProductFromFavorites(productName, userEmail);

        if(removedProduct)
            return ResponseEntity.status(HttpStatus.OK).header("Response", "ProductRemoved!!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Removed!"));
        else
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product not Removed!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product not Removed!"));

    }


    /**Endpoint che effettua il salvataggio nel cart di un prodotto specifico.
     * Oltre ai parametri presi dalla richiesta, aggiungo l'email utente nel UserAccountService */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/addToCart", method = GET)
    public ResponseEntity<JsonResponseBody> addToCart(@RequestParam("prodName") String productName,
                                                      @RequestParam("prodBrand") String productBrand,
                                                      @RequestParam("prodImage") String productImage,
                                                      @RequestParam("prodPrice") String productPrice) throws IOException {

        Boolean addProduct = UserAccountService.addProductToCart(productName,
                productBrand, productImage, productPrice);

        if(addProduct)
            return ResponseEntity.status(HttpStatus.OK).header("Response", "ProductSaved!!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Saved!"));
        else
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product not Saved!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product not Saved!"));

    }

    /**Endpoint che effettua la rimozione di un prodotto dal cart */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/removeFromCart", method = GET)
    public ResponseEntity<JsonResponseBody> removeFromCart(@RequestParam("prodName") String productName,
                                                           @RequestParam("userEmail") String userEmail){

        Boolean removedProduct = UserAccountService.removeProductFromCart(productName, userEmail);

        if(removedProduct)
            return ResponseEntity.status(HttpStatus.OK).header("Response", "ProductRemoved!!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product Removed!"));
        else
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product not Removed!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product not Removed!"));

    }

    /**Endpoint che richiede all'apigateway di eliminare ogni informazione dell'utente da
     * tutte le componenti dell'architettura */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/revokeUserConsent", method = POST)
    public ResponseEntity<JsonResponseBody> revokeUserConsent()  {

        String revokedUserConsent = UserAccountService.revokeUserConsentFromAll();

        if(revokedUserConsent.equals("All user info removed"))
            return ResponseEntity.status(HttpStatus.OK).header("Response", "User Info removed!!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "User Info removed!!"));
        else if(revokedUserConsent.equals("There are already some user info into products"))
            return ResponseEntity.status(HttpStatus.OK).header("Response", "There are already some user info into products!")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "There are already some user info into products!"));

        return null;

    }

    /**Endpoint che chiama cart microservice e setta il prodotto come "in ordine" */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/addToOrder", method = POST)
    public ResponseEntity<JsonResponseBody> addToOrder(@RequestParam("product_id") String productId){

        Boolean addProductToOrder = UserAccountService.addProductToOrder(productId);

        if(addProductToOrder)
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product updated")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product updated"));
        else
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product not updated")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product not updated"));

    }

    /**Endpoint che chiama cart microservice e setta il prodotto come "in ordine" */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/removeToOrder", method = POST)
    public ResponseEntity<JsonResponseBody> removeToOrder(@RequestParam("product_id") String productId){

        Boolean addProductToOrder = UserAccountService.removeProductToOrder(productId);

        if(addProductToOrder)
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product updated")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product updated"));
        else
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Product not updated")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Product not updated"));

    }

    /**Endpoint che permette di effettuare l'acquisto dei prodotti selezionati per l'ordine */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/buyProducts", method = POST)
    public ResponseEntity<JsonResponseBody> buyProducts()  {

        Boolean orderCompleted = UserAccountService.completeOrder();

        if(orderCompleted.equals(true)) {

            UserAccountService.removeAllOrderProduct();

            return ResponseEntity.status(HttpStatus.OK).header("Response", "Buy completed")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Buy completed"));
        }
        else if(orderCompleted.equals(false))
            return ResponseEntity.status(HttpStatus.OK).header("Response", "Buy not completed")
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Buy not completed"));

        return null;

    }

}

package com.shoppingmicroservice.project.controller;

import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.entities.LivingRoomProduct;
import com.shoppingmicroservice.project.Utils.JsonResponseBody;
import com.shoppingmicroservice.project.service.IHomePageService;
import com.shoppingmicroservice.project.service.IUserAccountService;
import com.springopenid.project.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    IHomePageService HomePageService;
    IUserAccountService UserAccountService;

    @Value("${apiGatewayLogout}")
    private String apiGatewayLogout;

    @Autowired
    public HomeController(IHomePageService HomePageService, IUserAccountService UserAccountService) {

        this.HomePageService = HomePageService;
        this.UserAccountService = UserAccountService;

    }

    /** Endpoint che richiama la home page */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/home")
    public String home(Model model){

        List<LinkedHashMap> kitchenProductsList = HomePageService.getAllKitchenProducts();
        List<LinkedHashMap> livingRoomProductslist = HomePageService.getAllLivingRoomProducts();

        model.addAttribute("kitchenProductsList", kitchenProductsList);
        model.addAttribute("livingRoomProductsList", livingRoomProductslist);

        User user = UserAccountService.getUserFromUserEmail();
        model.addAttribute("userInfo", user);

        return "home";

    }

    /** Endpoint che richiama la home page */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/productPage/{productId}&{productType}")
    public String productPage(@PathVariable("productId") String productId,
                              @PathVariable("productType") String productType,
                              Model model){

        if(productType.equals("kitchen")) {
            KitchenProduct kitchenProduct = HomePageService.getKitchenProduct(Integer.valueOf(productId));
            model.addAttribute("ProductType", "Kitchen");
            model.addAttribute("KitchenProduct", kitchenProduct);
        }
        else if(productType.equals("livingRoom")) {
            LivingRoomProduct livingRoomProduct = HomePageService.getLivingRoomProduct(Integer.valueOf(productId));
            model.addAttribute("ProductType", "LivingRoom");
            model.addAttribute("LivingRoomProduct", livingRoomProduct);
        }

        User user = UserAccountService.getUserFromUserEmail();
        model.addAttribute("userInfo", user);

        return "product";

    }

    /**Endpoint che reindirizza l'utente alla pagina dei prodotti da cucina */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/goToKitchenProductsPage")
    public String goToKitchenProductsPage(Model model) {

        List<LinkedHashMap> kitchenProductsList = HomePageService.getAllKitchenProducts();
        model.addAttribute("kitchenProductsList", kitchenProductsList);

        User user = UserAccountService.getUserFromUserEmail();
        model.addAttribute("userInfo", user);

        return "kitchenProductsPage";

    }

    /**Endpoint che reindirizza l'utente alla pagina dei prodotti da soggiorno */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/goToLivingRoomProductsPage")
    public String goToLivingRoomProductsPage(Model model) {

        List<LinkedHashMap> livingRoomProductslist = HomePageService.getAllLivingRoomProducts();
        model.addAttribute("livingRoomProductsList", livingRoomProductslist);

        User user = UserAccountService.getUserFromUserEmail();
        model.addAttribute("userInfo", user);

        return "livingRoomProductsPage";

    }

    /**Endpoint che effettua il logout e invalida la Spring Session */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/goToLogout")
    public void logout(HttpServletResponse response) throws IOException {

        /** Link a cui viene reindirizzato il client dopo il logout*/
            response.sendRedirect(apiGatewayLogout);

    }

    /**Endpoint che richiama la pagina per l'eccezione "Access Denied" personalizzata */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/403")
    public String page403(Model model){

        User user = UserAccountService.getUserFromUserEmail();
        model.addAttribute("userInfo", user);

        return "403Page";

    }

}

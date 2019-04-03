package com.productmicroservice.project.service;

import com.productmicroservice.project.entities.FavoriteProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "FavoriteProductService")
@Transactional
public class FavoriteProductService implements IFavoriteProductService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    com.productmicroservice.project.dao.FavoriteProductDao FavoriteProductDao;

    @Override
    public List<FavoriteProduct> getAllFavoriteProducts(Map<String, Object> jwtParameters) throws TokenErrorException {

        List<FavoriteProduct> favoriteProducts = new ArrayList<>();

        if(jwtParameters.containsKey("productsType") &&
                jwtParameters.get("productsType").equals("Favorite Products") &&
                jwtParameters.containsKey("user_name") &&
                jwtParameters.get("user_name") != null){

            favoriteProducts = FavoriteProductDao.findAllByUserEmail((String) jwtParameters.get("user_name"));

            logger.debug("All favorite products list recovered!");
            return favoriteProducts;

        }
        else if(!jwtParameters.containsKey("productsType")){
            throw new TokenErrorException("The type of products is not present or wrong in the request jwt");
        }
        else
            return favoriteProducts;

    }

    @Override
    public Boolean saveNewFavoriteProduct(Map<String, Object> requestParameters) throws TokenErrorException {

        /** Verifica che siano presenti i parametri del prodotto da memorizzare */
        if(requestParameters.containsKey("Product Name") &&
                requestParameters.containsKey("Product Brand") &&
                requestParameters.containsKey("Product Image") &&
                requestParameters.containsKey("Product Price") &&
                requestParameters.containsKey("Email User")){

            /** Se il prodotto non è gia presente nel DB lo aggiunge, altrimenti ritorna false */
            FavoriteProduct favoriteProductFromDb = this.getFavoriteProductByNameAndUserEmail(
                    (String) requestParameters.get("Product Name"), (String) requestParameters.get("Email User"));

            if(favoriteProductFromDb != null)
                return false;

            FavoriteProduct favoriteProductToAdd = new FavoriteProduct((String) requestParameters.get("Product Name"),
                    (String) requestParameters.get("Product Brand"), (String) requestParameters.get("Product Image"),
                    (String) requestParameters.get("Product Price"), (String) requestParameters.get("Email User"));

            FavoriteProduct productSaved = FavoriteProductDao.save(favoriteProductToAdd);

            if(productSaved != null) {
                logger.debug("Product saved into favorites!");
                return true;
            }
            else
                return false;

        }
        else{
            throw new TokenErrorException("Some request parameter is null");
        }

    }

    @Override
    public Boolean removeFavoriteProduct(Map<String, Object> requestParameters) throws TokenErrorException {

        /** Verifica che siano presenti i parametri del prodotto da rimuovere */
        if(requestParameters.containsKey("Product Name") &&
                requestParameters.containsKey("User Email") &&
                requestParameters.get("Product Name") != null &&
                requestParameters.get("User Email") != null){

            /** Se il prodotto è presente fra i preferiti lo rimuove */
            FavoriteProduct favoriteProductFromDb = this.getFavoriteProductByNameAndUserEmail(
                    (String) requestParameters.get("Product Name"), (String) requestParameters.get("User Email"));

            if(favoriteProductFromDb != null){

                FavoriteProductDao.delete(favoriteProductFromDb);

                logger.debug("Product removed from favorites!");
                return true;

            }
            else{
                return false;
            }

        }
        else{
            throw new TokenErrorException("Some request parameter is null");
        }

    }

    @Override
    public FavoriteProduct getFavoriteProductByNameAndUserEmail(String productName, String userEmail) {

        Optional<FavoriteProduct> prod = FavoriteProductDao.findByProductNameAndUserEmail(productName, userEmail);

         if(prod.isPresent())
             return prod.get();
         else
             return null;

    }

    @Override
    public Boolean removeUserInfo(String userEmail) {

        if(userEmail != null){

            List<FavoriteProduct> favoriteProducts;
            favoriteProducts = FavoriteProductDao.findAllByUserEmail(userEmail);

            favoriteProducts.forEach((product) -> FavoriteProductDao.delete(product));

            logger.debug("All user info removed");
            return true;

        }

        logger.debug("All user not info removed");
        return false;

    }
}

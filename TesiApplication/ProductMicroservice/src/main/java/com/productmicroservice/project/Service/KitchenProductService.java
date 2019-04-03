package com.productmicroservice.project.service;

import com.productmicroservice.project.dao.KitchenProductDao;
import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service(value = "KitchenProductService")
@Transactional
public class KitchenProductService implements com.productmicroservice.project.service.IKitchenProductService {

    Logger logger = LoggerFactory.getLogger(KitchenProductService.class);

    @Autowired
    KitchenProductDao KitchenProductDao;

    @Override
    public List<KitchenProduct> getAllKitchenProducts(Map<String, Object> jwtParameters) throws TokenErrorException {

        List<KitchenProduct> kitchenProducts = KitchenProductDao.findAll();
        if(jwtParameters.containsKey("productsType") &&
                jwtParameters.get("productsType").equals("Kitchen Products")){

            logger.debug("All kitchen product list recovered!");
            return kitchenProducts;

        }
        else if(!jwtParameters.containsKey("productsType")){
            throw new TokenErrorException("The type of products is not present or wrong in the request jwt");
        }
        else
            return kitchenProducts;

    }

    @Override
    public KitchenProduct getKitchenProduct(Map<String, Object> jwtParameters) throws TokenErrorException {

        KitchenProduct kitchenProduct = null;

        if(jwtParameters.containsKey("Product Id") &&
                jwtParameters.get("Product Id") != null){

            kitchenProduct = KitchenProductDao
                    .findByKitchenProductId(Integer.valueOf((String) jwtParameters.get("Product Id")));

        }
        else if(!jwtParameters.containsKey("productsType")){
            logger.debug("Kitchen Product is not present into DB!");
            throw new TokenErrorException("The id of product is not present or wrong in the request jwt");
        }

        logger.debug("Kitchen product recovered!");
        return kitchenProduct;

    }
}

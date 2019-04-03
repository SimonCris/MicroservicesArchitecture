package com.productmicroservice.project.service;

import com.productmicroservice.project.dao.LivingRoomProductDao;
import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.entities.LivingRoomProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service(value = "LivingRoomProductService")
@Transactional
public class LivingRoomProductService implements ILivingRoomProductService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LivingRoomProductDao LivingRoomProductDao;

    @Override
    public List<LivingRoomProduct> getAllLivingRoomProducts(Map<String, Object> jwtParameters) throws TokenErrorException {

        List<LivingRoomProduct> livingRoomProducts = LivingRoomProductDao.findAll();
        if(jwtParameters.containsKey("productsType") &&
                jwtParameters.get("productsType").equals("Living Room Products")){

            logger.debug("All living room products list recovered!");
            return livingRoomProducts;

        }
        else if(!jwtParameters.containsKey("productsType")){
            throw new TokenErrorException("The type of products is not present or wrong in the request jwt");
        }
        else
            return livingRoomProducts;

    }

    @Override
    public LivingRoomProduct getLivingRoomProduct(Map<String, Object> jwtParameters) throws TokenErrorException {

        LivingRoomProduct livingRoomProduct = null;

        if(jwtParameters.containsKey("Product Id") &&
                jwtParameters.get("Product Id") != null){

            livingRoomProduct = LivingRoomProductDao
                    .findByLivingRoomProductId(Integer.valueOf((String) jwtParameters.get("Product Id")));

        }
        else if(!jwtParameters.containsKey("productsType")){
            logger.debug("Living Room product is not present into DB!");
            throw new TokenErrorException("The id of product is not present or wrong in the request jwt");
        }

        logger.debug("Living Room product recovered!");
        return livingRoomProduct;

    }

}

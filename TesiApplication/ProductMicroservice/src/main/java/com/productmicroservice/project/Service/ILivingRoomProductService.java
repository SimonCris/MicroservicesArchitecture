package com.productmicroservice.project.service;

import com.productmicroservice.project.entities.LivingRoomProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;

import java.util.List;
import java.util.Map;

public interface ILivingRoomProductService {

    List<LivingRoomProduct> getAllLivingRoomProducts(Map<String, Object> jwtParameters) throws TokenErrorException;

    LivingRoomProduct getLivingRoomProduct(Map<String, Object> jwtParameters) throws TokenErrorException;
}

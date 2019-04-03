package com.productmicroservice.project.service;

import com.productmicroservice.project.entities.KitchenProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;

import java.util.List;
import java.util.Map;

public interface IKitchenProductService {

    List<KitchenProduct> getAllKitchenProducts(Map<String, Object> jwtParameters) throws TokenErrorException;

    KitchenProduct getKitchenProduct(Map<String, Object> jwtParameters) throws TokenErrorException;
}

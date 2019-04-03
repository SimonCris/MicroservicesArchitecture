package com.productmicroservice.project.service;

import com.productmicroservice.project.entities.FavoriteProduct;
import com.productmicroservice.project.exceptions.TokenErrorException;

import java.util.List;
import java.util.Map;

public interface IFavoriteProductService {

    List<FavoriteProduct> getAllFavoriteProducts(Map<String, Object> jwtParameters) throws TokenErrorException;

    Boolean saveNewFavoriteProduct(Map<String, Object> requestParameters) throws TokenErrorException;

    Boolean removeFavoriteProduct(Map<String, Object> requestParameters) throws TokenErrorException;

    FavoriteProduct getFavoriteProductByNameAndUserEmail(String productName, String userEmail);

    Boolean removeUserInfo(String userEmail);
}

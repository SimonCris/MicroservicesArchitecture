package com.cartmicroservice.project.dao;

import com.cartmicroservice.project.entities.CartProduct;
import com.cartmicroservice.project.service.CartProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartProductDao extends JpaRepository<CartProduct, Integer> {

    @Override
    List<CartProduct> findAll();

    @Override
    <S extends CartProduct> S save(S s);

    @Override
    void delete(CartProduct favoriteProduct);

    @Query(value = "SELECT * FROM shoppingcart.cart_products " +
            "WHERE shoppingcart.cart_products.cart_product_user_email=:email", nativeQuery = true)
    List<CartProduct> findAllByUserEmail(@Param("email") String userEmail);

    @Query(value = "SELECT * FROM shoppingcart.cart_products " +
                   "WHERE shoppingcart.cart_products.cart_product_name=:prodName and " +
            "shoppingcart.cart_products.cart_product_user_email=:email", nativeQuery = true)
    Optional<CartProduct> findByProductNameAndUserEmail(@Param("prodName") String productName,
                                                            @Param("email") String userEmail);

    @Query(value = "SELECT * FROM shoppingcart.cart_products " +
            "WHERE shoppingcart.cart_products.idcart_product=:productId and " +
            "shoppingcart.cart_products.cart_product_user_email=:email", nativeQuery = true)
    CartProduct findByProductIdAndUserEmail(@Param("productId") Integer productID,
                                            @Param("email") String userEmail);

    @Query(value = "SELECT * FROM shoppingcart.cart_products " +
            "WHERE shoppingcart.cart_products.cart_product_to_order=1 and " +
            "shoppingcart.cart_products.cart_product_user_email=:email", nativeQuery = true)
    List<CartProduct> findAllOrderProduct(@Param("email")String user_name);
}

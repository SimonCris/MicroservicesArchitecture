package com.productmicroservice.project.dao;

import com.productmicroservice.project.entities.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteProductDao extends JpaRepository<FavoriteProduct, Integer> {

    @Override
    List<FavoriteProduct> findAll();

    @Override
    <S extends FavoriteProduct> S save(S s);

    @Override
    void delete(FavoriteProduct favoriteProduct);

    @Query(value = "DELETE FROM productsdb.favorite_products " +
            "WHERE productsdb.favorite_products.email_user=:email and " +
            "productsdb.favorite_products.favorite_product_name=:prodName", nativeQuery = true)
    void deleteByUserEmailAndProdName(@Param("email") String userEmail,
                                      @Param("prodName") String prodName);

    @Query(value = "DELETE FROM productsdb.favorite_products " +
            "WHERE productsdb.favorite_products.email_user=:email", nativeQuery = true)
    void deleteByUserEmail(@Param("email") String userEmail);

    @Query(value = "SELECT * FROM productsdb.favorite_products " +
            "WHERE productsdb.favorite_products.email_user=:email", nativeQuery = true)
    List<FavoriteProduct> findAllByUserEmail(@Param("email") String userEmail);

    @Query(value = "SELECT * FROM productsdb.favorite_products " +
                   "WHERE productsdb.favorite_products.favorite_product_name=:prodName and " +
            "productsdb.favorite_products.email_user=:email", nativeQuery = true)
    Optional<FavoriteProduct> findByProductNameAndUserEmail(@Param("prodName") String productName,
                                                            @Param("email") String userEmail);


}

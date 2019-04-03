package com.productmicroservice.project.dao;

import com.productmicroservice.project.entities.KitchenProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KitchenProductDao extends JpaRepository<KitchenProduct, Integer> {

    @Override
    List<KitchenProduct> findAll();

    @Override
    <S extends KitchenProduct> S save(S s);

    @Query(value = "SELECT * " +
            "       FROM productsdb.kitchen_products " +
            "       WHERE productsdb.kitchen_products.kitchen_product_brand=:brand", nativeQuery = true)
    List<KitchenProduct> findByKitchenProductBrand(@Param("brand") String brand);

    @Query(value = "SELECT * " +
            "       FROM productsdb.kitchen_products " +
            "       WHERE productsdb.kitchen_products.idkitchen_product=:id", nativeQuery = true)
    KitchenProduct findByKitchenProductId(@Param("id") Integer id);
}

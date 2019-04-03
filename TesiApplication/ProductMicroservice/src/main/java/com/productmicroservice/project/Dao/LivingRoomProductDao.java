package com.productmicroservice.project.dao;

import com.productmicroservice.project.entities.LivingRoomProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivingRoomProductDao extends JpaRepository<LivingRoomProduct, Integer> {

    @Override
    List<LivingRoomProduct> findAll();

    @Override
    <S extends LivingRoomProduct> S save(S s);

    @Query(value = "SELECT * " +
                   "FROM productsdb.living_room_products " +
                   "WHERE productsdb.living_room_products.living_room_product_brand =:brand", nativeQuery = true)
    List<LivingRoomProduct> findByLivingRoomProductBrand(@Param("brand") String brand);

    @Query(value = "SELECT * " +
            "       FROM productsdb.living_room_products " +
            "       WHERE productsdb.living_room_products.idliving_room_product=:id", nativeQuery = true)
    LivingRoomProduct findByLivingRoomProductId(@Param("id") Integer id);

}

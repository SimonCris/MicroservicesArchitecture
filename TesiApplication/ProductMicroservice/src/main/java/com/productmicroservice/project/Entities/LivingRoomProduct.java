package com.productmicroservice.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "living_room_products")
public class LivingRoomProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer idliving_room_product;

    @NotNull
    private String living_room_product_name;

    private String living_room_product_price;

    private String living_room_product_brand;

    private String living_room_product_image;

    private String living_room_product_description;

    public LivingRoomProduct(@NotNull String living_room_product_name, String living_room_product_price, String living_room_product_brand, String living_room_product_image, String living_room_product_description) {
        this.living_room_product_name = living_room_product_name;
        this.living_room_product_price = living_room_product_price;
        this.living_room_product_brand = living_room_product_brand;
        this.living_room_product_image = living_room_product_image;
        this.living_room_product_description = living_room_product_description;
    }

    public LivingRoomProduct() {
    }

    @Override
    public String toString() {
        return "LivingRoomProduct{" +
                "idliving_room_product=" + idliving_room_product +
                ", living_room_product_name='" + living_room_product_name + '\'' +
                ", living_room_product_price='" + living_room_product_price + '\'' +
                ", living_room_product_brand='" + living_room_product_brand + '\'' +
                ", living_room_product_image='" + living_room_product_image + '\'' +
                ", living_room_product_description='" + living_room_product_description + '\'' +
                '}';
    }

    public Integer getIdliving_room_product() {
        return idliving_room_product;
    }

    public void setIdliving_room_product(Integer idliving_room_product) {
        this.idliving_room_product = idliving_room_product;
    }

    public String getLiving_room_product_name() {
        return living_room_product_name;
    }

    public void setLiving_room_product_name(String living_room_product_name) {
        this.living_room_product_name = living_room_product_name;
    }

    public String getLiving_room_product_price() {
        return living_room_product_price;
    }

    public void setLiving_room_product_price(String living_room_product_price) {
        this.living_room_product_price = living_room_product_price;
    }

    public String getLiving_room_product_brand() {
        return living_room_product_brand;
    }

    public void setLiving_room_product_brand(String living_room_product_brand) {
        this.living_room_product_brand = living_room_product_brand;
    }

    public String getLiving_room_product_image() {
        return living_room_product_image;
    }

    public void setLiving_room_product_image(String living_room_product_image) {
        this.living_room_product_image = living_room_product_image;
    }

    public String getLiving_room_product_description() {
        return living_room_product_description;
    }

    public void setLiving_room_product_description(String living_room_product_description) {
        this.living_room_product_description = living_room_product_description;
    }
}

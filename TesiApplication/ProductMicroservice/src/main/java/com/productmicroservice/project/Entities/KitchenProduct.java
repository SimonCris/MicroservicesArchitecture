package com.productmicroservice.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "kitchen_products")
public class KitchenProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer idkitchen_product;

    @NotNull
    private String kitchen_product_name;

    private String kitchen_product_price;

    private String kitchen_product_brand;

    private String kitchen_product_image;

    private String kitchen_product_description;

    public KitchenProduct(@NotNull String kitchen_product_name, String kitchen_product_price, String kitchen_product_brand, String kitchen_product_image, String kitchen_product_description) {
        this.kitchen_product_name = kitchen_product_name;
        this.kitchen_product_price = kitchen_product_price;
        this.kitchen_product_brand = kitchen_product_brand;
        this.kitchen_product_image = kitchen_product_image;
        this.kitchen_product_description = kitchen_product_description;
    }

    public KitchenProduct() {
    }

    @Override
    public String toString() {
        return "KitchenProduct{" +
                "idkitchen_product=" + idkitchen_product +
                ", kitchen_product_name='" + kitchen_product_name + '\'' +
                ", kitchen_product_price='" + kitchen_product_price + '\'' +
                ", kitchen_product_brand='" + kitchen_product_brand + '\'' +
                ", kitchen_product_image='" + kitchen_product_image + '\'' +
                ", kitchen_product_description='" + kitchen_product_description + '\'' +
                '}';
    }

    public Integer getIdkitchen_product() {
        return idkitchen_product;
    }

    public void setIdkitchen_product(Integer idkitchen_product) {
        this.idkitchen_product = idkitchen_product;
    }

    public String getKitchen_product_name() {
        return kitchen_product_name;
    }

    public void setKitchen_product_name(String kitchen_product_name) {
        this.kitchen_product_name = kitchen_product_name;
    }

    public String getKitchen_product_price() {
        return kitchen_product_price;
    }

    public void setKitchen_product_price(String kitchen_product_price) {
        this.kitchen_product_price = kitchen_product_price;
    }

    public String getKitchen_product_brand() {
        return kitchen_product_brand;
    }

    public void setKitchen_product_brand(String kitchen_product_brand) {
        this.kitchen_product_brand = kitchen_product_brand;
    }

    public String getKitchen_product_image() {
        return kitchen_product_image;
    }

    public void setKitchen_product_image(String kitchen_product_image) {
        this.kitchen_product_image = kitchen_product_image;
    }

    public String getKitchen_product_description() {
        return kitchen_product_description;
    }

    public void setKitchen_product_description(String kitchen_product_description) {
        this.kitchen_product_description = kitchen_product_description;
    }
}

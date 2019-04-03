package com.productmicroservice.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "favorite_products")
public class FavoriteProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer idfavorite_product;

    @NotNull
    @Column(unique = true)
    private String favorite_product_name;

    @NotNull
    private String favorite_product_brand;

    @NotNull
    private String favorite_product_image;

    @NotNull
    private String favorite_product_price;

    @NotNull
    private String email_user;

    public FavoriteProduct(String favorite_product_name, String favorite_product_brand,
                           String favorite_product_image, String favorite_product_price, String email_user) {
        this.favorite_product_name = favorite_product_name;
        this.favorite_product_brand = favorite_product_brand;
        this.favorite_product_image = favorite_product_image;
        this.favorite_product_price = favorite_product_price;
        this.email_user = email_user;
    }

    public FavoriteProduct() {
    }

    @Override
    public String toString() {
        return "FavoriteProduct{" +
                "idfavorite_product=" + idfavorite_product +
                ", favorite_product_name='" + favorite_product_name + '\'' +
                ", favorite_product_brand='" + favorite_product_brand + '\'' +
                ", favorite_product_image='" + favorite_product_image + '\'' +
                ", favorite_product_price='" + favorite_product_price + '\'' +
                ", email_user='" + email_user + '\'' +
                '}';
    }

    public Integer getIdfavorite_product() {
        return idfavorite_product;
    }

    public void setIdfavorite_product(Integer idfavorite_product) {
        this.idfavorite_product = idfavorite_product;
    }

    public String getFavorite_product_name() {
        return favorite_product_name;
    }

    public void setFavorite_product_name(String favorite_product_name) {
        this.favorite_product_name = favorite_product_name;
    }

    public String getFavorite_product_brand() {
        return favorite_product_brand;
    }

    public void setFavorite_product_brand(String favorite_product_brand) {
        this.favorite_product_brand = favorite_product_brand;
    }

    public String getFavorite_product_image() {
        return favorite_product_image;
    }

    public void setFavorite_product_image(String favorite_product_image) {
        this.favorite_product_image = favorite_product_image;
    }

    public String getFavorite_product_price() {
        return favorite_product_price;
    }

    public void setFavorite_product_price(String favorite_product_price) {
        this.favorite_product_price = favorite_product_price;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }
}

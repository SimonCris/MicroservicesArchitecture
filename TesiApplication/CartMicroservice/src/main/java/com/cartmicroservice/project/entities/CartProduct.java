package com.cartmicroservice.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_products")
public class CartProduct {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcart_product;

    private String cart_product_user_email;

    private String cart_product_name;

    private String cart_product_brand;

    private String cart_product_price;

    private String cart_product_image;

    private Boolean cart_product_to_order;

    public CartProduct(String cart_product_user_email, String cart_product_name, String cart_product_brand,
                       String cart_product_price, String cart_product_image,
                       Boolean cart_product_to_order) {
        this.cart_product_user_email = cart_product_user_email;
        this.cart_product_name = cart_product_name;
        this.cart_product_brand = cart_product_brand;
        this.cart_product_price = cart_product_price;
        this.cart_product_image = cart_product_image;
        this.cart_product_to_order = cart_product_to_order;
    }

    public CartProduct() {
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "idcart_product=" + idcart_product +
                ", cart_product_user_email='" + cart_product_user_email + '\'' +
                ", cart_product_name='" + cart_product_name + '\'' +
                ", cart_product_brand='" + cart_product_brand + '\'' +
                ", cart_product_price='" + cart_product_price + '\'' +
                ", cart_product_image='" + cart_product_image + '\'' +
                ", cart_product_to_order='" + cart_product_to_order + '\'' +
                '}';
    }

    public Integer getIdcart_product() {
        return idcart_product;
    }

    public void setIdcart_product(Integer idcart_product) {
        this.idcart_product = idcart_product;
    }

    public String getCart_product_user_email() {
        return cart_product_user_email;
    }

    public void setCart_product_user_email(String cart_product_user_email) {
        this.cart_product_user_email = cart_product_user_email;
    }

    public String getCart_product_name() {
        return cart_product_name;
    }

    public void setCart_product_name(String cart_product_name) {
        this.cart_product_name = cart_product_name;
    }

    public String getCart_product_brand() {
        return cart_product_brand;
    }

    public void setCart_product_brand(String cart_product_brand) {
        this.cart_product_brand = cart_product_brand;
    }

    public String getCart_product_price() {
        return cart_product_price;
    }

    public void setCart_product_price(String cart_product_price) {
        this.cart_product_price = cart_product_price;
    }

    public String getCart_product_image() {
        return cart_product_image;
    }

    public void setCart_product_image(String cart_product_image) {
        this.cart_product_image = cart_product_image;
    }

    public Boolean getCart_product_to_order() {
        return cart_product_to_order;
    }

    public void setCart_product_to_order(Boolean cart_product_to_order) {
        this.cart_product_to_order = cart_product_to_order;
    }
}

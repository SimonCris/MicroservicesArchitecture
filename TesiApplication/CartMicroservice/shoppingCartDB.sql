CREATE TABLE `shoppingcart`.`cart_products` (
  `idcart_product` INT NOT NULL AUTO_INCREMENT,
  `cart_product_user_email` VARCHAR(255) NOT NULL,
  `cart_product_name` VARCHAR(255) NOT NULL,
  `cart_product_brand` VARCHAR(255) NULL,
  `cart_product_price` VARCHAR(255) NULL,
  `cart_product_image` VARCHAR(255) NULL,
  `cart_product_to_order` TINYINT NULL,
  PRIMARY KEY (`idcart_product`));
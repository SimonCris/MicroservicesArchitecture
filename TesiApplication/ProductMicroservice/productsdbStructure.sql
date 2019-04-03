CREATE TABLE `productsdb`.`kitchen_products` (
  `idkitchen_product` INT NOT NULL AUTO_INCREMENT,
  `kitchen_product_name` VARCHAR(255) NOT NULL,
  `kitchen_product_price` VARCHAR(255) NULL,
  `kitchen_product_brand` VARCHAR(255) NULL,
  `kitchen_product_image` VARCHAR(255) NULL,
  `kitchen_product_description` VARCHAR(255) NULL,
  PRIMARY KEY (`idkitchen_product`),
  UNIQUE INDEX `idkitchen_product_UNIQUE` (`idkitchen_product` ASC));

INSERT INTO productsdb.kitchen_products (kitchen_product_name, kitchen_product_price,
                                         kitchen_product_brand, kitchen_product_image,
                                         kitchen_product_description)
VALUES ("Dishes Set", "100 Euro", "Scavolini", "DishesSet.jpg", "Elegant set of dishes, teapots, cups to be used for special occasions"),
       ("Pots Set", "330 Euro", "Scavolini", "PotsSet.jpg", "Fantastic and modern set of cookware to use for the most varied purposes"),
       ("CoffeePot", "14 Euro", "Bugatti", "Coffepot.jpg", "Modern and functional coffee maker that allows you to fill 3/4 cups of coffee"),
       ("Cutlery Set", "210 Euro", "Bugatti", "CutlerySet.jpg", "Set of stainless steel cutlery, to be used both on important occasions and for a quick meal"),
       ("Chopping Board", "35 Euro", "Pedrini", "ChoppingBoards.jpg", "Functional and massive kitchen chopping board"),
       ("Pressure Cooker", "160 Euro", "Pedrini", "PressureCooker.jpg", "Stainless steel pressure cooker that allows you to cook all the food in a very short time");

CREATE TABLE `productsdb`.`living_room_products` (
  `idliving_room_product` INT NOT NULL AUTO_INCREMENT,
  `living_room_product_name` VARCHAR(255) NOT NULL,
  `living_room_product_price` VARCHAR(255) NULL,
  `living_room_product_brand` VARCHAR(255) NULL,
  `living_room_product_image` VARCHAR(255) NULL,
  `living_room_product_description` VARCHAR(255) NULL,
  PRIMARY KEY (`idliving_room_product`),
  UNIQUE INDEX `idliving_room_product_UNIQUE` (`idliving_room_product` ASC));

INSERT INTO productsdb.living_room_products (living_room_product_name, living_room_product_price,
                                             living_room_product_brand, living_room_product_image,
                                             living_room_product_description)
VALUES ("Armchair", "400 Euro", "Divani&Divani", "Armchair.jpg", "Very convenient and comfortable"),
       ("Living Room Table", "1120 Euro", "Divani&Divani", "LivingRoomTable.jpg", "Very spacious with 6/8 seats"),
       ("Sofa", "380 Euro", "Bontempi", "Sofa.jpg", "Comfortable living room sofa composed of three modular pieces"),
       ("Living Room Chairs", "800 Euro", "Bontempi", "Chairs.jpg", "Comfortable living room chairs that can also be used as armchairs"),
       ("Sideboards", "1100 Euro", "Lexington", "Sideboards.jpg", "Spacious sideboard formed by four sections and for each section there are three drawers"),
       ("Side Table", "150 Euro", "Lexington", "SideTable.jpg", "It is possible to use both the upper and the lower level");

CREATE TABLE `productsdb`.`favorite_products` (
  `idfavorite_product` INT NOT NULL AUTO_INCREMENT,
  `email_user` VARCHAR(255) NOT NULL,
  `favorite_product_name` VARCHAR(255) NOT NULL,
  `favorite_product_price` VARCHAR(255) NOT NULL,
  `favorite_product_brand` VARCHAR(255) NOT NULL,
  `favorite_product_image` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idfavorite_product`),
  UNIQUE INDEX `idfavorite_product_UNIQUE` (`idfavorite_product` ASC));


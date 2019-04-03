CREATE TABLE `userdb`.`user` (
  `user_email` VARCHAR(255) NOT NULL,
  `user_name` VARCHAR(255) NULL,
  `user_surname` VARCHAR(255) NULL,
  `user_birthday_date` DATE NULL,
  `user_address` VARCHAR(255) NULL,
  `user_domicile_city` VARCHAR(255) NULL,
  `user_consent` TINYINT NULL,
  PRIMARY KEY (`user_email`),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC));

INSERT INTO userdb.user (userdb.user.user_email, userdb.user.user_name, userdb.user.user_surname,
                         userdb.user.user_birthday_date, userdb.user.user_address,
                         userdb.user.user_domicile_city, userdb.user.user_consent)
VALUES ("test@test.com", "RZhM0bdgLEnQ4npNNNLqJQ==",
        "EPONqWW9rVbyMxqYyUdohA==", "1900-01-01",
        "ykFL07ZGK/lTQv9OqR89yw==",
        "e+GXsuEbGN6nqZfo+OrZ/A==", false);
#VALUES ("test@test.com", "nome", "cognome", "1900-01-01", "via", "città", false);

INSERT INTO userdb.user (userdb.user.user_email, userdb.user.user_name, userdb.user.user_surname,
                         userdb.user.user_birthday_date, userdb.user.user_address,
                         userdb.user.user_domicile_city, userdb.user.user_consent)
VALUES ("test@test.com", "RZhM0bdgLEnQ4npNNNLqJQ==",
        "EPONqWW9rVbyMxqYyUdohA==", "1900-01-01",
        "ykFL07ZGK/lTQv9OqR89yw==",
        "e+GXsuEbGN6nqZfo+OrZ/A==", false);
#VALUES ("test@test.com", "nome", "cognome", "1900-01-01", "via", "città", false);

CREATE TABLE `userdb`.`user_role` (
  `user_role_id` INT NOT NULL AUTO_INCREMENT,
  `email_user` VARCHAR(255) NOT NULL,
  `role_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE INDEX `user_role_id_UNIQUE` (`user_role_id` ASC),
  INDEX `email_user_idx` (`email_user` ASC),
  CONSTRAINT `email_user`
  FOREIGN KEY (`email_user`)
  REFERENCES `userdb`.`user` (`user_email`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT INTO userdb.user_role (userdb.user_role.email_user, userdb.user_role.role_name)
VALUES ("test@test.com", "RL0pywuncFNwUF959zxxX+DZshAyOk/zVF6bgtVN8LouX/ZHLwDJpfdGjruZTb3l4Y+0kQ==");
#VALUES ("test@test.com", "ROLE_ADMIN");

INSERT INTO userdb.user_role (userdb.user_role.email_user, userdb.user_role.role_name)
VALUES ("test@test.com", "6Rqd5FlXRdz/GQl+6D0BU+iKK6tyHBu1hXANzY6XmpcRtvmpNGinxGIp/xfUmRu0l6WJrw==");
#VALUES ("test@test.com", "ROLE_USER");




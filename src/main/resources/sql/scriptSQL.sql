DROP PROCEDURE IF EXISTS InitData2;

DELIMITER $$

CREATE PROCEDURE InitData2()
  BEGIN
    DECLARE COUNTTABLE INT;
    DECLARE COUNTROW INT;

    SELECT count(*) FROM information_schema.tables
    WHERE table_schema = 'webshopbd2'
          AND table_name = 'product_bought' LIMIT 1
    INTO COUNTTABLE;

    IF (COUNTTABLE>0) THEN
      SELECT COUNT(*) FROM product_bought
      INTO COUNTROW;
    END IF;

    IF (COUNTROW=0) THEN


    INSERT INTO user_address (city, country, post_code, street) VALUES ('NewYork', 'USA', '12345', 'Broadway');
    INSERT INTO user_address (city, country, post_code, street) VALUES ('San Francisco', 'USA', '34333', 'Lombard');
      INSERT INTO user (login, name, password, surname, address_id) VALUES
        ('tomek371240@gmail.com', 'thomas', '$2a$10$Nijs1aGPCtgEGNzkDpabB./aeTTUUz6D3Lrc8YLNw2hFRjr.X/89i', 'thomas', 1);
      INSERT INTO user (login, name, password, surname, address_id) VALUES
        ('tomek371242@gmail.com', 'user', '$2a$10$Nijs1aGPCtgEGNzkDpabB./aeTTUUz6D3Lrc8YLNw2hFRjr.X/89i', 'user', 2);

      INSERT INTO role (code, name) VALUES ('admin', 'admin');
      INSERT INTO role (code, name) VALUES ('user', 'user');

      INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
      INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
      INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

      INSERT INTO bucket (user_id) VALUE (1);
      INSERT INTO bucket (user_id) VALUE (2);

      INSERT INTO product_status (code, name) VALUES ('initial', 'Product was initialised in shop');
      INSERT INTO product_status (code, name) VALUES ('sale', 'Product is on sale');
      INSERT INTO product_status (code, name) VALUES ('withdrawn', 'Product is withdrawn form sale');
      INSERT INTO product_status (code, name) VALUES ('inaccessible', 'Product is inaccessible');

      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://i.ebayimg.com/00/s/NTQzWDQxNQ==/z/X-IAAOSweDdaoUD1/$_86.JPG', 100, 'Samsung Galaxy S9', 100, 'Galaxy S9', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://www.att.com/catalog/en/skus/images/apple-iphone%20x-space%20gray-450x350.png', 100, 'super iphone iphone', 100, 'iphone10', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('http://nusignsupply.com/store/images/detailed/1/flatbed-front.jpg', 100, 'Super Printer', 100, 'Super Printer', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://image3.mouthshut.com/images/imagesp/925872676s.jpg', 100, 'Printer printer Cannon Printer', 100, 'Printer', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://assets.letemps.ch/sites/default/files/media/2018/01/19/file6yhesfeuttf1awwjugk.jpg', 100, 'Super Server', 100, 'Super Server', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('http://dataekb.ru/wp-content/uploads/2017/05/types-of-web-hosting-servers.jpg', 100, 'Server Server', 100, 'Server', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://static.pexels.com/photos/204611/pexels-photo-204611.jpeg', 100, 'Ultra Computer with Windows', 100, 'Ultra Compupter', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://des.gbtcdn.com/uploads/pdm-desc-pic/Electronic/image/2016/04/21/1461210911561985.jpg', 100, 'Standard Windows Computer', 100, 'Standard Comp', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('http://www.ex-astris-scientia.org/inconsistencies/monitors/monitor-tos-shine-theparadisesyndrome.jpg', 100, 'Super Spocks Computer', 100000, 'Spocks Computer', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('https://static.pexels.com/photos/204611/pexels-photo-204611.jpeg', 100, 'super computer computer', 200000, 'Super Comp', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('http://trojmiasto.wizytowka.waw.pl/wp-content/uploads/2017/11/dron-trojmiasto-bytow.jpg', 100, 'Super Dron Dron', 23000, 'Dron', 100, 2);
      INSERT INTO product (image_link, available_amount, description, price, title, total_amount, status_id) VALUES ('http://kaka.com.ng/image/data/layerslider/iphone%206.jpg', 100, 'super iphone iphone', 20000, 'iphone6', 100, 2);

      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 1,  1, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 2,  2, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 3,  3, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 4,  4, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 5,  5, curdate());

      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 1,  1, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 2,  2, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 3,  3, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 4,  4, curdate());
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 5,  5, curdate());

      INSERT INTO status (code, name) VALUES ('booked', 'Order was booked');
      INSERT INTO status (code, name) VALUES ('paid', 'Transaction confirmed');
      INSERT INTO status (code, name) VALUES ('prepared', 'Order was prepared and is ready to send ');
      INSERT INTO status (code, name) VALUES ('send', 'Order was send check status delivery in link');
      INSERT INTO status (code, name) VALUES ('delivered', 'Order was delivered to client');


      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
      INSERT INTO shipping_address (city, country, name, post_code, street, supplier, surname) VALUES ('Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');

      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', curdate(), 2, 200, 1, 4, 1);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', curdate(), 2, 200, 1, 4, 1);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', curdate(), 2, 200, 2, 4, 1);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', 2, 200, 3, 3, 1);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', 2, 200, 4, 2, 1);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', 2, 200, 5, 1, 1);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', curdate(), 2, 200, 6, 4, 2);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', curdate(), 2, 200, 7, 4, 2);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', 2, 200, 8, 3, 2);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', 2, 200, 1, 2, 2);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (curdate(), 'link_delivery', 2, 200, 1, 1, 2);

      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (1, 1, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (2, 2, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (3, 3, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (4, 4, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (5, 5, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (6, 6, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (7, 7, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (8, 8, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (9, 9, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (10, 10, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (2, 1, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (3, 2, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (4, 3, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (5, 4, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (6, 5, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (7, 6, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (8, 7, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (9, 8, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (10, 9, 1);
      INSERT INTO product_bought (product_id, products_order_id, amount) VALUES (1, 10, 1);
    END IF;

  END $$

    DELIMITER ;

  CALL InitData2();
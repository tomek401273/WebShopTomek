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
    INSERT INTO users (login, name, password, surname, address_id) VALUES ('tomek371240@gmail.com', 'thomas', '$2a$10$Nijs1aGPCtgEGNzkDpabB./aeTTUUz6D3Lrc8YLNw2hFRjr.X/89i', 'thomas', 1);
    INSERT INTO users (login, name, password, surname, address_id) VALUES ('tomek371242@gmail.com', 'user', '$2a$10$Nijs1aGPCtgEGNzkDpabB./aeTTUUz6D3Lrc8YLNw2hFRjr.X/89i', 'user', 2);

    INSERT INTO role (code, name) VALUES ('admin', 'admin');
    INSERT INTO role (code, name) VALUES ('user', 'user');

      INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
      INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
      INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

      INSERT INTO bucket (id, user_id) VALUES (1, 1);
      INSERT INTO bucket (id, user_id) VALUES (2, 2);

      INSERT INTO product_status (code, name) VALUES ('initial', 'Product was initialised in shop');
      INSERT INTO product_status (code, name) VALUES ('sale', 'Product is on sale');
      INSERT INTO product_status (code, name) VALUES ('withdrawn', 'Product is withdrawn form sale');
      INSERT INTO product_status (code, name) VALUES ('inaccessible', 'Product is inaccessible');

      INSERT INTO category (name) VALUES ( 'PC Gaming');
      INSERT INTO category (name) VALUES ( 'PC Desktop');
      INSERT INTO category (name) VALUES ( 'Laptops');
      INSERT INTO category (name) VALUES ( 'Phones');
      INSERT INTO category (name) VALUES ( 'Tablets');
      INSERT INTO category (name) VALUES ( 'Monitors');
      INSERT INTO category (name) VALUES ( 'Computer Components');
      INSERT INTO category (name) VALUES ( 'Storage & Hard Drivers');
      INSERT INTO category (name) VALUES ( 'Networking');
      INSERT INTO category (name) VALUES ( 'Computer Accessories');
      INSERT INTO category (name) VALUES ( 'Printer');
      INSERT INTO category (name) VALUES ( 'Server');


      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (1, 'https://i.ebayimg.com/00/s/NTQzWDQxNQ==/z/X-IAAOSweDdaoUD1/$_86.JPG', 100, 'Samsung Galaxy S9', 100, 'Galaxy S9', 100, 2, 0, 0, 0, localtimestamp, 4, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (2, 'https://www.att.com/catalog/en/skus/images/apple-iphone%20x-space%20gray-450x350.png', 100, 'super iphone iphone', 100, 'iphone10', 100, 2, 0, 0, 0, localtimestamp, 4, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (3, 'http://nusignsupply.com/store/images/detailed/1/flatbed-front.jpg', 100, 'Super Printer', 100, 'Super Printer', 100, 2, 0, 0, 0, localtimestamp, 11, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (4, 'https://image3.mouthshut.com/images/imagesp/925872676s.jpg', 100, 'Printer printer Cannon Printer', 100, 'Printer', 100, 2, 0, 0, 0, localtimestamp, 11, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (5, 'https://assets.letemps.ch/sites/default/files/media/2018/01/19/file6yhesfeuttf1awwjugk.jpg', 100, 'Super Server', 100, 'Super Server', 100, 2, 0, 0, 0, localtimestamp, 12, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (6, 'http://dataekb.ru/wp-content/uploads/2017/05/types-of-web-hosting-servers.jpg', 100, 'Server Server', 100, 'Server', 100, 2, 0, 0, 0, localtimestamp, 12, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (7, 'https://static.pexels.com/photos/204611/pexels-photo-204611.jpeg', 100, 'Ultra Computer with Windows', 100, 'Ultra Compupter', 100, 2, 0, 0, 0, localtimestamp, 1, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (8, 'https://des.gbtcdn.com/uploads/pdm-desc-pic/Electronic/image/2016/04/21/1461210911561985.jpg', 100, 'Standard Windows Computer', 100, 'Standard Comp', 100, 2, 0, 0, 0, localtimestamp, 2, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (9, 'http://www.ex-astris-scientia.org/inconsistencies/monitors/monitor-tos-shine-theparadisesyndrome.jpg', 100, 'Super Spocks Computer', 100000, 'Spocks Computer', 100, 2, 0, 0, 0, localtimestamp, 1, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (10, 'https://static.pexels.com/photos/204611/pexels-photo-204611.jpeg', 100, 'super computer computer', 200000, 'Super Comp', 100, 2, 0, 0, 0, localtimestamp, 1, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (11, 'http://trojmiasto.wizytowka.waw.pl/wp-content/uploads/2017/11/dron-trojmiasto-bytow.jpg', 100, 'Super Dron Dron', 23000, 'Dron', 100, 2, 0, 0, 0, localtimestamp, 10, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (12, 'http://kaka.com.ng/image/data/layerslider/iphone%206.jpg', 100, 'super iphone iphone', 20000, 'iphone6', 100, 2, 0, 0, 0, localtimestamp, 4, false);

      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 1,  1, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 2,  2, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 3,  3, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 4,  4, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 5,  5, localtimestamp);

      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 1,  1, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 2,  2, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 3,  3, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 4,  4, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (2, 5,  5, localtimestamp);

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

      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', localtimestamp, 2, 200, 1, 4, 1);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', localtimestamp, 2, 200, 1, 4, 1);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', localtimestamp, 2, 200, 2, 4, 1);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', 2, 200, 3, 3, 1);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', 2, 200, 4, 2, 1);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', 2, 200, 5, 1, 1);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', localtimestamp, 2, 200, 6, 4, 2);
      INSERT INTO products_order (bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', localtimestamp, 2, 200, 7, 4, 2);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', 2, 200, 8, 3, 2);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', 2, 200, 1, 2, 2);
      INSERT INTO products_order (bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (localtimestamp, 'link_delivery', 2, 200, 1, 1, 2);

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
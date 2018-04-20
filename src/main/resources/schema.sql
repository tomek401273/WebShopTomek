DROP TABLE IF EXISTS product_bought;
DROP TABLE IF EXISTS products_order;
DROP TABLE IF EXISTS shipping_address;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS product_bucket;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS joining_product_reminder;
DROP TABLE IF EXISTS product_mark;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product_status;
DROP TABLE IF EXISTS bucket;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_address;

CREATE TABLE user_address
(
  id bigint NOT NULL,
  city character varying(255),
  country character varying(255),
  county character varying(255),
  district character varying(255),
  post_code character varying(255),
  state character varying(255),
  street character varying(255),
  subdistrict character varying(255),
  CONSTRAINT user_address_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
  id bigint NOT NULL,
  code_confirm character varying(255),
  confirm boolean,
  login character varying(255),
  name character varying(255),
  password character varying(255),
  surname character varying(255),
  address_id bigint,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT fkd19fa39gev8b1urp9hmym73sl FOREIGN KEY (address_id)
      REFERENCES user_address (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_ow0gan20590jrb00upg3va2fn UNIQUE (login)
);

CREATE TABLE role
(
  id bigint NOT NULL,
  code character varying(255),
  name character varying(255),
  CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrhfovtciq1l558cw6udg0h0d3 FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE bucket
(
  id bigint NOT NULL,
  user_id bigint,
  CONSTRAINT bucket_pkey PRIMARY KEY (id),
  CONSTRAINT fk327w43qqj7sg7250igsyxw7s3 FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE product_status
(
  id bigint NOT NULL,
  code character varying(255),
  name character varying(255),
  CONSTRAINT product_status_pkey PRIMARY KEY (id)
);

CREATE TABLE category
(
  id bigint NOT NULL,
  name character varying(255),
  CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE product
(
  product_id bigint NOT NULL,
  image_link character varying(255),
  available_amount integer,
  average_marks numeric(19,2),
  count_marks numeric(19,2),
  description character varying(255),
  last_modification timestamp without time zone,
  price numeric(19,2),
  sum_marks numeric(19,2),
  title character varying(255),
  to_delete boolean NOT NULL,
  total_amount integer,
  category_id bigint,
  status_id bigint,
  CONSTRAINT product_pkey PRIMARY KEY (product_id),
  CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fksm8xpambory4vi2rm4wh5fef FOREIGN KEY (status_id)
      REFERENCES product_status (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE product_mark
(
  product_id bigint NOT NULL,
  user_id bigint NOT NULL,
  mark integer,
  CONSTRAINT product_mark_pkey PRIMARY KEY (product_id, user_id),
  CONSTRAINT fk16nbs9iewdojimalq1v93jkco FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkflqs201nsx5bcgik9ti8bn7ny FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE joining_product_reminder
(
  productemailreminder_id bigint NOT NULL,
  product_id bigint NOT NULL,
  CONSTRAINT fkfiyva4mdcd3bauh0tgxp2fi2o FOREIGN KEY (productemailreminder_id)
      REFERENCES product_email_reminder (reminder_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkktsi4ufa598i3b59p0ao2hw76 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE comment
(
  id bigint NOT NULL,
  created timestamp without time zone,
  message character varying(255),
  product_id bigint,
  user_id bigint,
  CONSTRAINT comment_pkey PRIMARY KEY (id),
  CONSTRAINT fkm1rmnfcvq5mk26li4lit88pc5 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkqm52p1v3o13hy268he0wcngr5 FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE product_bucket
(
  bucket_id bigint NOT NULL,
  product_id bigint NOT NULL,
  amount integer,
  date_added timestamp without time zone,
  CONSTRAINT product_bucket_pkey PRIMARY KEY (bucket_id, product_id),
  CONSTRAINT fknmt1ap2qtwjb460i9va4tb1d0 FOREIGN KEY (bucket_id)
      REFERENCES bucket (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkt3nnsco352wbdcuv10sr4npvy FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE status(
  id bigint NOT NULL,
  code character varying(255),
  name character varying(255),
  CONSTRAINT status_pkey PRIMARY KEY (id));

CREATE TABLE shipping_address
(
  id bigint NOT NULL,
  city character varying(255),
  country character varying(255),
  county character varying(255),
  district character varying(255),
  name character varying(255),
  post_code character varying(255),
  state character varying(255),
  street character varying(255),
  subdistrict character varying(255),
  supplier character varying(255),
  surname character varying(255),
  CONSTRAINT shipping_address_pkey PRIMARY KEY (id)
);

CREATE TABLE products_order
(
  id bigint NOT NULL,
  bought_date timestamp without time zone,
  delivered_date timestamp without time zone,
  link_delivery character varying(255),
  send_date timestamp without time zone,
  total_amount integer,
  total_value numeric(19,2),
  shipping_address_id bigint,
  status_id bigint,
  user_id bigint,
  CONSTRAINT products_order_pkey PRIMARY KEY (id),
  CONSTRAINT fk1w8o53ode2413cvims3hqy2aj FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk2x7vmu6gxlau2myqcfqcm5nqh FOREIGN KEY (shipping_address_id)
      REFERENCES shipping_address (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fksh3lpcap1b9dx3i35ube8v966 FOREIGN KEY (status_id)
      REFERENCES status (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE product_bought
(
  product_id bigint NOT NULL,
  products_order_id bigint NOT NULL,
  amount integer,
  CONSTRAINT product_bought_pkey PRIMARY KEY (product_id, products_order_id),
  CONSTRAINT fk991shhm4gknh6sjyvnis1vp74 FOREIGN KEY (products_order_id)
      REFERENCES products_order (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fki3edh689gwip4pvqun9j9lm87 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO user_address (id, city, country, post_code, street) VALUES (1, 'NewYork', 'USA', '12345', 'Broadway');
    INSERT INTO user_address (id, city, country, post_code, street) VALUES (2, 'San Francisco', 'USA', '34333', 'Lombard');

    INSERT INTO users (id, login, name, password, surname, address_id, confirm) VALUES (1, 'tomek371240@gmail.com', 'thomas', '$2a$10$Nijs1aGPCtgEGNzkDpabB./aeTTUUz6D3Lrc8YLNw2hFRjr.X/89i', 'thomas', 1, true);
    INSERT INTO users (id, login, name, password, surname, address_id, confirm) VALUES (2, 'tomek371242@gmail.com', 'user', '$2a$10$Nijs1aGPCtgEGNzkDpabB./aeTTUUz6D3Lrc8YLNw2hFRjr.X/89i', 'user', 2, true);

    INSERT INTO role (id, code, name) VALUES (1, 'admin', 'admin');
    INSERT INTO role (id, code, name) VALUES (2, 'user', 'user');

      INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
      INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
      INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

      INSERT INTO bucket (id, user_id) VALUES (1, 1);
      INSERT INTO bucket (id, user_id) VALUES (2, 2);

      INSERT INTO product_status (id, code, name) VALUES (1, 'initial', 'Product was initialised in shop');
      INSERT INTO product_status (id, code, name) VALUES (2, 'sale', 'Product is on sale');
      INSERT INTO product_status (id, code, name) VALUES (3, 'withdrawn', 'Product is withdrawn form sale');
      INSERT INTO product_status (id, code, name) VALUES (4, 'inaccessible', 'Product is inaccessible');

      INSERT INTO category (id, name) VALUES (1, 'PC Desktop');
      INSERT INTO category (id, name) VALUES (2, 'Laptops');
      INSERT INTO category (id, name) VALUES (3, 'Phones');
      INSERT INTO category (id, name) VALUES (4, 'Tablets');
      INSERT INTO category (id, name) VALUES (5, 'Monitors');
      INSERT INTO category (id, name) VALUES (6, 'Computer Components');
      INSERT INTO category (id, name) VALUES (7, 'Storage & Hard Drivers');
      INSERT INTO category (id, name) VALUES (8, 'Networking');
      INSERT INTO category (id, name) VALUES (9, 'Computer Accessories');
      INSERT INTO category (id, name) VALUES (10, 'Printer');
      INSERT INTO category (id, name) VALUES (11, 'Server');
      INSERT INTO category (id, name) VALUES (12, 'PC Gaming');


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
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (12, 'http://kaka.com.ng/image/data/layerslider/iphone%206.jpg', 100, 'super iphone iphone', 200.22, 'iphone6', 100, 2, 0, 0, 0, localtimestamp, 4, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES (13, 'https://i.ebayimg.com/00/s/NTQzWDQxNQ==/z/X-IAAOSweDdaoUD1/$_86.JPG', 100, 'Samsung Galaxy S9', 100, 'Galaxy S9', 100, 2, 0, 0, 0, to_date('03 03 2018', 'DD MM YYYY'), 4, false);

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

      INSERT INTO status (id, code, name) VALUES (1, 'booked', 'Order was booked');
      INSERT INTO status (id, code, name) VALUES (2, 'paid', 'Transaction confirmed');
      INSERT INTO status (id, code, name) VALUES (3, 'prepared', 'Order was prepared and is ready to send ');
      INSERT INTO status (id, code, name) VALUES (4, 'send', 'Order was send check status delivery in link');
      INSERT INTO status (id, code, name) VALUES (5, 'delivered', 'Order was delivered to client');


     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (1, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (2, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (3, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (4, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (5, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (6, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (7, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (8, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (9, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (11, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname) VALUES (12, 'Warsaw', 'Poland', 'Thomas', '00-100', 'Street', 'InPost', 'Smith');

      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (1, localtimestamp, 'link_delivery', localtimestamp, 2, 200, 1, 4, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (2, localtimestamp, 'link_delivery', localtimestamp, 2, 200, 1, 4, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (3, localtimestamp, 'link_delivery', localtimestamp, 2, 200, 2, 4, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (4, localtimestamp, 'link_delivery', 2, 200, 3, 3, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (5, localtimestamp, 'link_delivery', 2, 200, 4, 2, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (6, localtimestamp, 'link_delivery', 2, 200, 5, 1, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (7, localtimestamp, 'link_delivery', localtimestamp, 2, 200, 6, 4, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (8, localtimestamp, 'link_delivery', localtimestamp, 2, 200, 7, 4, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (9, localtimestamp, 'link_delivery', 2, 200, 8, 3, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (10, localtimestamp, 'link_delivery', 2, 200, 1, 2, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (11, localtimestamp, 'link_delivery', 2, 200, 1, 1, 2);

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
DROP TABLE IF EXISTS subscriber;
DROP TABLE IF EXISTS product_bought;
DROP TABLE IF EXISTS products_order;
DROP TABLE IF EXISTS shipping_address;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS product_bucket;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS joining_product_reminder;
DROP TABLE IF EXISTS product_email_reminder;
DROP TABLE IF EXISTS product_mark;
DROP TABLE IF EXISTS short_description;
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
  apartment integer,
  house integer,
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
  description character varying(1000),
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

CREATE TABLE short_description
(
  id bigint NOT NULL,
  attribute character varying(255),
  product_id bigint,
  CONSTRAINT short_description_pkey PRIMARY KEY (id),
  CONSTRAINT fkrr42h45l3n2fd6uoahiv2xpqg FOREIGN KEY (product_id)
      REFERENCES public.product (product_id) MATCH SIMPLE
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

CREATE TABLE product_email_reminder
(
  reminder_id bigint NOT NULL,
  email character varying(255),
  CONSTRAINT product_email_reminder_pkey PRIMARY KEY (reminder_id)
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
  apartment integer,
  house integer,
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


      INSERT INTO user_address (id, city, country, post_code, street, county, district, state, subdistrict, apartment, house)
      VALUES (1, 'Toruń', 'Poland', '87-100', 'Jurija Gagarina', 'Toruń', 'Toruń', 'Woj. Kujawsko-Pomorskie', 'Bydgoskie I', 1, 1);
      INSERT INTO user_address (id, city, country, post_code, street, county, district, state, subdistrict, apartment, house)
      VALUES (2, 'Toruń', 'Poland', '87-100', 'Jurija Gagarina', 'Toruń', 'Toruń', 'Woj. Kujawsko-Pomorskie', 'Bydgoskie I', 1, 1);

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
      INSERT INTO category (id, name) VALUES (4, 'Monitors');
      INSERT INTO category (id, name) VALUES (5, 'Computer Components');
      INSERT INTO category (id, name) VALUES (6, 'Storage & Hard Drivers');
      INSERT INTO category (id, name) VALUES (7, 'Networking');
      INSERT INTO category (id, name) VALUES (8, 'All');




      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (1, 'https://images-na.ssl-images-amazon.com/images/I/61Yeir0uhIL._SL1322_.jpg',
       100, 'Acer Aspire E 15 E5-576-392H comes with these high level specs: 8th Generation Intel Core i3-8130U Processor 2.2GHz with Turbo Boost Technology up to 3.4GHz, Windows 10 Home, 15.6" Full HD (1920 x 1080) widescreen LED-backlit display, Intel UHD Graphics 620, 6GB Dual Channel Memory, 1TB 5400RPM SATA Hard Drive, 8X DVD Double-Layer Drive RW (M-DISC enabled), Secure Digital (SD) card reader, Acer True Harmony, Two Built-in Stereo Speakers, 802.11ac Wi-Fi featuring MU-MIMO technology (Dual-Band 2.4GHz and 5GHz), Bluetooth 4.1, HD Webcam (1280 x 720) supporting High Dynamic Range (HDR), 1 - USB 3.1 Type C Gen 1 port (up to 5 Gbps), 2 - USB 3.0 ports (one with power-off charging), 1 - USB 2.0 port, 1 - HDMI Port with HDCP support, 6-cell Li-Ion Battery (2800 mAh), Up to 13.5-hours Battery Life, 5.27 lbs. | 2.39 kg (system unit only) (NX.GRYAA.001).',
       379.99, 'Acer Aspire E 15', 100, 2, 0, 0, 0, localtimestamp, 2, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (2, 'https://images-na.ssl-images-amazon.com/images/I/61piZBEt-vL._SL1000_.jpg', 100, 'Windows 10 brings back the Start Menu from Windows 7 and introduces new features, like the Edge Web browser that lets you markup Web pages on your screen. Typical 1366 x 768 HD resolution. Energy-efficient LED backlight.  AMD A6-9200 accelerated processor Dual-core processing. AMD A6 APU handles the AMD Radeon graphics alongside the central processor to balance the load, enabling great performance, rapid multitasking and immersive entertainment. AMD Radeon R4 Integrated graphics chipset with shared video memory provides solid image quality for Internet use, movies, basic photo editing and casual gaming. MaxxAudio to give you great sound across music, movies, voice and games. Weighs 5.07 lbs. and measures 0.9" thin Balances portability and screen size, so you get a respectable amount of viewing space without the laptop being too cumbersome for practical portability. 4-cell lithium-ion battery.',
       514.00, 'Dell Inspiron 15.6-inch', 100, 2, 0, 0, 0, localtimestamp, 2, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete)
      VALUES (3, 'https://images-na.ssl-images-amazon.com/images/I/619Azc33QuL._SL1500_.jpg', 100, 'The Dell Inspiron i3541 Premium Laptop feaures a widescreen HD display and AMD Quad-Core Processor for versatile multimedia functionality. Enjoy full functionality for all your day-to-day computing tasks with the Dell Inspiron i3541 15.6-inch Laptop. This laptop features a widescreen 15.6-inch HD display with 1366 x 768 resolution, offering ample screen space while working or web browsing, and letting you enjoy movies and TV shows in stunning high definition. Pre-loaded with Windows 8.1, this laptop is designed to handle everyday tasks with responsive power. A built-in DVD burner and reader lets you archive files and watch movies, while a media card slot lets you quickly access photos and other media from SD, SDHC, and SDXC cards. An integrated webcam and a microphone let you video chat with friends and family, while high-speed wireless LAN (802.11n) ensures you can stay connected anywhere.',
       344.00, 'Dell Inspiron i3541 15.6-inch', 100, 2, 0, 0, 0, localtimestamp, 2, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (4, 'https://images-na.ssl-images-amazon.com/images/I/81AzXZuhiqL._SL1500_.jpg', 100, 'The CyberPowerPC Gamer Xtreme is optimized for gaming. The Intel CPU and High Performance GPU gives the computer the raw power it needs to function at a high level. Added on, the high speed memory and large hard drive gives the CyberPowerPC Gamer Xtreme all the space needed to only focus on gaming. The Gamer Xtreme VR series also includes the AMD Radeon RX 500 series of graphics cards to deliver high frame rates and impeccable image quality in the newest PC games. Destroy the competition with the CYBERPOWERPC Gamer Xtreme VR series of gaming desktops. The Gamer Xtreme VR series features the latest generation of high performance Intel Core processors and ultra-quick DDR RAM to easily handle system-intensive tasks, such as high definition video playback and gaming. Coupled with powerful discreet video cards, the Gamer Xtreme VR series provides a smooth gaming and multimedia experience.',
       699.00, 'CYBERPOWERPC Gamer Xtreme GXIVR8020A5', 100, 2, 0, 0, 0, localtimestamp, 1, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete)
      VALUES (5, 'https://images-na.ssl-images-amazon.com/images/I/71mWiHqxerL._SL1500_.jpg', 100, 'Acer Aspire TC-780-ACKI5 Desktop PC comes with these specs: 7th Generation Intel Core i5-7400 processor (Up to 3.5GHz), Windows 10 Home, 12GB DDR4 2400MHz Memory, Intel HD Graphics 630, 8X DVD-Writer Double-Layer Drive (DVD-RW), 2TB 7200RPM SATA3 Hard Drive, Digital Media Card Reader -Secure Digital (SD) Card, High Definition Audio with 5.1-Channel Audio Support, 3 - USB 3.0 Ports, 4 - USB 2.0 Ports, 1 - HDMI Port, 1 - VGA Port, Gigabit Ethernet, 802.11ac WiFi, Bluetooth 4.0 LE, USB Keyboard and Mouse, 18.43 lbs. | 8.36 kg (system unit only), 1 Year Parts and Labor Limited Warranty with Toll Free Tech Support (DT.B89AA.033)',
       479.99, 'Acer Aspire TC-780-ACKI5', 100, 2, 0, 0, 0, localtimestamp, 1, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
       (6, 'https://images-na.ssl-images-amazon.com/images/I/817vnX9yB8L._SL1500_.jpg', 100, 'Intel Core i3-6100T Dual-core processor. 3.20GHz, 3MB Cache, up to 3.7GHz. 8GB DDR4 SDRAM system memory. Gives you the power to handle most power-hungry applications and tons of multimedia work. 1TB SATA hard drive. Store 666,000 photos, 284,000 songs or 526 hours of HD video and more. Ultra Slim-tray SuperMulti DVD Burner, Watch movies and read and write CDs and DVDs in multiple formats, 10/100/1000Base-T Ethernet, 802.11 a/b/g/n/ac Wireless LAN, Connect to a broadband modem with wired Ethernet or wirelessly connect to a WiFi signal or hotspot with the 802.11 a/b/g/n/ac connection built into your PC, 23.8" widescreen HD TN WLED-backlit monitor, Intel HD Graphics 530, HD webcam, 3-in-1 Card Reader, Bluetooth 4.0, 2 x USB 3.0 ports, 2 x USB 2.0 ports, 1 x HDMI port, 1 x Ethernet port, 1 x headphone/microphone jack 18.59 lbs, 22.8" x 7.6" x 17.6" Genuine Microsoft Windows 10 Home.',
        645.00, '2017 HP Pavilion', 100, 2, 0, 0, 0, localtimestamp, 1, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (7, 'https://images-na.ssl-images-amazon.com/images/I/514rKVG9NJL._SL1280_.jpg', 100, 'Capture stunning pictures in bright daylight and super low light. Capture stunning pictures in bright daylight and super low light. Our category-defining Dual Aperture lens adapts like the human eye. It''s able to automatically switch between various lighting conditions with ease—making your photos look great whether it''s bright or dark, day or night. The F1.5 aperture mode finds light even in the dark. So the low light camera delivers vibrant photos late into the night. Meaning your camera no longer has a curfew. Super Slow-mo lets you see the things you could have missed in the blink of an eye. Add your favourite music to the Intelligent Scan is a new technology that combines face recognition and iris scan to make unlocking simple even in low light. video or turn the video into a looping GIF, and share it with a tap. Then sit back and watch the reactions roll in.Hear the action with clarity thanks to the pure sound of the stereo speakers tuned by AKG.',
       633.16, 'Samsung Galaxy S9 SM-G9600', 100, 2, 0, 0, 0, localtimestamp, 3, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (8, 'https://images-na.ssl-images-amazon.com/images/I/51AYiQKtNRL._SL1100_.jpg', 100, 'LG V30 Plus offers a Balanced Design, Sturdy and Seamless: The surprisingly compact and lightweight body is constructed of premium materials and wrapped around a brilliant OLED Full Vision display. The slim bezels surround the 6-inch, 18:9 screen and create a viewing experience that makes you want to never look away. With a 6.0" QHD+ OLED Full Vision Display, See More, Hold Less: LG’s OLED display technology brings a cinematic viewing experience to the palm of your hand. Enjoy striking clarity, beautiful contrast, and an expansive spectrum of vibrant, authentic color that must be seen to be believed. Wide Angle Cameras, Capture the Bigger Picture: Capture the bigger picture with the front and rear wide-angle lenses to create compositions without compromise. The redesigned 13 MP rear camera lens reduces distortion, and features a 120 Degree field of view, while the front-facing 5 MP camera features a 90 Degree field of view, ensuring all your friends get in every shot.',
       599.00, 'LG V30 International Version', 100, 2, 0, 0, 0, localtimestamp, 3, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (9, 'https://images-na.ssl-images-amazon.com/images/I/81I3XrCtOxL._SL1500_.jpg', 100, 'Acer’s BE0 monitors offer a class-leading combination of ultra-high resolution screens, superb color definition, EyeProtect technologies, and a 4-side borderless zeroframe design so you can work comfortably all day.',
       360, 'Acer 27" Widescreen ', 100, 2, 0, 0, 0, localtimestamp, 4, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
       (10, 'https://images-na.ssl-images-amazon.com/images/I/81NkM0rbl9L._SL1500_.jpg', 100, 'Sceptre C278W-1920R 27 inch curved monitor full HD 1080P HDMI DisplayPort VGA speakers, Ultra thin brushed metallic 2017 product description (optional): with a silver exterior and sleek design, the Sceptre C278W-1920R is a 27” full HD delivers compelling visuals in appearance and on the screen with 1080P resolution. Featuring a 5ms response time, visuals seemingly appear instantly on the C278W-1920R. With an unprecedented Degree of versatility, DisplayPort delivers video and audio. Enjoy HDMI, and VGA inputs to connect all video.',
       159.99, 'Sceptre 27" Curved ', 100, 2, 0, 0, 0, localtimestamp, 4, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
       (11, 'https://images-na.ssl-images-amazon.com/images/I/91JIPh%2BN4FL._SL1500_.jpg', 100, 'Capacity: 500GB Form Factor: 2.5 inch Interface: SATA 6Gb/s (Compatible with SATA 3Gb/s & SATA 1.5Gb/s) NAND Flash: 32 Layer 3D V-NAND Thickness: 7.0 mm Optimized performance for everyday computing needs Sequential read speed 550 MB/s; Sequential write speed 520 MB/s; Random read speed 100K; Random write speed 90K Energy efficient - improves battery life by up to 50 minutes vs. hard disk drives Worry-free data security with AES 256-bit, TCG/Opal v.2 and Microsoft eDrive full-disk encryption Backed by a five-year limited warranty',
        141.50, 'Samsung 850 EVO', 100, 2, 0, 0, 0, localtimestamp, 6, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (12, 'https://images-na.ssl-images-amazon.com/images/I/71WNhLkWO9L._SL1000_.jpg', 100, 'Get SSD speeds and inject new life into your laptop or desktop PC with a solid state drive from Inland Professional. With a fast, reliable Inland Professional SSD, you will experience quicker boot-up and shutdown, quicker application response and data transfer speeds than with a typical hard disk drive without purchasing a new computer.',
       44.99, 'Inland Professional 240GB SATA III', 100, 2, 0, 0, 0, localtimestamp, 6, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (13, 'https://images-na.ssl-images-amazon.com/images/I/71NbPWLRohL._SL1200_.jpg', 100, 'Graphics Cards: EVGA GT 710 - Enjoy performance that''s superior to integrated graphics in all your PC multimedia applications and gaming. EVGA''s 24/7 Technical Support. Base Clock: 954 MHz. Memory Clock: 1800 MHz Effective. CUDA Cores: 192 Memory Detail: 2048MB DDR3. Memory Bit Width 64 Bit / Memory. Speed: 1.1ns / Memory Bandwidth: 14.4 GB/s. Recommended PSU: 300W or greater power supply.',
       54.99, 'EVGA GT 710', 100, 2, 0, 0, 0, to_date('03 03 2018', 'DD MM YYYY'), 5, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (14, 'https://images-na.ssl-images-amazon.com/images/I/91TI9EhlPtL._SL1500_.jpg', 100, 'Intel 7th Gen Intel Core Desktop Processor i7-7700K (BX80677I77700K). Item Weight 2.4 ounces. Product Dimensions 1.9 x 4.1 x 4.6 inches.  Item Dimensions L x W x H 1.9 x 4.1 x 4.6 inches. Computer Memory Type DDR4 SDRAM.  Compatibility (1. Excludes Intel Optane Technology support) Intel Turbo Boost 2.0 Technology. Intel Hyper-Threading Technology1',
       314.79, 'Intel 7th Gen Intel Core ', 100, 2, 0, 0, 0, to_date('03 03 2018', 'DD MM YYYY'), 5, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (15, 'https://images-na.ssl-images-amazon.com/images/I/91-tQAAX4jL._SL1500_.jpg', 100, 'The NETGEAR Nighthawk AC1750 Smart Wi-Fi Router delivers extreme Wi-Fi speed for gaming up to 1750Mbps. The Dual Core 1GHz processor boosts wireless & wired performance. High-powered amplifiers and external antennas increase range for whole-home mobility, while Beamforming+ boosts speed for mobile devices, up to 100% faster.',
       89.99, 'NETGEAR R6700 Nighthawk AC1750 ', 100, 2, 0, 0, 0, to_date('03 03 2018', 'DD MM YYYY'), 7, false);
      INSERT INTO product (product_id, image_link, available_amount, description, price, title, total_amount, status_id, average_marks, count_marks, sum_marks, last_modification, category_id, to_delete) VALUES
      (16, 'https://images-na.ssl-images-amazon.com/images/I/81K4IHoxEHL._SL1500_.jpg', 100, 'XR500 Nighthawk Pro Gaming Wi-Fi Router uses state-of-the-art software to optimize your internet connection by stabilizing ping, reducing lag spikes, and keeping you in the game with reliable wired and wireless connectivity for fast-paced gaming. Personalize your gaming dashboard to view real-time bandwidth utilization by device, ping and many other parameters. Limit lag and get a guaranteed local connection with Geo Filter or enable Quality of Service for prioritized gaming devices and allocated bandwidth. VPN Client protects your network identity and prevents DDoS attacks. Blazing-fast 2.6Gbps Wi-Fi speeds, Gigabit Ethernet ports, and a dual core 1.7GHz processor make online and network gaming frustration free.',
       298.69, 'NETGEAR Pro Gaming Nighthawk AC2600', 100, 2, 0, 0, 0, to_date('03 03 2018', 'DD MM YYYY'), 7, false);


      INSERT INTO short_description (id, attribute, product_id) values (1, '8th Generation Intel Core i3-8130U Processor (Up to 3.4GHz)', 1);
      INSERT INTO short_description (id, attribute, product_id) values (2, '15.6" Full HD (1920 x 1080) widescreen LED-lit Display', 1);
      INSERT INTO short_description (id, attribute, product_id) values (3, '6GB Dual Channel Memory, 1TB HDD & 8X DVD', 1);
      INSERT INTO short_description (id, attribute, product_id) values (4, 'Up to 13.5-hours of battery life', 1);
      INSERT INTO short_description (id, attribute, product_id) values (5, 'Windows 10 Home', 1);

      INSERT INTO short_description (id, attribute, product_id) values (6, 'AMD A6-9200 2.0 GHz Dual-core processor with 4GB DDR4 SDRAM', 2);
      INSERT INTO short_description (id, attribute, product_id) values (7, 'AMD Radeon R4 Integrated graphics chipset with shared video memory', 2);
      INSERT INTO short_description (id, attribute, product_id) values (8, '15.6" display with 1366 x 768 HD resolution with LED backlight.', 2);
      INSERT INTO short_description (id, attribute, product_id) values (9, '500GB hard drive and Built-in media reader that supports SD, SDHC and SDXC memory card formats', 2);
      INSERT INTO short_description (id, attribute, product_id) values (10, 'Windows 10 operating system', 2);

      INSERT INTO short_description (id, attribute, product_id) values (11, '15.6 inch LED Backlit Display with Truelife and HD resolution (1366 x 768)', 3);
      INSERT INTO short_description (id, attribute, product_id) values (12, 'AMD A6-6310 1.80 GHz Quad-Core Processor with Radeon R4 Graphics', 3);
      INSERT INTO short_description (id, attribute, product_id) values (13, '4GB Single Channel DDR3L 1600MHz (4GBx1), 500GB 5400 rpm SATA Hard Drive, Tray load DVD Drive (Reads and Writes to DVD/CD)', 3);
      INSERT INTO short_description (id, attribute, product_id) values (14, 'Windows 8.1 operating system (Free Upgrade to Windows 10)', 3);
      INSERT INTO short_description (id, attribute, product_id) values (15, 'Dell Wireless-N 1705 high-speed wireless LAN (802.11n), HDMI, USB 3.0 (1), USB 2.0 (2), security slot, media card reader (SD, SDHC, SDXC), non-backlit keyboard', 3);

      INSERT INTO short_description (id, attribute, product_id) values (16, 'System: Intel Core i5-8400 2.8GHz 6 Core | Intel B360 Chipset | 8GB DDR4 | 1TB HDD | Genuine Windows 10 Home 64-bit', 4);
      INSERT INTO short_description (id, attribute, product_id) values (17, 'Graphics: AMD Radeon RX 580 4GB Video Card | 1x DVI | 1x HDMI | 2x DisplayPort', 4);
      INSERT INTO short_description (id, attribute, product_id) values (18, 'Connectivity: 6 x USB 3.1 | 2 x USB 2.0 | 1x RJ-45 Network Ethernet 10/100/1000 | Audio: 7.1 Channel | Gaming Keyboard and Mouse', 4);
      INSERT INTO short_description (id, attribute, product_id) values (19, 'Special feature: 802.11AC Wi-Fi USB Adapter', 4);
      INSERT INTO short_description (id, attribute, product_id) values (20, 'Warranty: 1 Year Parts & Labor Warranty | Free Lifetime Tech Support', 4);

      INSERT INTO short_description (id, attribute, product_id) values (21, '7th Generation Intel Core i5-7400 Processor (Up to 3.5GHz)', 5);
      INSERT INTO short_description (id, attribute, product_id) values (22, '12GB DDR4 2400MHz Memory;Built to perform', 5);
      INSERT INTO short_description (id, attribute, product_id) values (23, 'Connectivity- 802.11ac wireless LAN, Gigabit LAN. Drive Interface-SATA 6 Gbps. 2TB 7200RPM SATA3 Hard Drive', 5);
      INSERT INTO short_description (id, attribute, product_id) values (24, 'Intel HD Graphics 630', 5);
      INSERT INTO short_description (id, attribute, product_id) values (25, 'Windows 10 Home', 5);

      INSERT INTO short_description (id, attribute, product_id) values (26, 'Premium 23.8" diagonal widescreen FHD IPS WLED-backlit edge-to-edge display (1920 x 1080 Resolution)',6);
      INSERT INTO short_description (id, attribute, product_id) values (27, 'Intel® Core i3-6100T processor (Dual-Core), 3.2 GHz, 3MB SmartCache 8 GB DDR4-2133 SDRAM memory (2x4 GB),', 6);
      INSERT INTO short_description (id, attribute, product_id) values (28, '8 GB DDR4-2133 SDRAM memory (2x4 GB), 1 TB 7200RPM SATA hard drive, Ultra Slim-tray SuperMulti DVD Burner', 6);
      INSERT INTO short_description (id, attribute, product_id) values (29, 'Connectivity: 4 x USB Ports (2 x USB 2.0, 1 x USB 3.0 (rear), 1 x USB 3.0 (bottom)), HDMI Out, Wireless LAN 802.11a/b/g/n/ac (1x1) and Bluetooth® 4.0 M, HP TrueVision HD webcam', 6);
      INSERT INTO short_description (id, attribute, product_id) values (30, 'Windows 10 Home Operating System, HP USB Wired Keyboard with volume control and Wired Optical Mouse, Integrated speakers', 6);

      INSERT INTO short_description (id, attribute, product_id) values (31, 'Display: 5.8-inch Quad HD + Curved Super AMOLED, 18.5:9 (570ppi)', 7);
      INSERT INTO short_description (id, attribute, product_id) values (32, 'Rear Camera: Super Speed Dual Pixel 12MP AF sensor with OIS (F1.5/F2.4)', 7);
      INSERT INTO short_description (id, attribute, product_id) values (33, 'Memory: 4GB RAM / 64GB + Micro SD Slot (up to 400 GB) Application Processor: 10nm, 64-bit, Octa-core processor (2.8 GHz Quad + 1.7 GHz Quad)', 7);
      INSERT INTO short_description (id, attribute, product_id) values (34, 'Connectivity: Wi-Fi 802.11 a/b/g/n/ac (2.4/5GHz), VHT80 MU-MIMO, 1024QAM, Bluetooth® v 5.0 (LE up to 2Mbps), ANT+, USB type-C, NFC, Location (GPS, Galileo, Glonass, BeiDou)', 7);
      INSERT INTO short_description (id, attribute, product_id) values (35, 'Biometric lock type: iris scanner, fingerprint scanner, face recognition', 7);

      INSERT INTO short_description (id, attribute, product_id) values (36, 'Hybrid Dual SIM (nano-sim, Dual stand-by), GSM only, no CDMA (won''t work with Sprint, Verizon, Boost, etc.)', 8);
      INSERT INTO short_description (id, attribute, product_id) values (37, '6.0 inch screen, 1440 x 2880 pixels, 18: 9 ratio', 8);
      INSERT INTO short_description (id, attribute, product_id) values (38, 'Android 7.1.2 (nougat), Dual 16mp+13mp rear Camera, 5MP front Camera', 8);
      INSERT INTO short_description (id, attribute, product_id) values (39, 'Octa-core (4x2. 45 GHz Kryo & 4x1. 9 GHz Kryo), 128GB ROM, 4GB RAM', 8);
      INSERT INTO short_description (id, attribute, product_id) values (40, 'Factory Unlocked international version, has no warranty', 8);

      INSERT INTO short_description (id, attribute, product_id) values (41, 'This Certified Refurbished product is certified factory refurbished, shows limited or no wear, and includes all original accessories plus a 90-day warranty.', 9);
      INSERT INTO short_description (id, attribute, product_id) values (42, 'Enjoy all media with a fast 75HZ refresh rate', 9);
      INSERT INTO short_description (id, attribute, product_id) values (43, 'Never miss a scene with 6ms', 9);
      INSERT INTO short_description (id, attribute, product_id) values (44, 'The best quality at WQHD', 9);
      INSERT INTO short_description (id, attribute, product_id) values (45, 'Crystal clear 2560x1440 resolution', 9);

      INSERT INTO short_description (id, attribute, product_id) values (46, '27" curved LED FHD monitor. OS Compatibility- Windows 10, Windows 8, Windows 7, macOS High Sierra, macOS Sierra, OS X El Capitan', 10);
      INSERT INTO short_description (id, attribute, product_id) values (47, '1800R immersive curvature (very curved)', 10);
      INSERT INTO short_description (id, attribute, product_id) values (48, 'HDMI, DisplayPort, VGA and speakers Fast response time 5ms', 10);
      INSERT INTO short_description (id, attribute, product_id) values (49, 'View angle: 178˚ (horizontal)/ 178˚ (vertical)', 10);
      INSERT INTO short_description (id, attribute, product_id) values (50, 'Fast response time 5ms', 10);


      INSERT INTO short_description (id, attribute, product_id) values (51, 'Powered by Samsung V-NAND Technology. Optimized Performance for Everyday Computing', 11);
      INSERT INTO short_description (id, attribute, product_id) values (52, 'Enhanced Performance: Sequential Read/Write speeds up to 550MB/s and 520MB/s respectively', 11);
      INSERT INTO short_description (id, attribute, product_id) values (53, 'Ideal for mainstream PCs and laptops for personal, gaming and business use', 11);
      INSERT INTO short_description (id, attribute, product_id) values (54, 'Hardware/Software Compatibility: Windows 8/Windows 7/Windows Server 2003 (32-bit and 64-bit), Vista (SP1 and above), XP (SP2 and above), MAC OSX, and Linux', 11);
      INSERT INTO short_description (id, attribute, product_id) values (55, 'Included Contents: 2.5" (7mm) SATA III (6Gb/s) SSD & User Manual (All Other Cables, Screws, Brackets Not Included). 5-Year Warranty', 11);

      INSERT INTO short_description (id, attribute, product_id) values (56, 'Hard Disk Form Factor	2.5 in', 12);
      INSERT INTO short_description (id, attribute, product_id) values (57, 'Hardware Connectivity	SATA 6.0 Gb/s', 12);
      INSERT INTO short_description (id, attribute, product_id) values (58, 'Memory Storage Capacity	240 GB', 12);
      INSERT INTO short_description (id, attribute, product_id) values (59, 'Data Transfer Rate	490 MB per second', 12);
      INSERT INTO short_description (id, attribute, product_id) values (60, 'Item Weight	2.12 ounces', 12);


      INSERT INTO short_description (id, attribute, product_id) values (61, 'Chipset: NVIDIA GeForce GT 710', 13);
      INSERT INTO short_description (id, attribute, product_id) values (62, 'Core Clock: 954 MHz', 13);
      INSERT INTO short_description (id, attribute, product_id) values (63, 'Memory Clock: 1800 MHz Effective', 13);
      INSERT INTO short_description (id, attribute, product_id) values (64, 'Bus: PCI-Express 2.0 x16', 13);
      INSERT INTO short_description (id, attribute, product_id) values (65, 'Max Resolution: 4096 x 2160, Support 3x Display Monitors', 13);

      INSERT INTO short_description (id, attribute, product_id) values (66, 'Processor Speed 4.2 GHz', 14);
      INSERT INTO short_description (id, attribute, product_id) values (67, 'Processor Count 4', 14);
      INSERT INTO short_description (id, attribute, product_id) values (68, 'Computer Memory Type	DDR4 SDRAM', 14);
      INSERT INTO short_description (id, attribute, product_id) values (69, 'Socket LGA 1151', 14);
      INSERT INTO short_description (id, attribute, product_id) values (70, 'Intel HD Graphics 630', 14);

      INSERT INTO short_description (id, attribute, product_id) values (71, 'AC1900 WiFi—600+1300 Mbps speeds', 15);
      INSERT INTO short_description (id, attribute, product_id) values (72, '1GHz Dual Core Processor', 15);
      INSERT INTO short_description (id, attribute, product_id) values (73, 'Ideal for homes with 12 or more WiFi devices', 15);
      INSERT INTO short_description (id, attribute, product_id) values (74, 'Advanced features for lag-free gaming', 15);
      INSERT INTO short_description (id, attribute, product_id) values (75, 'Prioritized bandwidth for gaming, streaming videos, or music. 1 USB 2.0 and 1 USB 3.0 Port', 15);

      INSERT INTO short_description (id, attribute, product_id) values (76, 'QoS—Prioritize gaming devices and allocate bandwidth by device to eliminate lag due to queuing at the ISP network.', 16);
      INSERT INTO short_description (id, attribute, product_id) values (77, 'Geo Filter—Fix your gaming lag by limiting distance. Get a guaranteed local connection. Create a blacklist or whitelist of your preferred servers', 16);
      INSERT INTO short_description (id, attribute, product_id) values (78, 'Gaming Dashboard—Personalize dashboard to view real-time bandwidth utilization by device, ping delays and more on a single screen.', 16);
      INSERT INTO short_description (id, attribute, product_id) values (79, 'Gaming VPN—Connect to the VPN server with a VPN client to protect your network identity.', 16);
      INSERT INTO short_description (id, attribute, product_id) values (80, 'Network Monitor—Check bandwidth utilization by device and application.', 16);



      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 1,  1, localtimestamp);
      INSERT INTO  product_bucket (bucket_id, product_id, amount, date_added) VALUES (1, 2,  2, localtimestamp);


      INSERT INTO status (id, code, name) VALUES (1, 'booked', 'Order was booked');
      INSERT INTO status (id, code, name) VALUES (2, 'paid', 'Transaction confirmed');
      INSERT INTO status (id, code, name) VALUES (3, 'prepared', 'Order was prepared and is ready to send ');
      INSERT INTO status (id, code, name) VALUES (4, 'send', 'Order was send check status delivery in link');
      INSERT INTO status (id, code, name) VALUES (5, 'delivered', 'Order was delivered to client');

     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (1, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (2, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (3, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (4, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (5, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (6, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (7, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (8, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (9, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (12, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (10, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);
     INSERT INTO shipping_address (id, city, country, name, post_code, street, supplier, surname, county, district, state, subdistrict, apartment, house) VALUES (11, 'Warsaw', 'Poland', 'John', '00-100', 'Street', 'InPost', 'Smith', 'County', 'District', 'State', 'Subdistrict', 2, 2);



      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (1, localtimestamp, 'link_delivery', localtimestamp, 2, 893.99, 1, 4, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (2, localtimestamp, 'link_delivery', localtimestamp, 2, 858, 1, 4, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (3, localtimestamp, 'link_delivery', localtimestamp, 2, 1043, 2, 4, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (4, localtimestamp, 'link_delivery', 2, 1178.99, 3, 3, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (5, localtimestamp, 'link_delivery', 2, 1124.99, 4, 2, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (6, localtimestamp, 'link_delivery', 2, 1278.16, 5, 1, 1);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (7, localtimestamp, 'link_delivery', localtimestamp, 2, 1232.16, 6, 4, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, send_date, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (8, localtimestamp, 'link_delivery', localtimestamp, 2, 956, 7, 4, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (9, localtimestamp, 'link_delivery', 2, 519.99, 8, 3, 2);
      INSERT INTO products_order (id, bought_date, link_delivery, total_amount, total_value, shipping_address_id, status_id, user_id) VALUES (10, localtimestamp, 'link_delivery', 2, 539.98, 1, 2, 2);

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
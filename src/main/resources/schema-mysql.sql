DROP TABLE IF EXISTS Price;
DROP TABLE IF EXISTS stocks;
CREATE TABLE Price (
id INTEGER NOT NULL AUTO_INCREMENT,
stock_id INTEGER NOT NULL,
price double NOT NULL,
updated_time_stamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (id));
CREATE TABLE stocks (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_name` varchar(255) DEFAULT NULL,
  `stock_code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_name_UNIQUE` (`stock_name`),
  UNIQUE KEY `stock_code_UNIQUE` (`stock_code`)
);
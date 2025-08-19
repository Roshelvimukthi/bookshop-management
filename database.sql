CREATE DATABASE pahana_edu;

USE pahana_edu;

CREATE TABLE users (
   username VARCHAR(50) PRIMARY KEY,
   password VARCHAR(50) NOT NULL
);

CREATE TABLE customers (
                           account_number VARCHAR(10) PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           address VARCHAR(255) NOT NULL,
                           phone VARCHAR(15) NOT NULL,
);

CREATE TABLE items (
                       item_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL
);
CREATE TABLE bills (
                       bill_id INT AUTO_INCREMENT PRIMARY KEY,
                       account_number VARCHAR(50),
                       total_amount DECIMAL(10,2),
                       bill_date DATETIME
);

-- CREATE TABLE bills (
--                        bill_id INT AUTO_INCREMENT PRIMARY KEY,
--                        account_number VARCHAR(10),
--                        units_consumed INT NOT NULL,
--                        total_amount DECIMAL(10, 2) NOT NULL,
--                        bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--                        FOREIGN KEY (account_number) REFERENCES customers(account_number)
-- );
-- -- CREATE TABLE bills (
--                        bill_id INT AUTO_INCREMENT PRIMARY KEY,
--                        account_number VARCHAR(10),
--                        units_consumed INT NOT NULL,
--                        total_amount DECIMAL(10, 2) NOT NULL,
--                        bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--                        FOREIGN KEY (account_number) REFERENCES customers(account_number)
-- );
INSERT INTO users (username, password) VALUES ('admin', 'admin123');
CREATE TABLE IF NOT EXISTS ingredient (
	id VARCHAR(4) NOT NULL,
	name VARCHAR(25) NOT NULL,
	type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS taco (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	createAt TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_ingredients (
	taco BIGINT NOT NULL,
	ingredient VARCHAR(4) NOT NULL
);

ALTER TABLE taco_ingredients ADD FOREIGN KEY (taco) REFERENCES taco(id);
ALTER TABLE taco_ingredients ADD FOREIGN KEY (ingredient) REFERENCES ingredient(id);

CREATE TABLE IF NOT EXISTS taco_order (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	deliveryName VARCHAR(50) NOT NULL,
	deliveryStreet VARCHAR(50) NOT NULL,
	deliveryCity VARCHAR(50) NOT NULL,
	deliveryState VARCHAR(2) NOT NULL,
	deliveryZip VARCHAR(10) NOT NULL,
	ccNumber VARCHAR(16) NOT NULL,
	ccExpiration VARCHAR(5) NOT NULL,
	ccCVV VARCHAR(3) NOT NULL,
	placedAt TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_order_tacos(
	tacoOrder BIGINT NOT NULL,
	taco BIGINT NOT NULL
);

ALTER TABLE taco_order_tacos ADD FOREIGN KEY (tacoOrder) REFERENCES taco_order(id);
ALTER TABLE taco_order_tacos ADD FOREIGN KEY (taco) REFERENCES taco(id);

-- Spring Security

CREATE TABLE users(
    username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
    password VARCHAR_IGNORECASE(50) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR_IGNORECASE(50) NOT NULL,
    authority VARCHAR_IGNORECASE(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);
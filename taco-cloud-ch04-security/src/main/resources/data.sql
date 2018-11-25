DELETE FROM taco_order_tacos;
DELETE FROM taco_ingredients;
DELETE FROM taco;
DELETE FROM taco_order;
DELETE FROM ingredient;
INSERT INTO ingredient (id, name, type) VALUES 
('FLTO', 'Flour Tortilla', 'WRAP'),
('COTO', 'Corn Tortilla', 'WRAP'),
('GRBF', 'Ground Beef', 'PROTEIN'),
('CARN', 'Carnitas', 'PROTEIN'),
('TMTO', 'Diced Tomatoes', 'VEGGIES'),
('LETC', 'Lettuce', 'VEGGIES'),
('CHED', 'Cheddar', 'CHEESE'),
('JACK', 'Monterrey Jack', 'CHEESE'),
('SLSA', 'Salsa', 'SAUCE'),
('SRCR', 'Sour Cream', 'SAUCE');

INSERT INTO users (username, password, enabled) VALUES
('jxxchallenger', '123456', TRUE);

INSERT INTO authorities (username, authority) VALUES
('jxxchallenger', 'ROLE_ADMIN'),
('jxxchallenger', 'ROLE_USER');
delete from ingredient_ref;
delete from ingredient;
delete from taco;
delete from taco_order;

insert into ingredient (id, name, type) values ('FLTO', 'Flour Tortilla', 'WRAP');
insert into ingredient (id, name, type) values ('COTO', 'Corn Tortilla', 'WRAP');
insert into ingredient (id, name, type) values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into ingredient (id, name, type) values ('CARN', 'Carnitas', 'PROTEIN');
insert into ingredient (id, name, type) values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
insert into ingredient (id, name, type) values ('LETC', 'Lettuce', 'VEGGIES');
insert into ingredient (id, name, type) values ('CHED', 'Cheddar', 'CHEESE');
insert into ingredient (id, name, type) values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into ingredient (id, name, type) values ('SLSA', 'Salsa', 'SAUCE');
insert into ingredient (id, name, type) values ('SRCR', 'Sour Cream', 'SAUCE');

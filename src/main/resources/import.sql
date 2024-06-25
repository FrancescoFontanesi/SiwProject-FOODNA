insert into recipe (id,name,main_image_path) values(nextval('recipe_seq'),'Pollo alle mandorle', '/images/pollo.jpg');
insert into recipe (id,name,main_image_path) values(nextval('recipe_seq'),'Pollo alle mandorle2', '/images/pollo.jpg');
insert into recipe (id,name,main_image_path) values(nextval('recipe_seq'),'Pollo alle mandorle3', '/images/pollo.jpg');
insert into recipe (id,name,main_image_path) values(nextval('recipe_seq'),'Pollo alle mandorle4', '/images/pollo.jpg');
insert into recipe (id,name,main_image_path) values(nextval('recipe_seq'),'Pollo alle mandorle5', '/images/pollo.jpg');
insert into recipe (id,name,main_image_path) values(nextval('recipe_seq'),'Pollo alle mandorle6', '/images/pollo.jpg');




-- Insert users
INSERT INTO users (id, name, surname, email, password, birth_date, role) values (nextval('users_seq'), 'John', 'Doe', 'john.doe@example.com', 'password', '1990-01-01', 'COOK');


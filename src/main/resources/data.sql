INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Jozsi', 'a@a', '$2a$04$nFSEioczBFIqwrdgN0XDo.4QFNqXW/QVNcmmkIAdNxIRYzYtJVJDe', false, 23, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Pista', 'b@b', '$2a$04$RcgzUDZmonRv03dd/WnXtunAOt/X2HKna5/SlbZZw.IUm2gY5fuKm', false, 34, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Sanyi', 'c@c', '$2a$04$3JBos6Gl5/Wb80OESOC.f.HbEdfIwQoV6vgxXv9GUxfR1Ld.yNZCq', false, 32, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Károly', 'd@d', '$2a$04$kpAB7knEnM6TsTU9sGE/XObY9UYcpuN739rW3tV2K3vnyHumGCXUO', false, 25, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Géza', 'e@e', '$2a$04$o8mbzKXx4NSduwgm2PW00OCWJPx7Z3LZuZsbA.cUWt48BMEkdSO2O', false, 26, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Elemér', 'f@f', '$2a$04$DCDuACUT./6r3F5vwH5S8uB8AqxC2o1LSH8arg2zB1H2v4FEXdg4i', false, 41, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Admin', 'admin@admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', false, 41, 'Male', 'admin to test', 'Budapest', true);

INSERT INTO authorities (username, authority)
VALUES ('a@a', 'ROLE_USER'),
('b@b', 'ROLE_USER'),
('c@c', 'ROLE_USER'),
('d@d', 'ROLE_USER'),
('e@e', 'ROLE_USER'),
('f@f', 'ROLE_USER'),
('admin@admin', 'ROLE_ADMIN');

INSERT INTO public.rent_ads(
	user_id, country, state, city, zip_code, district, street, description, cost, size, type, is_furnitured, rooms_available, is_premium, published_date, expiration_date, is_enabled)
	VALUES (1, 'Hungary', 'Pest_megye', 'Budapest', '1117', 'XI', 'Budafoki', 'Awesome', 5000, 10, 'flat', true, 5, true, '2001-10-10', '2022-10-10', true);

INSERT INTO public.rent_ads(
	user_id, country, state, city, zip_code, district, street, description, cost, size, type, is_furnitured, rooms_available, is_premium, published_date, expiration_date, is_enabled)
	VALUES (1, 'Hungary', 'Pest-Megye', 'Budapest', '1034', 'VIII. district', 'Lakatos street', 'A nice flat with a good view', 150000, 4, 'flat', true, 3, false, '2001-10-10', '2022-10-10', true);

INSERT INTO public.rent_ads(
	user_id, country, state, city, zip_code, district, street, description, cost, size, type, is_furnitured, rooms_available, is_premium, published_date, expiration_date, is_enabled)
	VALUES (1, 'Hungary', 'B-A-Z megye', 'Szikszó', '3800', '', 'Csokonai street', 'Perfect choice', 15000, 2, 'flat', true, 3, false, '2001-10-10', '2022-10-10', true);

INSERT INTO public.rent_ads(
	user_id, country, state, city, zip_code, district, street, description, cost, size, type, is_furnitured, rooms_available, is_premium, published_date, expiration_date, is_enabled)
	VALUES (1, 'Hungary', 'B-A-Z megye', 'Miskolc', '3800', '', 'József Attila street', 'Long street with a lot of parks', 15000, 2, 'flat', true, 3, false, '2001-10-10', '2022-10-10', true);

INSERT INTO public.rent_ads(
	user_id, country, state, city, zip_code, district, street, description, cost, size, type, is_furnitured, rooms_available, is_premium, published_date, expiration_date, is_enabled)
	VALUES (1, 'Hungary', 'B-A-Z megye', 'Tapolca', '3800', '', 'Bogdanfy street', 'Some description', 15000, 2, 'flat', true, 3, false, '2001-10-10', '2022-10-10', true);

INSERT INTO public.user_pictures(
	user_id, path)
	VALUES (1, 'edd/ki/a/vetest.jpg');

INSERT INTO public.messages(
	sender_id, receiver_id, content, date)
	VALUES (1, 2, 'helo', '2018-07-10');

INSERT INTO public.messages(
	sender_id, receiver_id, content, date)
	VALUES (1, 2, 'szia', '2018-07-09');

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (1, 2, 2, true);

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (2, 1, 2, true);

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (2, 3, 1, true);

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (3, 2, 3, true);

INSERT INTO public.rent_slots(
	rentad_id, renter_id)
	VALUES (1, 1), (1, 2);


/*Test Matches*/
INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (1, 3, 3, true);

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (3, 1, 1, true);

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (1, 4, 1, true);

INSERT INTO public.matches(
	user_a, user_b, status, is_enabled)
	VALUES (4, 1, 3, true);

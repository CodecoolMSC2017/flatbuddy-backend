INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Jozsi', 'a@a', 'a', false, 23, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Pista', 'b@b', 'b', false, 34, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Sanyi', 'c@c', 'c', false, 32, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Károly', 'd@d', 'd', false, 25, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Géza', 'e@e', 'e', false, 26, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Elemér', 'f@f', 'f', false, 41, 'Male', 'asaasdafsad', 'Budapest', true);

INSERT INTO public.users(
	first_name, last_name, username, password, is_flatmate, age, gender, description, destination, enabled)
	VALUES ('Test', 'Admin', 'admin@admin', 'admin', false, 41, 'Male', 'admin to test', 'Budapest', true);

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
	VALUES (1, 'Tadzsikisztán', 'Fityma', 'Anyam', 'It', 'van', 'galab', 'imadom', 5000, 10, 'posta', true, 5, true, '2001-10-10', '2022-10-10', true);

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
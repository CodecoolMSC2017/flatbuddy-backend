INSERT INTO public.users(
	first_name, last_name, email, password, role, is_flatmate, age, gender, description, destination)
	VALUES ('Test', 'Jozsi', 'a@a', 'asdasd', 'User', false, 23, 'Male', 'asaasdafsad', 'Budapest');

INSERT INTO public.users(
	first_name, last_name, email, password, role, is_flatmate, age, gender, description, destination)
	VALUES ('Test', 'Pista', 'b@b', 'asdasd', 'User', false, 34, 'Male', 'asaasdafsad', 'Budapest');

INSERT INTO public.users(
	first_name, last_name, email, password, role, is_flatmate, age, gender, description, destination)
	VALUES ('Test', 'Sanyi', 'c@c', 'asdasd', 'User', false, 32, 'Male', 'asaasdafsad', 'Budapest');

INSERT INTO public.users(
	first_name, last_name, email, password, role, is_flatmate, age, gender, description, destination)
	VALUES ('Test', 'Károly', 'd@d', 'asdasd', 'User', false, 25, 'Male', 'asaasdafsad', 'Budapest');

INSERT INTO public.users(
	first_name, last_name, email, password, role, is_flatmate, age, gender, description, destination)
	VALUES ('Test', 'Géza', 'e@e', 'asdasd', 'User', false, 26, 'Male', 'asaasdafsad', 'Budapest');

INSERT INTO public.users(
	first_name, last_name, email, password, role, is_flatmate, age, gender, description, destination)
	VALUES ('Test', 'Elemér', 'f@f', 'asdasd', 'User', false, 41, 'Male', 'asaasdafsad', 'Budapest');

INSERT INTO public.rent_ads(
	user_id, country, state, city, zip_code, district, street, description, cost, size, type, is_furnitured, rooms_available, is_premium, published_date, expiration_date)
	VALUES (1, 'Tadzsikisztán', 'Fityma', 'Anyam', 'It', 'van', 'galab', 'imadom', 5000, 10, 'posta', true, 5, true, '2001-10-10', '2022-10-10');

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
	user_a, user_b, status)
	VALUES (1, 2, 2);

INSERT INTO public.matches(
	user_a, user_b, status)
	VALUES (2, 1, 2);

INSERT INTO public.matches(
	user_a, user_b, status)
	VALUES (2, 3, 1);

INSERT INTO public.matches(
	user_a, user_b, status)
	VALUES (3, 2, 3);


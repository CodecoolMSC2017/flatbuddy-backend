ALTER TABLE Rent_ads DROP CONSTRAINT IF EXISTS Rent_ads_fk0;

ALTER TABLE Rent_slots DROP CONSTRAINT IF EXISTS Rent_slots_fk0;

ALTER TABLE Rent_slots DROP CONSTRAINT IF EXISTS Rent_slots_fk1;

ALTER TABLE Matches DROP CONSTRAINT IF EXISTS Matches_fk0;

ALTER TABLE Matches DROP CONSTRAINT IF EXISTS Matches_fk1;

ALTER TABLE Ad_pictures DROP CONSTRAINT IF EXISTS Ad_pictures_fk0;

ALTER TABLE User_pictures DROP CONSTRAINT IF EXISTS User_pictures_fk0;

ALTER TABLE Notifications DROP CONSTRAINT IF EXISTS Notifications_fk0;

ALTER TABLE Messages DROP CONSTRAINT IF EXISTS Messages_fk0;

ALTER TABLE Messages DROP CONSTRAINT IF EXISTS Messages_fk1;

ALTER TABLE Subscriptions DROP CONSTRAINT IF EXISTS Subscriptions_fk0;

DROP TABLE IF EXISTS Users;

DROP TABLE IF EXISTS Rent_ads;

DROP TABLE IF EXISTS Rent_slots;

DROP TABLE IF EXISTS Matches;

DROP TABLE IF EXISTS Ad_pictures;

DROP TABLE IF EXISTS User_pictures;

DROP TABLE IF EXISTS Notifications;

DROP TABLE IF EXISTS Messages;

DROP TABLE IF EXISTS Subscriptions;

CREATE TABLE Users (
	id serial NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL,
	password TEXT NOT NULL,
	role TEXT NOT NULL,
	is_flatmate BOOLEAN NOT NULL,
	age integer,
	gender TEXT,
	description TEXT,
	destination TEXT,
	CONSTRAINT Users_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Rent_ads (
	id serial NOT NULL,
	user_id integer NOT NULL,
	country TEXT NOT NULL,
	state TEXT NOT NULL,
	city TEXT NOT NULL,
	zip_code TEXT NOT NULL,
	district TEXT,
	street TEXT NOT NULL,
	description TEXT NOT NULL,
	cost integer NOT NULL,
	size integer NOT NULL,
	type TEXT NOT NULL,
	is_furnitured BOOLEAN NOT NULL,
	rooms_available integer NOT NULL,
	is_premium BOOLEAN NOT NULL,
	published_date DATE NOT NULL,
	expiration_date DATE NOT NULL,
	CONSTRAINT Rent_ads_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Rent_slots (
	id serial NOT NULL,
	rentad_id integer NOT NULL,
	renter_id integer,
	CONSTRAINT Rent_slots_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Matches (
	id serial NOT NULL,
	user_a integer NOT NULL,
	user_b integer NOT NULL,
	is_matched BOOLEAN NOT NULL,
	CONSTRAINT Matches_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Ad_pictures (
	id serial NOT NULL,
	ad_id integer NOT NULL,
	path VARCHAR(255) NOT NULL,
	CONSTRAINT Ad_pictures_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE User_pictures (
	id serial NOT NULL,
	user_id integer NOT NULL,
	path VARCHAR(255) NOT NULL,
	CONSTRAINT User_pictures_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Notifications (
	id serial NOT NULL,
	receiver_id integer NOT NULL,
	content TEXT NOT NULL,
	is_seen BOOLEAN NOT NULL,
	date DATE NOT NULL,
	type TEXT NOT NULL,
	CONSTRAINT Notifications_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Messages (
	id serial NOT NULL,
	sender_id integer NOT NULL,
	receiver_id integer NOT NULL,
	content TEXT NOT NULL,
	date DATE NOT NULL,
	CONSTRAINT Messages_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Subscriptions (
	id serial NOT NULL,
	ad_id integer NOT NULL,
	date DATE NOT NULL,
	CONSTRAINT Subscriptions_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);




ALTER TABLE Rent_ads ADD CONSTRAINT Rent_ads_fk0 FOREIGN KEY (user_id) REFERENCES Users(id);

ALTER TABLE Rent_slots ADD CONSTRAINT Rent_slots_fk0 FOREIGN KEY (rentad_id) REFERENCES Rent_ads(id);
ALTER TABLE Rent_slots ADD CONSTRAINT Rent_slots_fk1 FOREIGN KEY (renter_id) REFERENCES Users(id);

ALTER TABLE Matches ADD CONSTRAINT Matches_fk0 FOREIGN KEY (user_a) REFERENCES Users(id);
ALTER TABLE Matches ADD CONSTRAINT Matches_fk1 FOREIGN KEY (user_b) REFERENCES Users(id);

ALTER TABLE Ad_pictures ADD CONSTRAINT Ad_pictures_fk0 FOREIGN KEY (ad_id) REFERENCES Rent_ads(id);

ALTER TABLE User_pictures ADD CONSTRAINT User_pictures_fk0 FOREIGN KEY (user_id) REFERENCES Users(id);

ALTER TABLE Notifications ADD CONSTRAINT Notifications_fk0 FOREIGN KEY (receiver_id) REFERENCES Users(id);

ALTER TABLE Messages ADD CONSTRAINT Messages_fk0 FOREIGN KEY (sender_id) REFERENCES Users(id);
ALTER TABLE Messages ADD CONSTRAINT Messages_fk1 FOREIGN KEY (receiver_id) REFERENCES Users(id);

ALTER TABLE Subscriptions ADD CONSTRAINT Subscriptions_fk0 FOREIGN KEY (ad_id) REFERENCES Rent_ads(id);

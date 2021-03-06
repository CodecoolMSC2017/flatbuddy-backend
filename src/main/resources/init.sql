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

ALTER TABLE Advertisement_comments DROP CONSTRAINT IF EXISTS Advertisement_comments_fk0;
ALTER TABLE Advertisement_comments DROP CONSTRAINT IF EXISTS Advertisement_comments_fk1;


DROP TABLE IF EXISTS authorities;

DROP TABLE IF EXISTS Rent_ads;

DROP TABLE IF EXISTS Rent_slots;

DROP TABLE IF EXISTS Users;

DROP TABLE IF EXISTS Matches;

DROP TABLE IF EXISTS Ad_pictures;

DROP TABLE IF EXISTS User_pictures;

DROP TABLE IF EXISTS Notifications;

DROP TABLE IF EXISTS Messages;

DROP TABLE IF EXISTS Subscriptions;

DROP TABLE IF EXISTS Advertisement_comments;

CREATE TABLE Users (
	id serial NOT NULL,
	first_name TEXT NULL,
	last_name TEXT NULL,
	username varchar(50) not null unique,
	password varchar(60),
	is_flatmate BOOLEAN DEFAULT FALSE,
	age integer NULL,
	gender TEXT NULL,
	description TEXT NULL,
	destination TEXT NULL,
	enabled boolean NOT NULL DEFAULT TRUE,
	CONSTRAINT Users_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references Users (username),
    unique (username, authority)
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
	is_enabled BOOLEAN DEFAULT TRUE NOT NULL,
	is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
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
	status integer NOT NULL,
	is_enabled BOOLEAN DEFAULT TRUE NOT NULL,
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
	date TIMESTAMP NOT NULL,
	type TEXT NOT NULL,
	id_of_subject integer,
	CONSTRAINT Notifications_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Messages (
	id serial NOT NULL,
	sender_id integer NOT NULL,
	receiver_id integer NOT NULL,
	sender_name TEXT NOT NULL,
	receiver_name TEXT NOT NULL,
	subject TEXT NOT NULL,
	content TEXT NOT NULL,
	date TIMESTAMP NOT NULL,
	is_enabled_to_receiver BOOLEAN NOT NULL DEFAULT TRUE,
	is_enabled_to_sender BOOLEAN NOT NULL DEFAULT TRUE,
	is_seen BOOLEAN NOT NULL DEFAULT FALSE,
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

CREATE TABLE Advertisement_comments (
	id serial NOT NULL,
	ad_id integer NOT NULL,
	user_id integer NOT NULL,
	user_comment TEXT NOT NULL,
	comment_type integer NOT NULL,
	date DATE NOT NULL,
	CONSTRAINT Advertisement_comments_pk PRIMARY KEY (id)
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

ALTER TABLE Advertisement_comments ADD CONSTRAINT Advertisement_comments_fk0 FOREIGN KEY (ad_id) REFERENCES Rent_ads(id);
ALTER TABLE Advertisement_comments ADD CONSTRAINT Advertisement_comments_fk1 FOREIGN KEY (user_id) REFERENCES Users(id);


BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Admin" (
	"id"	INTEGER,
	"apothecary_name"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"second_name"	TEXT,
	"username"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	"doctor_first_name"	TEXT,
	"doctor_second_name"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Allergies" (
	"id"	INTEGER,
	"user_id"	INTEGER,
	"allergy"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("user_id") REFERENCES User("id")
);
CREATE TABLE IF NOT EXISTS "Apothecary" (
	"id"	INTEGER,
	"name"	TEXT,
	"email"	TEXT,
	"address"	TEXT,
	"contact_phone"	TEXT,
	"total_profit"	NUMERIC,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Drug" (
	"id"	INTEGER,
	"name_bosnian"	TEXT,
	"name_english"	TEXT,
	"name_latin"	TEXT,
	"content"	TEXT,
	"purpose"	TEXT,
	"expiration_date"	TEXT,
	"administration_type" INTEGER,
	"picture"	BLOB,
	"price"	REAL,
	"apothecary_id"	INTEGER,
	PRIMARY KEY("id")
	FOREIGN KEY("apothecary_id") REFERENCES Apothecary("id")
);
COMMIT;

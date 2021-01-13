INSERT INTO USERS (type, password, username, email, phone_number,enabled) VALUES ('ROLE_ADMIN',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','admin@nesto.com', 'admin@nesto.com', 12345,true); --sifra je 1
INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor1@nesto.com', 'kor1@nesto.com','Pera', 'Peric', true); --sifra je 1
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor2@nesto.com', 'kor2@nesto.com','Pera', 'Peric', true); --sifra je 1


insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_GUEST
insert into user_authority (user_id, authority_id) values (3, 2); -- user has ROLE_GUEST


INSERT INTO CATEGORY(name) values ('Institucija');
INSERT INTO SUBCATEGORY(name, category_id) values ('Muzej',1);
INSERT INTO SUBCATEGORY(name, category_id) values ('Pozoriste',1);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 21.895841, 43.320833,0,'Srpsko narodno pozoriste',2, 'Nis');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis madjarskog pozorista', 19.844722, 45.255,0,'Madjarsko pozoriste',2, 'Novi Sad');


INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/offerImage58624.jpg', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 2);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 2);


INSERT INTO CATEGORY(name) values ('Manifestacija');
INSERT INTO SUBCATEGORY(name, category_id) values ('Festival',2);
INSERT INTO SUBCATEGORY(name, category_id) values ('Sajam',2);

INSERT INTO CATEGORY(name) values ('Kulturno dobro');
INSERT INTO SUBCATEGORY(name, category_id) values ('Spomenik',3);
INSERT INTO SUBCATEGORY(name, category_id) values ('Znamenitost',3);

INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Exit festivala', 21.895841, 43.320833, 0,'Exit festival',3, 'Nis');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis nekog niskog festivala', 20.45938, 44.81578, 0,'Neki niski festival',3, 'Beograd');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 19.84102, 43.85581, 0,'Srpsko narodno pozoriste',2, 'Uzice');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 20.683333, 43.716667, 0,'Srpsko narodno pozoriste',2, 'Kraljevo');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 21.176166, 44.609166, 0,'Srpsko narodno pozoriste',2, 'Pozarevac');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis',  21.2595, 43.9796, 0,'Srpsko narodno pozoriste',2, 'Jagodina');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 20.2922, 42.66, 0,'Srpsko narodno pozoriste',2, 'Pec');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 20.86644, 42.89028, 0, 'Srpsko narodno pozoriste',2, 'Kosovska Mitrovica');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Petrovaradina', 19.66368, 46.1001, 0,'Petrovaradin',2, 'Subotica');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Muzeja Vojvodine', 20.74042, 42.21582,0,'Muzej Vojvodine',1, 'Prizren');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Kalemegdana', 20.4612, 45.82572,0,'Kalemegdan',6, 'Kikinda');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis spomenika neznanom junaku', 20.390551, 45.382233,0,'Spomenik neznanom junaku',5, 'Zrenjanin');




INSERT INTO SUBCATEGORY(name, category_id) values ('NAJNOVIJI',3);

INSERT INTO CATEGORY(name) values ('NAJNOVIJA');

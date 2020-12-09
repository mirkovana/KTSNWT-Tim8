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
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/offerImage59012.jpg', 1);
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

INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2);


INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 2);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 2);


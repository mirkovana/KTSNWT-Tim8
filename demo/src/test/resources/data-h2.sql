INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

DELETE FROM authority;
DELETE FROM USER_OFFER;
DELETE FROM USERS;
DELETE FROM OFFER_IMAGE;
DELETE FROM OFFER;
DELETE FROM SUBCATEGORY;
DELETE FROM CATEGORY;
--DELETE FROM authority;


INSERT INTO CATEGORY(name, id) values ('asdasdasdasdasdasd', 1L);
INSERT INTO SUBCATEGORY(name, category_id, id) values ('Muzej', 1L, 1L);

INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place, id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',1L, 'Novi Sad', 1L);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place, id) VALUES (0,'Opis madjarskog pozorista', 40,40,0,'Madjarsko pozoriste',1L, 'Novi Sad', 2L);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place, id) VALUES (0,'Opis  pozorista', 40,40,0,'Madjarsko pozoriste',1L, 'Novi Sad', 3L);

INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 1L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 2L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 3L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 4L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 5L);
INSERT INTO USERS (type, password, username, email, name, surname, enabled, id) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor1@nesto.com', 'kor1@nesto.com','Pera', 'Peric', true, 1L); --sifra je 1

INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

INSERT INTO USERS (type, password, username, email, phone_number,enabled, id) VALUES ('ROLE_ADMIN',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','admin@nesto.com', 'admin@nesto.com', 12345,true, 2L); --sifra je 1

INSERT INTO USER_OFFER (offer_id, user_id) values (1L, 1L);
INSERT INTO USER_OFFER (offer_id, user_id) values (3L, 1L);



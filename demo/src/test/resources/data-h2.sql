INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

DELETE FROM OFFER_IMAGE;
DELETE FROM OFFER;
DELETE FROM SUBCATEGORY;
DELETE FROM CATEGORY;


INSERT INTO CATEGORY(name, id) values ('asdasdasdasdasdasd', 1L);
INSERT INTO SUBCATEGORY(name, category_id, id) values ('Muzej', 1L, 1L);

INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place, id) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',1L, 'Novi Sad', 1L);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place, id) VALUES (0,'Opis madjarskog pozorista', 40,40,0,'Madjarsko pozoriste',1L, 'Novi Sad', 2L);

INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 1L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 2L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 3L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 4L);
INSERT INTO OFFER_IMAGE(description, path, offer_id, id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1L, 5L);



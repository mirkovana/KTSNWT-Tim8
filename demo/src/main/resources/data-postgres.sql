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
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.9,'ovo jesadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheri', 21.897222, 42.554167, 400,'Srpsko narodno pozoriste',2, 'Vranje');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.6,'Opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e pozorista', 19.844722, 45.255, 1200,'Madjarsko pozoriste',2, 'Novi Sad');


INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/offerImage58820.jpg', 1);
INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/petrovaradin.jpg', 1);
INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/snp.jpg', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('Nova predstava','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('Pop Cira i pop Spira','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('Cica Gorio','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('Cekajuci Godoa','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('ovo je neki post','2020-5-1 12:00:00', 'nova predstava', 1);
INSERT INTO POST(content, date, title, offer_id) VALUES ('Viszont','2020-5-1 12:00:00', 'nova predstava', 2);
INSERT INTO POST(content, date, title, offer_id) VALUES ('Lataszro','2020-5-1 12:00:00', 'nova predstava', 2);


INSERT INTO CATEGORY(name) values ('Manifestacija');
INSERT INTO SUBCATEGORY(name, category_id) values ('Festival',2);
INSERT INTO SUBCATEGORY(name, category_id) values ('Sajam',2);

INSERT INTO CATEGORY(name) values ('Kulturno dobro');
INSERT INTO SUBCATEGORY(name, category_id) values ('Spomenik',3);
INSERT INTO SUBCATEGORY(name, category_id) values ('Znamenitost',3);

INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.3,'Opis Exit festivala sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e ', 21.895841, 43.320833, 230,'Exit festival',3, 'Nis');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (1.3,'Opis nekog niskog festivalasadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e', 20.45938, 44.81578, 130,'Beograd',3, 'Beograd');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.5,'ovo je neki opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e', 19.84102, 43.85581, 200,'Uzice',2, 'Uzice');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.2,'ovo je neki opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e', 20.683333, 43.716667, 3500,'Kraljevo',2, 'Kraljevo');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.5,'ovo je neki sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e', 21.176166, 44.609166, 320,'Pozarevacko',2, 'Pozarevac');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (2.3,'ovo je neki sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e',  21.2595, 43.9796, 133,'pozoriste jagodina',2, 'Jagodina');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (3.4,'ovo je neki opsadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi eis', 20.2922, 42.66, 135,'Pecka patrijarsija ',2, 'Pec');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (2.7,'ovo je sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e opis', 20.86644, 42.89028, 400, 'Kosovska Mitrovica pozoriste',2, 'Kosovska Mitrovica');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.6,'Opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esa', 19.66368, 46.1001, 50,'Subotica',2, 'Subotica');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (3.9,'Opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e Vojvodine', 20.74042, 42.21582, 220,'Prizren',1, 'Prizren');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (2.7,'Opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e', 20.4612, 45.82572, 245,'Kikinda',6, 'Kikinda');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.6,'Opis spomenika sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e junaku', 20.390551, 45.382233, 2300,'Spomenik neznanom junaku',5, 'Zrenjanin');

INSERT INTO USER_OFFER (user_id, offer_id) values (2, 1);



INSERT INTO SUBCATEGORY(name, category_id) values ('NAJNOVIJI',3);

INSERT INTO CATEGORY(name) values ('NAJNOVIJA');

INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', 'src/main/resources/images/petrovaradin.jpg', 'Comment text 1', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Some other text', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Just sampling', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', 'src/main/resources/images/commentpicture76081.jpg', 'Great', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 2', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 3', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 4', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 5', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 6', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Great service!', 1, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 7', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 8', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 9', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 10', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 11', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Beautiful', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 13', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 14', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 115', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 16', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 17', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 18', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 19', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 120', 2, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 17', 3, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 18', 4, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 19', 4, 2);
INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', null, 'Comment text 120', 4, 2);

INSERT INTO RATING(offer_id, user_id, rating) VALUES (1, 2, 4);

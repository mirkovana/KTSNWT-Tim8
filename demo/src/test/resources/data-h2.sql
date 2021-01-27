INSERT INTO USERS (type, password, username, email, phone_number,enabled) VALUES ('ROLE_ADMIN',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','admin@nesto.com', 'admin@nesto.com', 12345,true); --sifra je 1
INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor1@nesto.com', 'kor1@nesto.com','Pera', 'Peric', true); --sifra je 1
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor2@nesto.com', 'kor2@nesto.com','Pera', 'Peric', true); --sifra je 1
INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor3@nesto.com', 'kor3@nesto.com','Pera', 'Peric', true); --sifra je 1


insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_GUEST
insert into user_authority (user_id, authority_id) values (3, 2); -- user has ROLE_GUEST
insert into user_authority (user_id, authority_id) values (4, 2); -- user has ROLE_GUEST



INSERT INTO CATEGORY(name) values ('Institucija');
INSERT INTO SUBCATEGORY(name, category_id) values ('Muzej',1);
INSERT INTO SUBCATEGORY(name, category_id) values ('Pozoriste',1);
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4,'ovo je neki opis', 40,40,3,'Uzice',2, 'Uzice');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4,'Opis madjarskog pozorista', 40,40,1,'Srpsko narodno pozoriste',2, 'Novi Sad');


INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/offerImage56246.jpg', 1);
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

INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Exit festivala', 40,40,0,'Exit festival',3, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis nekog niskog festivala', 40,40,0,'Neki niski festival',3, 'Nis');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4,'ovo je neki opis', 40,40,2,'Srpsko narodno pozoriste',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'ovo je neki opis', 40,40,0,'Srpsko narodno pozoriste',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Petrovaradina', 40,40,0,'Petrovaradin',2, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Muzeja Vojvodine', 40,40,0,'Muzej Vojvodine',1, 'Novi Sad');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis Kalemegdana', 40,40,0,'Kalemegdan',6, 'Beograd');
INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (0,'Opis spomenika neznanom junaku', 40,40,0,'Spomenik neznanom junaku',5, 'Beograd');

INSERT INTO SUBCATEGORY(name, category_id) values ('NAJNOVIJI',3);
INSERT INTO CATEGORY(name) values ('NAJNOVIJA');

INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/offerImage87516.jpg', 1);
INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica1', 'src/main/resources/images/offerImage18524.jpg', 1);
INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica2', 'src/main/resources/images/offerImage66669.jpg', 1);
INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica3', 'src/main/resources/images/offerImage74698.jpg', 1);

INSERT INTO USER_OFFER (offer_id, user_id) values (1, 2);
INSERT INTO USER_OFFER (offer_id, user_id) values (2, 2);
INSERT INTO USER_OFFER (offer_id, user_id) values (1, 3);
INSERT INTO USER_OFFER (offer_id, user_id) values (3, 3);

INSERT INTO public.rating(id, rating, offer_id, user_id) VALUES (1L, 5, 1L, 2L); 	-- 12/3 za offer 1
INSERT INTO public.rating(id, rating, offer_id, user_id) VALUES (2L, 5, 1L, 3L);
INSERT INTO public.rating(id, rating, offer_id, user_id) VALUES (3L, 2, 1L, 4L);


INSERT INTO public.rating(id, rating, offer_id, user_id) VALUES (4L, 4, 2L, 2L);    -- 1*4 za offer2 

INSERT INTO public.rating(id, rating, offer_id, user_id) values (5L, 3, 5L, 2L);
INSERT INTO public.rating(id, rating, offer_id, user_id) values (6L, 5, 5L, 3L);

INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (1L, '2020-5-1 12:00:00', null, 'Comment1', 1L, 2L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (2L, '2020-5-1 12:00:00', 'src/main/resources/images/commentpicture83800.jpg', 'Comment with picture 2', 2L, 2L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (3L, '2020-5-1 12:00:00', null, 'Comment without picture 2', 1L, 3L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (4L, '2020-5-1 12:00:00', null, 'Comment without picture 3', 3L, 4L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (5L, '2020-5-1 12:00:00', null, 'Comment without picture 3', 4L, 4L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (6L, '2020-5-1 12:00:00', null, 'Comment without picture 3', 10L, 4L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (7L, '2020-5-1 12:00:00', null, 'Comment without picture 7', 7L, 3L);
INSERT INTO public.comment(id, date, image_path, text, offer_id, user_id) VALUES (8L, '2020-5-1 12:00:00', null, 'Comment without picture 8', 7L, 4L);



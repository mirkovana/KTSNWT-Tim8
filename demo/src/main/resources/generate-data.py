import sys
import random
import os
import shutil

from random_username.generate import generate_username
from lorem_text import lorem

from datetime import datetime
from random import randrange
from datetime import timedelta


''' FORMAT OF DATA '''
#INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K','kor2@nesto.com', 'kor2@nesto.com','Pera', 'Peric', true); --sifra je 1
#insert into user_authority (user_id, authority_id) values (3, 2); -- user has ROLE_GUEST
#INSERT INTO CATEGORY(name) values ('Institucija');
#INSERT INTO SUBCATEGORY(name, category_id) values ('Pozoriste',1);
#INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES (4.6,'Opis sadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi esadsa asd asd asadasdasd goierhoghe eijrgo iehogiherio gheriohg eioheriohg erohg herhogh eoirhgi e pozorista', 19.844722, 45.255, 1200,'Madjarsko pozoriste',2, 'Novi Sad');
#INSERT INTO POST(content, date, title, offer_id) VALUES ('Nova predstava','2020-5-1 12:00:00', 'nova predstava', 1);
#INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/offerImage58624.jpg', 1);
#INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('2020-5-1 12:00:00', 'src/main/resources/images/petrovaradin.jpg', 'Comment text 1', 1, 2);
#INSERT INTO RATING(offer_id, user_id, rating) VALUES (1, 2, 4);

def random_date(start, end):
    """
    This function will return a random datetime between two datetime 
    objects.
    """
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = randrange(int_delta)
    return start + timedelta(seconds=random_second)

d1 = datetime.strptime('15/10/2020 1:30 PM', '%d/%m/%Y %I:%M %p')
d2 = datetime.strptime('27/1/2021 4:50 AM', '%d/%m/%Y %I:%M %p')


sourceFolder = "C:/Users/Korisnik/Desktop/slikekts"
destFolder = "D:/ktsnedelja/KTSNWT-Tim8/demo/src/main/resources/images/" #/
filenames = []
i = 0
for filename in os.listdir(sourceFolder):
    filenames.append(filename)
    file = open(sourceFolder +"/" + filename, 'r') # open in readonly mode
    shutil.copy(sourceFolder +"/" + filenames[i], destFolder + "offerImage" + str(random.randint(1, 10000000)) + ".jpg")
    i = i + 1


f = open('data.sql', 'w')


# generating users
password = "$2y$10$qHYGGSJnVTs3vVHITJuSwOQyki4XoMO5FgKa.psVze6VKaIJnYi9K"
domain = "@gmail.com"
for x in range(1, 200):
    email = generate_username(1)[0] + domain
    username = email
    name = generate_username(1)[0]
    surname = lorem.words(1)
    print("INSERT INTO USERS (type, password, username, email, name, surname, enabled) VALUES ('ROLE_USER',  '{0}','{1}', '{2}','{3}', '{4}', true);".format(password, username, email, name, surname), file=f)
    print("insert into user_authority (user_id, authority_id) values ({}, 2);".format(x), file=f)


# generating offers
for x in range(1000):
    avg = random.uniform(1.0, 5.0)
    votes = random.randint(1, 500)
    subcategory = random.randint(1, 5) # nmb of subcats (already in file)
    title = lorem.words(random.randint(1,4))
    description = lorem.paragraph()
    lat = random.uniform(1.0, 80.0)
    lon = random.uniform(1.0, 80.0)
    place = lorem.words(1)
    print("INSERT INTO OFFER (avg_rating, description, lat,lon, nmb_of_ratings, title, subcategory_id, place) VALUES ({0},'{1}', {2}, {3}, {4},'{5}',{6}, '{7}');".format(avg, description, lat, lon, votes, title, subcategory, place), file=f)
   

# generating posts
for x in range(1, 1000): # kao id ponude
    postNum = random.randint(1, 20)
    for y in range(postNum):
        content = lorem.paragraph()
        title = lorem.words(random.randint(1,4))
        date = random_date(d1, d2)
        print("INSERT INTO POST(content, date, title, offer_id) VALUES ('{0}','{1}', '{2}', {3});".format(content, date, title, x), file=f)


# generating offer images
for x in range(1, 1000):
    offerId = x
    numberOfImages = random.randint(0, 4)
    for y in range(0, numberOfImages):
        description = ""
        imageName = "offerImage" + str(random.randint(1, 10000000)) + ".jpg"
        print("INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('{0}', 'src/main/resources/images/{1}', {2});".format(description, imageName, offerId), file=f)
        indexToBeCopied = random.randint(0, len(filenames)-1)
        image = filenames[indexToBeCopied]
        shutil.copy(sourceFolder + "/" + image, destFolder + imageName)

# generating comments
for x in range(1, 1000):
    offerId = x
    numberOfComments = random.randint(0, 25)
    for y in range(0, numberOfComments):
        coef = random.uniform(0, 1) 
        imagePosted = False
        if coef > 0.9:
            imagePosted = True
        image = "null"
        if imagePosted:
            sn = str(random.randint(1, 10000000))
            image = "'src/main/resources/images/commentImage" + sn + ".jpg'"
            imageName = "commentImage"  + sn + ".jpg"
            indexToBeCopied = random.randint(0, len(filenames)-1)
            imageForCopying = filenames[indexToBeCopied]
            shutil.copy(sourceFolder + "/" + imageForCopying, destFolder + imageName)
        user_id = random.randint(2, 190)
        date = random_date(d1, d2)
        text = content = lorem.paragraph()
        print("INSERT INTO public.comment(date, image_path, text, offer_id, user_id) VALUES ('{0}', {1}, '{2}', {3}, {4});".format(date, image, text, offerId, user_id), file=f)

f.close()

import csv
import sys
import os

genre_dict = {
    "Action" : 0,
    "Adventure" : 1,
    "Animation" : 2,
    "Children's" : 3,
    "Comedy" : 4,
    "Crime" : 5,
    "Documentary" : 6,
    "Drama" : 7,
    "Fantasy" : 8,
    "Film-Noir" : 9,
    "Horror" : 10,
    "Musical" : 11,
    "Mystery" : 12,
    "Romance" : 13,
    "Sci-Fi" : 14,
    "Thriller" : 15,
    "War" : 16,
    "Western" : 17
}

gender_dict = {
    "M" : '0',
    "F" : 1
}

age_dict = {
    "1" : 0,
    "18" : 1,
    "25" : 2,
    "35" : 3,
    "45" : 4,
    "50" : 5,
    "56" : 6
}

#,user_id,movie_id,rating,timestamp,gender,age,occupation,zip,title,genres,for_testing
def convert_csv(in_file, out_file):
    o = open(out_file, 'w')
    count = -1
    with open(in_file) as f:
        for fields in csv.reader(f):
            count += 1
            if count == 0: continue
            for genre in fields[10].split('|'):
                user_id = int(fields[1])
                movie_id = int(fields[2])
                rating = int(fields[3])
                # skip fields[3] timestamp
                gender = gender_dict[fields[5]]
                age = fields[6]
                occupation = fields[7]
                # skip fields[8] zip
                title = fields[9]
                genre = str(genre_dict[genre])
                line = ','.join([
                    str(rating),
                    str(gender),
                    str(age),
                    str(occupation),
                    str(genre)
                ])
                o.write(line + "\n")

try:
    os.remove("test.csv")
except OSError:
    pass
try:
    os.remove("train.csv")
except OSError:
    pass

convert_csv("movielens_test.csv", "test.csv")
convert_csv("movielens_train.csv", "train.csv")

# makedata.py by Michael Ludwig

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
    "M" : 0,
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

class User:
    def __init__(self, id, gender, age, occupation, zipcode):
        self.id = id
        self.gender = gender
        self.age = age
        self.occupation = occupation
        self.zipcode = zipcode

class Movie:
    def __init__(self, title, genres):
        self.title = title
        self.genres = genres

class Rating:
    def __init__(self, user, movie, rating):
        self.user = user
        self.movie = movie
        self.rating = rating

users = {}
movies = {}
ratings = []

with open('users.dat') as f:
    for line in f.readlines():
        fields = line.strip().split('::')
        id = int(fields[0])
        gender = fields[1]
        age = fields[2]
        occupation = fields[3]
        zipcode = fields[4]
        users[id] = User(id, gender, age, occupation, zipcode)

with open('movies.dat') as f:
    for line in f.readlines():
        fields = line.strip().split('::')
        id = int(fields[0])
        title = fields[1]
        genres = fields[2].split('|')
        movies[id] = Movie(title, genres)

with open('ratings.dat') as f:
    for line in f.readlines():
        fields = line.strip().split('::')
        userid = int(fields[0])
        movieid = int(fields[1])
        rating = int(fields[2])
        ratings.append(Rating(users[userid], movies[movieid], rating))

for rating in ratings:
    user = rating.user
    movie = rating.movie
    for genre in movie.genres:
        line = ','.join([
            str(genre_dict[genre]),
            str(gender_dict[user.gender]),
            str(age_dict[user.age]),
            user.occupation,
            str(rating.rating - 1),
            str(user.id)
        ])
        print line
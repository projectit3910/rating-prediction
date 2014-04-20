# makedata.py by Michael Ludwig

class User:
    def __init__(self, gender, age, occupation, zipcode):
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
        users[id] = User(gender, age, occupation, zipcode)

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
        rating = fields[2]
        ratings.append(Rating(users[userid], movies[movieid], rating))

for rating in ratings:
    user = rating.user
    movie = rating.movie
    for genre in movie.genres:
        attributes = '|'.join([genre, user.gender, user.age, user.occupation])
        data_line = ':'.join([attributes, rating.rating])
        print data_line
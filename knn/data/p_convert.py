import pandas as pd
import numpy as np
import sys


unames = ['user_id', 'gender', 'age', 'occupation', 'zip']
users = pd.read_table('ml-1m/users.dat', sep='::', header=None, names=unames)

rnames = ['user_id', 'movie_id', 'rating', 'timestamp']
ratings = pd.read_table('ml-1m/ratings.dat', sep='::', header=None, names=rnames)

mnames = ['movie_id', 'title', 'genres']
movies = pd.read_table('ml-1m/movies.dat', sep='::', header=None, names=mnames)

movielens = pd.merge(pd.merge(ratings,users), movies)

movielens = movielens.ix[np.random.choice(movielens.index, size=10000, replace=False)]
user_ids_larger_1 = pd.value_counts(movielens.user_id, sort=False) > 1

movielens = movielens[user_ids_larger_1[movielens.user_id].values]
print np.all(movielens.user_id.value_counts() > 1)
print movielens.shape

def assign_to_set(df):
    sampled_ids = np.random.choice(df.index, size=np.int64(np.ceil(df.index.size * 0.2)), replace = False)
    df.ix[sampled_ids, 'for_testing'] = True
    return df

movielens['for_testing'] = False
grouped = movielens.groupby('user_id', group_keys=False).apply(assign_to_set)
movielens_train = movielens[grouped.for_testing == False]
movielens_test = movielens[grouped.for_testing == True]

del movielens_train['for_testing']
del movielens_test['for_testing']

print movielens_train.shape
print movielens_test.shape
print movielens_train.index & movielens_test.index

movielens_train.to_csv('movielens_train.csv')
movielens_test.to_csv('movielens_test.csv')

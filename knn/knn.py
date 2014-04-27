from sklearn import datasets
from sklearn import linear_model
from sklearn.neighbors import KNeighborsClassifier, NearestNeighbors
from sklearn.cross_validation import KFold
import numpy as np

def load_data_set(filename):
    X = []
    y = []
    with open(filename) as f:
        for line in f.readlines():
            fields = line.split(',')
            rating = fields[0]
            gender = fields[1]
            age = fields[2]
            occupation = fields[3]
            genre = fields[4]
            X.append([gender, age, occupation, genre])
            y.append(rating)
    return X, y

def evaluate(predicted, expected):
    print len(predicted)
    print len(expected)
    count = 0.0
    for i in range(len(predicted)):
        print [predicted[i], expected[i]]
        if predicted[i] == expected[i]:
            count += 1
    print count / len(predicted)


def knn_classifier_movielens():
    X_train, y_train = load_data_set("data/train.csv")
    X_test, y_test = load_data_set("data/test.csv")
    knn = KNeighborsClassifier(algorithm='kd_tree', metric='euclidean', n_neighbors = 50)
    knn.fit(X_train, y_train)
    predicted = knn.predict(X_test)
    evaluate(predicted, y_test)

#knn_classifier_iris()
#knn_classifier_diabetes()
knn_classifier_movielens()

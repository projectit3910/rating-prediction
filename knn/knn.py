from sklearn.neighbors import KNeighborsClassifier, NearestNeighbors
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
    count = 0.0
    for i in range(len(predicted)):
        if predicted[i] == expected[i]:
            count += 1
    return str(count / len(predicted))


def knn_classifier_movielens(k):
    X_train, y_train = load_data_set("data/train.csv")
    X_test, y_test = load_data_set("data/test.csv")
    knn = KNeighborsClassifier(algorithm='kd_tree', metric='euclidean', n_neighbors = k)
    knn.fit(X_train, y_train)
    predicted = knn.predict(X_test)
    return evaluate(predicted, y_test)


#print knn_classifier_movielens(50)
for i in range(1,101):
    print str(i) + ": " + str(knn_classifier_movielens(i))

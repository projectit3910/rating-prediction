from sklearn import datasets
from sklearn import linear_model
from sklearn.neighbors import KNeighborsClassifier, NearestNeighbors
import numpy as np

# Examples
def knn_classifier_iris():
    iris = datasets.load_iris()
    iris_X = iris.data
    iris_y = iris.target
    np.random.seed(0)
    indices = np.random.permutation(len(iris_X))
    iris_X_train = iris_X[indices[:-10]]
    iris_y_train = iris_y[indices[:-10]]
    iris_X_test = iris_X[indices[-10:]]
    iris_y_test = iris_y[indices[-10:]]

    # Create and fit a nearest-neighbor classifier
    knn = KNeighborsClassifier()
    knn.fit(iris_X_train, iris_y_train)
    print knn.predict(iris_X_test)
    print iris_y_test

def knn_classifier_diabetes():
    diabetes = datasets.load_diabetes()
    diabetes_X_train = diabetes.data[:-20]
    diabetes_X_test = diabetes.data[-20:]
    diabetes_y_train = diabetes.target[:-20]
    diabetes_y_test = diabetes.target[-20:]

    regr = linear_model.LinearRegression()
    regr.fit(diabetes_X_train, diabetes_y_train)
    print(regr.coef_)

    print np.mean((regr.predict(diabetes_X_test) - diabetes_y_test)**2)
    print regr.score(diabetes_X_test, diabetes_y_test)

#knn_classifier_iris()
knn_classifier_diabetes()

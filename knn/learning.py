from utils import *
import os
import copy, heapq, math, random
from collections import defaultdict

predicted = None
observed = None

#______________________________________________________________________________

def rms_error(predictions, targets):
    return math.sqrt(ms_error(predictions, targets))

def ms_error(predictions, targets):
    return mean([(p - t)**2 for p, t in zip(predictions, targets)])

def mean_error(predictions, targets):
    return mean([abs(p - t) for p, t in zip(predictions, targets)])

def mean_boolean_error(predictions, targets):
    return mean([(p != t)   for p, t in zip(predictions, targets)])

#______________________________________________________________________________

class DataSet:
    """A data set for a machine learning problem.  It has the following fields:

    d.examples    A list of examples.  Each one is a list of attribute values.
    d.attrs       A list of integers to index into an example, so example[attr]
                  gives a value. Normally the same as range(len(d.examples[0])).
    d.attrnames   Optional list of mnemonic names for corresponding attrs.
    d.target      The attribute that a learning algorithm will try to predict.
                  By default the final attribute.
    d.inputs      The list of attrs without the target.
    d.values      A list of lists: each sublist is the set of possible
                  values for the corresponding attribute. If initially None,
                  it is computed from the known examples by self.setproblem.
                  If not None, an erroneous value raises ValueError.
    d.distance    A function from a pair of examples to a nonnegative number.
                  Should be symmetric, etc. Defaults to mean_boolean_error
                  since that can handle any field types.
    d.name        Name of the data set (for output display only).
    d.source      URL or other source where the data came from.

    Normally, you call the constructor and you're done; then you just
    access fields like d.examples and d.target and d.inputs."""

    def __init__(self, examples=None, attrs=None, attrnames=None, target=-1,
                 inputs=None, values=None, distance=mean_boolean_error,
                 name='', source='', exclude=()):
        """Accepts any of DataSet's fields.  Examples can also be a
        string or file from which to parse examples using parse_csv.
        Optional parameter: exclude, as documented in .setproblem().
        >>> DataSet(examples='1, 2, 3')
        <DataSet(): 1 examples, 3 attributes>
        """
        update(self, name=name, source=source, values=values, distance=distance)
        # Initialize .examples from string or list or data directory
        if isinstance(examples, str):
            self.examples = parse_csv(examples)
        elif examples is None:
            self.examples = parse_csv(DataFile(name+'.csv').read())
        else:
            self.examples = examples
        # Attrs are the indices of examples, unless otherwise stated.
        if not attrs and self.examples:
            attrs = range(len(self.examples[0]))
        self.attrs = attrs
        # Initialize .attrnames from string, list, or by default
        if isinstance(attrnames, str):
            self.attrnames = attrnames.split()
        else:
            self.attrnames = attrnames or attrs
        self.setproblem(target, inputs=inputs, exclude=exclude)

    def setproblem(self, target, inputs=None, exclude=()):
        """Set (or change) the target and/or inputs.
        This way, one DataSet can be used multiple ways. inputs, if specified,
        is a list of attributes, or specify exclude as a list of attributes
        to not use in inputs. Attributes can be -n .. n, or an attrname.
        Also computes the list of possible values, if that wasn't done yet."""
        self.target = self.attrnum(target)
        exclude = map(self.attrnum, exclude)
        if inputs:
            self.inputs = removeall(self.target, inputs)
        else:
            self.inputs = [a for a in self.attrs
                           if a != self.target and a not in exclude]
        if not self.values:
            self.values = map(unique, zip(*self.examples))
        self.check_me()

    def check_me(self):
        "Check that my fields make sense."
        assert len(self.attrnames) == len(self.attrs)
        assert self.target in self.attrs
        assert self.target not in self.inputs
        assert set(self.inputs).issubset(set(self.attrs))
        map(self.check_example, self.examples)

    def add_example(self, example):
        "Add an example to the list of examples, checking it first."
        self.check_example(example)
        self.examples.append(example)

    def check_example(self, example):
        "Raise ValueError if example has any invalid values."
        if self.values:
            for a in self.attrs:
                if example[a] not in self.values[a]:
                    raise ValueError('Bad value %s for attribute %s in %s' %
                                     (example[a], self.attrnames[a], example))

    def attrnum(self, attr):
        "Returns the number used for attr, which can be a name, or -n .. n-1."
        if attr < 0:
            return len(self.attrs) + attr
        elif isinstance(attr, str):
            return self.attrnames.index(attr)
        else:
            return attr

    def sanitize(self, example):
       "Return a copy of example, with non-input attributes replaced by None."
       return [attr_i if i in self.inputs else None
               for i, attr_i in enumerate(example)]

    def __repr__(self):
        return '<DataSet(%s): %d examples, %d attributes>' % (
            self.name, len(self.examples), len(self.attrs))

#______________________________________________________________________________

def NearestNeighborLearner(dataset, k=1):
    "k-NearestNeighbor: the k nearest neighbors vote."
    def predict(example):
        "Find the k closest, and have them vote for the best."
        best = heapq.nsmallest(k, ((dataset.distance(e, example), e)
                                   for e in dataset.examples))
        return mode(e[dataset.target] for (d, e) in best)
    return predict

#_____________________________________________________________________________
# Functions for testing learners on examples

def test(predict, dataset, examples=None, verbose=0):
    global predicted
    predictions = []
    "Return the proportion of the examples that are correctly predicted."
    if examples is None: examples = dataset.examples
    if len(examples) == 0: return 0.0
    right = 0.0
    for example in examples:
        desired = example[dataset.target]
        output = predict(dataset.sanitize(example))
        predictions.append(output)
        if output == desired:
            right += 1
            if verbose >= 2:
               print '   OK: got %s for %s' % (desired, example)
        elif verbose:
            print 'WRONG: got %s, expected %s for %s' % (
               output, desired, example)
    predicted = predictions
    return right / len(examples)

def train_and_test(learner, dataset, start, end):
    """Reserve dataset.examples[start:end] for test; train on the remainder.
    Return the proportion of examples correct on the test examples."""
    examples = dataset.examples
    try:
        dataset.examples = examples[:start] + examples[end:]
        return test(learner(dataset), dataset, examples[start:end])
    finally:
        dataset.examples = examples

def cross_validation(learner, dataset, k=10, trials=1):
    """Do k-fold cross_validate and return their mean.
    That is, keep out 1/k of the examples for testing on each of k runs.
    Shuffle the examples first; If trials>1, average over several shuffles."""
    if k is None:
        k = len(dataset.examples)
    if trials > 1:
        return mean([cross_validation(learner, dataset, k, trials=1)
                     for t in range(trials)])
    else:
        n = len(dataset.examples)
        random.shuffle(dataset.examples)
        return mean([train_and_test(learner, dataset, i*(n/k), (i+1)*(n/k))
                     for i in range(k)])

def leave1out(learner, dataset):
    "Leave one out cross-validation over the dataset."
    return cross_validation(learner, dataset, k=len(dataset.examples))

def learningcurve(learner, dataset, trials=10, sizes=None):
    if sizes is None:
        sizes = range(2, len(dataset.examples)-10, 2)
    def score(learner, size):
        random.shuffle(dataset.examples)
        return train_and_test(learner, dataset, 0, size)
    return [(size, mean([score(learner, size) for t in range(trials)]))
            for size in sizes]

def load_movie_lens():
    X = []
    global observed
    temp = []
    with open("data/train.csv") as f:
        for line in f.readlines():
            fields = line.split(',')
            rating = fields[0]
            gender = fields[1]
            age = fields[2]
            occupation = fields[3]
            genre = fields[4]
            X.append([gender, age, occupation, genre, rating])

    with open("data/test.csv") as f:
        for line in f.readlines():
            fields = line.split(',')
            rating = fields[0]
            gender = fields[1]
            age = fields[2]
            occupation = fields[3]
            genre = fields[4]
            X.append([gender, age, occupation, genre, rating])
            temp.append(rating)
    observed = temp
    return X

def MovieLens():
    examples = load_movie_lens()
    return DataSet(name="MovieLens", examples=examples,
            attrnames="gender age occupation genre rating", target="rating", distance=ms_error)

#______________________________________________________________________________

if __name__ == '__main__':
    start = 0
    end = file_len("data/train.csv") - 1
    print train_and_test(NearestNeighborLearner, MovieLens(), start, end)
    print len(observed)
    print len(predicted)

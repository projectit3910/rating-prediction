def update(x, **entries):
    if isinstance(x, dict):
        x.update(entries)
    else:
        x.__dict__.update(entries)
    return x

def DataFile(name, mode='r'):
    "Return a file in the AIMA /data directory."
    return AIMAFile(['data', name], mode)

def AIMAFile(components, mode='r'):
    "Open a file based at the AIMA root directory."
    dir = os.path.dirname(__file__)
    return open(apply(os.path.join, [dir] + components), mode)

def isnumber(x):
    "Is x a number? We say it is if it has a __int__ method."
    return hasattr(x, '__int__')

def unique(seq):
    return list(set(seq))

def removeall(item, seq):
    if isinstance(seq, str):
        return seq.replace(item, '')
    else:
        return [x for x in seq if x != item]

def mean(values):
    """Return the arithmetic average of the values."""
    return sum(values) / float(len(values))

def mode(values):
    return histogram(values, mode=1)[0][0]

def histogram(values, mode=0, bin_function=None):
    """Return a list of (value, count) pairs, summarizing the input values.
    Sorted by increasing value, or if mode=1, by decreasing count.
    If bin_function is given, map it over values first."""
    if bin_function: values = map(bin_function, values)
    bins = {}
    for val in values:
        bins[val] = bins.get(val, 0) + 1
    if mode:
        return sorted(bins.items(), key=lambda x: (x[1],x[0]), reverse=True)
    else:
        return sorted(bins.items())

def file_len(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
    return i + 1

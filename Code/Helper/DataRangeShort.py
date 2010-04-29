from DataRange import *
from time import time

class DataRangeShort(DataRange):
    def __init__(self, interval, *args):
        self.interval = interval
        DataRange.__init__(self, *args)

    def add(self, DP):
        if len(self.range) >= 2:
            if DP.time - self.range[1].time >= self.interval:
                self.range.pop(0)
        DataRange.add(self, DP)

    def totalrange(self):
        if len(self.range) >= 1:
            return time() - self.range[-1].time

if __name__ == "__main__":
    test = DataRangeShort(10)
    test.add(DataPoint(5,0))
    test.add(DataPoint(8,5))
    test.add(DataPoint(12,10))
    print test.range
    test.add(DataPoint(13,15))
    print test.range
    test.add(DataPoint(20,25))
    print test.range

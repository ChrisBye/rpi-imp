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
            return time() - self.range[0].time

    def getMax(self):
        retmax = self.range[0].value
        for dp in self.range:
            retmax = max(dp.value, retmax)
        return retmax

    def getMin(self):
        retmin = self.range[0].value
        for dp in self.range:
            retmin = min(dp.value, retmin)
        return retmin

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

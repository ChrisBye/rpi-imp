
class DataPoint:
    def __init__(self, value, time):
        self.value = value
        self.time = time

    def __repr__(self):
        return "(value=%g, time=%f)" % (self.value, self.time)

class DataRange:
    def __init__(self, *args):
        self.range = list()
        for point in args:
            self.range.append(point)
        self.range.sort(key=lambda point: point.time)
        self.range.reverse()

    def add(self, DP):
        self.range.append(DP)

    def getCur(self):
        if len(self.range) >= 1:
            return self.range[-1].value
        else:
            return None

    def getAtTime(self,time):
        if len(self.range) == 0:
            return None
        if (time < self.range[0].time):
            return self.range[0].value
        for i in range(len(self.range)-1):
            if (time >= self.range[i].time) and (time < self.range[i+1].time):
                return self.range[i].value
        return self.range[-1].value
'''
    def Lerp(self, DP1, DP2, time):
        return DP1.value + (DP2.value - DP1.value) * ((DP1.time - time)/(DP1.time - DP2.time + 0.0))
'''

if __name__ == "__main__":
    point1 = DataPoint(10,0)
    point2 = DataPoint(8,2)
    point3 = DataPoint(5,6)
    range1 = DataRange(point1, point2, point3)
    print range1.range
    for i in range(-3,11):
        print "Time=%g, Value=%g" % (i,range1.getAtTime(i))

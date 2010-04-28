# The UserSetConstant class is just slightly above being a container class in terms of
#   functionality, but it still serves an important purpose. min should be less
#   than max, otherwise an exception is raised. Name should be something
#   relatively small so that it's easy to display in the program

class UserSetConstant:
    def __init__(self, name, min, max, incr):
        self.name = name
        self.min = min
        self.max = max
        self.incr = incr
        self.value = min

        if self.min > self.max:
            raise ValueError("min value greater than max value: (%s) %g > %g" % 
                (self.name, self.min, self.max))

    def __repr__(self):
        retstr = "Var: %s = %g (%g, %g, %g)" % (self.name, self.value, self.min, 
                                              self.max, self.incr)
        return retstr

    def increment(self):
        self.value = min(self.max, self.value + self.incr)

    def decrement(self):
        self.value = max(self.min, self.value - self.incr)

    def setValue(self, newValue):
        if newValue > 0:
            self.value = min(self.max, newValue)
        else:
            self.value = max(self.min, newValue)


if __name__ == "__main__":
    print "UserSetConstant TESTING"
    conA = UserSetConstant("conA", 0, 10, 1)
    conB = UserSetConstant("conB", -4, 8, 0.5)
    print conA,"\n", conB
    print "INCREMENT ONCE"
    conA.increment()
    conB.increment()
    print conA,"\n", conB
    print "DECREMENT ONCE"
    conA.decrement()
    conB.decrement()
    print conA,"\n", conB
    print "DECREMENT ONCE (Shouldn't change, at min already)"
    conA.decrement()
    conB.decrement()
    print conA,"\n", conB
    print "SETVALUE TO MAXIMUM"
    conA.setValue(10)
    conB.setValue(8)
    print conA,"\n", conB
    print "INCREMENT ONCE (Shouldn't change, at max already)"
    conA.increment()
    conB.increment()
    print conA,"\n", conB
    print "VALUEERROR TEST"
    conC = UserSetConstant("conC", 0, -10, 3)

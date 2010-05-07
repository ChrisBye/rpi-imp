# The UserSetConstant class is just slightly above being a container class in terms of
#   functionality, but it still serves an important purpose. min should be less
#   than max, otherwise an exception is raised. Name should be something
#   relatively small so that it's easy to display in the program

class UserSetConstant:
    def __init__(self, name, min=None, max=None):
        self.name = name
        self.min = min
        self.max = max
        self.value = 0
        self.setValue(0)

        if self.min != None and self.max != None:        
            if self.min > self.max:
                raise ValueError("min value greater than max value: (%s) %g > %g" % 
                    (self.name, self.min, self.max))

    def __repr__(self):
        retstr = "Con: %s = %g (%g, %g, %g)" % (self.name, self.value, self.min, 
                                              self.max, self.incr)
        return retstr
    '''
    def increment(self):
        if self.max != None:
            self.value = min(self.max, self.value + self.incr)
        else:
            self.value += self.incr

    def decrement(self):
        if self.min != None:
            self.value = max(self.min, self.value - self.incr)
    '''

    def setValue(self, newValue):
        if self.min != None and newValue < self.min:
            self.value = self.min
        elif self.max != None and newValue > self.max:
            self.value = self.max
        else:
            self.value = newValue
        

class Algorithm():
    def __init__(self):
        self.constants = UserSetConstantContainer()
        self.constants.add(UserSetConstant("a"))
        self.constants.add(UserSetConstant("b"))

    def Run():
        return self.USConstants["a"]*self.USConstants["b"]*IMP.GetAlgorithms["otherAlg"]

class Algorithm():
    def __init__(self):
        self.constants = UserSetConstantContainer()
        self.constants.add(UserSetConstant("c"))
        self.constants.add(UserSetConstant("d"))

    def Run():
        return self.USConstants["c"]/self.USConstants["d"]

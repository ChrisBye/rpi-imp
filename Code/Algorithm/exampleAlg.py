class Algorithm():
    def __init__(self):
        self.constants = UserSetConstantContainer()
        self.constants.add(UserSetConstant("a"))
        self.constants.add(UserSetConstant("b"))

    def Run():
        return self.constants["a"]*self.constants["b"]*IMP.GetAlgorithms["otherAlg"]

class Algorithm():
    def __init__(self):
        self.constants = UserSetConstantContainer()
        self.constants.add(UserSetConstant("c"))
        self.constants.add(UserSetConstant("d"))

    def Run():
        return self.constants["c"]/self.constants["d"]

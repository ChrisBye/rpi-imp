
class Algorithm():
    def __init__(self, IMP):
        self.IMP = IMP
        self.constants = dict()

    def Options(self):
        return self.constants

    def Run(self,stockdata,symbol):
        return self.IMP.BuiltInFunc("average",symbol)

alg = Algorithm(self.IMP)
self.algorithms[name[0][1]] = alg

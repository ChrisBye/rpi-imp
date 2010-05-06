class Algorithm():
    def __init__(self, IMP):
        self.IMP = IMP
        self.constants = dict()
        self.constants["bias"] = UserSetConstant("bias", min=0, max=1)

    def Options(self):
        return self.constants

    def Run(self,stockdata,symbol):
        dif = self.IMP.BuiltInFunc("average",symbol) - self.IMP.getStockPrice(symbol)
        return self.IMP.BuiltInFunc("average",symbol) - (dif * self.constants["bias"].value)

alg = Algorithm(self.IMP)
self.algorithms[name[0][1]] = alg

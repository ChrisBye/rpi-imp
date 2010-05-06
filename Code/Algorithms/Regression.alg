class Algorithm():
    def __init__(self, IMP):
        self.IMP = IMP
        self.constants = dict()
        self.constants["bias"] = UserSetConstant("bias", min=0, max=1)

    def Options(self):
        return self.constants

    def Run(self,stockdata,symbol):
        dif = self.IMP.BuiltInFunc("average",symbol) - self.IMP.getStockPrice(symbol) 
        if abs(dif) > self.IMP.BuiltInFunc("deviation",symbol):
            if self.IMP.getStockPrice(symbol) < self.IMP.BuiltInFunc("average",symbol):
                return self.IMP.BuiltInFunc("average",symbol) - self.constants["bias"].value * math.copysign(dif, self.IMP.BuiltInFunc("deviation",symbol))
            else:
                return self.IMP.BuiltInFunc("average",symbol) + self.constants["bias"].value * math.copysign(dif, self.IMP.BuiltInFunc("deviation",symbol))
        else:
            return self.IMP.BuiltInFunc("average",symbol)
            

alg = Algorithm(self.IMP)
self.algorithms[name[0][1]] = alg

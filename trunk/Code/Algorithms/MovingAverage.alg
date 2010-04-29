class Algorithm():
    def __init__(self):
        self.constants = None

    def Options(self):
        return self.constants

    def Run(self,symbol,IMP):
        return IMP.stocks[symbol].average

alg = Algorithm()
self.algorithms[name[0][1]] = alg

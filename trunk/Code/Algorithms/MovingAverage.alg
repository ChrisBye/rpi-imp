class Algorithm():
    def __init__(self):
        self.constants = list()
        self.constants.append("Thud")
        self.constants.append("Patches")
        self.constants.append("Peaches")
        self.constants.append("Sloth")

    def Options(self):
        return self.constants

    def Run(self,stockdata,symbol):
        return stockdata.stocks[symbol].average

alg = Algorithm()
self.algorithms[name[0][1]] = alg

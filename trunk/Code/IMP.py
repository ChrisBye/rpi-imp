import pygtk
pygtk.require('2.0')
import gtk

from AlgWindow import *
from AlgBackend import *
from GraphWindow import *
from StockData import *
from Helper.UserSetConstant import *


class IMP:
    def __init__(self):
        # The main IMP program (in all it's glory) simply creates and stores
        #   references to all the other components of IMP and makes sure that
        #   they have the proper references to each other
        self.algbackend = AlgBackend(self)
        self.algwindow = AlgWindow(self.algbackend)
        self.stockdata = StockData()
        self.graphwindow = GraphWindow(self.stockdata,self.algbackend)

    def BuiltInFunc(self, funcname, symbol):
        if funcname == "average":
            return self.stockdata.stocks[symbol].average
        elif funcname == "deviation":
            average = self.stockdata.stocks[symbol].average
            total = 0
            quotescopy = self.stockdata.stocks[symbol].quotesshort
            for quote in quotescopy.range:
                total += abs(quote.value - average)
            total = total /(quotescopy.totalrange() + 0.0)
            return total
        else:
            raise ValueError("Unknown function: " + funcname)

    def getStockPrice(self, symbol):
        return self.stockdata.stocks[symbol].price

    def GetAlgorithms(self, algname):
        # Currently bugged, see past revision
        return 0

if __name__ == "__main__":
    test = IMP()
    gtk.main()

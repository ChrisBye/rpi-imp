import pygtk
pygtk.require('2.0')
import gtk

from AlgWindow import *
from AlgBackend import *
from GraphWindow import *
from StockData import *


class IMP:
    def __init__(self):
        # The main IMP program (in all it's glory) simply creates and stores
        #   references to all the other components of IMP and makes sure that
        #   they have the proper references to each other
        self.algbackend = AlgBackend()
        self.algwindow = AlgWindow(self.algbackend)
        self.stockdata = StockData()
        self.graphwindow = GraphWindow(self.stockdata,self.algbackend)


if __name__ == "__main__":
    test = IMP()
    gtk.main()

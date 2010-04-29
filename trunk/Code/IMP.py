import pygtk
pygtk.require('2.0')
import gtk
import glib

import sys, os, glob

from AlgWindow import *
from GraphWindow import *

from Stock import Stock

class IMP:
    def __init__(self):
        # load algorithms
        # load scraper

        self.algorithms = dict()
        for inalg in glob.glob(os.path.join('Algorithms', "*.alg")):
            #print "Current file is " + inalg
            name = inalg.split(".alg")
            name[0] = name[0].split("/")
            #print inalg[0][1]
            execfile(inalg)

        self.algwindow = AlgWindow(self)
        self.graphwindow = GraphWindow(self)

        self.stocks = dict()

    def loadStock(self, symbol):
        if symbol == "test":
            self.stocks["test"] = Stock(symbol)
        else:
            pass

    def updateStock(self):
        for stock in self.stocks.values():
            stock.update()

if __name__ == "__main__":
    test = IMP()
    gtk.main()

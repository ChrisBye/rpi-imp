import pygtk
pygtk.require('2.0')
import gtk
import glib

import sys, os, glob

from AlgWindow import *
from AlgBackend import *
from GraphWindow import *
from StockData import *


class IMP:
    def __init__(self):
        self.algbackend = AlgBackend()
        self.algwindow = AlgWindow(self.algbackend)
        self.stockdata = StockData()
        self.graphwindow = GraphWindow(self.stockdata,self.algbackend)


if __name__ == "__main__":
    test = IMP()
    gtk.main()

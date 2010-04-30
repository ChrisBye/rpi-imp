import pygtk
pygtk.require('2.0')
import gtk
import glib

import Image, ImageDraw
from Helper.DataRangeShort import DataRangeShort
from Helper.DataRange import *
import time

#The size of the graph is important not only for drawing the graph itself, but
#   also for the initial size of the window and for the conversion of stock data
#   points into line drawings.
GRAPH_SIZE_X = 512
GRAPH_SIZE_Y = 256

#Interval refers to the how many pixels make up one time unit - for testing
#   purposes, that time unit is one second, but it could be set to anything
INTERVAL = 8

#Update interval refers to how often GraphWindow should redraw the graph (the
#   time is milliseconds
UPDATE_INTERVAL = 1000

class GraphWindow:
    def delete_event(self, widget, event, data=None):
        gtk.main_quit()
        return False

    def enter_callback(self, widget, entry):
        entry_text = entry.get_text()
        self.curstock = entry_text
        # If the user enters a stock into the entry, cache/load it into
        #   stockdata, where it can be retrieved for graphing later
        self.stockdata.loadStock(self.curstock)
        print "Entry contents: %s\n" % entry_text

    def update(self):
        self.stockdata.update()
        # Clear the old image and get it ready for graphing
        self.image = self.imagegraph.copy()

        # If the stock the user entered is actually valid, graph it and the
        #   selected algorithm. Otherwise, cancel everything.
        if self.stockdata.stocks.has_key(self.curstock):
            self.drawLines(self.stockdata.stocks[self.curstock].quotesshort, "red")
            self.stockprice.set_text(self.curstock + " (red)" + " : " + str(self.stockdata.stocks[self.curstock].quotesshort.getCur()))
            #self.algval.set_text("MovingAverage" + " (blue)" + " : " + str(self.stupid.getCur()))
        else:
            self.stockprice.set_text("<Stock> : <Price>")
            self.algval.set_text("<Algorithm> : <Value>")

        self.image.save("Tmp/tmpgraph.png")
        self.imagedisplay.set_from_file("Tmp/tmpgraph.png")
        return True

    # drawLines takes a DataRangeShort (preferably, but it could be
    #   DataRange as well) and a color fill. It then graphs the data on the
    #   image
    def drawLines(self, DRS, fillcolor):
        draw = ImageDraw.Draw(self.image)
        # zoom is a multiplier to the effective position of the point. Currently
        #   it makes it so that the last data point gotten is always at the 
        #   middle. Rather naive, as it messes up with "jaggedy" data and
        #   tends to oversmooth high-priced stocks. This is something that can
        #   be improved greatly.
        zoom = DRS.getAtTime(time.time())/(GRAPH_SIZE_Y/2.0)
        for i in range(GRAPH_SIZE_X/INTERVAL):
            n0 = DRS.getAtTime(time.time()-i)
            n0 /= zoom
            n1 = DRS.getAtTime(time.time()-(i+1))
            n1 /= zoom
            # The following scary-looking line merely draws a line between the
            #   two points n0 and n1
            draw.line((GRAPH_SIZE_X-((i+1)*INTERVAL),GRAPH_SIZE_Y-n1)+(GRAPH_SIZE_X-(i*INTERVAL),GRAPH_SIZE_Y-n0), fill = fillcolor)

    def __init__(self, stockdata, algbackend):
        self.stockdata = stockdata
        self.algbackend = algbackend

        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
        self.window.set_title("IMP Graph")

        self.window.connect("delete_event", self.delete_event)
        self.window.set_border_width(0)
        self.window.set_size_request(GRAPH_SIZE_X+40,GRAPH_SIZE_Y+200)
        vbox = gtk.VBox(False,0)
        self.window.add(vbox)
        vbox.show()

        self.graphframe = gtk.Frame("Graph")
        vbox.pack_start(self.graphframe)
        self.graphframe.show()

        vbox2 = gtk.VBox(False,0)
        vbox.pack_start(vbox2)
        vbox2.show()
        
        self.stockprice = gtk.Label()
        self.algval = gtk.Label()
        vbox2.pack_start(self.stockprice)
        vbox2.pack_start(self.algval)
        self.stockprice.show()
        self.algval.show()

        self.stockprice.set_text("<Stock> : <Price>")
        self.algval.set_text("<Algorithm> : <Value>")


        self.stockframe = gtk.Frame("Stock")
        self.stockentry = gtk.Entry()
        self.stockentry.set_max_length(10)
        self.stockentry.connect("activate", self.enter_callback, self.stockentry)
        self.stockframe.add(self.stockentry)
        self.stockentry.show()
        vbox2.pack_start(self.stockframe)
        self.stockframe.show()
        self.curstock = None


        self.image = Image.new("RGB", (GRAPH_SIZE_X, GRAPH_SIZE_Y), "white")
        self.imagegraph = self.image.copy()
        draw = ImageDraw.Draw(self.imagegraph)
        for i in range(1,GRAPH_SIZE_X/INTERVAL):
            draw.line((i*INTERVAL,0)+(i*INTERVAL,GRAPH_SIZE_Y), fill="lightgrey")     
        del draw
        self.image = self.imagegraph
        self.image.save("Tmp/tmpgraph.png")

        glib.timeout_add(UPDATE_INTERVAL,self.update)

        self.imagedisplay = gtk.Image()
        self.imagedisplay.set_from_file("Tmp/tmpgraph.png")
        self.imagedisplay.show()
        self.graphframe.add(self.imagedisplay)

        self.window.show()

if __name__ == "__main__":
    test = GraphWindow(None, None)
    gtk.main()

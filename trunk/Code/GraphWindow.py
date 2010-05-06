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
        self.algbackend.update(self.stockdata) #Avert ye eyes

        # Clear the old image and get it ready for graphing
        self.image = self.imagegraph.copy()

        # If the stock the user entered is actually valid, graph it and the
        #   selected algorithm. Otherwise, cancel everything.
        if self.stockdata.stocks.has_key(self.curstock):
            # Figure out the proper zoom factor (see drawLines below) and then
            #   draws to the graph the stock info and then the algorithm
            #zoom = self.stockdata.stocks[self.curstock].quotesshort.getAtTime(time.time())/(GRAPH_SIZE_Y/2.0)
            zoomMax = self.stockdata.stocks[self.curstock].getMax()/(GRAPH_SIZE_Y/2.0)
            zoomMin = self.stockdata.stocks[self.curstock].getMin()/(GRAPH_SIZE_Y/2.0)
            zoom = (zoomMax - zoomMin)/2 + zoomMin
            self.drawLines(self.stockdata.stocks[self.curstock].quotesshort, zoom, "red")
            self.drawLines(self.algbackend.getAlgorithmDRS(self.curstock), zoom, "blue")

            # Update the labels to display the proper information
            self.stockprice.set_text(self.curstock + " (red)" + " : " + str(self.stockdata.stocks[self.curstock].quotesshort.getCur()))
            self.algval.set_text(self.algbackend.curalg + " (blue)" + " : " + str(self.algbackend.getAlgorithmDRS(self.curstock).getCur()))
        else:
            self.stockprice.set_text("<Stock> : <Price>")
            self.algval.set_text("<Algorithm> : <Value>")

        self.image.save("Tmp/tmpgraph.png")
        self.imagedisplay.set_from_file("Tmp/tmpgraph.png")
        return True

    # drawLines takes a DataRangeShort (preferably, but it could be
    #   DataRange as well) and a color fill. It then graphs the data on the
    #   image
    def drawLines(self, DRS, zoom, fillcolor):
        draw = ImageDraw.Draw(self.image)
        # zoom is a multiplier to the effective position of the point. Currently
        #   it makes it so that the last data point gotten is always at the 
        #   middle. Rather naive, as it messes up with "jaggedy" data and
        #   tends to oversmooth high-priced stocks. This is something that can
        #   be improved greatly.
        
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

        # Create a gtk.Window, set the size to specifications based on global
        #   constants, and then connect closing it to a delete event (which
        #   closes gtk)
        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
        self.window.set_title("IMP Graph")
        self.window.connect("delete_event", self.delete_event)
        self.window.set_border_width(0)
        self.window.set_size_request(GRAPH_SIZE_X+40,GRAPH_SIZE_Y+200)

        # mainbox is where everything gets packed into. However, it should never
        #   be touched after being created, so it only has a temporary reference
        #   instead of being a class member
        mainbox = gtk.VBox(False,0)
        self.window.add(mainbox)
        mainbox.show()

        # graphframe is merely decorative and should never be referenced after
        #   it's created
        graphframe = gtk.Frame("Graph")
        mainbox.pack_start(graphframe)
        graphframe.show()

        # graphmiscbox holds stock price and algorithm value labels, along with
        #   a frame which contains the stock symbol entry. It should never be
        #   referenced after it is created
        graphmiscbox = gtk.VBox(False,0)
        mainbox.pack_start(graphmiscbox)
        graphmiscbox.show()
        
        # stockprice and algval are referenced later on - they are updated each
        #   time the graph is
        self.stockprice = gtk.Label()
        self.algval = gtk.Label()
        graphmiscbox.pack_start(self.stockprice)
        graphmiscbox.pack_start(self.algval)
        self.stockprice.show()
        self.algval.show()

        # Default settings
        self.stockprice.set_text("<Stock> : <Price>")
        self.algval.set_text("<Algorithm> : <Value>")


        # stockentry is where the user puts in the symbol of the stock they wish
        #   to view
        stockframe = gtk.Frame("Stock")
        self.stockentry = gtk.Entry()
        self.stockentry.set_max_length(10)
        self.stockentry.connect("activate", self.enter_callback, self.stockentry)
        stockframe.add(self.stockentry)
        self.stockentry.show()
        graphmiscbox.pack_start(stockframe)
        stockframe.show()
        self.curstock = None


        # Another little piece of hackery. PIL (or Python Imaging Library) is
        #   a powerful module for creating images. However, there's no easy
        #   conversion between the imagebufs used by PIL and the pixbufs used by
        #   pygtk, so, unfortunately, each time the graph is updated a temporary
        #   file is written to the hard drive and then read back in. While
        #   slower then reading from memory, it's still plenty fast enough
        # The following code declares image, which is what's going to be
        #   displayed for the graph, and imagegraph which is a series of lines.
        #   imagegraph should not be modified after it is created
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
        graphframe.add(self.imagedisplay)

        self.window.show()

if __name__ == "__main__":
    test = GraphWindow(None, None)
    gtk.main()

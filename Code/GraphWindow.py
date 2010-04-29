import pygtk
pygtk.require('2.0')
import gtk
import glib

import Image, ImageDraw
from random import randint
from random import shuffle
from Helper.DataRangeShort import DataRangeShort
from Helper.DataRange import *
import time

class GraphWindow:
    def delete_event(self, widget, event, data=None):
        gtk.main_quit()
        return False

    def enter_callback(self, widget, entry):
        entry_text = entry.get_text()
        self.curstock = entry_text
        self.IMP.loadStock(self.curstock)
        print "Entry contents: %s\n" % entry_text

    def update(self):
        self.IMP.updateStock()

        self.image = self.imagegraph.copy()

        if self.IMP.stocks.has_key(self.curstock):
            draw = ImageDraw.Draw(self.image)
            zoom = self.IMP.stocks[self.curstock].quotesshort.getAtTime().value/(256.0/2)
            for i in range(64):
                n0 = self.IMP.stocks[self.curstock].quotesshort.getAtTime(time.time()-i)
                n1 =self.IMP.stocks[self.curstock].quotesshort.getAtTime(time.time()-(i+1))
                n0 /= zoom
                n1 /= zoom
                #print "time:",time.time()-i,"n0:",n0,"n1:",n1
                draw.line((512-((i+1)*8),256-n1)+(512-(i*8),256-n0), fill = "red")
            
            self.stupid.add(DataPoint(self.IMP.algorithms["MovingAverage"].Run("test",self.IMP),time.time()))
            for i in range(64):
                n0 = self.stupid.getAtTime(time.time()-i)
                n1 =self.stupid.getAtTime(time.time()-(i+1))
                n0 /= zoom
                n1 /= zoom
                draw.line((512-((i+1)*8),256-n1)+(512-(i*8),256-n0), fill = self.colors[0])

        self.image.save("Tmp/tmpgraph.png")
        self.imagedisplay.set_from_file("Tmp/tmpgraph.png")
        return True

    def __init__(self, IMP):
        self.IMP = IMP
        self.colors = ["blue"]
        shuffle(self.colors)

        self.stupid = DataRangeShort(64)

        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
        self.window.set_title("IMP Graph")

        self.window.connect("delete_event", self.delete_event)
        self.window.set_border_width(0)
        self.window.set_size_request(550,400)
        vbox = gtk.VBox(False,0)
        self.window.add(vbox)
        vbox.show()

        self.graphframe = gtk.Frame("Graph")
        vbox.pack_start(self.graphframe)
        self.graphframe.show()

        self.stockframe = gtk.Frame("Stock")
        self.stockentry = gtk.Entry()
        self.stockentry.set_max_length(10)
        self.stockentry.connect("activate", self.enter_callback, self.stockentry)
        self.stockframe.add(self.stockentry)
        self.stockentry.show()
        vbox.pack_start(self.stockframe)
        self.stockframe.show()
        self.curstock = None


        self.image = Image.new("RGB", (512, 256), "white")
        self.imagegraph = self.image.copy()
        draw = ImageDraw.Draw(self.imagegraph)
        for i in range(1,512/8):
            draw.line((i*8,0)+(i*8,256), fill="lightgrey")     
        del draw
        self.image = self.imagegraph
        self.image.save("Tmp/tmpgraph.png")

        glib.timeout_add(1000,self.update)

        self.imagedisplay = gtk.Image()
        self.imagedisplay.set_from_file("Tmp/tmpgraph.png")
        self.imagedisplay.show()
        self.graphframe.add(self.imagedisplay)

        self.window.show()

if __name__ == "__main__":
    test = GraphWindow(None)
    gtk.main()

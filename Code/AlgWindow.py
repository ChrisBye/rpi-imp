import pygtk
pygtk.require('2.0')
import gtk
import glib

class AlgWindow:
    def delete_event(self, widget, event, data=None):
        gtk.main_quit()
        return False

    def update(self):
        if self.curalg != self.alglist.entry.get_text():
            self.curalg = self.alglist.entry.get_text()
            #print self.curalg
            self.show_options()
        return True

    def show_options(self):
        self.optionbox.foreach(lambda widget:self.optionbox.remove(widget))
        self.optionlist = list()
        if self.IMP != None:
            pass
        else:
            for i in range(7):
                self.optionlist.append(self.curalg + "-" + str(i))

        for option in self.optionlist:
            label = gtk.Label(option)
            self.optionbox.pack_start(label, True, True, 0)
            label.show()
        self.optionbox.show()
        

    def __init__(self, IMP):
        self.IMP = IMP

        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
        self.window.set_title("IMP Algorithms")

        self.window.connect("delete_event", self.delete_event)
        self.window.set_border_width(0)
        self.window.set_size_request(300,300)
        vbox = gtk.VBox(False,0)
        self.window.add(vbox)
        vbox.show()

        self.alglist = gtk.Combo()
        self.alglist.show()
        alist = list()
        if IMP != None:
            for name in self.IMP.algorithms.keys():
                alist.append(name)

        else:
            for i in range(10):
                alist.append("Test"+str(i))
        self.alglist.entry.set_text("")
        self.alglist.set_popdown_strings(alist)
        self.curalg = alist[0]
        
        self.algframe = gtk.Frame("Algorithms")
        self.algframe.add(self.alglist)
        vbox.pack_start(self.algframe)
        self.algframe.show()

        self.optionframe = gtk.Frame("Options")
        self.optionscroll = gtk.ScrolledWindow()
        self.optionscroll.set_policy(gtk.POLICY_AUTOMATIC, gtk.POLICY_ALWAYS)
        self.optionframe.add(self.optionscroll)
        self.optionbox = gtk.VBox(False, 0)
        self.optionscroll.add_with_viewport(self.optionbox)
        self.optionbox.show()
        self.optionscroll.show()
        vbox.pack_start(self.optionframe)
        self.optionframe.show()

        self.show_options()

        glib.timeout_add(100,self.update)

        self.window.show()

if __name__ == "__main__":
    test = AlgWindow(None)
    gtk.main()

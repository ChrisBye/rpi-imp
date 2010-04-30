import pygtk
pygtk.require('2.0')
import gtk
import glib

class AlgWindow:
    def delete_event(self, widget, event, data=None):
        # This callback is only necessary if AlgWindow is used alone.
        if self.backend == None:
            gtk.main_quit()
        return False

    def option_constant_callback(self, widget, constant):
        print constant.get_text()
        # check backend to makesure the constant is workable

    def update(self):
        # update is called every 100 millisecs. It checks to see whether the
        #   user has changed algcombo. This is pure busy waiting, but Combo
        #   doesn't have an elegant way of having a callback whenever the user
        #   selects something.
        # if the user has changed algcombo, set the backend's current algorithm
        #   (curalg) to that new algorithm
        if (self.backend != None) and self.backend.curalg != self.algcombo.entry.get_text():
            self.backend.curalg = self.algcombo.entry.get_text()
            print self.backend.curalg
            self.show_options()
        return True

    def show_options(self):
        # the following line uses a simple lambda function to clear the
        #   optionbox of any widget so that we can add new options to it
        self.optionbox.foreach(lambda widget:self.optionbox.remove(widget))
        optionlist = list()

        # if the backend exists i.e. we're not testing, then get list of options
        #   from the backend. Otherwise, create a list of fake options.
        if self.backend != None:
            optionlist = self.backend.getOptions()
        else:
            for i in range(7):
                optionlist.append("Test" + "-" + str(i))

        # for each option in the optionlist, create a label for it (it's name),
        #   and create an entry for it. Connect the activation of the entry to
        #   the option_constant_callback function (see above). Then, pack both
        #   the label and entry into a box then pack that box into optionbox.
        #   Once that's all done, make sure to show them!
        for option in optionlist:
            label = gtk.Label(option)
            entry = gtk.Entry()
            entry.connect("activate", self.option_constant_callback, entry)
            hbox = gtk.HBox(False,0)
            hbox.pack_start(label, True, True, 0)
            hbox.pack_start(entry, True, True, 0)
            hbox.show()
            label.show()
            entry.show()
            self.optionbox.pack_start(hbox, True, True, 0)
        self.optionbox.show()
        

    def __init__(self, backend):
        # Reference to backend - used for getting data to display
        self.backend = backend

        # Create a gtk.Window and set default parameters
        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
        self.window.set_title("IMP Algorithms")
        self.window.set_border_width(0)
        self.window.set_size_request(300,300)

        # Connect the window to a delete event. Only necessary for testing
        self.window.connect("delete_event", self.delete_event)

        # mainbox is where everything gets packed into. However, it should never
        #   be touched after being created, so it only has a temporary reference
        #   instead of being a class member
        mainbox = gtk.VBox(False,0)
        self.window.add(mainbox)
        mainbox.show()

        # algcombo is a gtk.Combo, which is essentially a pull-down menu by
        #   another name. It allows the user to select an algorithm and then
        #   sends that information back to the backend. It is important to note
        #   that gtk.Combo is technically deprecated. However, it's replacement
        #   is overly complicated for what we're doing and is a potential
        #   system resource hog without careful coding - giving the deadline
        #   this program was created under, it's a risk I'd rather avoid 
        self.algcombo = gtk.Combo()
        self.algcombo.show()
        alist = list()  # alist is simply a list of algorithm names, supplied by
                        #   the backend
        if backend != None:
            for name in self.backend.getAlgorithmNames():
                alist.append(name)
        else:
            #This is only used for testing purposes only
            for i in range(10):
                alist.append("Test"+str(i))
        self.algcombo.set_popdown_strings(alist)
        self.algcombo.entry.set_editable(False)
        if backend != None:
            self.backend.setCurAlg(self.algcombo.entry.get_text())
        
        # algframe, like mainbox, shouldn't be referenced again as it's function
        #   is purely cosmetic.
        algframe = gtk.Frame("Algorithms")
        algframe.add(self.algcombo)
        mainbox.pack_start(algframe)
        algframe.show()

        # optionframe is another never-referenced-after-init variable (at least
        #   not referenced by the class).
        # optionscroll and optionbox, however, are both referenced after init
        #   since they change each time a new algorithm is chosen. optionscroll
        #   is a scrollingwindow - which allows an unlimited number of options,
        #   if the algorithm calls for it. optionbox is where the options are
        #   actually placed
        optionframe = gtk.Frame("Options")
        self.optionscroll = gtk.ScrolledWindow()
        self.optionscroll.set_policy(gtk.POLICY_AUTOMATIC, gtk.POLICY_ALWAYS)     
        optionframe.add(self.optionscroll)
        self.optionbox = gtk.VBox(False, 0)
        self.optionscroll.add_with_viewport(self.optionbox)
        self.optionbox.show()
        self.optionscroll.show()
        mainbox.pack_start(optionframe)
        optionframe.show()

        # show_options simply displays options based on which algorithm is
        #   currently selected
        self.show_options()

        # One of the few non-callback type functions. Combo only activates a
        #   callback when the enter key is pressed, not when you select an
        #   option. This is part of the reason why Combo is now deprecated, but
        #   eh.
        glib.timeout_add(100,self.update)

        self.window.show()

# The following bit of code allows this module to be called individually. In
#   that case, it only creates AlgWindow without any backend - any info required
#   is just dummy data.
if __name__ == "__main__":
    test = AlgWindow(None)
    gtk.main()

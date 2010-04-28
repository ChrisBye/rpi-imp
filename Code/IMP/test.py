import pygtk
pygtk.require('2.0')
import gtk

class Test:
    def delete_event(self, widget, event, data=None):
        gtk.main_quit()
        return False

    def __init__(self):
        # Create a new window and title it
        self.window = gtk.Window(gtk.WINDOW_TOPLEVEL)
        self.window.set_title("Peaches for Peaches")

        self.window.connect("delete_event", self.delete_event)
        self.window.set_border_width(0)
        self.window.set_size_request(600, 300)


        # mainBox ###########################################
        #                                                   #
        #  # graphBox ###############  algBox# ###########  #
        #  #                        #  #                 #  #
        #  # # graphDisplayBox #### #  # # algSelectBox# #  #
        #  # #                    # #  # #             # #  #
        #  # #                    # #  # #             # #  #
        #  # ###################### #  # ############### #  #
        #  #                        #  #                 #  #
        #  # # graphOptionBox ##### #  # # algOptionBox# #  #
        #  # #                    # #  # #             # #  #
        #  # #                    # #  # #             # #  #
        #  # ###################### #  # ############### #  #
        #  #                        #  #                 #  #
        #  ##########################  ###################  #
        #                                                   #
        #####################################################


        # Everything goes into mainBox
        self.mainBox = gtk.HBox(False,0)
        self.window.add(self.mainBox)

        # graphBox contains graph related widgets, while (surprise) algBox
        #   contains algorithm related widgets. Both go directly into mainBox
        self.graphBox = gtk.VBox(False,0)
        self.algBox = gtk.VBox(False,0)
        self.mainBox.add(self.graphBox)
        self.mainBox.add(self.algBox)

        # graphDisplayBox will contain the actual graph display, while
        #   graphOptionBox will contain any options related to the graph,
        #   including but not limited to Add and Remove for both Stocks and Algs
        self.graphDisplayBox = gtk.VBox(False,0)
        self.graphOptionBox = gtk.VBox(False,0)
        self.graphBox.add(self.graphDisplayBox)
        self.graphBox.add(self.graphOptionBox)

        # algSelectBox will contain the algorithm selection pulldown, while
        #   algOptionBox will contain a scrolled window which has all the
        #   associated options
        self.algSelectBox = gtk.VBox(False,0)
        self.algOptionBox = gtk.VBox(False,0)
        self.algBox.add(self.algSelectBox)
        self.algBox.add(self.algOptionBox)


        #DUMMY PLACEHOLDERS

        #graphDisplayBox
        image = gtk.Image()
        image.set_from_file("chaos.jpg")
        image.show()
        label1 = gtk.Label("graphDisplayBox")
        self.graphDisplayBox.pack_start(label1, False, False,0)
        self.graphDisplayBox.pack_start(image)
        label1.show()

        #graphOptionBox
        label2 = gtk.Label("graphOptionBox")
        self.graphOptionBox.pack_start(label2, False, False,0)
        label2.show()

        #algSelectBox
        combo = gtk.Combo()
        combo.show()
        combo.entry.set_text("Algorithm (0)")
        slist = []
        for i in range(4):
            buffer = "Algorithm (%d)" % (i)
            slist.append(buffer)
        combo.set_popdown_strings(slist)
        label3 = gtk.Label("algSelectBox")
        self.algSelectBox.pack_start(label3, False, False,0)
        self.algSelectBox.pack_start(combo, False, False,0)
        label3.show()

        #algOptionBox
        self.scroll_test = gtk.ScrolledWindow()
        self.scroll_test.set_policy(gtk.POLICY_AUTOMATIC, gtk.POLICY_ALWAYS)
        label4 = gtk.Label("algOptionBox")
        self.algOptionBox.pack_start(label4, False, False,0)
        label4.show()
        self.algOptionBox.pack_start(self.scroll_test, True, True, 0)
        tmpBox = gtk.VBox(False,0)
        self.scroll_test.add_with_viewport(tmpBox)
        for i in range(20):
            buffer = "AlgOption (%d)" % (i)
            button = gtk.ToggleButton(buffer)
            tmpBox.pack_start(button, True, True, 0)
            button.show()  

        # In actual program, should probably delegate the show to each part - 
        #   make everything nice and simple and only one call. W/E

        self.mainBox.show()
        self.graphBox.show()
        self.algBox.show()
        self.graphDisplayBox.show()
        self.graphOptionBox.show()
        self.algSelectBox.show()
        self.algOptionBox.show()
        self.scroll_test.show()
        tmpBox.show()

        self.window.show()

if __name__ == "__main__":
    test = Test()
    gtk.main()

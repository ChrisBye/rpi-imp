import sys, os, glob

class AlgBackend:
    def __init__(self):
        self.algorithms = dict()
        self.curalg = ""

        # This is a little bit of a hack. The .alg file is simply executable
        #   python code. In the final product, it should make sure to sanitize
        #   the input first - we are, after all, allowing the user to run
        #   arbitrary code.
        # After the .alg file is executed, there is a new entry in algorithms.
        #   with the key being the name of the file and the value being the 
        #   Algorithm class created by the file.
        # As noted, this is rather hackish.
        for inalg in glob.glob(os.path.join('Algorithms', "*.alg")):
            name = inalg.split(".alg")
            name[0] = name[0].split("/")
            execfile(inalg)

    def getAlgorithmNames(self):
        # Returns a list of all the names of the algorithms. Used by AlgWindow
        #   to display all the algorithms
        return self.algorithms.keys()

    def getOptions(self):
        # Returns a list of options - UserSetConstants - for the current
        #   algorithm. This is used by AlgWindow to display all the options to
        #   the user and allow them to enter options
        return self.algorithms[self.curalg].Options()

    def setCurAlg(self, algname):
        # Simply sets the current algorithm, though not before checking that
        #   the algorithm actually exists - no need to have weird errors
        #   resulting from references to non-existent algorithms
        if self.algorithms.has_key(algname):
            self.curalg = algname
        else:
            raise ValueError("self.algorithms does not contain " + algname)

# The following bit of code allows this module to be called individually. In
#   that case, it not only creates a backend but also a window to access that
#   backend. Useful for testing purposes.
if __name__ == "__main__":
    import pygtk
    pygtk.require('2.0')
    import gtk

    from AlgWindow import AlgWindow

    testBackend = AlgBackend()
    testWindow = AlgWindow(testBackend)
    gtk.main()

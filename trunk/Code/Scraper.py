import urllib
import sys
import time
import shutil

class Scraper:
    def __init__(self, symbols = None):
        if symbols != None:
            self.symbols = symbols
        else:
            self.symbols = list()

    def update(self):
        quotes = urllib.urlopen('http://finance.yahoo.com/d/quotes.csv?s='+'+'.join(self.symbols) + '&f=l1&e=.csv').read().split()
        
        for i in range(len(self.symbols)):
            fout = open("Tmp/tmpstock.txt",'w')
            fin = open("Data/" + self.symbols[i] + ".txt", 'r')
            lastdata = fin.readline()
            lastdata = lastdata.split(" ")
            #print lastdata
            if lastdata[0] != quotes[i]:
                fin.seek(0)
                data = fin.read()    
                fout.write(quotes[i] + " " + str(time.time()))
                fout.write('\n')
                fout.write(data)
                fout.close()
                shutil.copyfile("Tmp/tmpstock.txt", "Data/" + self.symbols[i] + ".txt")
            else:
                pass
            fout.close()
            fin.close()

    def add(self,symbol):
        # Check whether symbol is valid
        test = urllib.urlopen('http://finance.yahoo.com/d/quotes.csv?s='+'+'+symbol+ '&f=l1&e=.csv').read()
        if float(test) != 0:
            self.symbols.append(symbol)
        else:
            print symbol + " is not a valid stock"

if __name__ == "__main__":
    test = Scraper()
    test.add('ford')
    while(True):
        test.update()
        time.sleep(1)

import sys, os
import random
import time
from Helper.DataRange import *
from Helper.DataRangeShort import DataRangeShort
import urllib

class Stock:
    def __init__(self, symbol):
        self.symbol = symbol
        self.quotes = DataRange()
        self.quotesshort = DataRangeShort(64)
        self.average = None
        self.price = 0

        self.test = False
        if self.symbol == "test":
            # Part of the code that generates test stock info and not pertinent
            #   to the main code
            self.test = True
            self.price = 40
            self.lasttime = time.time()
            self.interval = random.randint(1,5)
            self.add(DataPoint(self.price, time.time()))
        else:
            # get the scraper
            pass

    def add(self, DP):
        self.quotes.add(DP)
        self.quotesshort.add(DP)

    def update(self):
        if self.test:
            # The following code simply generates a "random walk" - simulating
            #   stock prices rather naively. It's useful enough for our purposes
            if self.average == None:
                self.average = self.price
            else:
                self.average = (self.price + self.quotesshort.totalrange() * self.average) / (self.quotesshort.totalrange() + 1)
            if time.time() - self.lasttime >= self.interval:
                self.lasttime = time.time()
                self.interval = random.randint(1,5)
                self.price += random.random()*5
                self.price -= random.random()*5
                self.add(DataPoint(self.price, time.time()))
        else:
            # Currently weirdly broken. Just use 'test' for now until fixed
            curprice = urllib.urlopen('http://finance.yahoo.com/d/quotes.csv?s='+'+'.join(self.symbol) + '&f=l1&e=.csv').read().split()
            print curprice
            self.add(DataPoint(float(curprice[0]), time.time())) 

    def getMax(self):
        return self.quotesshort.getMax()

    def getMin(self):
        return self.quotesshort.getMin()

    def getQuote(self, getTime = None):
        if getTime:
            return self.quotesshort.getAtTime(getTime)
        elif self.test:
            return self.quotesshort.getAtTime()
        else:
            f = open("Data/" + self.symbol + ".txt", 'r')
            lastQuote = fin.readline()
            lastQuote = lastQuote.split(" ")
            return lastQuote[0]

if __name__ == "__main__":
    test = Stock("test")
    for i in range(60):
        test.update()
        print "Quote:",test.getQuote()
        time.sleep(1)

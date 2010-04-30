from Stock import Stock

class StockData:
    # StockData is essentially just a container class for all the stocks.
    #   That being said, if it was necessary for optimization, it would be
    #   pretty easy to add some sort of optimization code here (probably dealing
    #   with some sort of caching.
    def __init__(self):
        self.stocks = dict()

    def update(self):
        for stock in self.stocks.values():
            stock.update()

    def loadStock(self, symbol):
        if not self.stocks.has_key(symbol):
            self.stocks[symbol] = Stock(symbol)

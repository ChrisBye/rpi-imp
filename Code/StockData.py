from Stock import Stock

class StockData:
    def __init__(self):
        self.stocks = dict()

    def update(self):
        for stock in self.stocks.values():
            stock.update()

    def loadStock(self, symbol):
        if not self.stocks.has_key(symbol):
            self.stocks[symbol] = Stock(symbol)

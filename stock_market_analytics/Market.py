from yahoo_finance import Share
import time

class Market:
    __PULL_INTERVAL_IN_MIN = 5
            
    def __init__ (self, s):
        self.share_string       = s
        self.share              = Share(s)
        
        self.current_price      = self.share.get_price()
        self.price              = self.current_price
        self.market_cap         = self.share.get_market_cap()
        self.open_price         = self.share.get_open()
        self.prev_close_price   = self.share.get_prev_close()
        self.percent_change     = self.share.get_percent_change()
        
        self.price_array = []
        self.time_array = []
    
    
    def pullPrices(self):
        file_string = "share_" + self.share_string + ".dat"
        dataFile = open(file_string, "w+")
        start_time = int(time.strftime("%-S"))
        last_time = start_time
        
        while (1 == 1):
            current_time = int(time.strftime("%-S"))
            if (current_time >= 60):
                current_time = 0
            
            if (current_time - last_time < 0):
                last_time = -60 - (current_time - last_time)
                
            if (int(time.strftime("%-S")) - last_time >= self.__PULL_INTERVAL_IN_MIN):
                dataFile.write (time.strftime('%l:%M%p, %b %d, %Y') + "\t")
                dataFile.write (self.share.get_price())
                dataFile.write ("\n")
                dataFile.flush ()
                last_time = int(time.strftime("%-S"))
    
    
    
    def __storeData(self, filename):
        dataFile = open(filename, "r")
        lineList = dataFile.readLines()
        
        for i in range(len(lineList)):
            index1 = lineList[i].index('\t')
            price = float(lineList[i][index1:])
            price_array.append(price)
        
        dataFile.close() 
    
    
    def getSlope(self, filename):
        __storeData(filename)
        
        length = len(self.price_array)
        current_slope = (self.price_array[length] - self.price_array[length-1])/(self.time_array[length] - self.time_array[length-1])
        return current_slope
        
        
    def getSecondDegreeSlope(self, filename):
        __storeData(filename)
        
        length = len(price_array)
        current_slope = (self.price_array[length] - self.price_array[length-1])/(self.time_array[length] - self.time_array[length-1])
        last_slope = (self.price_array[length-1] - self.price_array[length-2])/(self.time_array[length-1] - self.time_array[length-2])
        
        current_secondDegreeSlope = (current_slope - last_slope)/(self.time_array[length] - self.time_array[length-1])
        
        return current_secondDegreeSlope
    
     
    
    
        
    

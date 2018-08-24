#from rtstock.stock import Stock
from yahoo_finance import Share
import datetime
#import pandas_datareader as panda
import pandas_datareader.data as panda
#import fix_yahoo_finance as yf
import numpy as np

class Stock:
    def __init__ (self, ticker):
        #self.stock = Share(ticker)
        self.ticker = ticker
        
        #days of training data to look at
        self.TRAIN_LENGTH = 200
        
        #days later to test against results
        self.RESULT_LENGTH = 7
        
        #threshold for a positive change
        self.CHANGE_THRESHOLD = 0.04

        self. Xdayhigh = 200
        
        self.inputSize = 120
        #yf.pdr_override()
        
        
    def getTrainData(self, year, month, day):
        #get current data
        #self.stock
        #get historical data table
        current_date = datetime.date(year, month, day)
        d = datetime.timedelta(days = self.TRAIN_LENGTH)
        start_date = current_date - d
        
        #find 50 day high
        oneYearAgo = datetime.timedelta(days = self.Xdayhigh)
        lastYear_date = current_date - oneYearAgo

        test_date = current_date + datetime.timedelta(days = self.RESULT_LENGTH+2)
        #data = panda.get_data_yahoo(self.ticker, start=start_date.strftime("%Y-%m-%d"),
        #                           end=test_date.strftime("%Y-%m-%d"))
        
        data = panda.DataReader(self.ticker, 'yahoo', start_date, test_date)


        #calculate 52 week high
        year_data = panda.DataReader(self.ticker, 'yahoo', lastYear_date, current_date)
        highest_price = 0
        highest_volume = 0
        for i in range(self.Xdayhigh):
            date = lastYear_date + datetime.timedelta(days = i)
            date_str = date.strftime("%Y-%m-%d")
            
            try:
                if year_data.ix[date_str]['Close'] > highest_price:
                    highest_price = year_data.ix[date_str]['Close']

                if year_data.ix[date_str]['Volume'] > highest_volume:
                    highest_volume = year_data.ix[date_str]['Volume']
            except:
                q = 1


        #print highest_price
        #print highest_volume
        #get relevant
        close_prices = []
        volumes = []
        lastprice = 0
        
        #print data.ix['2017-07-05']['Close']
        
        for i in range(self.TRAIN_LENGTH):
            date = start_date + datetime.timedelta(days = i)
            date_str = date .strftime("%Y-%m-%d")
            
            try:
                close_prices.append(data.ix[date_str]['Close']/highest_price)
                volumes.append(data.ix[date_str]['Volume']/highest_volume)
                lastprice = data.ix[date_str]['Close']
            except Exception as e:
                #print "weekend"
                #print e
                a = 0
        #print close_prices
        #print volumes
        
        #[0] - price goes down
        #[1] - price goes sideways
        #[2] - price goes up
        output = [0, 0, 0]
        test_price = 0
        
        #test if weekend
        try:
            test_price = data.ix[test_date.strftime("%Y-%m-%d")]['Close']
        except:
            test_price = data.ix[(test_date + datetime.timedelta(days = 2)).strftime("%Y-%m-%d")]['Close']
        
        if ((test_price-lastprice)/lastprice <= -self.CHANGE_THRESHOLD):
            output[0] = 1
        elif ((test_price-lastprice)/lastprice <= self.CHANGE_THRESHOLD):
            output[1] = 1
        elif ((test_price-lastprice)/lastprice >= self.CHANGE_THRESHOLD):
            output[2] = 1
            
        
        #return all lists catted together
        ret_list = close_prices + volumes + output
        #print ret_list
        
        return close_prices, volumes, output
    
    def saveTrainData(self, filename, year, month, day):
        
        try:
            close_prices, volumes, output = self.getTrainData(year, month, day)
            
            file = open(filename, "a")
            
            for i in range(len(close_prices)):
                file.write(str(close_prices[i]))
                file.write("\t")
            file.write("\n")
            
            for i in range(len(volumes)):
                file.write(str(volumes[i]))
                file.write("\t")
            file.write("\n")
            
            for i in range(len(output)):
                file.write(str(output[i]))
                file.write("\t")
            file.write("\n")
            
            file.close()
        except Exception as e:
            a = 0
    
        
    def getData (self):
       
        #get historical data table
        current_date = datetime.date.today()# - datetime.timedelta(days = 10)
        d = datetime.timedelta(days = self.TRAIN_LENGTH)
        start_date = current_date - d
        
        #find 50 day high
        oneYearAgo = datetime.timedelta(days = self.Xdayhigh)
        lastYear_date = current_date - oneYearAgo

        #test_date = current_date + datetime.timedelta(days = self.RESULT_LENGTH+2)
        
        data = panda.DataReader(self.ticker, 'yahoo', start_date, current_date)


        #calculate 50 day high
        year_data = panda.DataReader(self.ticker, 'yahoo', lastYear_date, current_date)
        highest_price = 0
        highest_volume = 0
        for i in range(self.Xdayhigh):
            date = lastYear_date + datetime.timedelta(days = i)
            date_str = date.strftime("%Y-%m-%d")
            
            try:
                if year_data.ix[date_str]['Close'] > highest_price:
                    highest_price = year_data.ix[date_str]['Close']

                if year_data.ix[date_str]['Volume'] > highest_volume:
                    highest_volume = year_data.ix[date_str]['Volume']
            except:
                q = 1


        
        close_prices = []
        volumes = []
        lastprice = 0
        #print highest_price
        #print highest_volume
        #print data.ix['2017-07-05']['Close']
        
        for i in range(self.TRAIN_LENGTH):
            date = start_date + datetime.timedelta(days = i)
            date_str = date .strftime("%Y-%m-%d")
            
            try:
                close_prices.append(data.ix[date_str]['Close']/highest_price)
                volumes.append(data.ix[date_str]['Volume']/highest_volume)
                lastprice = data.ix[date_str]['Close']
            except Exception as e:
                #print "weekend"
                #print e
                a = 0
        
        close_prices = close_prices[-self.inputSize:]
        volumes = close_prices[-self.inputSize:]
        # print close_prices
        # print volumes

        dataList = []
        data = []
        for i in range(self.inputSize):
            data.append(close_prices[i])
        for i in range(self.inputSize):
            data.append(volumes[i])
        dataList.append(data)
        return np.array(dataList)


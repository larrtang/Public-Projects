from stock import Stock
import getSP500 as sp
filename = "train_sp500.dat"
tickers = ['AAPL','GOOG',
           'ABBV','ATVI','AKAM','RCL','REGN','PEP','NVDA','CY','P',
           'CELG','KO','EQT','FB','SIRI','STRP','AAOI','SYX','MU','JNPR',
           'JNUG','ZIOP','GPRO','ODP','TWTR','BOX','SQ','HPQ','CSCO','STX',
           'FAST','AMD','MRK','PSX','F','DDR','CHS','BA','KMX','CHD','DISCA',
           'DG','DLTR','DD','EMN','OLED','SYMC','NFLX','HIMX','CA', 'CTSH',
           'COTY','COST','CMCSA','KO','DHI','EOG','ETFC','FITB','EA', 'ZIOP',
           'COP', 'PULM','LYG','OCLR','GG','PENN','DHT', 'GV','WWE', 'PYPL', 'FOX',
           'QCOM']

tickers = sp.save_sp500_tickers()


#AAPL = Stock('AAPL')
#GOOG = Stock('GOOG')
#ABBV = Stock('ABBV')
#ATVI = Stock('ATVI')
#AKAM = Stock('AKAM')
#CELG = Stock('CELG')
#KO = Stock('KO')
#EQT = Stock('EQT')
#FB = Stock('FB')
#FAST = Stock('FAST')
#AMD = Stock('AMD')
#MRK = Stock('MRK')
#PSX = Stock('PSX')
#QCOM = Stock('QCOM')


def batchSave(year, month, day):
    for t in tickers:
        try:
            Stock(t).saveTrainData(filename, year, month, day)
            print t, " done\t"
        except Exception as e:
            print e
            
    
    #Stock(tickers[0]).saveTrainData(filename, year, month, day)
    print "Batch saved"

# batchSave(2017,1,2)
# batchSave(2017,1,10)
# batchSave(2017,1,20)
# batchSave(2017,2,1)
# batchSave(2017,2,10)
# batchSave(2017,2,20)
# batchSave(2017,3,1)
# batchSave(2017,3,10)

# batchSave(2016,1,1)
# batchSave(2016,1,10)
# batchSave(2016,1,20)
# # batchSave(2016,2,1)
# batchSave(2016,2,10)
# batchSave(2016,2,20)
# batchSave(2016,3,1)
# batchSave(2016,3,10)

# batchSave(2017,8,1)
# batchSave(2017,5,1)
# batchSave(2017,5,10)

# # batchSave(2016,12,1)
# batchSave(2016,10,12)
# batchSave(2016,8,4)
# batchSave(2016,6,6)
# batchSave(2016,4,4)

# batchSave(2017, 4, 2)
# batchSave(2017, 3, 20)
# batchSave(2017, 5, 20)

#batchSave(2017,12,20)
batchSave(2017,10,20)
batchSave(2017,12,1)
#Stock('AAPL').getTrainData(2015,12,1)


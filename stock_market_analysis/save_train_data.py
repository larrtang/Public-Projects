from stock import Stock
import getSP500 as sp
filename = "train_sp500.dat"
# tickers = ['AAPL','GOOG',
#            'ABBV','ATVI','AKAM','RCL','REGN','PEP','NVDA','CY','P',
#            'CELG','KO','EQT','FB','SIRI','STRP','AAOI','SYX','MU','JNPR',
#            'JNUG','ZIOP','GPRO','ODP','TWTR','BOX','SQ','HPQ','CSCO','STX',
#            'FAST','AMD','MRK','PSX','F','DDR','CHS','BA','KMX','CHD','DISCA',
#            'DG','DLTR','DD','EMN','OLED','SYMC','NFLX','HIMX','CA', 'CTSH',
#            'COTY','COST','CMCSA','KO','DHI','EOG','ETFC','FITB','EA', 'ZIOP',
#            'COP', 'PULM','LYG','OCLR','GG','PENN','DHT', 'GV','WWE', 'PYPL', 'FOX',
#            'QCOM']

tickers = sp.save_sp500_tickers()

def batchSave(year, month, day):
    print month, "/", day, "/", year
    for t in tickers:
        try:
            Stock(t).saveTrainData(filename, year, month, day)
            print t
        except Exception as e:
            print e
            
    
    #Stock(tickers[0]).saveTrainData(filename, year, month, day)
    print "Batch saved\n"

# batchSave(2017,12,20)
# batchSave(2017,10,20)
# batchSave(2017,8,20)
# batchSave(2016,12,20)
# batchSave(2016,4,20)
batchSave(2017,4,20)
batchSave(2017,9,10)
batchSave(2017,11,10)



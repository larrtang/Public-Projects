import datetime

class top10:

    def __init__ (self):
        self.tickerList = []
        self.probList = []
        self.probupList = []

        self.maxSize = 30
        #self.size = 0
        
    def add(self, ticker, prob, probup):
        if len(self.tickerList) == 0:
            self.tickerList.append(ticker)
            self.probList.append(prob)
            self.probupList.append(probup)

        elif probup > self.probupList[0]:
            self.tickerList.insert(0, ticker)
            self.probList.insert(0, prob)
            self.probupList.insert(0, probup)
        else:
            for i in range(len(self.tickerList)):
                if (probup > self.probupList[len(self.probupList) - i - 1]) and (probup < self.probupList[len(self.probupList) - i - 2]):
                    self.tickerList.insert(len(self.tickerList) - i - 1, ticker)
                    self.probList.insert(len(self.probList) - i - 1, prob)
                    self.probupList.insert(len(self.probupList) - i - 1, probup)
                    break                        
                
        if (len(self.tickerList) > self.maxSize):
            del self.tickerList[self.maxSize]
            del self.probList[self.maxSize]
            del self.probupList[self.maxSize]
            return
            
            

                    
        

    def remove(self, ticker):
        print "a"


    def getLists(self):
        return self.tickerList, self.probList, self.probupList

    def printLists(self):
        for i in range(len(self.tickerList)):
            print self.tickerList[i], "\t", self.probList[i], "\t\t", self.probupList[i]
        print
        

    def saveList(self, filename):
        now = datetime.datetime.now()
        with open(filename, "w+") as f:
            time = now.strftime("%Y-%m-%d %H:%M")
            f.write(time)
            f.write('\n\n')
            for i in range(len(self.tickerList)):
                line = self.tickerList[i]+ "\t"+ str(self.probList[i])+ "\t\t"+ str(self.probupList[i]) + '\n'
                f.write(line)


    def saveListHTML(self, filename):
        now = datetime.datetime.now()
        with open(filename, "w+") as f:
            f.write("<html><body>")
            time = now.strftime("%Y-%m-%d %H:%M")
            f.write(time)
            f.write('\n\n')
            
            for i in range(len(self.tickerList)):
                line = self.tickerList[i]+ "&nbsp;&nbsp;&nbsp;&nbsp;"+ str(self.probList[i])+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ str(self.probupList[i]) + '<br/>'
                f.write(line)

            f.write("</body></html>")
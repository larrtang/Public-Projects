class Matrix:
	
	def __init__ (self, matrix=None):
		if (matrix == None):
			self.__row = 0
			self.__col = 0
			self.__length = 0
			self.__matrixArray = [0]
	
		else:
			self.__row = matrix.getRow()
			self.__col = matrix.getCol()
			self.__length = matrix.getCol() * matrix.getRow()
			self.__matrixArray = matrix.getArray()
	
	def __init__ (self, x, y, array=None):
		if (array == None):
			self.__row = x;
			self.__col = y;
			self.__length = x*y;
			self.__matrixArray = [0] * x * y
		else:
			self.__row = x;
			self.__col = y;
			self.__length = x*y;
			self.__matrixArray = array
	
	
	def getValue (self, x, y):
		if (x >= self.row or y >= self.col):
			print "The index you choose was out of bound."
			return 0.0
		
		return self.__matrixArray[x*self.col + y]
	
	def getValue (self, i):
		if (i >= self.__length):
			print "The index you choose was out of bound."
			return 0.0
		
		return self.__matrixArray[i]
	
	def setValue(self, x, y, value):
		if (x >= row or y >= col):
			print "The index you choose was out of bound."
		
		else:
			self.__matrixArray[x*self.col + y] = value;
	
	def setValue(self, i, value):
		if (i >= self.__length):
			print "The index you choose was out of bound."
		
		else:
			self.__matrixArray[i] = value
	
	
	def getRow (self):
		return self.__row
	def getCol (self):
		return self.__col
	def getLength (self):
		return self.__length
	def getArray (self):
		return self.__matrixArray
	
	def printMatrix (self):	
		r = 0
		c = 0
		for i in range(self.__length):
			if (c >= self.__col):
				print
				c = 0
			print self.getValue(i),
			print "\t",
			c += 1
		print "\n"
		 
	
			
			
			
			

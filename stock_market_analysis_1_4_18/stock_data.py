import tensorflow as tf
import numpy as np
def getInputData(filename):
    data_num = 0
    dataList = []
    
    file = open(filename, "r")
    
    
    #read each line
    inputs_str = []
    inputs = []
    i = 0
    for line in file:
        
        data = line.split("\t")
        #print data
        if i == 0 or i == 1:
            data.pop()
            inputs_str += data[-30:]
            i += 1
        
        elif i == 2:
            #print inputs_str
            #print inputs_str
            #print len(inputs_str)
            
            for i in range (len(inputs_str)):
                inputs.append(float(inputs_str[i]))
            
            #inputs = np.array(inputs, dtype=float).reshape(1,60)
            #inputs = tf.convert_to_tensor(inputs, dtype=tf.float32)
            dataList.append(inputs)
            #print len(inputs)        
            inputs_str = []
            inputs = []
            
            data_num += 1
            #print data_num
            i = 0
    
    print np.array(dataList).shape
    print
    print np.transpose(np.array(dataList))
    return np.array(dataList)

        
def getOutputData(filename):
    dataList = []
    
    file = open(filename, "r")
    
    
    #read each line
    i = 0
    outputs = []
    for line in file:
        data = line.split("\t")
        
        if i == 0 or i == 1:
            i += 1
            
        elif i == 2:
            
            data.pop(len(data)-1) #remove \n
            
            for i in range(len(data)):
                outputs.append(float(data[i]))
            
            #outputs = np.array(outputs, dtype=float)
            
            dataList.append(outputs)
            
            outputs = []
            
            i = 0
    #print dataList
    
    return np.array(dataList)

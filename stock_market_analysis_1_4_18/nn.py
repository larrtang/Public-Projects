import tensorflow as tf
import numpy as np
#mnist = input_data.read_data_sets("./", one_hot=True)
import stock_data as sd
from stock import Stock
import getSP500 as sp
from top10 import top10
import datetime

input_data = sd.getInputData("train_sp500.dat")
actual_output = sd.getOutputData("train_sp500.dat")
model = "model/model.ckpt"

INPUT_SIZE = 60
HLAYER1_SIZE = 200
HLAYER2_SIZE = 300
HLAYER3_SIZE = 100
#HLAYER4_SIZE = 62

NUM_CLASSES = 3
BATCH_SIZE = 200

x = tf.placeholder('float', [None, INPUT_SIZE])
y = tf.placeholder('float')


def neuralNetwork (data):
    
    # input * weight + biases
    
    hlayer1 = {'weights' : tf.Variable(tf.random_normal([INPUT_SIZE, HLAYER1_SIZE])),
               'biases' : tf.Variable(tf.random_normal([HLAYER1_SIZE]))}
               
    hlayer2 = {'weights' : tf.Variable(tf.random_normal([HLAYER1_SIZE, HLAYER2_SIZE])),
               'biases' : tf.Variable(tf.random_normal([HLAYER2_SIZE]))}
            
    hlayer3 = {'weights' : tf.Variable(tf.random_normal([HLAYER2_SIZE, HLAYER3_SIZE])),
               'biases' : tf.Variable(tf.random_normal([HLAYER3_SIZE]))}
    '''
    hlayer4 = {'weights' : tf.Variable(tf.random_normal([HLAYER3_SIZE, HLAYER4_SIZE])),
               'biases' : tf.Variable(tf.random_normal([HLAYER4_SIZE]))}
    '''
    output_layer = {'weights' : tf.Variable(tf.random_normal([HLAYER3_SIZE, NUM_CLASSES])),
                    'biases' : tf.Variable(tf.random_normal([NUM_CLASSES]))}
                    
    
    l1 = tf.add(tf.matmul(data, hlayer1['weights']), hlayer1['biases'])
    l1 = tf.nn.sigmoid(l1)
    
    l2 = tf.add(tf.matmul(l1, hlayer2['weights']), hlayer2['biases'])
    l2 = tf.nn.sigmoid(l2)
    
    l3 = tf.add(tf.matmul(l2, hlayer3['weights']), hlayer3['biases'])
    l3 = tf.nn.sigmoid(l3)
    
    #l4 = tf.add(tf.matmul(l3, hlayer4['weights']), hlayer4['biases'])
    #l4 = tf.nn.sigmoid(l4)
    
    output = tf.add(tf.matmul(l3, output_layer['weights']), output_layer['biases'])

    #output = tf.add(tf.matmul(l4, output_layer['weights']), output_layer['biases'])
    
    
    return output
    
def train (x) :
    prediction = neuralNetwork(x)
    cost = tf.reduce_mean (tf.nn.sigmoid_cross_entropy_with_logits(logits=prediction, labels=y))
    #cost = tf.reduce_sum(y*tf.log(prediction))
    optimizer = tf.train.AdamOptimizer().minimize(cost)
    #optimizer = tf.train.GradientDescentOptimizer(0.01).minimize(cost)
    #cycles of feed forward + back prop
    epochs = 500
    
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())
        saver = tf.train.Saver()
        
        try :
            saver.restore(sess, model)
        except:
            print 
        for epoch in range(epochs):
            epoch_loss = 0
            
            i = 0
            while i < len(input_data):
                start = i
                end = i + BATCH_SIZE
                
                #batch_x = np.array(input_data[start:end])
                #batch_y = np.array(actual_output[start:end])
                
                batch_x = input_data[start:end]
                batch_y = actual_output[start:end]

                _, c = sess.run([optimizer, cost], feed_dict = {x:batch_x, y:batch_y})
                epoch_loss += c
                i += BATCH_SIZE
                
                #print batch_y
                
            
            #print(epoch, ": loss: ", epoch_loss)
            print "epoch: " ,
            print epoch ,
            print "loss: " ,
            print epoch_loss ,
            print   
            
            
        correct = tf.equal(tf.argmax(prediction, 1), tf.argmax(y,1))
        accuracy = tf.reduce_mean(tf.cast(correct, 'float'))
        #print ('Accuracy: ', accuracy.eval({x:mnist.test.images, y:mist.test.labels}))
        print "Accuracy:" ,
        print accuracy.eval({x:input_data, y:actual_output}) ,
        print
        
        save_path = saver.save(sess, model)


def train_evaluate (x):
    shit = input_data[0:100,]
    #print shit
    #print shit.shape
    output = neuralNetwork(x)
    
    #print np.transpose(input_data[0]).reshape(1,60).shape
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())
        
        saver = tf.train.Saver()
        saver.restore(sess, model)

        #print output.shape
        #print sess.run(output, feed_dict={x:shit})
        print sess.run(output, feed_dict={x:shit})

        print actual_output[0:100,]
        save_path = saver.save(sess, model)
    
#train(x)
def evaluate (x, ticker):
    inputData = Stock(ticker).getData()
    #print shit
    #print shit.shape
    output = neuralNetwork(x)
    
    #print np.transpose(input_data[0]).reshape(1,60).shape
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())
        
        saver = tf.train.Saver()
        saver.restore(sess, model)

        #print output.shape
        #print sess.run(output, feed_dict={x:shit})
        print sess.run(output, feed_dict={x:inputData})


def evaluate_list(x, ticker_list):
    top = top10()

    #print shit
    #print shit.shape
    output = neuralNetwork(x)
    
    #print np.transpose(input_data[0]).reshape(1,60).shape
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())
        
        saver = tf.train.Saver()
        saver.restore(sess, model)
        for t in ticker_list:
            try:
                inputData = Stock(t).getData()
            
                #print output.shape
                #print sess.run(output, feed_dict={x:shit})
                print t, "\t",
                out =  sess.run(output, feed_dict={x:inputData})

                print out
                
                # top.add(t, out, -(out.tolist()[0][2] - out.tolist()[0][0]))       
                top.add(t, out,out.tolist()[0][2])             
      
                top.printLists()
            except Exception as e:
                print e
        filename = "quotes/quote_"+ datetime.datetime.now().strftime("%Y_%m_%d_%H_%M")
        top.saveList(filename)

#train(x)
#train_evaluate(x)
#evaluate(x, 'SOFO')
#evaluate(x, 'amd')

def run():
    print "Running..."
    evaluate_list(x, sp.save_sp500_tickers())



run()
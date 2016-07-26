#include "NeuralNetwork.h"

int main () {
	NeuralNetwork neuralNetwork;
	neuralNetwork.addInputLayer(2);
	neuralNetwork.addHiddenLayer(3);
	neuralNetwork.addHiddenLayer(3);
	neuralNetwork.addHiddenLayer(2);
	neuralNetwork.addOutputLayer(1);
	
	double data[12*2]  = {10,10,
						  10,4 ,
						  2 ,3 ,
						  5 , 6,
						  7 , 6,
						  10, 0,
						  0 ,10,
						   4,10,
						   3,2 ,
						   0, 0,
						   10,10,
						   2,2};
	
	
	double data2 [2*2]  = {0,0,
						10,10};
						
	double data3 [4*2]  = {0,3,
							0,6,
							0,4,
							0,2};					
	Matrix in (4,2, data3);
	
	double train[12*1] = {0.9,0.8,0.5,0.7,0.85,0.75,0.6,0.7, 0.55, 0.4,0.9,0.5};
	double train2 [2*1] = {0.3,0.9};
	double train3 [4*1] = {0.3,0.6,0.4,0.2};
	Matrix c (4, 1, train3);
	
	
	
	
	

	Matrix out = neuralNetwork.train (in, c);
	//cout <<"output:\n";
	//out.printMatrix();
	
	
	Matrix in1 (1,2);
	in1.setValue(0,0, 0);
	in1.setValue(0,1, 3);
	Matrix in2 (1,2);
	in2.setValue(0,0, 0);
	in2.setValue(0,1, 6);
	Matrix out1 = neuralNetwork.evaluate(in1);
	out1.printMatrix();
	Matrix out2 = neuralNetwork.evaluate(in2);
	out2.printMatrix();
	
	Matrix test = neuralNetwork.evaluate(in);
	test.printMatrix();
	
	return 0;
}


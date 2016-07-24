#include "NeuralNetwork.h"

int main () {
	NeuralNetwork neuralNetwork;
	neuralNetwork.addInputLayer(2);
	neuralNetwork.addHiddenLayer(20);
	neuralNetwork.addHiddenLayer(20);
	neuralNetwork.addHiddenLayer(20);
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
						   20,20,
						   2,2};
	Matrix in (12,2, data);
	
	double train[12*1] = {1,0.8,0.5,0.7,0.85,0.75,0.6,0.7, 0.55, 0.4,1.5,0.5};
	Matrix c (12, 1, train);
	
	

	for (int i = 0; i < 1000; i++) {
		Matrix out = neuralNetwork.train (in, c);
		cout <<"output:\n";
		out.printMatrix();
	}
	
	Matrix in1 (1,2);
	in1.setValue(0,0, 0);
	in1.setValue(0,1, 0);
	Matrix in2 (1,2);
	in2.setValue(0,0, 10);
	in2.setValue(0,1, 10);
	Matrix out1 = neuralNetwork.evaluate(in1);
	out1.printMatrix();
	Matrix out2 = neuralNetwork.evaluate(in2);
	out2.printMatrix();
	return 0;
}


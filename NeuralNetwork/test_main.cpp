#include "NeuralNetwork.h"
#include <cstdio>

int main (int argc, char ** argv) {
	NeuralNetwork neuralNetwork;
	neuralNetwork.addInputLayer(2);
	neuralNetwork.addHiddenLayer(3);
	neuralNetwork.addHiddenLayer(4);
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
						
	double data3 [7*2]  = {	0,1,
							0,2,
							0,3,
							0,4,
							0,5,
							0,6,
							0,7};					
	Matrix in (7,2, data3);
	
	double train[12*1] = {0.9,0.8,0.5,0.7,0.85,0.75,0.6,0.7, 0.55, 0.4,0.9,0.5};
	double train2 [2*1] = {0.3,0.9};
	double train3 [7*1] = {0.1,0.2,0.3,0.4,0.5,0.6, 0.7};
	Matrix c (7, 1, train3);
	
	
	
	while (0) {
		double a, b;
		printf ("Input: ");
		scanf ("%lf %lf",&a, &b);
		Matrix input (1,2);
		input.setValue(0,0, a);
		input.setValue(0,1, b);
		Matrix output = neuralNetwork.evaluate(input);
		output.printMatrix();

		double c;
		printf ("Correct: ");
		scanf ("%lf",&c);
		Matrix correct (1,1);
		correct.setValue(0,0, c);
		output = neuralNetwork.train(input, correct);
		output.printMatrix();
	}
	

	Matrix out = neuralNetwork.train (in, c);
	cout <<"output:\n";
	out.printMatrix();
	
	
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


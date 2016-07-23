#include "NeuralNetwork.h"

int main () {
	NeuralNetwork neuralNetwork;
	neuralNetwork.addInputLayer(2);
	neuralNetwork.addHiddenLayer(3);
		neuralNetwork.addHiddenLayer(4);
	neuralNetwork.addOutputLayer(1);
	
	Matrix in (1,2);
	
	in.setValue(0,0, 1);
	in.setValue(0,1, 2);
	//in.printMatrix();
	
	Matrix out = neuralNetwork.evaluate (in);
	
	out.printMatrix();
	return 0;
}


/**
 *	Note: Messing with the descentMultiplier and randomWidth 
 *	in NeuralNetwork.h can affect your learning accuracy.
 *	Test out which combinations give you better results.
 */

#include "NeuralNetwork.h"
#include "MatrixBuilder.h"
#include <cstdio>

int main (int argc, char ** argv) {

	NeuralNetwork * neuralNetwork = new NeuralNetwork(SIG);

	//even odd example

	double inputs [8 * 3]  = {0,0,0,
							  0,0,1,
							  0,1,0,
							  0,1,1,
							  1,0,0,
							  1,0,1,
							  1,1,0,
							  1,1,1};

    double outputs [8*1] = {1,0,1,0,1,0,1,0};

	neuralNetwork->addInputLayer(3);
	neuralNetwork->addHiddenLayer(8);
	neuralNetwork->addHiddenLayer(5);
	neuralNetwork->addOutputLayer(1);

	MatrixBuilder in (8,3, inputs);
	MatrixBuilder correct (8,1, outputs);

	Matrix out = neuralNetwork->train(in.getMatrix(), correct.getMatrix());
	out.printMatrix();

	return 0;
}


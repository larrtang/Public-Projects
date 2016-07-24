#ifndef NN_H
#define NN_H

#include "Matrix.h"
#include <cstdlib>
#include <iostream>
#include <vector>
#include <cmath>
#include <stdbool.h>

using namespace std; 

class NeuralNetwork {
	protected:	
		vector <Matrix> weightMatrices;
		vector <Matrix> gradientChange;
		vector <int> layerNeuronCount;		//each index indicates how many neurons are in that layer
		vector <Matrix> z;					//individual z values of layers
		vector <Matrix> layerOutputs;		//individual outputs of layers
		vector <Matrix> temp;	
		bool hasInputLayer;
		bool hasOutputLayer;
		int numLayers;
		int numHiddenLayers;
		int numInputs;
		int numOutputs;
		
	public:
		double descentMultiplier = 0.01;
		
		NeuralNetwork ();
		//the next 3 functions must be done in  the specific order.
		void addInputLayer (int num_input);
		bool addHiddenLayer (int num_hidden);	//can be called multiple times
		bool addOutputLayer (int num_output);
		
		Matrix train (Matrix input_train, Matrix output_train);
		Matrix evaluate (Matrix input);
		
		Matrix forwardPropagate (Matrix input_matrix, int currentLayer);
		void backPropagate (Matrix input, Matrix output, Matrix correct_output);
		//dJ/dW (matrix of partials)
		Matrix derrorFunction (Matrix input, Matrix output, Matrix correct_output, int currentLayer, Matrix& delta);
		
		static double sigmoid (double z) {
			return 1/(1+exp(-z));
		}
		static Matrix dsigmoid (Matrix z) {
			Matrix newMatrix = z;
			for (int i = 0; i < z.getLength(); i++) {
				newMatrix.setValue (i, sigmoid(z.getValue(i)));
			}
			return newMatrix;
		}
};

#endif


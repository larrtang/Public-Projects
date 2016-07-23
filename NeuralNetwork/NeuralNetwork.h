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
		vector <int> layerNeuronCount;	//each index indicates how many neurons are in that layer
		bool hasInputLayer;
		bool hasOutputLayer;
		int numLayers;
		int numHiddenLayers;
		int numInputs;
		int numOutputs;
		
	public:
		NeuralNetwork ();
		//the next 3 functions must be done in  the specific order.
		void addInputLayer (int num_input);
		bool addHiddenLayer (int num_hidden);	//can be called multiple times
		bool addOutputLayer (int num_output);
		
		Matrix train (Matrix input_train, Matrix output_train);
		Matrix evaluate (Matrix input);
		
		Matrix forwardPropagate (Matrix input_matrix, int currentLayer);
		
		static double sigmoid (double z) {
			return 1/(1+exp(-z));
		}
		static double sigmoid (double z) {
			return exp(-z)/(pow(1+exp(-z),2));
		}
};

#endif


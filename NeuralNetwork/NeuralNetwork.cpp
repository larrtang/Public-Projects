#include "NeuralNetwork.h"

//NeuralNetwork::NeuralNetwork () {
//	hasInputLayer = false;
//	hasOutputLayer = false;
//	numLayers = 0;
//	numHiddenLayers = 0;
//}

void NeuralNetwork::addInputLayer (int num_input) {
	if (hasInputLayer) {
		cerr <<"Input layer already present." << endl;
		//return false;
	}
	hasInputLayer = true;
	numInputs = num_input;
	numLayers++;
	layerNeuronCount.push_back(num_input);
	
	//return true;
}

bool NeuralNetwork::addHiddenLayer (int num_hidden) {
	if (hasOutputLayer) {
		cerr <<"Output layer already present." << endl;
		return false;
	}
	numHiddenLayers++;
	numLayers++;
	layerNeuronCount.push_back(num_hidden);
	
	//this initializes a matrix obj that has the num rows from last layer size, 
	//number of col from current layer size.
	Matrix layerWeightMatrix (layerNeuronCount[layerNeuronCount.size()-2], 
								layerNeuronCount[layerNeuronCount.size()-1]);
								
	weightMatrices.push_back(layerWeightMatrix);
	
	return true;
}

bool NeuralNetwork::addOutputLayer (int num_output) {
	if (hasOutputLayer) {
		cerr <<"Output layer already present." << endl;
		return false;	
	}
	numLayers++;
	numOutputs = num_output;
	layerNeuronCount.push_back(num_output);
	
	//this initializes a matrix obj that has the num rows from last layer size, 
	//number of col from current layer size.
	Matrix layerWeightMatrix (layerNeuronCount[layerNeuronCount.size()-2], 
								layerNeuronCount[layerNeuronCount.size()-1]);
								
	weightMatrices.push_back(layerWeightMatrix);	
	
	return true;
}


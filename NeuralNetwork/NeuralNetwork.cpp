#include "NeuralNetwork.h"


NeuralNetwork::NeuralNetwork() {
	hasInputLayer = false;
	hasOutputLayer = false;
	numLayers = 0;
	numHiddenLayers = 0;
}

bool NeuralNetwork::addInputLayer (int num_input) {
	if (hasInputLayer) {
		cerr <<"Input layer already present." << endl;
		return false;
	}
	hasInputLayer = true;
	numInputs = num_input;
	numLayers++;
	layerNeuronCount.push_back(num_input);
	
	return true;
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
	
	layerWeightMatrix.randomize();
	weightMatrices.push_back(layerWeightMatrix);
	//note that weightMatrices's size will be one less of layerNeuronCount.
	
	return true;
}

bool NeuralNetwork::addOutputLayer (int num_output) {
	if (hasOutputLayer) {
		cerr <<"Output layer already present." << endl;
		return false;	
	}

	hasOutputLayer = true;
	numLayers++;
	numOutputs = num_output;
	layerNeuronCount.push_back(num_output);
	
	//this initializes a matrix obj that has the num rows from last layer size, 
	//number of col from current layer size.
	Matrix layerWeightMatrix (layerNeuronCount[layerNeuronCount.size()-2], 
								layerNeuronCount[layerNeuronCount.size()-1]);
	
	layerWeightMatrix.randomize();							
	weightMatrices.push_back(layerWeightMatrix);	
	
	return true;
}


Matrix NeuralNetwork::forwardPropagate (Matrix input_matrix, int currentLayer) {
	//this->weightMatrices[currentLayer].printMatrix();
	return input_matrix * this->weightMatrices[currentLayer];
}



Matrix NeuralNetwork::evaluate (Matrix input) {
	z.erase(z.begin(), z.end());
	layerOutputs.erase(layerOutputs.begin(), layerOutputs.end());

	if (input.getCol() != numInputs) {
		cerr << "Input matrix does not match input size." << endl;
		return Matrix(0,0);
	}
	
	Matrix output;
	for (int currentLayer = 0; currentLayer < numLayers-1; currentLayer++) {
		if (currentLayer == 0) 
			output = forwardPropagate (input, currentLayer);
		else
			output = forwardPropagate (output, currentLayer);
		
		//save the z values b4 sigmoid
		z.push_back(output);
		
		//apply tanh function to matrix.
		for (int i = 0; i < output.getLength(); i++) {
			output.setValue(i, tanh(output.getValue(i)));
		}
	
		//save individual layer output
		layerOutputs.push_back(output);
	}
	return output;
}



Matrix NeuralNetwork::derrorFunction (Matrix input, Matrix output, 
										Matrix correct_output, int currentLayer, 
										Matrix& delta) {
	Matrix dJdW;
	
	//check if output layer
	if (currentLayer == numLayers-2) {
		Matrix difference = correct_output - output;
		difference = difference.scalarMultiply(-1);
	 	delta = difference.scalarMultiply(dtanh(z[currentLayer]));
	 	dJdW = input.transpose() * delta;
	}
	//check if input layer or hidden layer
	else {
		delta = delta.multiply(weightMatrices[currentLayer+1].transpose());
		delta = delta.scalarMultiply(dtanh(z[currentLayer]));
		dJdW = input.transpose() * delta;
	}
	
	
	return dJdW;
}

void NeuralNetwork::backPropagate (Matrix input, Matrix output, Matrix correct_output) {
	gradientChange.erase(gradientChange.begin(), gradientChange.end());
	Matrix delta;
	
	for (int currentLayer = numLayers-2; currentLayer >= 0; currentLayer--) {
		Matrix derrorMatrix;
		//check if input layer
		if (currentLayer == 0) {
			derrorMatrix = derrorFunction (input, output, correct_output, currentLayer, delta);
		}
		else {
			derrorMatrix = derrorFunction (this->layerOutputs[currentLayer-1], output, correct_output,
													currentLayer, delta);
		}
	
		
		Matrix scaledDerrorMatrix = derrorMatrix.scalarMultiply(descentMultiplier);
		this->gradientChange.push_back (scaledDerrorMatrix);
	}
	//reorganize the vector
	vector<Matrix> temp = this->gradientChange;
	for (int i = 0; i < temp.size(); i++) {
		gradientChange[i] = temp[temp.size()-i-1];
	}
}


Matrix NeuralNetwork::step_train (Matrix input_train, Matrix output_train) {
	Matrix output = this->evaluate (input_train);
	backPropagate (input_train, output, output_train);
	for (int currentLayer = 0; currentLayer < weightMatrices.size(); currentLayer++) {
		weightMatrices[currentLayer] = weightMatrices[currentLayer] - gradientChange[currentLayer];
		weightMatrices[currentLayer].printMatrix();
	}

	return output;
}



Matrix NeuralNetwork::train(Matrix input_train, Matrix output_train) {

	Matrix output;
	
	int iteration = 0;
	double greatestGradient = 100;
	double greatestError = 100;
	while (abs(greatestGradient) > this->gradientThreshold && iteration < this->maxIteration) {
		greatestGradient = 0;
		
		output = this->evaluate(input_train);
		//output.printMatrix();
		backPropagate (input_train, output, output_train);

		//update weights, check if the gradientChange is enough
		for (int currentLayer = 0; currentLayer < weightMatrices.size(); currentLayer++) {
		
			//update the weights
			weightMatrices[currentLayer] = weightMatrices[currentLayer] - gradientChange[currentLayer];
			//weightMatrices[currentLayer].printMatrix();
			//gradientChange[currentLayer].printMatrix();
			
			for (int i = 0; i < gradientChange[currentLayer].getLength(); i++) {
				if (abs(gradientChange[currentLayer].getValue(i)) > greatestGradient) {

					greatestGradient = abs(gradientChange[currentLayer].getValue(i));
					cout <<&gradientChange[currentLayer]<< "\t" << gradientChange[currentLayer].getValue(i)<< endl;
				}
			}
		}
		cout << "Iteration " << iteration <<", Highest gradient: " << greatestGradient << endl;
		
		iteration++;
		output = this->evaluate(input_train);

	}

	
	return output;	
}



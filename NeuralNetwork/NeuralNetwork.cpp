#include "NeuralNetwork.h"


NeuralNetwork::NeuralNetwork() {
	hasInputLayer = false;
	hasOutputLayer = false;
	numLayers = 0;
	numHiddenLayers = 0;
	setThresholdFunction (TANH);
}

NeuralNetwork::NeuralNetwork(int func) {
	hasInputLayer = false;
	hasOutputLayer = false;
	numLayers = 0;
	numHiddenLayers = 0;
	setThresholdFunction (func);
}

void NeuralNetwork::setThresholdFunction (int func) {
	this->thresholdFunction = func;
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
	
	layerWeightMatrix.randomize(randomWidth);
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
	
	layerWeightMatrix.randomize(randomWidth);							
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
		
		//apply function to matrix.
		switch (thresholdFunction) {
			case TANH:
				for (int i = 0; i < output.getLength(); i++) {
					output.setValue(i, tanh(output.getValue(i)));
				}
				break;
			
			case SIG:
				for (int i = 0; i < output.getLength(); i++) {
					output.setValue(i, sigmoid(output.getValue(i)));
				}
				break;
			
			case STEP:
				if (currentLayer == numLayers-2) {
					for (int i = 0; i < output.getLength(); i++) {
						output.setValue(i, stepFunc(output.getValue(i)));
					}
				}
				else {
					for (int i = 0; i < output.getLength(); i++) {
						output.setValue(i, sigmoid(output.getValue(i)));
					}
				}
				break;
		}
		//save individual layer output
		layerOutputs.push_back(output);
	}
	return output;
}


Matrix NeuralNetwork::derrorFunction (Matrix input, Matrix output, 
										Matrix correct_output, int currentLayer, 
										Matrix& delta) {
	Matrix dEdW;
	
	//check if output layer
	if (currentLayer == numLayers-2) {
		Matrix difference = correct_output - output;
		difference = difference.scalarMultiply(-1);

		//different derivative for different threshold function
		if (thresholdFunction == TANH)
	 		delta = difference.scalarMultiply(dtanh(z[currentLayer]));
		else
			delta = difference.scalarMultiply(dsigmoid(z[currentLayer]));
	 	dEdW = input.transpose() * delta;
	}
	//check if input layer or hidden layer
	else {
		delta = delta.multiply(weightMatrices[currentLayer+1].transpose());
		if (thresholdFunction == TANH)
			delta = delta.scalarMultiply(dtanh(z[currentLayer]));
		else
			delta = delta.scalarMultiply(dsigmoid(z[currentLayer]));
		dEdW = input.transpose() * delta;
	}
	
	
	return dEdW;
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



Matrix NeuralNetwork::train (Matrix input_train, Matrix output_train) {
	Matrix output;
	
	int iteration = 0;
	double greatestGradient = 100;
	double greatestError = 100;
	double lastGreatestGradient = greatestGradient;
	while (abs(greatestGradient) > this->gradientThreshold && iteration < this->maxIteration) {
		greatestGradient = 0;
		
		output = this->evaluate(input_train);
		backPropagate (input_train, output, output_train);

		//update weights, check if the gradientChange is enough
		for (int currentLayer = 0; currentLayer < weightMatrices.size(); currentLayer++) {
		
			//update the weights
			weightMatrices[currentLayer] = weightMatrices[currentLayer] - gradientChange[currentLayer];
			
			for (int i = 0; i < gradientChange[currentLayer].getLength(); i++) {
				if (abs(gradientChange[currentLayer].getValue(i)) > greatestGradient) {
					greatestGradient = abs(gradientChange[currentLayer].getValue(i));
				}
			}
		}
		cout << "Iteration " << iteration <<", Highest gradient: " << greatestGradient << endl;
		
		//check the difference between largest gradients to prevent them from diverging.
		if (abs (lastGreatestGradient - greatestGradient) <= this->GRADIENT_DIFFERENCE_LIMIT) {
			iteration = maxIteration;
		}
		lastGreatestGradient = greatestGradient;
		iteration++;
		output = this->evaluate(input_train);

	}

	//for (int i = 0; i < weightMatrices.size(); i++) {
	//	weightMatrices[i].printMatrix();
	//}	
	
	return output;	
}



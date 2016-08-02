#ifndef _NN_H_
#define _NN_H_

#include "Matrix.h"
#include <cstdlib>
#include <iostream>
#include <vector>
#include <cmath>
#include <stdbool.h>

#define TANH	0
#define SIG		1
#define STEP 	2

using namespace std; 

class NeuralNetwork {
	protected:	
		vector <Matrix> weightMatrices;
		vector <Matrix> gradientChange;
		vector <int> layerNeuronCount;		//each index indicates how many neurons are in that layer
		vector <Matrix> z;					//individual z values of layers
		vector <Matrix> layerOutputs;		//individual outputs of layers
		bool hasInputLayer;
		bool hasOutputLayer;
		int numLayers;
		int numHiddenLayers;
		int numInputs;
		int numOutputs;
		static const double GRADIENT_DIFFERENCE_LIMIT 	= 0.0000001;
		static const double GRADIENT_REVERSAL_LIMIT 	= 0.1;
		int thresholdFunction;
		
	public:
		double descentMultiplier 		= 1; 
		double step_descentMultiplier 	= 1;
		double gradientThreshold 		= 0.0001; 
		double errorThreshold			= 0.1;
		int maxIteration				= 10000;
		int randomWidth					= 64;


		NeuralNetwork ();
		NeuralNetwork (int func);
		void setThresholdFunction (int func);

		//the next 3 functions must be done in  the specific order.
		bool addInputLayer (int num_input);
		bool addHiddenLayer (int num_hidden);	//can be called multiple times
		bool addOutputLayer (int num_output);
		
		Matrix step_train (Matrix input_train, Matrix output_train);
		Matrix train (Matrix input_train, Matrix output_train);
		Matrix evaluate (Matrix input);
		
		Matrix forwardPropagate (Matrix input_matrix, int currentLayer);
		void backPropagate (Matrix input, Matrix output, Matrix correct_output);
		
		//dJ/dW (matrix of partials)
		Matrix derrorFunction (Matrix input, Matrix output, Matrix correct_output, int currentLayer, Matrix& delta);
		
		//void setRandomWidth(int w);

		static double sigmoid (double z) {
			return 1/(1+exp(-z));
		}
		static double dsigmoid (double z) {
			return exp(-z)/ (pow(1+exp(-z),2));
		}
		static Matrix dsigmoid (Matrix z) {
			Matrix newMatrix (z.getRow(),z.getCol());
			for (int i = 0; i < z.getLength(); i++) {
				newMatrix.setValue (i, dsigmoid(z.getValue(i)));
			}
			return newMatrix;
		}

		static double dtanh(double z) {
			return 1 - pow (tanh(z), 2);
		}
		static Matrix dtanh (Matrix z) {
			Matrix newMatrix (z.getRow(),z.getCol());
			for (int i = 0; i < z.getLength(); i++) {
				newMatrix.setValue (i, dtanh(z.getValue(i)));
			}
			return newMatrix;
		}

		static double stepFunc (double z) {
			double f = sigmoid(z);
			if (f >= 0.5)
				return 1;
			else 
				return 0; 
		}
};	

#endif


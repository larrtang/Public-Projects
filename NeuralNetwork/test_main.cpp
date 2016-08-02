#include "NeuralNetwork.h"
#include "MatrixBuilder.h"
#include <cstdio>

int main (int argc, char ** argv) {

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
						
	double data3 [7*2]  = {	0,0.1,
							0,0.2,
							0,0.3,
							0,0.4,
							0,0.5,
							0,0.6,
							0,0.7};

	Matrix d1 (12, 2, data);
	MatrixBuilder mb (d1);

	for (double i = 0; i < 10; i++) {
		Matrix m (1, 2);
		m.setValue(0,0, i);
		m.setValue(0,1, i+1);

		mb.addRow(m);
		mb.getMatrix().printMatrix();
	}

	return 0;
}


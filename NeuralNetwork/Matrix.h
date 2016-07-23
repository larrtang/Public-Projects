#ifndef MATRIX_H
#define MATRIX_H

#include <cstdlib>
#include <iostream>

using namespace std;

class Matrix {
	private:
		double * matrixArray;
		int row;
		int col;
		int length;
		
	public:
		Matrix (int x, int y);
		Matrix (int x, int y, double * array);		//if chooses to initialized with own matrix array
		double getValue (int x, int y);
		double getValue (int i);
		void setValue (int x, int y, double value);
		void setValue (int i, double value);
		double * getArray ();
		Matrix operator + ( Matrix& matrix);
		Matrix operator * ( Matrix& matrix);
		void printMatrix ();
};

#endif
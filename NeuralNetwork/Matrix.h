#ifndef _MATRIX_H_
#define _MATRIX_H_

#include <cstdlib>
#include <iostream>

using namespace std;

class Matrix {
	private:
		double * matrixArray;
		int row;
		int col;
		int length;
		static const int randomWidth = 64;

	public:
		Matrix ();

		//copy constructor
		Matrix (const Matrix& m);
		
		//destructor
		~Matrix();

		//assign a size
		Matrix (int x, int y);
		
		//assign a size and array matching the size
		Matrix (int x, int y, double * array);		//if chooses to initialized with own matrix array
	
		double getValue (int x, int y); //return at position

		double getValue (int i); //return at index

		void setValue (int x, int y, double value); //set at position
		
		void setValue (int i, double value); //set at index
		
		//randomizes the matrix values
		void randomize ();
		
		//same as randomize() but with a specific random width
		void randomize (int width);
		
		int getRow();
		
		int getCol();
		
		int getLength();
		
		double * getArray ();
		
		Matrix transpose ();
		
		Matrix scalarMultiply (double s);
		
		Matrix scalarMultiply (Matrix matrix);
		
		Matrix multiply (Matrix matrix);
		
		Matrix operator + ( Matrix& matrix);
		
		Matrix operator - ( Matrix& matrix);
		
		Matrix operator * ( Matrix& matrix);
		
		void printMatrix ();
};

#endif

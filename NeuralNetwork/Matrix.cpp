#include "Matrix.h"
//Matrix::Matrix () {
//	matrixArray = (double*) malloc (sizeof(double) * 0);
//	row = 0;
//	col = 0;
//	length = 0;
//}

//Matrix::Matrix (const Matrix& m) {
//	matrixArray = m.getArray();
//	row = m.getRow();
//	col = m.getCol();
//	length = m.getRow()*m.getCol();
//}

Matrix::Matrix (int x, int y) {
	matrixArray = (double*) malloc (sizeof(double) * x * y);
	row = x;
	col = y;
	length = row * col;
	
	for (int i = 0; i < length; i++) {
		matrixArray[i] = 0;
	}
}

Matrix::Matrix (int x, int y, double * array) {
	matrixArray = array;
	row = x;
	col = y;
	length = row * col;
}

double Matrix::getValue (int x, int y) {
	if (x >= this->row || y >= this->col) {
		cerr <<"The index you choose was out of bound." << endl;
		return 0.0;
	}
	return matrixArray[x*col + y];
}

double Matrix::getValue (int i) {
	if (i >= this->length) {
		cerr <<"The index you choose was out of bound." << endl;
		return 0.0;
	}
	return matrixArray[i];
}

void Matrix::setValue (int x, int y, double value) {
	if (x >= row || y >= col) {
		cerr <<"The index you choose was out of bound." << endl;
		return;
	}
	matrixArray[x*col + y] = value;
}

void Matrix::setValue (int i, double value) {
	if (i >= length) {
		cerr <<"The index you choose was out of bound." << endl;
		return;
	}
	matrixArray[i] = value;
}

int Matrix::getRow () {return this->row;}
int Matrix::getCol () {return this->col;}
int Matrix::getLength () {return this->length;}

double * Matrix::getArray () {return matrixArray;}

Matrix Matrix::transpose (Matrix matrix) {
	Matrix newMatrix (matrix.getCol(), matrix.getRow());
	for (int i = 0; i < matrix.getRow(); i++) {
		for (int j = 0; j < matrix.getCol(); j++) {
			newMatrix.setValue(j,i, matrix.getValue(i,j));
		}
	}
	return newMatrix;
}

Matrix Matrix::operator + ( Matrix& matrix) {
	
	//check if the matrices are the same size
	if (this->row != matrix.row || this->col != matrix.col) {
		cerr <<"Matrices are different sizes." << endl;
		return Matrix(0,0);
	}	
	
	Matrix newMatrix (this->row, this->col);
	
	for (int i = 0; i < newMatrix.length; i++) {
		double sum = this->getValue(i) + matrix.getValue(i);
		newMatrix.setValue (i, sum);
	}
	
	return newMatrix;
}

Matrix Matrix::operator * ( Matrix& matrix) {
	
	//check if the matrices are compatible
	if (this->col != matrix.row) {
		cerr <<"Matrices are not compatible." << endl;
		return Matrix(0,0);
	}
	
	Matrix newMatrix (this->row, matrix.col);
	int index = 0;
	
	for (int r = 0; r < this->row; r++) {
		for (int c = 0; c < matrix.col; c++) {
			for (int k = 0; k < this->col; k++) {
				newMatrix.setValue(index, newMatrix.getValue(index) 
									+ this->getValue(r,k) 
									* matrix.getValue(k, c));
			}
			index++;
		}
	}
	
	return newMatrix;
}

void Matrix::printMatrix () {
	int r = 0;
	int c = 0;
	for (int i = 0; i < length; i++) {
		if (c >= this->col) {
			cout << endl;
			c = 0;
		}
		cout << this->getValue(i) << ",";
	}
	cout << endl;
}




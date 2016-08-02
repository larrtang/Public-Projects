#include "Matrix.h"

Matrix::Matrix () {
	matrixArray = (double*) malloc (sizeof(double) * 0);
	row = 0;
	col = 0;
	length = 0;
}

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
		cerr <<"The index you choose was out of bound. Index: " <<i<< ".\n\n" << endl;
		return 0.0;
		this->printMatrix();
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

void Matrix::randomize (){
	srand (time(0));
	for (int i = 0; i < length; i++) {
		this->setValue(i, rand() %randomWidth - randomWidth/2);
	}
}

void Matrix::randomize (int width){
	srand (time(0));
	for (int i = 0; i < length; i++) {
		this->setValue(i, rand() %width - width/2);
	}
}

double * Matrix::getArray () {return matrixArray;}

Matrix Matrix::transpose () {
	Matrix newMatrix (getCol(), getRow());
	for (int i = 0; i < getRow(); i++) {
		for (int j = 0; j < getCol(); j++) {
			newMatrix.setValue(j,i, getValue(i,j));
		}
	}
	return newMatrix;
}

Matrix Matrix::scalarMultiply (double s) {
	Matrix newMatrix (this->row, this->col);
	for (int i = 0; i < this->length; i++) {
		newMatrix.setValue(i, this->getValue(i)*s);
	}
	return newMatrix;
}

Matrix Matrix::scalarMultiply (Matrix matrix) {

	//check if matrices are same size
	if (this->row != matrix.getRow() || this->col != matrix.getCol()) {
		cerr << "Matrice's are not the same size." << endl;
		return Matrix ();
	}
	
	Matrix newMatrix (this->row, this->col);
	for (int i = 0; i < this->length; i++) {
		newMatrix.setValue(i, matrix.getValue(i)*this->getValue(i));
	}
	return newMatrix;
}

Matrix Matrix::multiply (Matrix matrix) {
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

Matrix Matrix::operator - ( Matrix& matrix) {
	
	//check if the matrices are the same size
	if (this->row != matrix.row || this->col != matrix.col) {
		cerr <<"Matrices are different sizes." << endl;
		return Matrix(0,0);
	}	
	
	Matrix newMatrix (this->row, this->col);
	
	for (int i = 0; i < newMatrix.length; i++) {
		double diff = this->getValue(i) - matrix.getValue(i);
		newMatrix.setValue (i, diff);
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
			cout<< endl;
			c = 0;
		}
		cout << this->getValue(i) << "\t";
		c++;
	}
	cout << endl <<endl;
}




#include "Matrix.h"

int main () {
	Matrix  m1 (1,3);
	Matrix  m2 (3,2);
	
	
	m1.setValue (0,0, 1);
	m1.setValue (0,1, 4);
	m1.setValue (0,2, 6);
	
	m2.setValue (0,0, 2);
	m2.setValue (0,1, 3);
	m2.setValue (1,0, 5);
	m2.setValue (1,1, 8);
	m2.setValue (2,0, 7);
	m2.setValue (2,1, 9);

	Matrix  m3 = m1 * m2;
	
	m3.printMatrix();
	
	return 0;
}


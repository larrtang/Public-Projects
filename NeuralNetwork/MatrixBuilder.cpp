#include "MatrixBuilder.h"

MatrixBuilder::MatrixBuilder () {
    Matrix m (0,0);
    this->matrix = m;
}

MatrixBuilder::MatrixBuilder (Matrix m) {
    this->matrix = m;
}
MatrixBuilder::MatrixBuilder (MatrixBuilder& matrixBuilder) {
    this->matrix = matrixBuilder.getMatrix();
}

void MatrixBuilder::addRow (double values[]) {
    Matrix newMatrix (matrix.getRow() + 1, matrix.getCol());
    for (int r = 0; r < newMatrix.getRow(); r++) {
        for (int c = 0; c < newMatrix.getCol(); c++) {
            if (r >= matrix.getRow()) {
                newMatrix.setValue(r,c, values[c]);
            }
            else {
                newMatrix.setValue(r, c, matrix.getValue(r, c));
            }
        }
    }
    matrix = newMatrix;
}

void MatrixBuilder::addRow (Matrix row) {
    Matrix newMatrix (matrix.getRow() + 1, matrix.getCol());
    for (int r = 0; r < newMatrix.getRow(); r++) {
        for (int c = 0; c < newMatrix.getCol(); c++) {
            if (r >= matrix.getRow()) {
                newMatrix.setValue(r,c, row.getValue(c));
            }
            else {
                newMatrix.setValue(r, c, matrix.getValue(r, c));
            }
        }
    }
    matrix = newMatrix;
}

void MatrixBuilder::addCol (double values[]) {
    Matrix newMatrix (matrix.getRow() + 1, matrix.getCol());
    for (int r = 0; r < newMatrix.getRow(); r++) {
        for (int c = 0; c < newMatrix.getCol(); c++) {
            if (c >= matrix.getCol()) {
                newMatrix.setValue(r,c, values[r]);
            }
            else {
                newMatrix.setValue(r, c, matrix.getValue(r, c));
            }
        }
    }
    matrix = newMatrix;
}

void MatrixBuilder::addCol (Matrix col) {
    Matrix newMatrix (matrix.getRow() + 1, matrix.getCol());
    for (int r = 0; r < newMatrix.getRow(); r++) {
        for (int c = 0; c < newMatrix.getCol(); c++) {
            if (c >= matrix.getCol()) {
                newMatrix.setValue(r,c, col.getValue(r));
            }
            else {
                newMatrix.setValue(r, c, matrix.getValue(r, c));
            }
        }
    }
    matrix = newMatrix;
}

void MatrixBuilder::popRow () {
    Matrix newMatrix (matrix.getRow() - 1, matrix.getCol());
    for (int r = 0; r < newMatrix.getRow(); r++) {
        for (int c = 0; c < newMatrix.getCol(); c++) {
            newMatrix.setValue(r, c, matrix.getValue(r, c));
        }
    }
    matrix = newMatrix;
}

void MatrixBuilder::popCol () {
    Matrix newMatrix (matrix.getRow(), matrix.getCol() - 1);
    for (int r = 0; r < newMatrix.getRow(); r++) {
        for (int c = 0; c < newMatrix.getCol(); c++) {
            newMatrix.setValue(r, c, matrix.getValue(r, c));
        }
    }
    matrix = newMatrix;
}

Matrix MatrixBuilder::getMatrix () {return this->matrix;}
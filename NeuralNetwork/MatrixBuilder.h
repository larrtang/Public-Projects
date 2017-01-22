#ifndef _MATRIXBUILDER_H_
#define _MATRIXBUILDER_H_

#include "Matrix.h"


class MatrixBuilder {
    private:
        Matrix matrix;

    public:
        MatrixBuilder ();
        MatrixBuilder (Matrix m);
        MatrixBuilder (int row, int col, double * values);

        MatrixBuilder (MatrixBuilder& matrixBuilder);

        void addRow (double values[]);
        void addRow (Matrix row);
        void addCol (double values[]);
        void addCol (Matrix col);
        void popRow ();
        void popCol ();

        Matrix getMatrix ();
};

#endif
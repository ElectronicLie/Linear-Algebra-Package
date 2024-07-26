package linalg;

import java.util.ArrayList;
import java.util.Arrays;
import polynomials.*;

public class Tester{

  public static void main(String[] args){

    Matrix m = Matrix.randomMatrix(2, 4, -1);
    System.out.println(m);
    System.out.println("covariance matrix:\n"+m.coVarianceMatrix());
    System.out.println(m.allPrincipalComponents());

    // // Matrix d = Matrix.randomMatrix(2, 2);
    // Matrix d = new Identity(2);
    // System.out.println("\noriginal:\n"+d);
    // // Matrix rr = d.rref();
    // // System.out.println("RREFed:\n"+rr);
    // Matrix dcopy = d.copy();
    // dcopy.meanCenterColumns();
    // System.out.println("mean centered:\n"+dcopy);
    // System.out.println("covariance matrix:\n"+d.coVarianceMatrix());
    // ArrayList<Eigenvector> pca = d.allPrincipalComponents();
    // System.out.println(pca);
    // System.out.println(Matrix.roughlyEquals(0.7, 0, 1));

    // System.out.println(Matrix.round(3.04,-2));

    // SquareMatrix m = SquareMatrix.randomMatrix(3, -1);
    // // SquareMatrix m = new SquareMatrix(new double[][] {{1,4},{3,2}});
    // // SquareMatrix m = new SquareMatrix(new double[][] {{1}});
    // System.out.println(m.toString(-1));
    // Polynomial charPoly = m.characteristicPolynomial();
    // // System.out.println(charPoly.toString());
    // // System.out.println(Arrays.toString(charPoly.roots()));
    // System.out.println(m.getEigenvectors());

  }

}

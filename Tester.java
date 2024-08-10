package linalg;

import java.util.ArrayList;
import java.util.Arrays;
import polynomials.*;

public class Tester{

  public static void main(String[] args){

    // Matrix m = Matrix.randomMatrix(2, 4, -1);
    // System.out.println(m);
    // System.out.println("covariance matrix:\n"+m.coVarianceMatrix());
    // System.out.println(m.allPrincipalComponents());

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

    // SquareMatrix m = SquareMatrix.randomMatrix(25, 0);
    // SquareMatrix m = new SquareMatrix(new double[][] {{1,4},{3,2}});
    // SquareMatrix m = new SquareMatrix(new double[][] {{1}});
    // System.out.println(m.toString(-1));
    // Polynomial charPoly = m.characteristicPolynomial();
    // System.out.println(charPoly.toString());
    // System.out.println(Arrays.toString(charPoly.roots()));
    // System.out.println(m.getEigenvectors());

    // Matrix m = Matrix.randomMatrix(25,26,0);
    // // SystemOfEquations soe = new SystemOfEquations(m);
    // // System.out.println(soe.solution());
    // System.out.println(m.rref());

    // SquareMatrix m = SquareMatrix.randomMatrix(3,0);
    // SquareMatrix m = new SquareMatrix(new double[][] {{5,-9,9},{-5,-1,8},{8,5,2}});
    // SquareMatrix r = SquareMatrix.randomMatrix(4,0);
    // SquareMatrix test = r;
    // System.out.println(test);
    // PolynomialMatrix ce = test.characteristicEquation();
    // // System.out.println(ce.factorsToString());
    // System.out.println(ce);
    // PolynomialMatrix lu = ce.upperTriangularViaREF();
    // // System.out.println(lu.factorsToString());
    // System.out.println(lu);
    // SimpleRationalFraction cp = lu.mainDiagonalProduct("L");
    // System.out.println(cp);
    // SimpleRationalFraction cpp = test.characteristicPolynomial();
    // System.out.println("char. poly.: \n"+cpp+"\n");
    // // System.out.println("numerator:\n"+cp.getNumerator().toStringUnRounded());
    // // double[] numRts = cp.getNumerator().roots();
    // // System.out.println("numerator roots:\n"+Arrays.toString(numRts)+"\n");
    // // double[] denRts = cp.getDenominator().roots();
    // // System.out.println("denominator roots:\n"+Arrays.toString(denRts)+"\n");
    // double[] rts = cp.roots();
    // System.out.println("rational fraction roots:\n"+Arrays.toString(rts));
    // System.out.println(test.getEigenvectors());

    // RationalFraction tst = new RationalFraction(new Polynomial(new double[] {3,-1}, "L"), "L");
    // System.out.println(tst.factorsToString());

  }

}

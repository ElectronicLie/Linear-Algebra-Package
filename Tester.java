package linalg;

import java.util.ArrayList;
import java.util.Arrays;
import polynomials.*;
import fractions.*;

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

    // SquareMatrix m = SquareMatrix.random(3,0);
    // SquareMatrix m = new SquareMatrix(new int[][] {{5,-9,9},{-5,-1,8},{8,5,2}});
    // // SquareMatrix r = SquareMatrix.random(4,0);
    // SquareMatrix test = m;
    // System.out.println(test);
    // PolynomialMatrix ce = test.characteristicEquation();
    // // System.out.println(ce.factorsToString());
    // System.out.println(ce);
    // PolynomialMatrix lu = ce.upperTriangularViaREF();
    // System.out.println(lu.factorsToString());
    // System.out.println(lu);
    // RationalExpression cp = lu.mainDiagonalProduct("L");
    // System.out.println(cp);
    // RationalExpression cpp = test.characteristicPolynomial();
    // System.out.println("char. poly.: \n"+cpp+"\n");
    // // // System.out.println("numerator:\n"+cp.getNumerator().toStringUnRounded());
    // // // double[] numRts = cp.getNumerator().roots();
    // // // System.out.println("numerator roots:\n"+Arrays.toString(numRts)+"\n");
    // // // double[] denRts = cp.getDenominator().roots();
    // // // System.out.println("denominator roots:\n"+Arrays.toString(denRts)+"\n");
    // Fraction[] rts = cp.roots();
    // System.out.println("char. poly. roots:\n"+Arrays.toString(rts));
    // System.out.println(test.getEigenvectors());

    // RationalFraction tst = new RationalFraction(new Polynomial(new double[] {3,-1}, "L"), "L");
    // System.out.println(tst.factorsToString());

    // Fraction[][] ma = new Fraction[][] {
    //   {new Fraction(-2,3), new Fraction(1,6)},
    //   {new Fraction(4,5), new Fraction(-5)}
    // };
    // SquareMatrix m = new SquareMatrix(ma);
    // System.out.println(m);
    // System.out.println(m.refPreservePivots());
    // System.out.println("app: \n"+m);
    // System.out.println(m.det());

    int[][] ma = new int[][] {
      {-10, 1,   2, -8},
      {-7,  6, -10,  0},
      {-4, -7,   0,  7},
      {8,   0,  -2,  7}
    };
    SquareMatrix m = new SquareMatrix(ma);
    System.out.println(m);
    // System.out.println(m.getEigenvectors());
    // Identity lil = new Identity(m.dim());
    // lil.scale(10.0000625);
    // SquareMatrix lilt = m.add(lil);
    // System.out.println(lilt.unRoundedToString());
    // System.out.println(lilt.refPreservePivots().unRoundedToString());
    // System.out.println(lilt.det());
    System.out.println(m.getEigenvectors());
    // System.out.println(m.det());

  }

}

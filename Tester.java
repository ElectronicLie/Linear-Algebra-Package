package linalg;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Arrays;
import polynomials.*;

public class Tester{

  public static void main(String[] args) throws FileNotFoundException{

    // String eStr = "+ 0 0 1 1";
    // // EntryExpression ee = EntryExpression.parseEntryExpression(eeStr)
    // Entry e = Entry.parseEntry(eStr);
    // System.out.println(e);
    //
    // String eeStr = "+ 0 0 , - 1 1 ,";
    // EntryExpression ee = EntryExpression.parseEntryExpression(eeStr);
    // System.out.println(ee);

    // int[][] ma = new int[][] {{1, 2, 3},{4,5, 6}, {7, 8, 9}};
    // SquareMatrix m = new SquareMatrix(ma);
    SquareMatrix m = SquareMatrix.random(7, 0);

    System.out.println(m);
    System.out.println(m.det());

    // String[][] mas = new String[][] {{"a", "b", "c", "k1"}, {"d", "e", "f", "k2"}, {"g", "h", "i", "k3"}};
    // ComplexFractionMatrix m = new ComplexFractionMatrix(mas);
    // System.out.println(m.toString());
    // System.out.println(m.rref());

    // // int[][] ma = new int[][] {{5, -2, 4}, {-7, -8, 0}, {0, 0, 2}};
    // SquareMatrix m = SquareMatrix.random(5, 0);
    // // SystemOfEquations soe = new SystemOfEquations(m);
    // // soe.addZeroConstants();
    // // soe.makeFirstVariableOne();
    // // System.out.println(soe);
    // // boolean solved = soe.solve();
    // // System.out.println(soe.solution());
    // // // SquareMatrix m = new SquareMatrix(ma);
    // System.out.println(m);
    // System.out.println(m.det());
    // System.out.println(m.getEigenvectors());

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
    //   {new Fraction(3527, 200), new Fraction(-9), new Fraction(9), Fraction.zero()},
    //   {new Fraction(-5), new Fraction(8207329, 200), new Fraction(28216), Fraction.zero()},
    //   {Fraction.zero(), Fraction.zero(), Fraction.one(), Fraction.one()}
    // };
    // Matrix m = new Matrix(ma);
    //
    // System.out.println(m);
    // // System.out.println(m.ref());
    // System.out.println(m.rref());


  }

}

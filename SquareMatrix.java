package linalg;

import polynomials.*;
import malo.*;
import fractions.Fraction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SquareMatrix extends Matrix{

  protected ArrayList<Eigenvector> eigenvectors;
  protected boolean eigenCalced;
  protected final static File detFormulas = new File("determinant-formulas.txt");

  public SquareMatrix(){
    super();
  }

  public SquareMatrix(int dim){
    super(dim, dim);
  }

  public SquareMatrix(int[][] ary){
    super(ary);
    if (ary.length != ary[0].length){
      throw new IllegalArgumentException("square matrix must have an equal number of rows and columns");
    }
  }

  public SquareMatrix(Fraction[][] ary){
    if (ary.length != ary[0].length){
      throw new IllegalArgumentException("square matrix must have an equal number of rows and columns");
    }
    vals = ary;
    eigenCalced = false;
  }

  public SquareMatrix(ArrayList<Vector> cols){
    super(cols);
    if (vals.length != vals[0].length){
      throw new IllegalArgumentException("square matrix must have an equal number of rows and columns");
    }
    eigenCalced = false;
  }

  public SquareMatrix(Network network){
    this(network.size());
    for (int c = 0; c < network.size(); c++){
      for (int r = 0; r < network.size(); r++){
        if (network.getNode(c).getEdge(network.getNode(r)) == null){
          vals[r][c] = Fraction.zero();
        }else{
          vals[r][c] = network.getNode(c).getEdgeWeight(network.getNode(r));
        }
      }
    }
    eigenCalced = false;
  }

  public int dim(){
    return n();
  }

  public SquareMatrix minor(int rExc, int cExc){
    SquareMatrix result = new SquareMatrix(dim()-1);
    for (int r = 0; r < rExc; r++){
      for (int c = 0; c < cExc; c++){
        result.vals[r][c] = vals[r][c];
      }
    }
    for (int r = rExc+1; r < dim(); r++){
      for (int c = 0; c < cExc; c++){
        result.vals[r-1][c] = vals[r][c];
      }
    }
    for (int r = 0; r < rExc; r++){
      for (int c = cExc+1; c < dim(); c++){
        result.vals[r][c-1] = vals[r][c];
      }
    }
    for (int r = rExc+1; r < dim(); r++){
      for (int c = cExc+1; c < dim(); c++){
        result.vals[r-1][c-1] = vals[r][c];
      }
    }
    return result;
  }

  private SquareMatrix minor(int cExc){
    return minor(0, cExc);
  }

  public Fraction det(){
    VariableExpression formula = getDetFormula(dim());
    System.out.println(formula);
    String[] standardEntryOrder = VariableMatrix.standardEntryOrder(dim());
    System.out.println(Arrays.toString(standardEntryOrder));
    System.out.println(Arrays.toString(entriesAsArray()));
    return formula.plugIn(standardEntryOrder, entriesAsArray());
  }

  // public SquareMatrix luDecomposition(){
  //
  // }

  // public Polynomial characteristicPolynomial(){
  //   Minor minor = new Minor(this);
  //   Polynomial result = minor.characteristicPolynomial();
  //   return result;
  // }

  // public PolynomialMatrix characteristicEquation(){
  //   PolynomialMatrix charEq = new PolynomialMatrix(dim(), dim());
  //   for (int r = 0; r < dim(); r++){
  //     for (int c = 0; c < dim(); c++){
  //       if (r == c){
  //         charEq.vals[r][c] = new RationalExpression
  //           (new Polynomial(new Fraction[] {vals[r][c], Fraction.negOne()}, "L"), "L");
  //       }else{
  //         charEq.vals[r][c] = new RationalExpression
  //           (new Polynomial(new Fraction[] {vals[r][c]}, "L"), "L");
  //       }
  //     }
  //   }
  //   return charEq;
  // }

  // public RationalExpression characteristicPolynomial(){
  //   PolynomialMatrix charEq = characteristicEquation();
  //   PolynomialMatrix lu = charEq.upperTriangularViaREF();
  //   RationalExpression result = lu.mainDiagonalProduct("L");
  //   Polynomial one = new Polynomial(new int[] {1});
  //   // if (! result.getDenominator().equals(one)){
  //   //   throw new IllegalStateException("denominator is complex");
  //   // }
  //   return result;
  // }

  public Polynomial characteristicPolynomial(){
    String[] seo = VariableMatrix.standardEntryOrder(dim());
    Polynomial[] entries = new Polynomial[seo.length];
    for (int r = 0; r < dim(); r++){
      for (int c = 0; c < dim(); c++){
        if (r == c){
          entries[r*dim()+c] = new Polynomial(new Fraction[] {vals[r][c], Fraction.negOne()}, "L");
        }else{
          entries[r*dim()+c] = new Polynomial(new Fraction[] {vals[r][c]}, "L");
        }
      }
    }
    VariableExpression formula = getDetFormula(dim());
    return formula.plugIn(seo, entries);
  }

  private void calcEigenvectors(){
    this.eigenvectors = new ArrayList<Eigenvector>();
    Polynomial charPoly = characteristicPolynomial();
    Fraction[] eigenvals = charPoly.roots();
    for (int e = 0; e < eigenvals.length; e++){
      this.eigenvectors.add(new Eigenvector(this, eigenvals[e]));
    }
  }

  public Eigenvector getEigenvector(int v){
    if (! eigenCalced){
      calcEigenvectors();
      this.eigenCalced = true;
    }
    return eigenvectors.get(v);
  }

  public ArrayList<Eigenvector> getEigenvectors(){
    if (! eigenCalced){
      calcEigenvectors();
      this.eigenCalced = true;
    }
    return eigenvectors;
  }

  public SquareMatrix add(Matrix other){
    return super.add(other).squareCopy();
  }

  public static SquareMatrix randomFromValue(int dim, double min, double max){
    return Matrix.randomFromValue(dim, dim, min, max).squareCopy();
  }

  public static SquareMatrix randomFromValue(int dim, double min, double max, int p){
    return Matrix.randomFromValue(dim, dim, min, max, p).squareCopy();
  }

  public static SquareMatrix random(int dim, long min, long max){
    return Matrix.random(dim, dim, min, max).squareCopy();
  }

  public static SquareMatrix random(int dim){
    return random(dim, Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER);
  }

  public static SquareMatrix intRandom(int dim, long min, long max){
    return Matrix.intRandom(dim, dim, min, max).squareCopy();
  }

  public static SquareMatrix intRandom(int dim){
    return Matrix.intRandom(dim, dim).squareCopy();
  }

  protected static VariableExpression getDetFormula(int dim){
    if (dim == 0){
      return VariableExpression.one();
    }
    String formulaStr = "";
    Scanner scanner = new Scanner("");
    try{
      scanner = new Scanner(detFormulas);
    }catch(Exception e){
      e.printStackTrace();
    }
    for (int l = 1; l <= dim; l++){
      try{
        formulaStr = scanner.nextLine();
      }catch(Exception e){
        throw new IllegalStateException
        ("formula for determinant of dimension "+dim+" has not yet been generated");
      }
    }
    VariableExpression formula = VariableExpression.parseVariableExpression(formulaStr);
    return formula;
  }

}

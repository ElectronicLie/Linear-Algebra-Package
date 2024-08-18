package linalg;

import polynomials.*;
import malo.*;
import fractions.Fraction;
import java.util.ArrayList;
import java.util.Arrays;

public class SquareMatrix extends Matrix{

  protected ArrayList<Eigenvector> eigenvectors;
  protected boolean eigenCalced;

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

  // public double det(){
  //   if (dim() == 1){
  //     return get(0,0);
  //   }
  //   double sum = 0;
  //   for (int c = 0; c < dim(); c++){
  //     double minorDet = det(minor(c)) * get(0,c);
  //     if (c % 2 == 1)
  //       minorDet *= -1;
  //     sum += minorDet;
  //   }
  //   return sum;
  // }

  public Fraction det(){
    Matrix l = refPreservePivots();
    // System.out.println(this);
    // System.out.println(l);
    Fraction product = Fraction.one();
    for (int i = 0; i < l.m(); i++){
      // System.out.println("multiplying "+product+" by "+l.vals[i][i]);
      product = product.mult(l.vals[i][i]);
      // System.out.println("diagonal product: "+product);
    }
    return product;
  }

  // public SquareMatrix luDecomposition(){
  //
  // }

  // public Polynomial characteristicPolynomial(){
  //   Minor minor = new Minor(this);
  //   Polynomial result = minor.characteristicPolynomial();
  //   return result;
  // }

  public PolynomialMatrix characteristicEquation(){
    PolynomialMatrix charEq = new PolynomialMatrix(dim(), dim());
    for (int r = 0; r < dim(); r++){
      for (int c = 0; c < dim(); c++){
        if (r == c){
          charEq.vals[r][c] = new RationalExpression
            (new Polynomial(new Fraction[] {vals[r][c], Fraction.negOne()}, "L"), "L");
        }else{
          charEq.vals[r][c] = new RationalExpression
            (new Polynomial(new Fraction[] {vals[r][c]}, "L"), "L");
        }
      }
    }
    return charEq;
  }

  public RationalExpression characteristicPolynomial(){
    PolynomialMatrix charEq = characteristicEquation();
    PolynomialMatrix lu = charEq.upperTriangularViaREF();
    RationalExpression result = lu.mainDiagonalProduct("L");
    Polynomial one = new Polynomial(new int[] {1});
    // if (! result.getDenominator().equals(one)){
    //   throw new IllegalStateException("denominator is complex");
    // }
    return result;
  }

  private void calcEigenvectors(){
    this.eigenvectors = new ArrayList<Eigenvector>();
    Fraction[] eigenvals = calcEigenvalues();
    for (int e = 0; e < eigenvals.length; e++){
      this.eigenvectors.add(new Eigenvector(this, eigenvals[e]));
    }
  }

  private Fraction[] calcEigenvalues(){
    Fraction[] roots = doubleRoots
      (new Fraction(-1000), new Fraction(1000), -3, Mathematic.DEFAULT_ZERO_MARGIN);
    return roots;
  }

  private Fraction[] doubleRoots(Fraction min, Fraction max, int scanStepPowerOfTen, int marginPoT){

    if (min.moreThan(max)){
      throw new IllegalArgumentException("interval for root-searching is collapsed");
    }

    Fraction[] roots = new Fraction[0];

    Fraction scanStep = Fraction.powerOfTen(scanStepPowerOfTen);
    Fraction margin = Fraction.powerOfTen(marginPoT);

    boolean lastPos = false;
    boolean lastNeg = false;
    boolean curPos = false;
    boolean curNeg = false;

    for (Fraction x = min; x.lessThanOrEqualTo(max); x = x.add(scanStep)){
      // System.out.println("lambda: "+x);
      // x = Malo.roundDouble(x, scanStepPowerOfTen);
      Fraction value = this.detAtLambda(x);
      System.out.print(x+": ");
      System.out.println(value);
      if (value.isNegative()){
        curNeg = true;
        curPos = false;
      }else if (value.isPositive()){
        curNeg = false;
        curPos = true;
      }else if (value.isZero()){
        roots = Malo.aryAppend(roots, x);
        curPos = false;
        curNeg = false;
      }

      if (! value.isZero()){
        if ((curPos && lastNeg) || (curNeg && lastPos)){
          Fraction xLast = x.subtract(scanStep);
          Fraction r = binarySearchForRoot(xLast, x, marginPoT);
          roots = Malo.aryAppend(roots, r);
        }
      }

      lastNeg = curNeg;
      lastPos = curPos;
    }

    return roots;
  }

  private Fraction binarySearchForRoot(Fraction lo, Fraction hi, int marginPoT){
    System.out.println("binary search was called");
    if (lo.moreThanOrEqualTo(hi)){
      throw new IllegalArgumentException
        ("lo bound (" + lo + ") is greater than or equal to hi bound (" + hi + ")");
    }
    Fraction margin = Fraction.powerOfTen(marginPoT);
    boolean done = false;
    Fraction loVal, midVal, hiVal;
    Fraction mid = lo.add(hi).divide(2);
    while (! done){
      mid = lo.add(hi).divide(2);
      midVal = this.detAtLambda(mid);
      if (Malo.roughlyEquals(midVal, 0, margin)){
        break;
      }
      loVal = this.detAtLambda(lo);
      hiVal = this.detAtLambda(hi);
      // System.out.println("lo: "+lo+"\tlo val: "+loVal);
      // System.out.println("mid: "+mid+"\tmid val: "+midVal);
      // System.out.println("hi: "+hi+"\thi val: "+hiVal+"\n");
      if (loVal.isNegative() && hiVal.isPositive()){
        if (midVal.isPositive()){
          hi = mid;
        }else{
          lo = mid;
        }
      }else{
        if (midVal.isPositive()){
          lo = mid;
        }else{
          hi = mid;
        }
      }
    }
    // System.out.println(mid);
    return mid;
  }

  private Fraction detAtLambda(Fraction lambda){
    // System.out.print("det at lambda "+lambda+" : ");
    Identity identTimesNegEV = new Identity(this.dim());
    identTimesNegEV.scale(lambda.mult(-1));
    SquareMatrix charEq = this.add(identTimesNegEV);
    Fraction result = charEq.det();
    // System.out.println(result);
    return result;
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

}

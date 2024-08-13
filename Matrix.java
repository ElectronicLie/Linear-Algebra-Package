package linalg;

import malo.*;
import fractions.Fraction;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Matrix{

  // FIELDS //

  protected Fraction[][] vals;

  static final int DEFAULT_ROUND = -3;
  static final int DEFAULT_MARGIN = -6;

  // CONSTRUCTORS //

  public Matrix(){}

  public Matrix(int rows, int cols){
    vals = new Fraction[rows][cols];
  }

  public Matrix(Fraction[][] vals){
    this.vals = vals;
  }

  public Matrix(int[][] iVals){
    this.vals = new Fraction[iVals.length][iVals[0].length];
    for (int r = 0; r < vals.length; r++){
      for (int c = 0; c < vals.length; c++){
        this.vals[r][c] = new Fraction(iVals[r][c]);
      }
    }
  }

  public Matrix(ArrayList<Vector> cols){
    int m;
    try{
      m = cols.get(0).dim();
    }catch(Exception e){
      throw new IllegalArgumentException
        ("matrix must be constructed with at least one column vector");
    }
    vals = new Fraction[m][cols.size()];
    for (int c = 0; c < cols.size(); c++){
      if (cols.get(c).dim() != m){
        throw new IllegalArgumentException
          ("matrix columns must all have the same dimension");
      }
      for (int r = 0; r < m; r++){
        vals[r][c] = cols.get(c).get(r);
      }
    }
  }

  // GETTERS //

  public int m(){
    return vals.length; // number of rows
  }

  public int n(){
    if (vals.length == 0){
      return 0;
    }else{
      return vals[0].length;
    }
  }

  public Vector row(int i){
    return new Vector(vals[i]);
  }

  public Vector col(int i){
    Fraction[] ary = new Fraction[m()];
    for (int r = 0; r < m(); r++){
      ary[r] = vals[r][i];
    }
    return new Vector(ary);
  }

  public Fraction get(int r, int c){
    return vals[r][c];
  }

  // OPERATIONS //

  public Matrix mult(Matrix A){
    if (n() != A.m()){
      throw new IllegalArgumentException
        ("left matrix's number of columns is not equal to right matrix's number of rows");
    }
    Matrix product = new Matrix(m(), A.n());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < A.n(); c++){
        product.vals[r][c] = Vector.dot(row(r), A.col(c));
      }
    }
    return product;
  }

  public static Matrix mult(Matrix B, Matrix A){
    return B.mult(A);
  }

  protected static Matrix combineVertically(Matrix A, Matrix B){
    if (A.n() != B.n() && (! (A.m() == 0 || B.m() == 0))){ // emoty
      throw new IllegalArgumentException
        ("matrices must have the same number of columns when combining vertically");
    }
    Matrix result = new Matrix(A.m() + B.m(), A.n());
    for (int r = 0; r < A.m(); r++){
      result.vals[r] = A.vals[r];
    }
    for (int r = A.m(); r < A.m() + B.m(); r++){
      result.vals[r] = B.vals[r - A.m()];
    }
    return result;
  }

  protected static Matrix combineHorizontally(Matrix A, Matrix B){
    if (A.m() != B.m() && (! (A.n() == 0 || B.n() == 0))){
      throw new IllegalArgumentException
        ("matrixes must have the same number of rows when combining horizontally");
    }
    ArrayList<Vector> resultCols = new ArrayList<Vector>();
    for (int c = 0; c < A.n(); c++){
      resultCols.add(A.col(c));
    }
    for (int c = 0; c < B.n(); c++){
      resultCols.add(B.col(c));
    }
    return new Matrix(resultCols);
  }

  public Matrix pow(int n){
    if (n == 1){
      return this;
    }
    return this.mult(this.pow(n-1));
  }

  public SquareMatrix coVarianceMatrix(){
    Matrix coV = this;
    coV.meanCenterRows();
    coV = coV.mult(coV.transpose());
    coV.scale(new Fraction(1, this.n()));
    return coV.squareCopy();
  }

  protected Matrix refPreservePivots(){ // ref without making pivots 1
    if (isZero() || m() == 0){
      return this;
    }else{
      Matrix copy = this.copy();
      int c;
      for (c = 0; c < n(); c++){
        if (! col(c).isZero()){
          if (col(c).get(0).isZero()){
            for (int r = 0; r < m(); r++){
              if (! col(c).get(r).isZero()){
                copy.swapRows(0, r);
              }
            }
          }
          break;
        }
      }
      // copy.vals[0][c] = 1.0; //fail-safe for double arithmetic
      for (int r = 1; r < m(); r++){
        copy.combineRows(r, 0, copy.col(c).get(r).mult(-1));
        copy.vals[r][c] = new Fraction(0); //fail-safe for double arithmetic
      }
      Matrix thisStep = copy.submatrix(0, 1, 0, n());
      Matrix nextStep = copy.submatrix(1, 0);
      return combineVertically(thisStep, nextStep.ref()); // recursion
    }
  }

  public Matrix ref(){
    if (isZero() || m() == 0){
      return this;
    }else{
      Matrix copy = this.copy();
      int c;
      for (c = 0; c < n(); c++){
        if (! col(c).isZero()){
          if (col(c).get(0).isZero()){
            for (int r = 0; r < m(); r++){
              if (! col(c).get(r).isZero()){
                copy.swapRows(0, r);
              }
            }
          }
          break;
        }
      }
      copy.scaleRow(0, Fraction.one().divide(copy.col(c).get(0)));
      // copy.vals[0][c] = 1.0; //fail-safe for double arithmetic
      for (int r = 1; r < m(); r++){
        copy.combineRows(r, 0, copy.col(c).get(r).mult(-1));
        // copy.vals[r][c] = 0.0; //fail-safe for double arithmetic
      }
      Matrix thisStep = copy.submatrix(0, 1, 0, n());
      Matrix nextStep = copy.submatrix(1, 0);
      Matrix result = combineVertically(thisStep, nextStep.ref()); // recursion
      return result;
    }
  }

  public Matrix rref(){
    if (isZero()){
      return this;
    }
    Matrix refed = this.ref();
    System.out.println("REFed:\n"+refed);
    int[][] pivots = new int[0][0];
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        if (! refed.vals[r][c].isZero()){
          if (! refed.vals[r][c].equals(Fraction.one())){
            throw new IllegalStateException
              ("REF did not properly make all row pivots equal to one; row pivot: "+vals[r][c]);
                // for programmer
          }else{
            int[] pivotCoords = new int[2];
            pivotCoords[0] = r;
            pivotCoords[1] = c;
            pivots = Malo.aryAppend(pivots, pivotCoords);
            c = n();
          }
        }
      }
    }
    return refed.rrefAlg(pivots);
  }

  private Matrix rrefAlg(int[][] pivots){
    System.out.println("rrefAlg:\n"+this);
    if (pivots.length == 0){
      return this;
    }
    for (int p = pivots.length-1; p >= 0; p--){
      int c = pivots[p][1];
      int rPivot = pivots[p][0];
      for (int r = rPivot-1; r >= 0; r--){
        System.out.println("going to combine rows");
        System.out.println(this.vals[r][c]);
        combineRows(r, rPivot, this.vals[r][c].mult(-1));
        System.out.println("have combined rows");
        vals[r][c] = Fraction.zero(); //fail-safe for double arithmetic
      }
    }
    return this;
  }

  private void combineRows(int addedTo, int adding, Fraction scalar){
    if (addedTo == adding){
      throw new IllegalArgumentException("cannot combine a row with itself");
    }
    for (int c = 0; c < n(); c++){
      vals[addedTo][c] = vals[addedTo][c].add(vals[adding][c].mult(scalar));
    }
  }

  private void combineRows(int added, int adding){
    combineRows(added, adding, Fraction.one());
  }

  private void scaleRow(int row, Fraction scalar){
    for (int c = 0; c < n(); c++){
      vals[row][c] = vals[row][c].mult(scalar);
    }
  }

  public void swapRows(int rowI1, int rowI2){
    if (rowI1 != rowI2){
      Fraction[] temp = vals[rowI1];
      vals[rowI1] = vals[rowI2];
      vals[rowI2] = temp;
    }
  }

  public void meanCenterColumns(){
    Fraction colMean = Fraction.zero();
    for (int c = 0; c < n(); c++){
      for (int r = 0; r < m(); r++){
        colMean = colMean.add(vals[r][c]);
      }
      colMean = colMean.divide(n());
      for (int r = 0; r < m(); r++){
        vals[r][c] = vals[r][c].subtract(colMean);
      }
      colMean = Fraction.zero();
    }
  }

  public void meanCenterRows(){
    Fraction rowMean = Fraction.zero();
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        rowMean = rowMean.add(vals[r][c]);
      }
      rowMean = rowMean.divide(n());
      for (int c = 0; c < n(); c++){
        vals[r][c] = vals[r][c].subtract(rowMean);
      }
      rowMean = Fraction.zero();
    }
  }

  public Eigenvector principalComponent(int n){ // i.e. principal axis
    SquareMatrix coV = this.coVarianceMatrix();
    try{
      return coV.getEigenvector(n-1); // n = 1 for first principal component
    }catch(ArrayIndexOutOfBoundsException e){
      throw new ArrayIndexOutOfBoundsException("matrix has less than " + 1 + " (not unique) eigenvectors");
    }
  }

  public ArrayList<Eigenvector> allPrincipalComponents(){
    // System.out.println("PCA is underway");
    SquareMatrix coV = this.coVarianceMatrix();
    ArrayList<Eigenvector> result = coV.getEigenvectors();
    // System.out.println("PCA is complete");
    return result;
  }

  public Vector weightedAverageOfPrincipalComponents(){
    ArrayList<Eigenvector> pca = allPrincipalComponents();
    // System.out.println("weighted average of PCs is being calculated");
    Vector mu;
    try{
      mu = new Vector(pca.get(0).dim());
    }catch(ArrayIndexOutOfBoundsException e){
      throw new IllegalStateException
        ("cannot get weighted average of principal components when matrix has no eigenvectors");
    }
    Eigenvector pc;
    Fraction eigenvalsSum = Fraction.zero();
    for (int i = 1; i < pca.size(); i++){
      pc = pca.get(i);
      eigenvalsSum = eigenvalsSum.add(pc.getEigenvalue());
      pc.scale(pc.getEigenvalue());
      mu = mu.add(pc);
    }
    mu.scale(new Fraction(1, eigenvalsSum));
    // System.out.println("weighted average of PCs is complete");
    return mu;
  }

  public boolean equals(Matrix other){
    if (n() != other.n() || m() != other.m()){
      return false;
    }
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        if (! vals[r][c].equals(other.vals[r][c])){
          return false;
        }
      }
    }
    return true;
  }

  // public boolean roughlyEquals(Matrix other, double margin){
  //   if (n() != other.n() || m() != other.m()){
  //     return false;
  //   }
  //   for (int r = 0; r < m(); r++){
  //     for (int c = 0; c < n(); c++){
  //       if (! roughlyEquals(vals[r][c], other.vals[r][c], margin)){
  //         return false;
  //       }
  //     }
  //   }
  //   return true;
  // }
  //
  // public boolean roughlyEquals(Matrix other){
  //   return roughlyEquals(other, Math.pow(10, DEFAULT_ROUND));
  // }

  // toString //

  public String toString(){
    return toString(0);
  }

  public String toString(int noTabs){
    String tabs = "";
    for (int i = 0; i < noTabs; i++){
      tabs += "\t";
    }
    if (m() == 0 && n() == 0){
      return "[]";
    }
    String result = "";
    Fraction rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[n()];
    for (int c = 0; c < n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < m(); r++){
        cur = vals[r][c].toString();
        // cur = rounded + "";
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < m(); r++){
      result += tabs+"[ ";
      for (int c = 0; c < n(); c++){
        cur = vals[r][c].toString();
        curChars = cur.length();
        for (int i = 0; i < colMaxChars[c] - curChars; i++){
          cur += " ";
        }
        result += cur + " ";
      }
      result += "]\n";
    }
    return result;
  }

  public String doubleToString(){
    return doubleToString(Mathematic.DEFAULT_ROUND);
  }

  public String doubleToString(int n){
    return doubleToString(n, 0);
  }

  public String doubleToString(int n, int noTabs){
    String tabs = "";
    for (int i = 0; i < noTabs; i++){
      tabs += "\t";
    }
    if (m() == 0 && n() == 0){
      return "[]";
    }
    String result = "";
    double rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[n()];
    for (int c = 0; c < n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < m(); r++){
        rounded = Malo.roundDouble(vals[r][c].getValue(), n);
        cur = rounded + "";
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < m(); r++){
      result += tabs+"[ ";
      for (int c = 0; c < n(); c++){
        rounded = Malo.roundDouble(vals[r][c].getValue(), n);
        cur = rounded+"";
        curChars = cur.length();
        for (int i = 0; i < colMaxChars[c] - curChars; i++){
          cur += " ";
        }
        result += cur + " ";
      }
      result += "]\n";
    }
    return result;
  }

  public Matrix copy(){
    Matrix copy = new Matrix(m(), n());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        copy.vals[r][c] = this.vals[r][c];
      }
    }
    return copy;
  }

  public SquareMatrix squareCopy(){
    if (m() != n()){
      throw new IllegalStateException("cannot make a square copy of a non-square matrix");
    }
    SquareMatrix copy = new SquareMatrix(m());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        copy.vals[r][c] = this.vals[r][c];
      }
    }
    return copy;
  }

  public static Matrix randomFromValue(int m, int n, double min, double max){
    Matrix random = new Matrix(m, n);
    for (int r = 0; r < m; r++){
      for (int c = 0; c < n; c++){
        random.vals[r][c] = new Fraction(Malo.randomDouble(min, max), "round");
      }
    }
    return random;
  }

  public static Matrix randomFromValue(int m, int n, double min, double max, int p){
    Matrix random = new Matrix(m, n);
    for (int r = 0; r < m; r++){
      for (int c = 0; c < n; c++){
        random.vals[r][c] = new Fraction(Malo.randomDouble(min, max), p, "round");
      }
    }
    return random;
  }

  public static Matrix random(int m, int n, long min, long max){
    Matrix random = new Matrix(m, n);
    long num, den;
    for (int r = 0; r < m; r++){
      for (int c = 0; c < n; c++){
        random.vals[r][c] = Fraction.random(min, max);
      }
    }
    return random;
  }

  public static Matrix random(int m, int n){
    return random(m, n, Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER);
  }

  public void scale(Fraction k){
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        this.vals[r][c] = vals[r][c].mult(k);
      }
    }
  }

  public void plus(Fraction k){
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        vals[r][c] = vals[r][c].add(k);
      }
    }
  }

  public Matrix add(Matrix m){
    if (m() != m.m() || n() != m.n()){
      throw new IllegalArgumentException("Matrices must have the same dimensions to add");
    }
    Matrix result = new Matrix(m(), n());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        result.vals[r][c] = this.get(r,c).add(m.get(r,c));
      }
    }
    return result;
  }

  public Matrix transpose(){
    Matrix T = new Matrix(n(), m());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        T.vals[c][r] = this.vals[r][c];
      }
    }
    return T;
  }

  public Matrix submatrix(int r1, int r2, int c1, int c2){
    Matrix result = new Matrix(r2-r1, c2-c1);
    for (int r = r1; r < r2; r++){
      for (int c = c1; c < c2; c++){
        result.vals[r-r1][c-c1] = this.vals[r][c];
      }
    }
    return result;
  }

  public Matrix submatrix(int r1, int c1){
    return submatrix(r1, m(), c1, n());
  }

  public Fraction sum(){
    Fraction sum = Fraction.zero();
    for (Fraction[] row : vals){
      for (Fraction val : row){
        sum = sum.add(val);
      }
    }
    return sum;
  }

  protected boolean hasZeros(){
    for (Fraction[] row : vals){
      for (Fraction val : row){
        if (val.isZero())
          return true;
      }
    }
    return false;
  }

  protected boolean isZero(){
    for (Fraction[] row : vals){
      for (Fraction val : row){
        if (! val.isZero())
          return false;
      }
    }
    return true;
  }

  // protected boolean isRoughlyZero(){
  //   for (double[] row : vals){
  //     for (double val : row){
  //       if (! roughlyEquals(val, 0, Math.pow(10, DEFAULT_MARGIN)));
  //         return false;
  //     }
  //   }
  //   return true;
  // }

}

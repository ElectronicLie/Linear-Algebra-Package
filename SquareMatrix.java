package linalg;

import polynomials.*;
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
          vals[r][c] = 0;
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

  public double det(){
    Matrix l = refPreservePivots();
    Fraction product = 1;
    for (int i = 0; i < l.m(); i++){
      product = product.mult(l.vals[i][i]);
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
          charEq.vals[r][c] = new SimpleRationalFraction
            (new Polynomial(new double[] {vals[r][c],-1}, "L"), "L");
        }else{
          charEq.vals[r][c] = new SimpleRationalFraction
            (new Polynomial(new double[] {vals[r][c]}, "L"), "L");
        }
      }
    }
    return charEq;
  }

  public SimpleRationalFraction characteristicPolynomial(){
    PolynomialMatrix charEq = characteristicEquation();
    PolynomialMatrix lu = charEq.upperTriangularViaREF();
    SimpleRationalFraction result = lu.mainDiagonalProduct("L");
    // Polynomial one = new Polynomial(new double[] {1.0});
    // if (! result.getDenominator().equals(one)){
    //   throw new IllegalStateException("denominator is complex");
    // }
    // System.out.println(charEq);
    // System.out.println(lu.toStringUnRounded());
    // System.out.println(lu.factorsToString());
    // System.out.println(result);
    // RationalFraction ads = new RationalFraction(new Polynomial(new double[] {-5,1}));
    // RationalFraction abs = new RationalFraction(
    //   new Polynomial(new double[] {1}), new Polynomial(new double[] {5,-1}));
    // System.out.println(ads.mult(abs));
    return result;
  }

  private void calcEigenvectors(){
    this.eigenvectors = new ArrayList<Eigenvector>();
    SimpleRationalFraction charPoly = characteristicPolynomial();
    double[] eigenvals = charPoly.roundedRoots();
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

  public static SquareMatrix randomMatrix(int dim, double min, double max){
    SquareMatrix random = new SquareMatrix(dim);
    for (int r = 0; r < dim; r++){
      for (int c = 0; c < dim; c++){
        random.vals[r][c] = (max - min) * Math.random() + min;
      }
    }
    return random;
  }

  public static SquareMatrix randomMatrix(int dim, double min, double max, int p){
    SquareMatrix random = new SquareMatrix(dim);
    for (int r = 0; r < dim; r++){
      for (int c = 0; c < dim; c++){
        random.vals[r][c] = Matrix.round((max - min) * Math.random() + min, p);
      }
    }
    return random;
  }

  public static SquareMatrix randomMatrix(int dim){
    return randomMatrix(dim, -10.0, 10.0);
  }

  public static SquareMatrix randomMatrix(int dim, int p){
    return randomMatrix(dim, -10.0, 10.0, p);
  }

}

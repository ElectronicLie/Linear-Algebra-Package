package linalg;

import malo.*;
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

  public SquareMatrix(double[][] ary){
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

  public double det(){
    Matrix l = refPreservePivots();
    double product = 1;
    for (int i = 0; i < l.m(); i++){
      product *= l.vals[i][i];
    }
    return product;
  }

  // public Polynomial characteristicPolynomial(){
  //   Minor minor = new Minor(this);
  //   Polynomial result = minor.characteristicPolynomial();
  //   return result;
  // }

  private void calcEigenvectors(){
    this.eigenvectors = new ArrayList<Eigenvector>();
    double[] eigenvals = calcEigenvalues();
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

  private double[] calcEigenvalues(){
    return doubleRoots(-1000, 1000, -3, Math.pow(10, Mathematic.DEFAULT_ZERO_MARGIN));
  }

  private double[] doubleRoots(double min, double max, int scanStepPowerOfTen, double margin){

    if (min > max){
      throw new IllegalArgumentException("interval for root-searching is collapsed");
    }

    double[] roots = new double[0];

    double scanStep = Math.pow(10, scanStepPowerOfTen);

    boolean lastPos = false;
    boolean lastNeg = false;
    boolean curPos = false;
    boolean curNeg = false;

    for (double x = min; x <= max; x += scanStep){
      x = Malo.roundDouble(x, scanStepPowerOfTen);
      double value = this.detAtLambda(x);
      if (value < 0){
        curNeg = true;
        curPos = false;
      }else if (value > 0){
        curNeg = false;
        curPos = true;
      }else if (value == 0){
        roots = Malo.aryAppend(roots, x);
        curPos = false;
        curNeg = false;
      }

      if (value != 0){
        if ((curPos && lastNeg) || (curNeg && lastPos)){
          double xLast = Malo.roundDouble(x - scanStep, scanStepPowerOfTen);
          double r = binarySearchForRoot(xLast, x, margin);
          roots = Malo.aryAppend(roots, r);
        }
      }

      lastNeg = curNeg;
      lastPos = curPos;
    }

    return roots;
  }

  private double binarySearchForRoot(double lo, double hi, double margin){
    if (lo >= hi){
      throw new IllegalArgumentException
        ("lo bound (" + lo + ") is greater than or equal to hi bound (" + hi + ")");
    }
    boolean done = false;
    double loVal, midVal, hiVal;
    double mid = 0.5*(lo+hi);
    while (! done){
      mid = 0.5*(lo + hi);
      midVal = this.detAtLambda(mid);
      if (Malo.roughlyEquals(midVal, 0, margin)){
        break;
      }
      loVal = this.detAtLambda(lo);
      hiVal = this.detAtLambda(hi);
      if (loVal < 0 && hiVal > 0){
        if (midVal > 0){
          hi = mid;
        }else{
          lo = mid;
        }
      }else{
        if (midVal > 0){
          lo = mid;
        }else{
          hi = mid;
        }
      }
    }
    return mid;
  }

  private double detAtLambda(double lambda){
    Identity identTimesNegEV = new Identity(this.dim());
    identTimesNegEV.scale(-1*lambda);
    SquareMatrix charEq = this.add(identTimesNegEV);
    return charEq.det();
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
        random.vals[r][c] = Malo.roundDouble((max - min) * Math.random() + min, p);
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

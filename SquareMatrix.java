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

  private static double det(SquareMatrix part){
    if (part.dim() == 1){
      return part.get(0,0);
    }
    double sum = 0;
    for (int c = 0; c < part.dim(); c++){
      double minorDet = det(part.minor(c)) * part.get(0,c);
      if (c % 2 == 1)
        minorDet *= -1;
      sum += minorDet;
    }
    return sum;
  }

  public double det(){
    return det(this);
  }

  public Polynomial characteristicPolynomial(){
    if (dim() == 1){
      return new Polynomial(new double[] {get(0,0), -1}, "λ");
    }
    Polynomial result = new Polynomial("λ");
    // System.out.println("rows left: " + m() + "\n");
    for (int c = 0; c < dim(); c++){
      Minor minor = new Minor(this, 0, c);
      Polynomial part = minor.characteristicPolynomial();
      // System.out.println("\tcolumn "+c + " -\n");
      // System.out.println("\t\tminor:\n\t\t"+minor);
      // System.out.println("\t\tminor's char. poly.:\n\t\t"+part+"\n");
      if (c == 0){
        part = part.mult(new Polynomial(new double[] {get(0,c), -1}, "λ"));
      }else{
        part = part.scale(get(0,c));
      }
      if (c % 2 == 1){
        part = part.scale(-1);
        // System.out.println("scaled by -1");
      }
      // System.out.println("\t\tchar. poly. after mult. by top entry:\n\t\t"+part+"\n");
      result = result.add(part);
      // System.out.println("\t\t\tresult after adding char. poly.:\n\t\t\t"+result);
    }
    return result;
  }

  private void calcEigenvectors(){
    this.eigenvectors = new ArrayList<Eigenvector>();
    Polynomial charPoly = characteristicPolynomial();
    double[] eigenvals = charPoly.roots();
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

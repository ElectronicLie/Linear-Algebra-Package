package linalg;

import java.util.ArrayList;
import java.util.Arrays;

public class SquareMatrix extends Matrix{

  protected ArrayList<Eigenvector> eigenvectors;
  protected boolean eigenCalced;

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

  private SquareMatrix minor(int rExc, int cExc){
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
    }else{
      double sum = 0;
      for (int c = 0; c < part.dim(); c++){
        double minorDet = det(part.minor(c)) * part.get(0,c);
        if (c % 2 == 1)
          minorDet *= -1;
        sum += minorDet;
      }
      return sum;
    }
  }

  public double det(){
    return det(this);
  }

  private double[] calcEigenvals(double range, int stepPowerOfTen){
    double[] result = new double[0];
    double step = Math.pow(10, stepPowerOfTen);
    for (double lambda = range; lambda >= -1 * range; lambda -= step){
      lambda = Matrix.round(lambda, -1 * stepPowerOfTen);
      // System.out.println(lambda);
      SquareMatrix characteristicEquationMatrix = new Identity(dim());
      characteristicEquationMatrix.scale(-1*lambda);
      characteristicEquationMatrix.addTo(this);
      double determinant = characteristicEquationMatrix.det();
      boolean roughlyZero = Matrix.roughlyEquals(determinant, 0, step);
      if (roughlyZero){
        result = aryAppend(result, lambda);
      }
    }
    System.out.println("eigenvals:"+Arrays.toString(result));
    return result;
  }

  private double[] calcEigenvals(){
    return calcEigenvals(Math.pow(10,3), -3);
  }

  private void calcEigenvectors(){
    double[] eigenvals = calcEigenvals();
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

  ArrayList<Eigenvector> getEigenvectors(){
    if (! eigenCalced){
      calcEigenvectors();
      this.eigenCalced = true;
    }
    return eigenvectors;
  }

  public SquareMatrix add(Matrix other){
    return super.add(other).squareCopy();
  }

}

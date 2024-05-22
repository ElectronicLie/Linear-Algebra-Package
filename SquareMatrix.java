package linalg;

public class SquareMatrix extends Matrix{

  public SquareMatrix(int dim){
    super(dim, dim);
  }

  public SquareMatrix(double[][] ary){
    if (ary.length != ary[0].length){
      throw new IllegalArgumentException("vals array is not square");
    }
    vals = ary;
  }

}

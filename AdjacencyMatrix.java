package linalg;

public class AdjacencyMatrix extends SquareMatrix{

  public AdjacencyMatrix(int dim){
    super(dim);
  }

  public AdjacencyMatrix(double[][] ary){
    super(ary);
    for (double[] row : ary){
      for (double val : row){
        if (! (val == 1 || val == 0)){
          throw new IllegalArgumentException("AdjacencyMatrix can only have entries of 1 or 0");
        }
      }
    }
  }

  public AdjacencyMatrix(Network network){
    super(network);
  }

}

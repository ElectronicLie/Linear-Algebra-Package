package linalg;

import java.util.ArrayList;

public class SquareMatrix extends Matrix{

  public SquareMatrix(int dim){
    super(dim, dim);
  }

  public SquareMatrix(double[][] ary){
    if (ary.length != ary[0].length){
      throw new IllegalArgumentException("square matrix must have an equal number of rows and columns");
    }
    vals = ary;
  }

  public SquareMatrix(ArrayList<Vector> cols){
    super(cols);
    if (vals.length != vals[0].length){
      throw new IllegalArgumentException("square matrix must have an equal number of rows and columns");
    }
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
  }

}

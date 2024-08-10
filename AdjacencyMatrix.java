package linalg;

import fractions.Fraction;

public class AdjacencyMatrix extends SquareMatrix{

  public AdjacencyMatrix(int dim){
    super(dim);
  }

  public AdjacencyMatrix(Fraction[][] ary){
    super(ary);
    for (Fraction[] row : ary){
      for (Fraction val : row){
        if ((! val.equals(Fraction.one())) && (! val.isZero())){
          throw new IllegalArgumentException("AdjacencyMatrix can only have entries of 1 or 0");
        }
      }
    }
  }

  public AdjacencyMatrix(Network network){
    super(network);
  }

}

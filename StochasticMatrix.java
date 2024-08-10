package linalg;

import fractions.Fraction;
import java.util.ArrayList;

public class StochasticMatrix extends SquareMatrix{

  public StochasticMatrix(int dim){
    super(dim);
  }

  public StochasticMatrix(Fraction[][] vals){
    super(vals);
    stochasticize();
  }

  public StochasticMatrix(ArrayList<Vector> cols){
    super(cols);
    stochasticize();
  }

  public StochasticMatrix(Network network){
    super(network);
    stochasticize();
  }

  private void stochasticize(){
    //if (! allNonNegative(vals))
      //throw new IllegalArgumentException("stochastic matrices cannot have negative entries");
    Fraction colSum = Fraction.zero();
    for (int c = 0; c < n(); c++){
      for (int r = 0; r < m(); r++){
        colSum = colSum.add(this.col(c).get(r));
      }
      for (int r = 0; r < m(); r++){
        if (colSum.isZero()){
          throw new IllegalArgumentException("stochastic matrix column has all zero entries");
        }
        vals[r][c] = vals[r][c].divide(colSum);
      }
      colSum = Fraction.zero();
    }
  }

  private static boolean allNonNegative(Fraction[][] vals){
    boolean result = true;
    for (int i = 0; i < vals.length; i++){
      for (int j = 0; j < vals[0].length; j++){
        if (vals[i][j].isNegative()){
          return false;
        }
      }
    }
    return result;
  }

}

package linalg;

import fractions.Fraction;

public class Identity extends SquareMatrix{

  public Identity(int dim){
    super(dim);
    for (int r = 0; r < dim; r++){
      for (int c = 0; c < dim; c++){
        if (r != c)
          vals[r][c] = Fraction.zero();
        else
          vals[r][c] = Fraction.one();
      }
    }
  }

}

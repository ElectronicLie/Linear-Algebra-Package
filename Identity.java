package linalg;

public class Identity extends SquareMatrix{

  public Identity(int dim){
    super(dim);
    for (int r = 0; r < dim; r++){
      for (int c = 0; c < dim; c++){
        if (r != c)
          vals[r][c] = 0;
        else
          vals[r][c] = 1;
      }
    }
  }

}

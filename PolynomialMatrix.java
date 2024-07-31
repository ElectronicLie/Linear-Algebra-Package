package linalg;

import polynomials.*;

public class PolynomialMatrix extends Matrix{

  protected Polynomial[][] vals;

  public PolynomialMatrix(Polynomial[][] pVals){
    this.vals = pVals;
  }

  public PolynomialMatrix(int rows, int cols){
    this.vals = new Polynomial[rows][cols];
  }

  private PolynomialMatrix upperTriangularViaREF(){
    if (isZero() || m() == 0){
      return this;
    }else{
      Matrix copy = this.copy();
      int c;
      for (c = 0; c < n(); c++){
        if (! col(c).isZero()){
          if (col(c).get(0) == 0){
            for (int r = 0; r < m(); r++){
              if (col(c).get(r) != 0){
                copy.swapRows(0, r);
              }
            }
          }
          break;
        }
      }
      copy.vals[0][c] = 1.0; //fail-safe for double arithmetic
      for (int r = 1; r < m(); r++){
        copy.combineRows(r, 0, copy.col(c).get(r) * -1);
        copy.vals[r][c] = 0.0; //fail-safe for double arithmetic
      }
      Matrix thisStep = copy.submatrix(0, 1, 0, n());
      Matrix nextStep = copy.submatrix(1, 0);
      return combineVertically(thisStep, nextStep.upperTriangularViaRREF()); // recursion
    }
  }

  public Polynomial det(){

  }

  protected boolean isZero(){
    for (Polynomial[] row : vals){
      for (Polynomial val : row){
        if (! val.isZero())
          return false;
      }
    }
    return true;
  }

  private void combineRows(int addedTo, int adding, double scalar){
    if (addedTo == adding){
      throw new IllegalArgumentException("cannot combine a row with itself");
    }
    for (int c = 0; c < n(); c++){
      vals[addedTo][c] = vals[addedTo][c].add(vals[adding][c].scale(scalar));
    }
  }

  private void combineRows(int added, int adding){
    combineRows(added, adding, 1);
  }

  private void scaleRow(int row, double scalar){
    for (int c = 0; c < n(); c++){
      vals[row][c] = vals[row][c].scale(scalar);
    }
  }

  // public void swapRows(int rowI1, int rowI2){
  //   if (rowI1 != rowI2){
  //     double[] temp = vals[rowI1];
  //     vals[rowI1] = vals[rowI2];
  //     vals[rowI2] = temp;
  //   }
  // }

}

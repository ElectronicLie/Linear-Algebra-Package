package linalg;

import polynomials.*;

public class Minor extends SquareMatrix{

  protected int[][] mainDiagonalCoords;

  private Minor(){
    vals = new double[0][0];
    mainDiagonalCoords = new int[0][2];
  }

  private Minor(int dim){
    vals = new double[dim][dim];
    mainDiagonalCoords = new int[0][2];
  }

  protected Minor(SquareMatrix matrix, int rExc, int cExc){
    mainDiagonalCoords = new int[0][2];
    vals = new double[matrix.m()-1][matrix.n()-1];
    for (int r = 0; r < rExc; r++){
      for (int c = 0; c < cExc; c++){
        this.vals[r][c] = matrix.vals[r][c];
        if (r == c){
          mainDiagonalCoords = Matrix.aryAppend(mainDiagonalCoords, new int[] {r, c});
        }
      }
    }
    for (int r = rExc+1; r < matrix.dim(); r++){
      for (int c = 0; c < cExc; c++){
        this.vals[r-1][c] = matrix.vals[r][c];
        if (r == c){
          mainDiagonalCoords = Matrix.aryAppend(mainDiagonalCoords, new int[] {r-1, c});
        }
      }
    }
    for (int r = 0; r < rExc; r++){
      for (int c = cExc+1; c < matrix.dim(); c++){
        this.vals[r][c-1] = matrix.vals[r][c];
        if (r == c){
          mainDiagonalCoords = Matrix.aryAppend(mainDiagonalCoords, new int[] {r, c-1});
        }
      }
    }
    for (int r = rExc+1; r < matrix.dim(); r++){
      for (int c = cExc+1; c < matrix.dim(); c++){
        this.vals[r-1][c-1] = matrix.vals[r][c];
        if (r == c){
          mainDiagonalCoords = Matrix.aryAppend(mainDiagonalCoords, new int[] {r-1, c-1});
        }
      }
    }
  }

  protected Minor nextMinor(int rExc, int cExc){
    Minor result = new Minor(dim()-1);
    for (int r = 0; r < rExc; r++){
      for (int c = 0; c < cExc; c++){
        result.vals[r][c] = vals[r][c];
        if (Matrix.aryContains(mainDiagonalCoords, new int[] {r,c})){
          result.mainDiagonalCoords = Matrix.aryAppend(result.mainDiagonalCoords, new int[] {r,c});
        }
      }
    }
    for (int r = rExc+1; r < dim(); r++){
      for (int c = 0; c < cExc; c++){
        result.vals[r-1][c] = vals[r][c];
        if (Matrix.aryContains(mainDiagonalCoords, new int[] {r,c})){
          result.mainDiagonalCoords = Matrix.aryAppend(result.mainDiagonalCoords, new int[] {r-1,c});
        }
      }
    }
    for (int r = 0; r < rExc; r++){
      for (int c = cExc+1; c < dim(); c++){
        result.vals[r][c-1] = vals[r][c];
        if (Matrix.aryContains(mainDiagonalCoords, new int[] {r,c})){
          result.mainDiagonalCoords = Matrix.aryAppend(result.mainDiagonalCoords, new int[] {r,c-1});
        }
      }
    }
    for (int r = rExc+1; r < dim(); r++){
      for (int c = cExc+1; c < dim(); c++){
        result.vals[r-1][c-1] = vals[r][c];
        if (Matrix.aryContains(mainDiagonalCoords, new int[] {r,c})){
          result.mainDiagonalCoords = Matrix.aryAppend(result.mainDiagonalCoords, new int[] {r-1,c-1});
        }
      }
    }
    return result;
  }

  public Polynomial characteristicPolynomial(){
    // System.out.println(this);
    if (dim() == 1){
      if (mainDiagonalCoords.length == 1){
        return new Polynomial(new double[] {vals[0][0], -1}, "λ");
      }else if (mainDiagonalCoords.length == 0){
        return new Polynomial(new double[] {vals[0][0]}, "λ");
      }else{
        throw new IllegalStateException("Minor of dim=1 has more than 1 on main diagonal!?!!?");
      }
    }
    Polynomial result = new Polynomial("λ");
    // System.out.println("rows left: " + m() + "\n");
    for (int c = 0; c < dim(); c++){
      Minor next = nextMinor(0,c);
      // System.out.println(next);
      Polynomial part = next.characteristicPolynomial(); //recursion
      // System.out.println("\tcolumn "+c + " -\n");
      // System.out.println("\t\tminor:\n\t\t"+next);
      // System.out.println("\t\tminor's char. poly.:\n\t\t"+part+"\n");
      if (Matrix.aryContains(mainDiagonalCoords, new int[] {0, c})){
        part = part.mult(new Polynomial(new double[] {this.vals[0][c], -1}, "λ"));
      }else{
        part = part.scale(this.vals[0][c]);
      }
      if (c % 2 == 1){
        part = part.scale(-1);
      }
      // System.out.println("\t\tchar. poly. after mult. by top entry:\n\t\t"+part+"\n");
      result = result.add(part);
      // System.out.println("\t\t\tresult after adding char. poly.:\n\t\t\t"+result);
    }
    return result;
  }

}

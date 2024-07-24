package linalg;

public class SystemOfEquations{

  protected Matrix matrix;
  protected Vector solution;

  public SystemOfEquations(Matrix M){
    matrix = M;
    solve();
  }

  public void addZeroConstants(){
    matrix = Matrix.combineHorizontally(matrix, Vector.zero(matrix.m()));
    solve();
    // System.out.println("\n"+"System of equations:"+"\n"+this.toString());
    // System.out.println("\n"+"system RREFed:"+"\n"+this.matrix.rref().toString());
  }

  public void makeFirstVariableOne(){
    matrix = matrix.submatrix(1,0);
    Matrix firstRow = new Matrix(1, matrix.n());
    for (int c = 0; c < firstRow.n(); c++){
      if (c == 0 || c == firstRow.n()-1){
        firstRow.vals[0][c] = 1;
      }else{
        firstRow.vals[0][c] = 0;
      }
    }
    matrix = Matrix.combineVertically(firstRow, matrix);
    solve();
  }

  private void solve(){
    Matrix rrefed = matrix.rref();
    solution = rrefed.col(rrefed.n()-1);
  }

  public Vector solution(){
    return solution;
  }

  public String toString(){
    return matrix.toString();
  }

}

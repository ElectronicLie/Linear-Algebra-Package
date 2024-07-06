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
  }

  private void solve(){
    Matrix rrefed = matrix.rref();
    solution = rrefed.col(rrefed.n()-1);
  }

  public Vector solution(){
    return solution;
  }

}

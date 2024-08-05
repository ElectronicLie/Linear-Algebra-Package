package linalg;

import java.util.Arrays;

public class SystemOfEquations{

  protected Matrix matrix;
  protected Matrix rrefed;
  protected Vector solution;

  public SystemOfEquations(Matrix M){
    matrix = M;
    solve();
  }

  public void addZeroConstants(){
    matrix = Matrix.combineHorizontally(matrix, Vector.zero(matrix.m()));
    solve();
  }

  public void makeFirstVariableOne(){
    Matrix copy = matrix.copy();
    makeVariableOne(0);
    int var = 0;
    var = 1;
    while (inconsistent() && var < matrix.m()){
      matrix = copy.copy();
      // System.out.println(var);
      // System.out.println("number of rows: "+matrix.m());
      // System.out.println("var<matrix.m(): "+(var<matrix.m()));
      // System.out.println(matrix);
      makeVariableOne(var);
      var++;
    }
  }

  private void makeVariableOne(int n){
    int noCols = matrix.n();
    Matrix above = matrix.submatrix(0, n, 0, matrix.n());
    matrix = matrix.submatrix(n+1, 0);
    Matrix replacement = new Matrix(1, noCols);
    for (int c = 0; c < replacement.n(); c++){
      if (c == n || c == replacement.n()-1){
        replacement.vals[0][c] = 1;
      }else{
        replacement.vals[0][c] = 0;
      }
    }
    Matrix top = Matrix.combineVertically(above, replacement);
    matrix = Matrix.combineVertically(top, matrix);
    solve();
  }

  private void solve(){
    rrefed = matrix.rref();
    // System.out.println(rrefed);
    solution = rrefed.col(rrefed.n()-1);
  }

  public Vector solution(){
    return solution;
  }

  public double[] solutionAsArray(){
    return solution.ary;
  }

  public boolean inconsistent(){
    for (int r = 0; r < noEquations(); r++){
      boolean zero = true;
      for (int c = 0; c < noVariables(); c++){
        if (! Matrix.roughlyEquals(rrefed.vals[r][c],0,Math.pow(10,Matrix.DEFAULT_MARGIN))){
          zero = false;
          break;
        }
      }
      if (zero &&
        (! Matrix.roughlyEquals(rrefed.vals[r][noVariables()],0,Math.pow(10,Matrix.DEFAULT_MARGIN)))){
          // System.out.println("inconsistent:\n"+rrefed);
          return true;
      }
    }
    return false;
  }

  public boolean underconstrained(){
    boolean undcon = noIndependentEquations() < noVariables();
    // if (undcon){
    //   System.out.println("underconstrained");
    // }
    return undcon;
  }

  public boolean dependent(){
    return noIndependentEquations() < noEquations();
  }

  public int noVariables(){
    return matrix.n()-1;
  }

  public int noEquations(){
    return matrix.m();
  }

  public int noIndependentEquations(){
    int result = 0;
    for (int r = 0; r < rrefed.m(); r++){
      if (! rrefed.row(r).isRoughlyZero()){
        result++;
      }
    }
    return result;
  }

  public String toString(){
    return matrix.toString();
  }

}

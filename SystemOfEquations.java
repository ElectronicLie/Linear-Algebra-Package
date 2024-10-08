package linalg;

import fractions.Fraction;
import java.util.Arrays;

public class SystemOfEquations{

  protected Matrix matrix;
  protected Matrix rrefed;
  protected Vector solution;

  public SystemOfEquations(Matrix M){
    matrix = M;
  }

  public void addZeroConstants(){
    matrix = Matrix.combineHorizontally(matrix, Vector.zero(matrix.m()));
  }

  public void makeFirstVariableOne(){
    Matrix copy = matrix.copy();
    // System.out.println("matrix:\n"+matrix);
    makeVariableOne(matrix.m()-1);
    // System.out.println("matrix:\n"+matrix);
    int var = matrix.m()-2;
    while (inconsistent() && var >= 0){
      matrix = copy.copy();
      // System.out.println(var);
      // System.out.println("number of rows: "+matrix.m());
      // System.out.println("var<matrix.m(): "+(var<matrix.m()));
      // System.out.println(matrix);
      makeVariableOne(var);
      var--;
    }
  }

  private void makeVariableOne(int n){
    int noCols = matrix.n();
    Matrix above = matrix.submatrix(0, n, 0, matrix.n());
    matrix = matrix.submatrix(n+1, 0);
    Matrix replacement = new Matrix(1, noCols);
    for (int c = 0; c < replacement.n(); c++){
      if (c == n || c == replacement.n()-1){
        replacement.vals[0][c] = Fraction.one();
      }else{
        replacement.vals[0][c] = Fraction.zero();
      }
    }
    Matrix top = Matrix.combineVertically(above, replacement);
    matrix = Matrix.combineVertically(top, matrix);
  }

  public void solve(){
    System.out.println("system:\n"+matrix);
    // System.out.println("matrix before RREF:\n"+matrix);
    rrefed = matrix.rref();
    System.out.println("RREFed:\n"+rrefed+"\n");
    // System.out.println("matrix after RREF:\n"+matrix);
    // System.out.println(rrefed);
    solution = rrefed.col(rrefed.n()-1);
  }

  public Vector solution(){
    return solution;
  }

  public Fraction[] solutionAsArray(){
    return solution.ary;
  }

  public boolean inconsistent(){
    solve();
    for (int r = 0; r < noEquations(); r++){
      boolean zero = true;
      for (int c = 0; c < noVariables(); c++){
        if (! rrefed.vals[r][c].equals(0)){
          zero = false;
          break;
        }
      }
      if (zero && (! rrefed.vals[r][noVariables()].equals(0))){
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
      if (! rrefed.row(r).isZero()){
        result++;
      }
    }
    return result;
  }

  public String toString(){
    return matrix.toString();
  }

}

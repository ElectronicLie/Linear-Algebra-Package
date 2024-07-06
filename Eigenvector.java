package linalg;

public class Eigenvector extends Vector{

  protected double eigenval;
  protected SquareMatrix M;

  Eigenvector(SquareMatrix m, double eigenvalue){
    M = m;
    eigenval = eigenvalue;
    Matrix identity = new Identity(M.dim());
    identity.scale(-1 * eigenval);
    SquareMatrix characteristicEqMatrix = M.add(identity);
    SystemOfEquations eq = new SystemOfEquations(characteristicEqMatrix);
    eq.addZeroConstants();
    this.vals = eq.solution().vals;
  }

  public double getEigenvalue(){
    return eigenval;
  }

  public String toString(){
    return super.toString() + "\neigenvalue: " + eigenval;
  }

}

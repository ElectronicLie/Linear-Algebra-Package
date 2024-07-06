package linalg;

public class Eigenvector extends Vector{

  protected double eigenval;
  protected SquareMatrix M;

  public Eigenvector(SquareMatrix m, double eigenvalue){
    M = m;
    eigenval = eigenvalue;
    Matrix identity = new Identity(M.dim());
    SquareMatrix characteristicEqMatrix = M.add(identity.scale(-1 * eigenval));
    SystemOfEquations eq = new SystemOfEquations(characteristicEqMatrix);
    eq.addZeroConstants();
    this.vals = eq.solution().vals;
  }

}

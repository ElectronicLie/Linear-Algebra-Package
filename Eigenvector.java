package linalg;

public class Eigenvector extends Vector{

  private double eigenval;
  private SquareMatrix M;

  public Eigenvector(SquareMatrix m, double eigenvalue){
    M = m;
    eigenval = eigenvalue;
    identity = new Identity(M.dim());
    Matrix characteristicEqMatrix = M.add(identity.scale(-1 * eigenval));
    Vector zerosConstants = Vector.zero();
    
  }

}

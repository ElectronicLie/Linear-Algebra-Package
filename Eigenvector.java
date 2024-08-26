package linalg;

import fractions.Fraction;

public class Eigenvector extends Vector{

  protected Fraction eigenval;
  protected SquareMatrix M;

  Eigenvector(SquareMatrix m, Fraction eigenvalue){
    M = m;
    eigenval = eigenvalue;
    Matrix identity = new Identity(M.dim());
    identity.scale(eigenval.mult(-1));
    SquareMatrix characteristicEqMatrix = M.add(identity);
    SystemOfEquations eq = new SystemOfEquations(characteristicEqMatrix);
    eq.addZeroConstants();
    eq.makeFirstVariableOne();
    // System.out.println("system of equations to calculate eigenvector: \n"+eq);
    eq.solve();
    this.vals = eq.solution().vals;
  }

  public Fraction getEigenvalue(){
    return eigenval;
  }

  public String fractionToString(){
    return "\n"+super.toString() + "eigenvalue: " + eigenval+"\n";
  }

  public String toString(){
    return "\n"+super.doubleToString() + "eigenvalue: "+eigenval.getValue()+"\n";
  }

}

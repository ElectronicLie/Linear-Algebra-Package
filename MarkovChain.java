package linalg;

import fractions.Fraction;

public class MarkovChain{

  private Network network;
  private StochasticMatrix matrix;
  private Vector steadyState;

  public MarkovChain(Network network){
    this.network = network;
    matrix = new StochasticMatrix(network);
    // System.out.println("stochastic matrix:\n"+matrix);
    steadyState = calcSteadyState();
  }

  private Vector calcSteadyState(){
    return matrix.pow(1023).col(0);
  }

  public Vector getSteadyState(){
    return steadyState;
  }

  public Vector step(Vector initialState, int n){
    return (Vector)(initialState.mult(this.matrix.pow(n)));
  }

  public String steadyStateToString(){
    Vector v = getSteadyState();
    String result = "";
    for (int n = 0; n < network.size(); n++){
      result += "[ " + v.get(n) + " ] " + network.getNode(n).getName() + "\n";
    }
    return result;
  }

  public String sortedSteadyStateToString(){
    String[] nodeNames = new String[network.size()];
    for (int n = 0; n < network.size(); n++){
      nodeNames[n] = network.getNode(n).getName();
    }
    Vector v = this.steadyState;
    for (int i = v.dim()-1; i >= 0; i--){ //bubble sort
      for (int j = 0; j < i; j++){
        if (v.get(j).moreThan(v.get(j+1))){
          Fraction tmp = v.get(j);
          v.vals[j][0] = v.get(j+1);
          v.vals[j+1][0] = tmp;
          String strTmp = nodeNames[j];
          nodeNames[j] = nodeNames[j+1];
          nodeNames[j+1] = strTmp;
        }
      }
    }
    String result = "";
    for (int n = 0; n < network.size(); n++){
      result += "[ " + v.get(n) + " ] " + nodeNames[n] + "\n";
    }
    return result;
  }

  public String matrixToString(){
    return matrix.toString();
  }

  public String steadyStateMatrixToString(){
    return matrix.pow(1023).toString();
  }

  public String toString(){
    String result = "";
    Fraction rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[matrix.n()];
    for (int c = 0; c < matrix.n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < matrix.m(); r++){
        rounded = matrix.get(r,c);
        cur = rounded + "";
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < matrix.m(); r++){
      result += "[ ";
      for (int c = 0; c < matrix.n(); c++){
        rounded = matrix.get(r,c);
        cur = rounded + "";
        curChars = cur.length();
        for (int i = 0; i < colMaxChars[c] - curChars; i++){
          cur += " ";
        }
        result += cur + " ";
      }
      result += "] " + network.getNode(r).getName() + "\n";
    }
    return result;
  }

}

package linalg;

public class MarkovChain{

  private Network network;
  private StochasticMatrix matrix;

  public MarkovChain(Network network){
    this.network = network;
    matrix = new StochasticMatrix(network);
  }

  public Vector steadyState(){
    return matrix.pow(1023).col(0);
  }

  public Vector step(Vector initialState, int n){
    return (Vector)(initialState.mult(this.matrix.pow(n)));
  }

  public String steadyStateToString(int p){
    Vector v = steadyState();
    String result = "";
    for (int n = 0; n < network.size(); n++){
      result += "[ " + Matrix.round(v.get(n), p) + " ] " + network.getNode(n).getName() + "\n";
    }
    return result;
  }

  public String steadyStateToString(){
    return steadyStateToString(Matrix.DEFAULT_ROUND);
  }

  public String sortedSteadyStateToString(int p){
    String[] nodeNames = new String[network.size()];
    for (int n = 0; n < network.size(); n++){
      nodeNames[n] = network.getNode(n).getName();
    }
    Vector v = steadyState();
    for (int i = v.dim()-1; i >= 0; i--){ //bubble sort
      for (int j = 0; j < i; j++){
        if (v.get(j) > v.get(j+1)){
          double tmp = v.get(j);
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
      result += "[ " + Matrix.round(v.get(n), p) + " ] " + nodeNames[n] + "\n";
    }
    return result;
  }

  public String sortedSteadyStateToString(){
    return sortedSteadyStateToString(Matrix.DEFAULT_ROUND);
  }

  public String toString(){
    return toString(Matrix.DEFAULT_ROUND);
  }

  public String matrixToString(){
    return matrix.toString();
  }

  public String steadyStateMatrixToString(){
    return matrix.pow(1023).toString();
  }

  public String toString(int n){
    String result = "";
    double rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[matrix.n()];
    for (int c = 0; c < matrix.n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < matrix.m(); r++){
        rounded = Math.round(Math.pow(10,n) * matrix.get(r,c)) / Math.pow(10,n);
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
        rounded = Math.round(Math.pow(10,n) * matrix.get(r,c)) / Math.pow(10,n);
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

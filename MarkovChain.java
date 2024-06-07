package linalg;

public class MarkovChain{

  private Network network;
  private StochasticMatrix matrix;

  public MarkovChain(Network network){
    this.network = network;
    matrix = new StochasticMatrix(network);
  }

  public Vector steadyState(){
    return matrix.pow(255).col(0);
  }

  public Vector step(Vector initialState, int n){
    return (Vector)(initialState.mult(this.matrix.pow(n)));
  }

  public String steadyStateToString(){
    Vector v = steadyState();
    String result = "";
    for (int n = 0; n < network.size(); n++){
      result += "[ " + v.get(n) + " ]" + network.getNode(n).getName();
    }
    return result;
  }

  public String toString(){
    return toString(Matrix.DEFAULT_ROUND);
  }

  public String matrixToString(){
    return matrix.toString();
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

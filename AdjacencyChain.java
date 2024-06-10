package linalg;

public class AdjacencyChain{

  private Network network;
  private AdjacencyMatrix matrix;

  public AdjacencyChain(Network network){
    this.network = network;
    matrix = new AdjacencyMatrix(network);
  }

  public Matrix sumChain(){
    Matrix result = new Matrix(matrix.m(), matrix.n());
    Matrix term;
    for (double n = 1; result.hasZeros(); n++){
      term = matrix.pow(n);
      term.scale(1.0/n);
      result.addTo(term);
    }
    return result;
  }

  public double qualityIndex(double noPaths){
    return sumChain().sum() / noPaths;
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

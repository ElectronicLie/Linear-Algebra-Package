package linalg;

import malo.*;

public class AdjacencyChain{

  private Network network;
  private AdjacencyMatrix matrix;
  private Matrix sumChain;
  private int noIterations;

  public AdjacencyChain(Network network){
    this.network = network;
    matrix = new AdjacencyMatrix(network);
    calculateSumChain();
  }

  private AdjacencyChain(double[][] ary, int iterations){
    matrix = new AdjacencyMatrix(ary);
    calculateSumChain(iterations);
  }

  private void calculateSumChain(int iterations){
    Matrix result = new Matrix(matrix.m(), matrix.n());
    Matrix term;
    double n;
    for (n = 1; n <= iterations; n++){
      term = matrix.pow(n);
      term.scale(1.0/(Math.pow(matrix.m(),n-1)));
      result.addTo(term);
    }
    noIterations = iterations;
    sumChain = result;
  }

  private void calculateSumChain(){
    Matrix result = new Matrix(matrix.m(), matrix.n());
    Matrix term;
    double n;
    for (n = 1; result.hasZeros(); n++){
      term = matrix.pow(n);
      term.scale(1.0/(Math.pow(matrix.m(),n-1)));
      result.addTo(term);
    }
    System.out.println("number of iterations in AdjacencyChain: " + n);
    noIterations = (int)n;
    sumChain = result;
  }

  public double qualityIndex(double noPaths){
    double thisIndex = sumChain.sum() / (sumChain.m() * sumChain.n() * noPaths * noIterations);
    return thisIndex;
  }

  public double get(String node1, String node2){
    return sumChain.get(network.indexOfNode(node1), network.indexOfNode(node2));
  }

  private static double[][] allOnes(int dim){
    double[][] perfect = new double[dim][dim];
    for (int i = 0; i < perfect.length; i++){
      for (int j = 0; j < perfect[i].length; j++){
        perfect[i][j] = 1;
      }
    }
    return perfect;
  }

  public String toString(){
    return toString(Mathematic.DEFAULT_ROUND);
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

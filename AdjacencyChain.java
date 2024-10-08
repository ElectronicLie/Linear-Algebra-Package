package linalg;

import malo.Malo;
import fractions.Fraction;

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

  private AdjacencyChain(Fraction[][] ary, int iterations){
    matrix = new AdjacencyMatrix(ary);
    calculateSumChain(iterations);
  }

  private void calculateSumChain(int iterations){
    Matrix result = new Matrix(matrix.m(), matrix.n());
    Matrix term;
    int n;
    for (n = 1; n <= iterations; n++){
      term = matrix.pow(n);
      term.scale(new Fraction(1, Malo.pow(matrix.m(), n-1)));
      result = result.add(term);
    }
    noIterations = iterations;
    sumChain = result;
  }

  private void calculateSumChain(){
    Matrix result = new Matrix(matrix.m(), matrix.n());
    Matrix term;
    int n;
    for (n = 1; result.hasZeros(); n++){
      term = matrix.pow(n);
      term.scale(new Fraction(1, Malo.pow(matrix.m(), n-1)));
      result = result.add(term);
    }
    System.out.println("number of iterations in AdjacencyChain: " + n);
    noIterations = (int)n;
    sumChain = result;
  }

  public double qualityIndex(int noPaths){
    Fraction thisIndex = sumChain.sum().divide(sumChain.m() * sumChain.n() * noPaths * noIterations);
    return thisIndex.getValue();
  }

  public Fraction get(String node1, String node2){
    return sumChain.get(network.indexOfNode(node1), network.indexOfNode(node2));
  }

  private static Fraction[][] allOnes(int dim){
    Fraction[][] perfect = new Fraction[dim][dim];
    for (int i = 0; i < perfect.length; i++){
      for (int j = 0; j < perfect[i].length; j++){
        perfect[i][j] = Fraction.one();
      }
    }
    return perfect;
  }

  public String toString(){
    String result = "";
    double rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[matrix.n()];
    for (int c = 0; c < matrix.n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < matrix.m(); r++){
        cur = matrix.get(r,c).toString();
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < matrix.m(); r++){
      result += "[ ";
      for (int c = 0; c < matrix.n(); c++){
        cur = matrix.get(r,c).toString();
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

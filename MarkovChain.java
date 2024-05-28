package linalg;

public class MarkovChain{

  private Network network;
  private StochasticMatrix matrix;

  public MarkovChain(Network network){
    network = network;
    matrix = new StochasticMatrix(network);
  }

  public Vector steadyState(){
    return matrix.pow(255).col(0);
  }

  public String toString(){
    Vector v = steadyState();
    String result = "";
    for (int n = 0; n < network.size(); n++){
      result += "[ " + v.get(n) + " ]" + network.getNode(n).getName();
    }
    return result;
  }

}

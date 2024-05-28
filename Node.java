package linalg;

import java.util.ArrayList;

public class Node{

  protected ArrayList<Edge> edges;
  protected double[] edgeVals;
  protected ArrayList<Node> neighbors;
  protected String[] nabrs;
  protected Network network;
  protected String name;

  public Node(String name, String[] neibrs, double[] vals){
    name = name;
    if (neibrs.length != vals.length){
      throw new IllegalArgumentException("Node must have the same number of neighbors and valued edges");
    }
    nabrs = neibrs;
    edgeVals = vals;
    neighbors = new ArrayList<Node>(network.size());
    edges = new ArrayList<Edge>(network.size());
    updateNeighbors(vals);
  }

  public Node(String name, String[] neibrs){
    this(name, neibrs, uniformStochasticAry(neibrs.length));
  }

  public void updateNeighbors(double[] vals){
    for (int n = 0; n < network.size(); n++){
      if (aryContains(nabrs, network.getNode(n).getName())){
        neighbors.set(n, network.getNode(n));
        edges.set(n, new Edge(this, network.getNode(n), vals[n]));
      }
    }
  }

  public void setNetwork(Network network){
    network = network;
  }

  public Edge getEdge(Node other){
    for (int n = 0; n < neighbors.size(); n++){
      if (other == neighbors.get(n)){
        return edges.get(n);
      }
    }
    return null;
  }

  public double getEdgeVal(Node other){
    return getEdge(other).getVal();
  }

  public double[] getEdgeVals(){
    return edgeVals;
  }

  public String getName(){
    return name;
  }

  private static boolean aryContains(String[] ary, String target){
    for (int i = 0; i < ary.length; i++){
      if (ary[i].equals(target)){
        return true;
      }
    }
    return false;
  }

  private static double[] uniformStochasticAry(int length){
    double[] result = new double[length];
    for (int i = 0; i < length; i++){
      result[i] = 1.0 / length;
    }
    return result;
  }

}

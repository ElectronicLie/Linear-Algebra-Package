package linalg;

import java.util.ArrayList;
import java.util.Arrays;

public class Node{

  protected ArrayList<Edge> edges;
  protected double[] edgeVals;
  protected ArrayList<Node> neighbors;
  protected String[] nabrs;
  protected Network network;
  protected String name;

  public Node(String name, String[] neibrs, double[] vals, double selfVal){
    this.name = name;
    if (neibrs.length != vals.length){
      throw new IllegalArgumentException("Node must have the same number of neighbors and valued edges");
    }
    nabrs = neibrs;
    edgeVals = vals;
    neighbors = new ArrayList<Node>(neibrs.length);
    for (int i = 0; i < neibrs.length; i++){
      neighbors.add(null);
    }
    edges = new ArrayList<Edge>(neibrs.length+1);
    for (int i = 0; i < neibrs.length+1; i++){
      edges.add(null);
    }
    edges.set(neibrs.length, new Edge(this, this, selfVal));
    //updateNeighbors();
  }

  public Node(String name, String[] neibrs){
    this(name, neibrs, uniformPseudoStochasticAry(neibrs.length+1), 1.0/((double)neibrs.length+1.0));
  }

  public void updateNeighbors(){
    for (int n = 0; n < network.size(); n++){
      Node other = network.getNode(n);
      int i = aryIndexOf(nabrs, other.getName());
      if (i != -1 && getEdge(other) == null){
        neighbors.set(i, other);
        edges.set(i, new Edge(this, other, edgeVals[i]));
        if (network.even()){
          if (aryIndexOf(other.nabrs, this.getName()) == -1){
            other.neighbors.add(this);
            other.nabrs = aryCopyAdd(other.nabrs, this.getName());
            other.edgeVals = uniformPseudoStochasticAry(other.nabrs.length);
            other.addEdge(new Edge(other, this, other.edgeVals[other.edgeVals.length-1]));
          }
        }
      }
    }
  }

  public void setNetwork(Network network){
    this.network = network;
  }

  public Edge getEdge(Node other){
    if (other == this){
      return edges.get(edges.size()-1);
    }
    for (int n = 0; n < neighbors.size(); n++){
      if (neighbors.get(n) != null){
        if (other.getName().equals(neighbors.get(n).getName())){
          return edges.get(n);
        }
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

  private void addEdge(Edge edge){
    Edge selfEdge = edges.get(edges.size()-1);
    edges.add(selfEdge);
    edges.set(edges.size()-2,edge);
  }

  public String toString(){
    return getName();
  }

  public String sumToString(){
    return "Node name: " + name + "\n" + "neighbors: " + Arrays.toString(nabrs);
  }

  public String deepToString(){
    return sumToString() + "\n"
      + "Network: " + network.toString() + "\n"
      + "neighbors ArrayList: " + neighbors.toString() + "\n"
      + "Edges ArrayList: " + edges.toString() + "\n"
      + "Edge values: " + Arrays.toString(edgeVals);
  }

  private static int aryIndexOf(String[] ary, String target){
    for (int i = 0; i < ary.length; i++){
      if (ary[i].equals(target)){
        return i;
      }
    }
    return -1;
  }

  private static double[] uniformStochasticAry(int length){
    double[] result = new double[length];
    for (int i = 0; i < length; i++){
      result[i] = 1.0 / (double)length;
    }
    return result;
  }

  private static double[] uniformPseudoStochasticAry(int trueLength){
    double[] result = new double[trueLength-1];
    for (int i = 0; i < result.length; i++){
      result[i] = 1.0 / (double)trueLength;
    }
    return result;
  }

  private static String[] aryCopyAdd(String[] ary, String newStr){
    String[] result = new String[ary.length+1];
    for (int i = 0; i < ary.length-1; i++){
      result[i] = ary[i];
    }
    result[ary.length] = newStr;
    result[ary.length] = ary[ary.length-1];
    return result;
  }

}

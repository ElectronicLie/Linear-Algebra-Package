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
    edges = new ArrayList<Edge>(neibrs.length);
    for (int i = 0; i < neibrs.length; i++){
      edges.add(null);
    }
    edges.add(new Edge(this, this, selfVal));
  }

  public Node(String name, String[] neibrs){
    this(name, neibrs, uniformPseudoStochasticAry(neibrs.length+1), 1.0/((double)neibrs.length+1.0));
  }

  void updateNeighbors(){
    for (int n = 0; n < network.size(); n++){
      Node other = network.getNode(n);
      int i = aryIndexOf(nabrs, other.getName());
      if (i != -1 && getEdge(other) == null){
        neighbors.set(i, other);
        edges.set(i, new Edge(this, other, edgeVals[i]));
      }
      if (network.isEven()){
        edgeVals = evenEdgeValsForActiveNeighbors();
        updateEdges();
      }
      if (network.isEven() && i != -1 && other.getEdge(this) == null){
        if (aryIndexOf(other.nabrs, this.getName()) == -1){
          other.neighbors.add(this);
          other.nabrs = aryCopyAdd(other.nabrs, this.getName());
          other.edgeVals = other.evenEdgeValsForActiveNeighbors();
          other.updateEdges();
          other.edges.get(other.edges.size()-1).setWeight(1.0/(double)(noActiveNeighbors()+1));
          other.addEdge(new Edge(other, this, other.edgeVals[other.edgeVals.length-1]));
        }
      }
    }
  }

  private void updateEdges(){
    for (int e = 0; e < edges.size(); e++){
      if (edges.get(e) != null){
        edges.get(e).setWeight(edgeVals[e]);
      }
    }
  }

  public int noActiveNeighbors(){
    int result = 0;
    for (int i = 0; i < neighbors.size(); i++){
      if (neighbors.get(i) != null){
        result++;
      }
    }
    // System.out.println(name + neighbors);
    return result;
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

  private void addEdge(Edge edge){
    Edge selfEdge = edges.get(edges.size()-1);
    edges.add(selfEdge);
    edges.set(edges.size()-2,edge);
  }

  public double getEdgeWeight(Node other){
    return getEdge(other).getWeight();
  }

  public double[] getEdgeWeights(){
    return edgeVals;
  }

  public String getName(){
    return name;
  }

  private double[] evenEdgeValsForActiveNeighbors(){
    double[] result = new double[noActiveNeighbors()];
    for (int i = 0; i < result.length; i++){
      if (neighbors.get(i) == null)
        result[i] = 0;
      else
        result[i] = 1.0 / (double)(noActiveNeighbors()+1); //active neighbors plus self
    }
    return result;
  }

  public String toString(){
    return getName();
  }

  public String sumToString(){
    return "Node name: " + name + "\n" + "neighbors' names: " + Arrays.toString(nabrs);
  }

  public String deepToString(){
    return sumToString() + "\n"
      + "Network: " + network.toString() + "\n"
      + "neighbors ArrayList: " + neighbors.toString() + "\n"
      + "Edges ArrayList: " + edges.toString() + "\n"
      + "Edge weights: " + Arrays.toString(edgeVals);
  }

  private static int aryIndexOf(String[] ary, String target){
    for (int i = 0; i < ary.length; i++){
      if (ary[i] != null){
        if (ary[i].equals(target)){
          return i;
        }
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
    for (int i = 0; i < ary.length; i++){
      result[i] = ary[i];
    }
    result[ary.length] = newStr;
    return result;
  }

}

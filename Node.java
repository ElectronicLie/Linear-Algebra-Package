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
  protected String[] terminology;

  public Node(String name, String[] neibrs, double[] vals, double selfVal){
    this.name = name;
    if (neibrs.length != vals.length){
      throw new IllegalArgumentException("Node must have the same number of neighbors and weighted edges");
    }
    nabrs = aryPrepend(neibrs, name);
    edgeVals = aryPrepend(vals, selfVal);
    neighbors = new ArrayList<Node>(neibrs.length);
    neighbors.add(this);
    for (int i = 0; i < neibrs.length; i++){
      neighbors.add(null);
    }
    edges = new ArrayList<Edge>(neibrs.length);
    edges.add(new Edge(this, this, selfVal));
    for (int i = 0; i < neibrs.length; i++){
      edges.add(null);
    }
    terminology = new String[] {"Node name", "Network", "neighbors' names", "Neighbors ArrayList",
      "Edges ArrayList", "Edge weights"};
  }

  public Node(String name, String[] neibrs){
    this(name, neibrs, uniformPseudoStochasticAry(neibrs.length+1), 1.0/((double)neibrs.length+1.0));
  }

  void updateNeighbors(){
    if (nabrs.length != neighbors.size()){
      throw new IllegalStateException("nabrs - "+nabrs.length+", "+"neighbors - "+neighbors.size());
    }
    for (int n = 0; n < network.size(); n++){
      Node other = network.getNode(n);
      int indexOfNeighbor = aryIndexOf(nabrs, other.getName());
      if (indexOfNeighbor != -1){
        if (getEdge(other) == null)
          neighbors.set(indexOfNeighbor, other);
        updateEdge(indexOfNeighbor);
      }
      if (network.isEven()){
        edgeVals = evenEdgeValsForActiveNeighbors();
        updateEdges();
        int indexOfSelf = aryIndexOf(other.nabrs, getName());
        if (indexOfSelf != -1 && indexOfNeighbor == -1){
          nabrs = aryAppend(nabrs, other.getName());
          neighbors.add(other);
          edges.add(null);
          edgeVals = evenEdgeValsForActiveNeighbors();
          updateEdges();
        }
      }else if (network.isAdjacency()){
        edgeVals = adjacentEdgeValsForActiveNeighbors();
        updateEdges();
        int indexOfSelf = aryIndexOf(other.nabrs, getName());
        if (indexOfSelf != -1 && indexOfNeighbor == -1){
          nabrs = aryAppend(nabrs, other.getName());
          neighbors.add(other);
          edges.add(null);
          edgeVals = adjacentEdgeValsForActiveNeighbors();
          updateEdges();
        }
      }else{
        throw new IllegalStateException("network is neither an even network or an adjacency network");
      }
    }
  }

  private void updateEdge(int n){
    if (neighbors.get(n) != null){
      if (edges.get(n) == null){
        edges.set(n, new Edge(this, neighbors.get(n), edgeVals[n]));
      }else{
        edges.get(n).setWeight(edgeVals[n]);
      }
    }
  }

  private void updateEdges(){
    for (int n = 0; n < neighbors.size(); n++){
      updateEdge(n);
    }
  }

  public int noActiveNeighbors(){
    int result = 0;
    for (int i = 0; i < neighbors.size(); i++){
      if (neighbors.get(i) != null){
        result++;
      }
    }
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

  public double getEdgeWeight(Node other){
    return getEdge(other).getWeight();
  }

  public double[] getEdgeWeights(){
    return edgeVals;
  }

  public String getName(){
    return name;
  }

  private double[] evenEdgeValsForActiveNeighbors(){ // including self
    double[] result = new double[edges.size()];
    for (int i = 0; i < result.length; i++){
      if (neighbors.get(i) == null)
        result[i] = 0;
      else
        result[i] = 1.0 / (double)(noActiveNeighbors()); //active neighbors plus self
    }
    return result;
  }

  private double[] adjacentEdgeValsForActiveNeighbors(){
    double[] result = new double[edges.size()];
    for (int i = 0; i < result.length; i++){
      if (neighbors.get(i) == null)
        result[i] = 0;
      else
        result[i] = 1;
    }
    return result;
  }

  public String toString(){
    return getName();
  }

  public String sumToString(){
    return terminology[0] + ": " + name + "\n" + terminology[2] + ": " +
      Arrays.toString((nabrs));
  }

  public String sumMoreToString(){
    return sumToString() + "\n"
      + terminology[4] + ": " + edges.toString();
  }

  public String deepToString(){
    return sumToString() + "\n"
      + terminology[1] + ": " + network.toString() + "\n"
      + terminology[3] + ": " + neighbors.toString() + "\n"
      + terminology[4] + ": " + edges.toString() + "\n"
      + terminology[5] + ": " + Arrays.toString(edgeVals);
  }

  public String deepToStringWithoutNetwork(){
    return sumToString() + "\n"
      + terminology[3] + ": " + neighbors.toString() + "\n"
      + terminology[4] + ": " + edges.toString() + "\n"
      + terminology[5] + ": " + Arrays.toString(edgeVals);
  }

  private static String[] aryRemoveFirst(String[] ary){
    String[] result = new String[ary.length-1];
    for (int i = 0; i < result.length; i++){
      result[i] = ary[i+1];
    }
    return result;
  }

  public static int aryIndexOf(String[] ary, String target){
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

  public static String[] aryAppend(String[] ary, String newStr){
    String[] result = new String[ary.length+1];
    for (int i = 0; i < ary.length; i++){
      result[i] = ary[i];
    }
    result[ary.length] = newStr;
    return result;
  }

  private static String[] aryPrepend(String[] ary, String newElement){
    String[] result = new String[ary.length+1];
    result[0] = newElement;
    for (int i = 1; i < result.length; i++){
      result[i] = ary[i-1];
    }
    return result;
  }

  private static double[] aryPrepend(double[] ary, double newDouble){
    double[] result = new double[ary.length+1];
    result[0] = newDouble;
    for (int i = 1; i < result.length; i++){
      result[i] = ary[i-1];
    }
    return result;
  }

}

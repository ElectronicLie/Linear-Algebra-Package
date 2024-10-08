package linalg;

import malo.Malo;
import fractions.Fraction;
import java.util.ArrayList;
import java.util.Arrays;

public class Node{

  protected ArrayList<Edge> edges;
  protected Fraction[] edgeVals;
  protected ArrayList<Node> neighbors;
  protected String[] nabrs;
  protected Network network;
  protected String name;
  protected String[] terminology;

  public Node(String name, String[] neibrs, Fraction[] vals, Fraction selfVal){
    this.name = name;
    if (neibrs.length != vals.length){
      throw new IllegalArgumentException("Node must have the same number of neighbors and weighted edges");
    }
    nabrs = Malo.aryPrepend(neibrs, name);
    edgeVals = Malo.aryPrepend(vals, selfVal);
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
    this(name, neibrs, Malo.uniformPseudoStochasticAry(neibrs.length+1),
      new Fraction(1, neibrs.length+1));
  }

  void updateNeighbors(){
    if (nabrs.length != neighbors.size()){
      throw new IllegalStateException("nabrs - "+nabrs.length+", "+"neighbors - "+neighbors.size());
    }
    for (int n = 0; n < network.size(); n++){
      Node other = network.getNode(n);
      int indexOfNeighbor = Malo.aryIndexOf(nabrs, other.getName());
      if (indexOfNeighbor != -1){
        if (getEdge(other) == null)
          neighbors.set(indexOfNeighbor, other);
        updateEdge(indexOfNeighbor);
      }
      if (network.isEven()){
        edgeVals = evenEdgeValsForActiveNeighbors();
        updateEdges();
        int indexOfSelf = Malo.aryIndexOf(other.nabrs, getName());
        if (indexOfSelf != -1 && indexOfNeighbor == -1){
          nabrs = Malo.aryAppend(nabrs, other.getName());
          neighbors.add(other);
          edges.add(null);
          edgeVals = evenEdgeValsForActiveNeighbors();
          updateEdges();
        }
      }else if (network.isAdjacency()){
        edgeVals = adjacentEdgeValsForActiveNeighbors();
        updateEdges();
        int indexOfSelf = Malo.aryIndexOf(other.nabrs, getName());
        if (indexOfSelf != -1 && indexOfNeighbor == -1){
          nabrs = Malo.aryAppend(nabrs, other.getName());
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

  public Fraction getEdgeWeight(Node other){
    return getEdge(other).getWeight();
  }

  public Fraction[] getEdgeWeights(){
    return edgeVals;
  }

  public String getName(){
    return name;
  }

  private Fraction[] evenEdgeValsForActiveNeighbors(){ // including self
    Fraction[] result = new Fraction[edges.size()];
    for (int i = 0; i < result.length; i++){
      if (neighbors.get(i) == null)
        result[i] = Fraction.zero();
      else
        result[i] = new Fraction(1, noActiveNeighbors()); //active neighbors plus self
    }
    return result;
  }

  private Fraction[] adjacentEdgeValsForActiveNeighbors(){
    Fraction[] result = new Fraction[edges.size()];
    for (int i = 0; i < result.length; i++){
      if (neighbors.get(i) == null)
        result[i] = Fraction.zero();
      else
        result[i] = Fraction.one();
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

}

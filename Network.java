package linalg;

import java.util.ArrayList;

public class Network{

  private ArrayList<Node> nodes;
  private boolean equallyWeightedEdges;

  public Network(boolean equal){
    equallyWeightedEdges = equal;
    nodes = new ArrayList<Node>();
  }

  public Network(ArrayList<Node> nodes, boolean equal){
    this(equal);
    nodes = nodes;
    updateNodes();
    for (int n = 0; n < nodes.size(); n++){
      nodes.get(n).setNetwork(this);
    }
  }

  public int size(){
    return nodes.size();
  }

  public Node getNode(int n){
    return nodes.get(n);
  }

  public void add(Node node){
    node.setNetwork(this);
    nodes.add(node);
    updateNodes();
  }

  private void updateNodes(){
    for (int n = 0; n < size(); n++){
      getNode(n).updateNeighbors();
    }
  }

  public boolean even(){
    return equallyWeightedEdges;
  }

  public String toString(){
    String result = "[";
    for (int i = 0; i < nodes.size(); i++){
      result += nodes.get(i).getName();
      if (i < nodes.size() - 1){
        result += ", ";
      }
    }
    result += "]";
    return result;
  }

}

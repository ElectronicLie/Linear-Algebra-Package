package linalg;

import java.util.ArrayList;

public class Network<N extends Node>{

  protected ArrayList<N> nodes;

  public Network(){
    nodes = new ArrayList<N>();
  }

  public Network(ArrayList<N> nodes){
    this.nodes = nodes;
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

  public void addNode(N node){
    node.setNetwork(this);
    nodes.add(node);
    updateNodes();
  }

  public void addNodes(ArrayList<N> al){
    for (int i = 0; i < al.size(); i++){
      addNode(al.get(i));
    }
  }

  protected void updateNode(int n){
    getNode(n).updateNeighbors();
  }

  protected void updateNodes(){
    for (int n = 0; n < size(); n++){
      updateNode(n);
    }
  }

  public void clear(){
    nodes = new ArrayList<N>();
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

  public String deepToString(){
    String result = "Network: " + toString() + "\n{\n\n";
    for (int i = 0; i < nodes.size(); i++){
      result += nodes.get(i).deepToStringWithoutNetwork() + "\n\n";
    }
    result += "}";
    return result;
  }

  public String sumToString(){
    String result = "Network: " + toString() + "\n{\n\n";
    for (int i = 0; i < nodes.size(); i++){
      result += nodes.get(i).sumMoreToString() + "\n\n";
    }
    result += "}";
    return result;
  }

  public boolean isEven(){
    return false;
  }

  public boolean isAdjacency(){
    return false;
  }

  public int indexOfNode(String nodeName){
    for (int n = 0; n < nodes.size(); n++){
      if (nodes.get(n).getName().equals(nodeName)){
        return n;
      }
    }
    return -1;
  }

}

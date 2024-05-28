package linalg;

import java.util.ArrayList;

public class Network{

  private ArrayList<Node> nodes;

  public Network(){
    nodes = new ArrayList<Node>();
  }

  public Network(ArrayList<Node> nodes){
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

  public void addNode(Node node){
    nodes.add(node);
    updateNodes();
    node.setNetwork(this);
  }

  public void updateNodes(){
    for (int n = 0; n < size(); n++){
      getNode(n).updateNeighbors(getNode(n).getEdgeVals());
    }
  }

}

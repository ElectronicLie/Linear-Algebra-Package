package linalg;

import java.util.ArrayList;

public class Edge{

  private Node giver;
  private Node taker;
  private double val;

  public Edge(Node n1, Node n2, double val){
    giver = n1;
    taker = n2;
    val = val;
  }

  public double getVal(){
    return val;
  }

}

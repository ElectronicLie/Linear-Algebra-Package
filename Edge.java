package linalg;

import java.util.ArrayList;

public class Edge{

  private Node giver;
  private Node taker;
  private double weight;

  public Edge(Node n1, Node n2, double weight){
    giver = n1;
    taker = n2;
    this.weight = weight;
  }

  public double getWeight(){
    return weight;
  }

  protected void setWeight(double newVal){
    weight = newVal;
  }

  public String deepToString(){
    return "initial Node: " + giver.getName() + "\n"
      + "terminal Node: " + taker.getName() + "\n"
      + "weight: " + weight;
  }

  public String toString(){
    return "(" + giver.getName() + ", " + taker.getName() + " : " + weight + ")";
  }

}

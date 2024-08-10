package linalg;

import fractions.Fraction;
import java.util.ArrayList;

public class Edge{

  private Node giver;
  private Node taker;
  private Fraction weight;

  public Edge(Node n1, Node n2, Fraction weight){
    giver = n1;
    taker = n2;
    this.weight = weight;
  }

  public Fraction getWeight(){
    return weight;
  }

  protected void setWeight(Fraction newVal){
    weight = newVal;
  }

  public String deepToString(){
    return "initial Node: " + giver.getName() + "\n"
      + "terminal Node: " + taker.getName() + "\n"
      + "weight: " + weight;
  }

  public String toString(){
    return "(" + giver.getName() + ", " + taker.getName() + " : " + weight.toString() + ")";
  }

}

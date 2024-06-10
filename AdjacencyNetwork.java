package linalg;

public class AdjacencyNetwork<N extends Node> extends Network<N>{

  public AdjacencyNetwork(){
    super();
  }

  public boolean isAdjacency(){
    return true;
  }

  public boolean isEven(){
    return false;
  }

}

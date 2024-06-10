package linalg;

public class EvenNetwork<N extends Node> extends Network<N>{

  public EvenNetwork(){
    super();
  }

  public boolean isEven(){
    return true;
  }

  public boolean isAdjacency(){
    return false;
  }

}

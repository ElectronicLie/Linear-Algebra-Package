package linalg;

public class Tester{

  public static void main(String[] args){
    Matrix d = Matrix.randomMatrix(3, 4);
    System.out.println("original:\n"+d);
    Matrix rr = d.rref();
    System.out.println("RREFed:\n"+rr);
  }

}

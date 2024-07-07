package linalg;
import java.util.ArrayList;

public class Tester{

  public static void main(String[] args){
    // Matrix d = Matrix.randomMatrix(2, 2);
    Matrix d = new Identity(2);
    System.out.println("\noriginal:\n"+d);
    // Matrix rr = d.rref();
    // System.out.println("RREFed:\n"+rr);
    Matrix dcopy = d.copy();
    dcopy.meanCenterColumns();
    System.out.println("mean centered:\n"+dcopy);
    System.out.println("covariance matrix:\n"+d.coVarianceMatrix());
    ArrayList<Eigenvector> pca = d.allPrincipalComponents();
    System.out.println(pca);
    System.out.println(Matrix.roughlyEquals(0.7, 0, 1));
  }

}

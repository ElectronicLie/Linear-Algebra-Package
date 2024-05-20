import java.util.Arrays;

public class Main{

  public static void main(String[] args){
    double[][] mk = new double[12][12];
    for (int i = 0; i < 12; i++){
      for (int j = 0; j < 12; j++){
        if (i == j)
          mk[i][j] = 0;
        else
          mk[i][j] = (double)(j+1) / (double)(i + j+2);
      }
    }

    // System.out.println(Arrays.deepToString(mk));

    StochasticMatrix mkm = new StochasticMatrix(mk);
    Matrix mks = mkm.pow(100);
    mks.scale(82*2);

    System.out.println(mks.plus(-1 *7.5));

    // Identity d2 = new Identity(2);
    // System.out.println(Matrix.mult(d2, d2));

    // Vector v = new Vector(new double[] {1,2});
    // System.out.println(v);
    // System.out.println(v.dot(v));

  }

}

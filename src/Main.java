package linalg;

import java.util.Arrays;

public class Main{

  public static void main(String[] args){
    Vector actual = new Vector(new double[] {15,12,10,8,7,6,5,4,3,2,1,0});

    double[][] mk = new double[12][12];
    for (int i = 0; i < 12; i++){
      for (int j = 0; j < 12; j++){
        if (i == j)
          mk[i][j] = 0;
        else
          mk[i][j] = (double)(j+1) / (double)(i + j+2);
      }
    }

    StochasticMatrix mkm = new StochasticMatrix(mk);
    Matrix mks = mkm.pow(255);
    Vector mkv = mks.col(0);
    mkv.plus(-1 * (mkv.get(mkv.dim()-1)));
    mkv.scale(1.0/mkv.sum());
    mkv.scale(actual.sum());

    System.out.println(mkv.toString(1));
  }

}

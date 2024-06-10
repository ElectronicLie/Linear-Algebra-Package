package linalg;

import java.util.Arrays;

public class Vector extends Matrix{

  protected double[] ary;

  public Vector(double[] ary){
    this.vals = new double[ary.length][1];
    for (int i = 0; i < ary.length; i++){
      vals[i][0] = ary[i];
    }
    this.ary = ary;
  }

  public Vector(int dim){
    vals = new double[dim][1];
  }

  public int dim(){
    return m();
  }

  public double get(int i){
    return this.vals[i][0];
  }

  public double dot(Vector v){
    if (v.dim() != this.dim()){
      throw new IllegalArgumentException("unequal vector dimensions");
    }
    double dot = 0;
    for (int i = 0; i < dim(); i++){
      dot += get(i) * v.get(i);
    }
    return dot;
  }

  public static double dot(Vector u, Vector v){
    return v.dot(u);
  }

  public boolean orthogonal(Vector v){
    return (dot(v) == 0);
  }

  public static boolean orthogonal(Vector u, Vector v){
    return (dot(u,v) == 0);
  }

  public double sum(){
    double sum = 0;
    for (int i = 0; i < dim(); i++){
      sum += get(i);
    }
    return sum;
  }

  public double mag(){
    double mag = 0;
    for (int i = 0; i < dim(); i++){
      mag += Math.pow(get(i), 2);
    }
    mag = Math.sqrt(mag);
    return mag;
  }

  public void stochasticize(){
    scale(1.0/sum());
  }

  public void normalize(){
    scale(1.0/mag());
  }

  protected Vector sorted(){
    double[] aryCopy = Arrays.copyOf(this.ary, this.ary.length);
    Arrays.sort(aryCopy);
    return new Vector(aryCopy);
  }

}

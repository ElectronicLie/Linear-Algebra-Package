package linalg;

import malo.*;
import java.util.Arrays;

public class Vector extends Matrix{

  protected double[] ary;

  public Vector(){
    ary = new double[0];
  }

  public Vector(double[] ary){
    this.vals = new double[ary.length][1];
    for (int i = 0; i < ary.length; i++){
      vals[i][0] = ary[i];
    }
    this.ary = ary;
  }

  public Vector(int[] ary){
    double[] doubleAry = new double[ary.length];
    for (int i = 0; i < ary.length; i++){
      doubleAry[i] = (double)(ary[i]);
    }
    this.vals = new double[ary.length][1];
    for (int i = 0; i < ary.length; i++){
      vals[i][0] = ary[i];
    }
    this.ary = doubleAry;
  }

  // public Vector(int[] intAry){
  //   double[] ary = new double[intAry.length];
  //   for (int i = 0; i < intAry.length; i++){
  //     ary[i] = (double)(intAry[i]);
  //   }
  //   this.vals = new double[ary.length][1];
  //   for (int i = 0; i < ary.length; i++){
  //     vals[i][0] = ary[i];
  //   }
  //   this.ary = ary;
  // }

  public Vector(int dim){
    vals = new double[dim][1];
    ary = new double[dim];
  }

  public static Vector zero(int dim){
    Vector result = new Vector(dim);
    result.vals = new double[dim][1];
    for (int i = 0; i < dim; i++){
      result.vals[i][0] = 0;
    }
    return result;
  }

  public int dim(){
    return m();
  }

  public double get(int i){
    return this.vals[i][0];
  }

  public double[] getVals(){
    return ary;
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

  public static Vector weightAverage(Vector[] vectors, double[] weights){
    if (vectors.length != weights.length){
      throw new IllegalArgumentException("different number of vectors and weights");
    }
    Vector average = zero(vectors[0].dim());
    for (int i = 0; i < vectors.length; i++){
      vectors[i].scale(weights[i]);
      average = average.add(vectors[i]);
    }
    average.scale(1.0 / (double)(vectors.length));
    return average;
  }

  public boolean orthogonal(Vector v){
    return (dot(v) == 0);
  }

  public static boolean orthogonal(Vector u, Vector v){
    return (dot(u,v) == 0);
  }

  public Vector add(Vector other){
    if (dim() != other.dim()){
      throw new IllegalArgumentException("cannot add two Vectors of different dimensions");
    }
    double[] ary = new double[dim()];
    for (int i = 0; i < ary.length; i++){
      ary[i] = get(i) + other.get(i);
    }
    return new Vector(ary);
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
    scale(1.0 / sum());
  }

  // public void normalize(){
  //   scale(1.0/mag());
  // }

  protected Vector sorted(){
    double[] aryCopy = Arrays.copyOf(this.ary, this.ary.length);
    double temp;
    for (int i = aryCopy.length-1; i > 0; i--){
      for (int j = 0; j < i; j++){
        if (aryCopy[j] > (aryCopy[j+1])){
          temp = aryCopy[j];
          aryCopy[j] = aryCopy[j+1];
          aryCopy[j+1] = temp;
        }
      }
    }
    return new Vector(aryCopy);
  }

}

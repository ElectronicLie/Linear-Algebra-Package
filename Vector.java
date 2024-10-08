package linalg;

import fractions.Fraction;
import malo.*;
import java.util.Arrays;

public class Vector extends Matrix{

  protected Fraction[] ary;

  public Vector(){
    ary = new Fraction[0];
  }

  public Vector(Fraction[] ary){
    this.vals = new Fraction[ary.length][1];
    for (int i = 0; i < ary.length; i++){
      vals[i][0] = ary[i];
    }
    this.ary = ary;
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
    vals = new Fraction[dim][1];
    ary = new Fraction[dim];
  }

  public static Vector zero(int dim){
    Vector result = new Vector(dim);
    result.vals = new Fraction[dim][1];
    for (int i = 0; i < dim; i++){
      result.vals[i][0] = Fraction.zero();
    }
    return result;
  }

  public int dim(){
    return m();
  }

  public Fraction get(int i){
    return this.vals[i][0];
  }

  public Fraction[] getVals(){
    return ary;
  }

  public Fraction dot(Vector v){
    if (v.dim() != this.dim()){
      throw new IllegalArgumentException("unequal vector dimensions");
    }
    Fraction dot = Fraction.zero();
    for (int i = 0; i < dim(); i++){
      dot = dot.add(get(i).mult(v.get(i)));
    }
    return dot;
  }

  public static Fraction dot(Vector u, Vector v){
    return v.dot(u);
  }

  public boolean orthogonal(Vector v){
    return (dot(v).isZero());
  }

  public static boolean orthogonal(Vector u, Vector v){
    return (dot(u,v).isZero());
  }

  public Vector add(Vector other){
    if (dim() != other.dim()){
      throw new IllegalArgumentException("cannot add two Vectors of different dimensions");
    }
    Fraction[] ary = new Fraction[dim()];
    for (int i = 0; i < ary.length; i++){
      ary[i] = this.get(i).add(other.get(i));
    }
    return new Vector(ary);
  }

  public Fraction sum(){
    Fraction sum = Fraction.zero();
    for (int i = 0; i < dim(); i++){
      sum = sum.add(get(i));
    }
    return sum;
  }

  // public double mag(){
  //   T mag = 0;
  //   for (int i = 0; i < dim(); i++){
  //     mag += Math.pow(get(i), 2);
  //   }
  //   mag = Math.sqrt(mag);
  //   return mag;
  // }

  public void stochasticize(){
    scale(new Fraction(1, sum()));
  }

  // public void normalize(){
  //   scale(1.0/mag());
  // }

  protected Vector sorted(){
    Fraction[] aryCopy = Arrays.copyOf(this.ary, this.ary.length);
    Fraction temp;
    for (int i = aryCopy.length-1; i > 0; i--){
      for (int j = 0; j < i; j++){
        if (aryCopy[j].moreThan(aryCopy[j+1])){
          temp = aryCopy[j];
          aryCopy[j] = aryCopy[j+1];
          aryCopy[j+1] = temp;
        }
      }
    }
    return new Vector(aryCopy);
  }

}

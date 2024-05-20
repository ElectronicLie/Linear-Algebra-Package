public class Vector extends Matrix{

  public Vector(double[] ary){
    this.vals = new double[ary.length][1];
    for (int i = 0; i < ary.length; i++){
      vals[i][0] = ary[i];
    }
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

}

public class Vector extends Matrix{

  public Vector(double[] ary){
    //super(new double[][] {{0}});
    this.vals = new double[1][ary.length];
    this.vals[0] = ary;
  }

  public int dim(){
    return m();
  }

  public double get(int i){
    return this.vals[0][i];
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

}

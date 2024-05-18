public class Matrix{

  protected double[][] vals;

  public Matrix(){}

  public Matrix(double[][] vals){
    vals = vals;
  }

  public int m(){
    return vals.length;
  }

  public int n(){
    return vals[0].length;
  }

  public Vector row(int i){

  }

  public Vector col(int i){}


}

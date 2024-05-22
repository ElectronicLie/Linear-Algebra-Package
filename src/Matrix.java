package linalg;

public class Matrix{

  protected double[][] vals;

  public Matrix(){}

  public Matrix(int rows, int cols){
    vals = new double[rows][cols];
  }

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
    return new Vector(vals[i]);
  }

  public Vector col(int i){
    double[] ary = new double[m()];
    for (int r = 0; r < m(); r++){
      ary[r] = vals[r][i];
    }
    return new Vector(ary);
  }

  public double get(int r, int c){
    return vals[r][c];
  }

  public Matrix mult(Matrix A){
    if (n() != A.m()){
      throw new IllegalArgumentException
        ("left matrix's number of columns is not equal to right matrix's number of rows");
    }
    Matrix product = new Matrix(m(), A.n());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < A.n(); c++){
        product.vals[r][c] = Vector.dot(row(r), A.col(c));
      }
    }
    return product;
  }

  public static Matrix mult(Matrix B, Matrix A){
    return B.mult(A);
  }

  public Matrix pow(int n){
    if (n == 0){
      return this;
    }
    return this.mult(this.pow(n-1));
  }

  public String toString(){
    return toString(3);
  }

  public String toString(int n){
    String result = "";
    double rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[n()];
    for (int c = 0; c < n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < m(); r++){
        rounded = Math.round(Math.pow(10,n) * get(r,c)) / Math.pow(10,n);
        cur = rounded + "";
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < m(); r++){
      result += "[ ";
      for (int c = 0; c < n(); c++){
        rounded = Math.round(Math.pow(10,n) * get(r,c)) / Math.pow(10,n);
        cur = rounded + "";
        curChars = cur.length();
        for (int i = 0; i < colMaxChars[c] - curChars; i++){
          cur += " ";
        }
        result += cur + " ";
      }
      result += "]\n";
    }
    return result;
  }

  private Matrix copy(){
    return new Matrix(this.vals);
  }

  public void scale(double k){
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        this.vals[r][c] *= k;
      }
    }
  }

  public void plus(double k){
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        vals[r][c] += k;
      }
    }
  }

}

package linalg;

import polynomials.*;
import java.util.Arrays;

public class PolynomialMatrix extends Matrix{

  protected RationalExpression[][] vals;

  public PolynomialMatrix(RationalExpression[][] pVals){
    this.vals = pVals;
  }

  public PolynomialMatrix(int rows, int cols){
    this.vals = new RationalExpression[rows][cols];
  }

  public int m(){
    return vals.length;
  }

  public int n(){
    if (vals.length == 0){
      return 0;
    }
    return vals[0].length;
  }

  public PolynomialMatrix upperTriangularViaREF(){
    if (isZero() || m() == 0){
      return this;
    }else{
      for (int c = 0; c < n(); c++){
        for (int r = c+1; r < m(); r++){
          combineRows(r, c, vals[r][c].scale(-1.0).divide(vals[c][c]));
        }
      }
      return this;
    }
  }

  public RationalExpression mainDiagonalProduct(String symbol){
    RationalExpression product = new RationalExpression(symbol);
    for (int d = 0; d < m(); d++){
      product = product.mult(this.vals[d][d]);
    }
    return product;
  }

  protected boolean isZero(){
    for (RationalExpression[] row : vals){
      for (RationalExpression val : row){
        if (! val.isZero())
          return false;
      }
    }
    return true;
  }

  private void combineRows(int addedTo, int adding, RationalExpression multiplier){
    if (addedTo == adding){
      throw new IllegalArgumentException("cannot combine a row with itself");
    }
    for (int c = 0; c < n(); c++){
      vals[addedTo][c] = vals[addedTo][c].add(vals[adding][c].mult(multiplier));
    }
  }

  private void combineRows(int added, int adding){
    combineRows(added, adding, new RationalExpression("L"));
  }

  private void scaleRow(int row, double scalar){
    for (int c = 0; c < n(); c++){
      vals[row][c] = vals[row][c].scale(scalar);
    }
  }

  public PolynomialMatrix copy(){
    PolynomialMatrix copy = new PolynomialMatrix(m(), n());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < n(); c++){
        copy.vals[r][c] = this.vals[r][c];
      }
    }
    return copy;
  }

  public String toString(){
    return toString(DEFAULT_ROUND,0);
  }

  public String toString(int n){
    return toString(0);
  }

  public String toString(int noTabs){
    String tabs = "";
    for (int i = 0; i < noTabs; i++){
      tabs += "\t";
    }
    if (m() == 0 && n() == 0){
      return "[]";
    }
    String result = "";
    RationalExpression rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[n()];
    for (int c = 0; c < n(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < m(); r++){
        rounded = vals[r][c];
        cur = rounded.toString();
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < m(); r++){
      result += tabs;
      result += "[ ";
      for (int c = 0; c < n(); c++){
        rounded = vals[r][c];
        cur = rounded.toString();
        curChars = cur.length();
        for (int i = 0; i < colMaxChars[c] - curChars; i++){
          cur += " ";
        }
        cur += " ";
        result += cur;
      }
      result += "]\n";
    }
    return result;
  }

  // public String factorsToString(){
  //   return factorsToString(-3);
  // }
  //
  // public String factorsToString(int n){
  //   return factorsToString(n, 0);
  // }
  //
  // public String factorsToString(int n, int noTabs){
  //   String tabs = "";
  //   for (int i = 0; i < noTabs; i++){
  //     tabs += "\t";
  //   }
  //   if (m() == 0 && n() == 0){
  //     return "[]";
  //   }
  //   String result = "";
  //   RationalExpression rounded;
  //   String cur;
  //   int curChars;
  //   int[] colMaxChars = new int[n()];
  //   for (int c = 0; c < n(); c++){
  //     colMaxChars[c] = 0;
  //     for (int r = 0; r < m(); r++){
  //       rounded = vals[r][c].round(n);
  //       cur = rounded.factorsToString();
  //       curChars = cur.length();
  //       if (curChars > colMaxChars[c]){
  //         colMaxChars[c] = curChars;
  //       }
  //     }
  //   }
  //   for (int r = 0; r < m(); r++){
  //     result += tabs;
  //     result += "[ ";
  //     for (int c = 0; c < n(); c++){
  //       rounded = vals[r][c].round();
  //       cur = rounded.factorsToString();
  //       // System.out.println(vals[r][c].factorsToString());
  //       curChars = cur.length();
  //       for (int i = 0; i < colMaxChars[c] - curChars; i++){
  //         cur += " ";
  //       }
  //       cur += " ";
  //       result += cur;
  //     }
  //     result += "]\n";
  //   }
  //   return result;
  // }

}

package linalg;

import polynomials.*;

public class Minor extends SquareMatrix{

  // // protected int[][] mainDiagonalCoords;
  // protected int noLambdas;
  //
  // private Minor(int dim, int noMD){
  //   vals = new double[dim][dim];
  //   noLambdas = noMD;
  // }
  //
  // public Minor(SquareMatrix matrix){
  //   this.vals = matrix.vals;
  //   this.noLambdas = matrix.dim();
  // }
  //
  // protected Minor nextMinor(int cExc){
  //   int lambdaStart = this.dim() - this.noLambdas;
  //   int noMD = this.noLambdas;
  //   if (lambdaStart == 0){
  //     noMD--;
  //   }
  //   if (cExc != 0 && cExc >= lambdaStart){
  //     noMD--;
  //   }
  //   Minor result = new Minor(dim()-1, noMD);
  //   for (int r = 1; r < dim(); r++){
  //     for (int c = 0; c < cExc; c++){
  //       result.vals[r-1][c] = vals[r][c];
  //     }
  //   }
  //   for (int r = 1; r < dim(); r++){
  //     for (int c = cExc+1; c < dim(); c++){
  //       result.vals[r-1][c-1] = vals[r][c];
  //     }
  //   }
  //   for (int r = cExc; r >= 2; r--){
  //     result.swapRows(cExc-1, cExc-2);
  //   }
  //   return result;
  // }
  //
  // public Polynomial characteristicPolynomial(){
  //   Polynomial result;
  //   String tabs="";
  //   for (int i = 0; i < 3-dim(); i++){
  //     tabs += "\t";
  //   }
  //   // System.out.println(this.toString(Matrix.DEFAULT_ROUND,3-dim()));
  //   // System.out.println(tabs+"number of lambdas: "+this.noLambdas);
  //   if (dim() == 1){
  //     if (this.noLambdas == 1){
  //       result = new Polynomial(new double[] {vals[0][0], -1}, "λ");
  //     }else{
  //       result = new Polynomial(new double[] {vals[0][0]}, "λ");
  //     }
  //   }else{
  //     result = new Polynomial("λ");
  //     for (int c = 0; c < dim(); c++){
  //       Minor next = nextMinor(c);
  //       // System.out.println(tabs+"column "+c+":");
  //       Polynomial part = next.characteristicPolynomial(); //recursion
  //       if (noLambdas == dim() && c == 0){
  //         part = part.mult(new Polynomial(new double[] {this.vals[0][c], -1}, "λ"));
  //       }else{
  //         part = part.scale(this.vals[0][c]);
  //       }
  //       if (c % 2 == 0 && c != 0){
  //         part = part.scale(-1);
  //       }
  //       if (c % 2 == 1){
  //         part = part.scale(-1);
  //       }
  //       result = result.add(part);
  //     }
  //   }
  //   // System.out.println(tabs+"minor's char. poly.:\n"+tabs+result+"\n");
  //   return result;
  // }
  //
  // public String toString(int n, int noTabs){
  //   String tabs = "";
  //   for (int i = 0; i < noTabs; i++){
  //     tabs += "\t";
  //   }
  //   if (m() == 0 && n() == 0){
  //     return "[]";
  //   }
  //   String result = "";
  //   double rounded;
  //   String cur;
  //   int curChars;
  //   int[] colMaxChars = new int[n()];
  //   for (int c = 0; c < n(); c++){
  //     colMaxChars[c] = 0;
  //     for (int r = 0; r < m(); r++){
  //       rounded = round(get(r,c));
  //       cur = rounded + "";
  //       curChars = cur.length();
  //       if (curChars > colMaxChars[c]){
  //         colMaxChars[c] = curChars;
  //       }
  //     }
  //   }
  //   for (int r = 0; r < m(); r++){
  //     result += tabs+"[ ";
  //     for (int c = 0; c < n(); c++){
  //       rounded = round(get(r,c));
  //       cur = rounded + "";
  //       if (r == c && r >= dim()-noLambdas){
  //         cur = "<"+cur+">";
  //       }
  //       curChars = cur.length();
  //       for (int i = 0; i < colMaxChars[c] - curChars; i++){
  //         cur += " ";
  //       }
  //       result += cur + " ";
  //     }
  //     result += "]\n";
  //   }
  //   return result;
  // }

}

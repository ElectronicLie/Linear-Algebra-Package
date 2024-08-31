package linalg;

import polynomials.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import malo.Malo;

public class ComplexFractionMatrix{

  // private ComplexFraction[][] vals;
  //
  // public ComplexFractionMatrix(ComplexFraction[][] ary){
  //   vals = ary;
  // }
  //
  // public ComplexFractionMatrix(int m, int n){
  //   vals = new ComplexFraction[m][n];
  // }
  //
  // public ComplexFractionMatrix(String[][] strs){
  //   vals = new ComplexFraction[strs.length][strs[0].length];
  //   for (int r = 0; r < m(); r++){
  //     for (int c = 0; c < n(); c++){
  //       vals[r][c] = new ComplexFraction(strs[r][c]);
  //     }
  //   }
  // }
  //
  // public ComplexFraction get(int r, int c){
  //   return vals[r][c];
  // }
  //
  // public int m(){
  //   return vals.length;
  // }
  //
  // public int n(){
  //   if (m() == 0){
  //     return 0;
  //   }
  //   return vals[0].length;
  // }
  //
  // // public ComplexFractionMatrix minor(int cExc){
  // //   ComplexFraction[][] minorVals = new ComplexFraction[vals.length-1][vals.length-1];
  // //   for (int r = 1; r < dim(); r++){
  // //     for (int c = 0; c < cExc; c++){
  // //       minorVals[r-1][c] = vals[r][c];
  // //     }
  // //   }
  // //   for (int r = 1; r < dim(); r++){
  // //     for (int c = cExc+1; c < dim(); c++){
  // //       minorVals[r-1][c-1] = vals[r][c];
  // //     }
  // //   }
  // //   return new ComplexFractionMatrix(minorVals);
  // // }
  //
  // // public VariableExpression det() throws FileNotFoundException{
  // //   File file = new File("determinant-formulas.txt");
  // //   Scanner scanner = new Scanner(file);
  // //   String formula = "1";
  // //   for (int i = 1; i <= dim(); i++){
  // //     formula = scanner.nextLine();
  // //     // System.out.println("formula for this dimension: "+formula);
  // //   }
  // //   // System.out.println("det formula: "+formula);
  // //   VariableExpression f = VariableExpression.parseVariableExpression(formula);
  // //   String[] entryOrder = entryOrder();
  // //   return f.plugIn(standardEntryOrder(dim()), entryOrder);
  // // }
  //
  //
  // public VariableMatrix pseudoMinor(int cExc){
  //   VariableMatrix result = new VariableMatrix(m(), n()-1);
  //   for (int c = 0; c < cExc; c++){
  //     for (int r = 0; r < m(); r++){
  //       result.vals[r][c] = this.vals[r][c];
  //     }
  //   }
  //   for (int c = cExc + 1; c < n(); c++){
  //     for (int r = 0; r< m(); r++){
  //       result.vals[r][c-1] = this.vals[r][c];
  //     }
  //   }
  //   return result;
  // }
  //
  // public ComplexFractionMatrix rref(){
  //   ComplexFractionMatrix copy = this.copy();
  //   for (int c = 0; c < Malo.min(n(), m()); c++){
  //     copy.scaleRow(c, copy.vals[c][c].reciprocal());
  //     for (int r = 0; r < m(); r++){
  //       if (r != c){
  //         copy.combineRows(r, c, copy.vals[r][c].scale(-1));
  //       }
  //     }
  //   }
  //   return copy;
  // }
  //
  // private void combineRows(int addedTo, int adding, ComplexFraction scalar){
  //   if (addedTo == adding){
  //     throw new IllegalArgumentException("cannot combine a row with itself");
  //   }
  //   for (int c = 0; c < n(); c++){
  //     vals[addedTo][c].plus(vals[adding][c].mult(scalar));
  //   }
  // }
  //
  // private void combineRows(int added, int adding){
  //   combineRows(added, adding, ComplexFraction.one());
  // }
  //
  // private void scaleRow(int row, ComplexFraction scalar){
  //   for (int c = 0; c < n(); c++){
  //     vals[row][c] = vals[row][c].mult(scalar);
  //   }
  // }
  //
  // public void swapRows(int rowI1, int rowI2){
  //   if (rowI1 != rowI2){
  //     ComplexFraction[] temp = vals[rowI1];
  //     vals[rowI1] = vals[rowI2];
  //     vals[rowI2] = temp;
  //   }
  // }
  //
  // public ComplexFractionMatrix copy(){
  //   ComplexFractionMatrix copy = new ComplexFractionMatrix(m(), n());
  //   for (int r = 0; r < m(); r++){
  //     for (int c = 0; c < n(); c++){
  //       copy.vals[r][c] = this.vals[r][c].copy();
  //     }
  //   }
  //   return copy;
  // }
  //
  // private String[] entryOrder(){
  //   String[] result = new String[m()*n()];
  //   for (int r = 0; r < m(); r++){
  //     for (int c = 0; c < n(); c++){
  //       result[r*n()+c] = vals[r][c].getSingleVariable();
  //     }
  //   }
  //   return result;
  // }
  //
  // public static String[] standardEntryOrder(int m, int n){
  //   String[] result = new String[m*n];
  //   int row;
  //   int col;
  //   for (int i = 0; i < result.length; i++){
  //     row = i / n;
  //     col = i % n;
  //     result[i] = "m_("+row+","+col+")";
  //   }
  //   return result;
  // }
  //
  // private static String[][] standardEntryOrder2d(int m, int n){
  //   String[][] result = new String[m][n];
  //   for (int r = 0; r < m; r++){
  //     for (int c = 0; c < n; c++){
  //       result[r][c] = "m_("+r+","+c+")";
  //     }
  //   }
  //   return result;
  // }
  //
  // public static ComplexFractionMatrix matrixWithStandardEntries(int m, int n){
  //   return new ComplexFractionMatrix(standardEntryOrder2d(m, n));
  // }
  //
  // public String toString(){
  //   return toString(0);
  // }
  //
  // public String toString(int noTabs){
  //   String tabs = "";
  //   for (int i = 0; i < noTabs; i++){
  //     tabs += "\t";
  //   }
  //   if (m() == 0 || n() == 0){
  //     return "[]";
  //   }
  //   String result = "";
  //   // Fraction rounded;
  //   String cur;
  //   int curChars;
  //   int[] colMaxChars = new int[n()];
  //   for (int c = 0; c < n(); c++){
  //     colMaxChars[c] = 0;
  //     for (int r = 0; r < m(); r++){
  //       cur = vals[r][c].toString();
  //       // cur = rounded + "";
  //       curChars = cur.length();
  //       if (curChars > colMaxChars[c]){
  //         colMaxChars[c] = curChars;
  //       }
  //     }
  //   }
  //   for (int r = 0; r < m(); r++){
  //     result += tabs+"[ ";
  //     for (int c = 0; c < n(); c++){
  //       cur = vals[r][c].toString();
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
  //
  // public String parseableToString(){
  //   String result = "";
  //   for (int c = 0; c < n(); c++){
  //     result += "";
  //   }
  //   return result;
  // }

}

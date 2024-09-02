package linalg;

import polynomials.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class VariableMatrix{

  private Entry[][] vals;

  public VariableMatrix(Entry[][] ary){
    vals = ary;
  }

  private VariableMatrix(int dim){
    vals = new Entry[dim][dim];
    for (int r = 0; r < dim; r++){
      for (int c = 0; c < dim; c++){
        vals[r][c] = new Entry(1, r, c);
      }
    }
  }

  public int dim(){
    return vals.length;
  }

  public Entry get(int r, int c){
    return vals[r][c];
  }



  public VariableMatrix minor(int cExc){
    Entry[][] minorVals = new Entry[vals.length-1][vals.length-1];
    for (int r = 1; r < dim(); r++){
      for (int c = 0; c < cExc; c++){
        minorVals[r-1][c] = vals[r][c];
      }
    }
    for (int r = 1; r < dim(); r++){
      for (int c = cExc+1; c < dim(); c++){
        minorVals[r-1][c-1] = vals[r][c];
      }
    }
    return new VariableMatrix(minorVals);
  }

  public EntryExpression det() throws FileNotFoundException{
    File file = new File("determinant-formulas.txt");
    Scanner scanner = new Scanner(file);
    String formula = "1";
    for (int i = 1; i <= dim(); i++){
      formula = scanner.nextLine();
      // System.out.println("formula for this dimension: "+formula);
    }
    // System.out.println("det formula: "+formula);
    EntryExpression f = EntryExpression.parseEntryExpression(formula);
    // String[] entryOrder = entryOrder();
    return f.plugIn(this);
  }

  // private String[] entryOrder(){
  //   String[] result = new String[dim()*dim()];
  //   for (int r = 0; r < dim(); r++){
  //     for (int c = 0; c < dim(); c++){
  //       result[r*dim()+c] = vals[r][c].getSingleVariable();
  //     }
  //   }
  //   return result;
  // }

  // public static String[] standardEntryOrder(int dim){
  //   String[] result = new String[dim*dim];
  //   int row;
  //   int col;
  //   for (int i = 0; i < result.length; i++){
  //     row = i / dim;
  //     col = i % dim;
  //     result[i] = "m_("+row+","+col+")";
  //   }
  //   return result;
  // }

  // private static String[][] standardEntryOrder2d(int dim){
  //   String[][] result = new String[dim][dim];
  //   for (int r = 0; r < dim; r++){
  //     for (int c = 0; c < dim; c++){
  //       result[r][c] = "m_("+r+","+c+")";
  //     }
  //   }
  //   return result;
  // }

  public static VariableMatrix matrixWithStandardEntries(int dim){
    VariableMatrix result = new VariableMatrix(dim);
    for (int r = 0; r < dim; r++){
      for (int c = 0; c < dim; c++){
        result.vals[r][c] = new Entry(1, r, c);
      }
    }
    return result;
  }

  public String toString(){
    return toString(0);
  }

  public String toString(int noTabs){
    String tabs = "";
    for (int i = 0; i < noTabs; i++){
      tabs += "\t";
    }
    if (dim() == 0){
      return "[]";
    }
    String result = "";
    // Fraction rounded;
    String cur;
    int curChars;
    int[] colMaxChars = new int[dim()];
    for (int c = 0; c < dim(); c++){
      colMaxChars[c] = 0;
      for (int r = 0; r < dim(); r++){
        cur = vals[r][c].toString();
        // cur = rounded + "";
        curChars = cur.length();
        if (curChars > colMaxChars[c]){
          colMaxChars[c] = curChars;
        }
      }
    }
    for (int r = 0; r < dim(); r++){
      result += tabs+"[ ";
      for (int c = 0; c < dim(); c++){
        cur = vals[r][c].toString();
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

}

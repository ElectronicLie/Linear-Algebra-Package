package linalg;

import polynomials.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class DeterminantFormulaGenerator{

  public static void main(String[] args) throws FileNotFoundException{
    if (args.length != 1){
      throw new IllegalArgumentException("args.length is not 1");
    }
    VariableExpression result = generateFormula(Integer.parseInt(args[0]));
    System.out.println("generated formula for dimension " + args[0] + ":");
    System.out.println(result.toString());
    System.out.println(result.parseableToString());
    // FileWriter fileWriter = new FileWriter("DeterminantFormulas.txt");
  }

  private static VariableExpression generateFormula(int dim) throws FileNotFoundException{
    if (dim == 1){
      return new VariableExpression("m_(0,0)");
    }
    File file = new File("DeterminantFormulas.txt");
    Scanner formulasScanner;
    try{
      formulasScanner = new Scanner(file);
    }catch(FileNotFoundException e){
      throw new RuntimeException("file not found");
    }
    int noFormulas = 0;
    String line;
    while (formulasScanner.hasNextLine()){
      line = formulasScanner.nextLine();
      noFormulas++;
    }
    if (noFormulas < dim - 1){
      throw new IllegalArgumentException
      ("cannot generate formula for determinant of matrix of dimension "+dim+" if the one for"
      +"dimension "+(dim-1)+" has not already been generated");
    }
    formulasScanner = new Scanner("DeterminantFormulas.txt");
    String minorFormula;
    for (int i = 1; i < dim; i++){
      minorFormula = formulasScanner.nextLine();
    }
    VariableMatrix matrix = VariableMatrix.matrixWithStandardEntries(dim);
    System.out.println(matrix);
    VariableMatrix minor;
    VariableExpression determinant = VariableExpression.zero();
    VariableExpression minorDet;
    int k;
    for (int c = 0; c < dim; c++){
      minor = matrix.minor(c);
      System.out.println("minor: \n"+minor);
      if (c % 2 == 1){
        k = -1;
      }else{
        k = 1;
      }
      minorDet = minor.det().scale(new AlgebraicTerm(k)).scale(new AlgebraicTerm(matrix.get(0,c).toString()));
      System.out.println("minor det: "+minor.det());
      System.out.println("term from minor:\n"+minorDet);
      determinant.plus(minorDet);
    }
    return determinant;
  }

}

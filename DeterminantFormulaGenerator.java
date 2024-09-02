package linalg;

import polynomials.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeterminantFormulaGenerator{

  public static void main(String[] args) throws FileNotFoundException, IOException{
    if (! (args.length == 1 || args.length == 2)){
      throw new IllegalArgumentException("args.length is not 1 or 2");
    }
    int dim = Integer.parseInt(args[0]);
    EntryExpression result = generateFormula(dim);
    // System.out.println("generated formula for dimension " + args[0] + ":");
    System.out.println(result.toString());
    // System.out.println(result.parseableToString());
    if (args.length == 2 && args[1].equals("write")){
      File file = new File("determinant-formulas.txt");
      Scanner scanner = new Scanner(file);
      String line;
      String text = "";
      // System.out.println("dimension: "+ dim);
      scanner.reset();
      for (int l = 1; l < dim; l++){
        line = scanner.nextLine();
        text += line + "\n";
      }
      text += result.toString();
      if (scanner.hasNextLine()){
        line = scanner.nextLine();
      }
      while (scanner.hasNextLine()){
        line = scanner.nextLine();
        text += line + "\n";
      }
      Path fileName = Path.of("determinant-formulas.txt");
      Files.writeString(fileName, text);
      // Files.writeString(fileName, "111");
    }else if (args.length == 2){
      throw new IllegalArgumentException("second argument is "+args[1]+", not \"write\"");
    }
  }

  private static EntryExpression generateFormula(int dim) throws FileNotFoundException, IOException{
    // System.out.println("FILE CONTENTS: ");
    // try (BufferedReader br = new BufferedReader(new FileReader("determinant-formulas.txt"))) {
    //    String bline;
    //    while ((bline = br.readLine()) != null) {
    //        System.out.println(bline);
    //    }
    // }
    if (dim == 1){
      return new EntryExpression(1, 0, 0);
    }
    File file = new File("determinant-formulas.txt");
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
      ("cannot generate formula for determinant of matrix of dimension "+dim+" if the one for "
      +"dimension "+(dim-1)+" has not already been generated");
    }
    formulasScanner = new Scanner(file);

    // System.out.println("FILE CONTENTS: ");
    // try (BufferedReader br = new BufferedReader(new FileReader("determinant-formulas.txt"))) {
    //    String bline;
    //    while ((bline = br.readLine()) != null) {
    //        System.out.println(bline);
    //    }
    // }
    // System.out.println();

    String minorFormula;
    for (int i = 1; i < dim; i++){
      minorFormula = formulasScanner.nextLine();
      // System.out.println("cur line: "+minorFormula);
    }
    VariableMatrix matrix = VariableMatrix.matrixWithStandardEntries(dim);
    // System.out.println(matrix);
    VariableMatrix minor;
    EntryExpression determinant = EntryExpression.zero();
    EntryExpression minorDet;
    int k;
    for (int c = 0; c < dim; c++){
      minor = matrix.minor(c);
      // System.out.println("minor: \n"+minor);
      if (c % 2 == 1){
        k = -1;
      }else{
        k = 1;
      }
      minorDet = minor.det();
      // System.out.println("minor det.: " + minorDet);
      minorDet.scale(k);
      minorDet.scale(matrix.get(0,c ));
      // System.out.println("scaled minor det.: "+minorDet);
      determinant.add(minorDet);
    }
    // formulasScanner.reset();
    // System.out.println("FILE CONTENTS: ");
    // try (BufferedReader br = new BufferedReader(new FileReader("determinant-formulas.txt"))) {
    //    String bline;
    //    while ((bline = br.readLine()) != null) {
    //        System.out.println(bline);
    //    }
    // }
    return determinant;
  }

}

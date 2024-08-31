package linalg;

import polynomials.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class RREFFormulaGenerator{

  // public static void main(String[] args){
  //
  // }
  //
  // public static ComplexFraction generateFormula(int dim) throws FileNotFoundException{
  //   // 2x1 = [1 x] (dim = 1)
  //
  //   if (dim == 1){
  //     return new ComplexFraction("m_(0,0)");
  //   }
  //   File file = new File("rref-formulas.txt");
  //   Scanner formulasScanner;
  //   try{
  //     formulasScanner = new Scanner(file);
  //   }catch(FileNotFoundException e){
  //     throw new RuntimeException("file not found");
  //   }
  //   int noFormulas = 0;
  //   String line;
  //   while (formulasScanner.hasNextLine()){
  //     line = formulasScanner.nextLine();
  //     noFormulas++;
  //   }
  //   if (noFormulas < dim - 1){
  //     throw new IllegalArgumentException
  //     ("cannot generate formula for rref of matrix of dimension "+dim+" if the one for "
  //     +"dimension "+(dim-1)+" has not already been generated");
  //   }
  //   formulasScanner = new Scanner(file);
  //
  //   // formulasScanner.reset();
  //   // System.out.println("FILE CONTENTS: ");
  //   // try (BufferedReader br = new BufferedReader(new FileReader("determinant-formulas.txt"))) {
  //   //    String bline;
  //   //    while ((bline = br.readLine()) != null) {
  //   //        System.out.println(bline);
  //   //    }
  //   // }
  //
  //   ComplexFractionMatrix system = ComplexFractionMatrix.matrixWithStandardEntries(dim, dim+1);
  //
  //
  //   return new ComplexFraction("uh oh");
  // }

}

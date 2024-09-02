package linalg;

import java.util.Scanner;
import java.util.Arrays;
import polynomials.Polynomial;

public class Entry{

  private int sign;
  private int[] entries;

  public Entry(){
    sign = 1;
    entries = new int[] {};
  }

  public Entry(int s, int r, int c){
    entries = new int[] {r,c};
    sign = s;
  }

  public Entry(int s){
    sign = s;
    entries = new int[] {};
  }

  public int getRow(int p){
    return entries[p*2];
  }

  public int getCol(int p){
    return entries[p*2+1];
  }

  private int noCoords(){
    return entries.length;
  }

  public void mult(Entry other){
    int[] newEntries = new int[this.noCoords()+other.noCoords()];
    for (int i = 0; i < entries.length; i++){
      newEntries[i] = entries[i];
    }
    for (int i = 0; i < other.entries.length; i++){
      newEntries[entries.length+i] = other.entries[i];
    }
    sign *= other.sign;
    entries = newEntries;
  }

  void scale(int s){
    if (! (s == 1 || s== -1)){
      throw new IllegalArgumentException();
    }
    this.sign *= s;
  }

  public String toString(){
    String result = "";
    if (sign == 1){
      result += "+ ";
    }else{
      result += "- ";
    }
    for (int i = 0; i < entries.length; i++){
      result += entries[i] + " ";
    }
    return result;
  }

  // public static double plugIn(Matrix matrix, String exp){
  //   Scanner scanner = new Scanner(exp);
  //   double result = scanner.nextInt();
  //   while(scanner.hasNextInt()){
  //     result *= matrix.get(scanner.nextInt(), scanner.nextInt());
  //   }
  //   return result;
  // }

  public double plugIn(Matrix matrix){
    double result = this.sign;
    int r;
    int c;
    for (int p = 0; p < entries.length/2; p++){
      r = getRow(p);
      c = getCol(p);
      result *= (matrix.get(r,c));
    }
    return result;
  }

  public Entry plugIn(VariableMatrix matrix){
    Entry result = new Entry(this.sign);
    int r;
    int c;
    for (int p = 0; p < entries.length/2; p++){
      r = getRow(p);
      c = getCol(p);
      result.mult(matrix.get(r,c));
    }
    return result;
  }

  public Polynomial plugIn(Polynomial[][] ary){
    Polynomial result = new Polynomial(sign, ary[0][0].getVar());
    int r, c;
    for (int p = 0; p < entries.length/2; p++){
      r = getRow(p);
      c = getCol(p);
      result = result.mult(ary[r][c]);
    }
    return result;
  }

  public Entry copy(){
    Entry copy = new Entry();
    copy.sign = this.sign;
    copy.entries = Arrays.copyOf(this.entries, this.entries.length);
    return copy;
  }

  public static Entry parseEntry(String str){
    Scanner scanner = new Scanner(str);
    Entry result = new Entry();
    String nextStr;
    nextStr = scanner.next();
    int noCoords = 0;
    int next;
    while (scanner.hasNextInt()){
      next = scanner.nextInt();
      noCoords++;
    }
    if (noCoords % 2 != 0){
      throw new IllegalArgumentException("string has an odd number of coordinates");
    }
    result.entries = new int[noCoords];
    scanner = new Scanner(str);
    nextStr = scanner.next();
    if (nextStr.equals("+")){
      result.sign = 1;
    }else{
      result.sign = -1;
    }
    for (int i = 0; i < noCoords; i++){
      result.entries[i] = scanner.nextInt();
    }
    return result;
  }

}

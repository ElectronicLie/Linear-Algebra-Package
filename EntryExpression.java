package linalg;

import java.util.ArrayList;
import java.util.Scanner;
import polynomials.Polynomial;

public class EntryExpression{

  private ArrayList<Entry> terms;

  private EntryExpression(){
    terms = new ArrayList<Entry>();
  }

  public EntryExpression(Entry entry){
    this();
    terms.add(entry);
  }

  public EntryExpression(int s){
    this();
    terms.add(new Entry(s));
  }

  public EntryExpression(int s, int r, int c){
    this();
    terms.add(new Entry(s, r, c));
  }

  public void add(EntryExpression other){
    for (int i = 0; i < other.terms.size(); i++){
      terms.add(other.terms.get(i));
    }
  }

  public void add(Entry entry){
    this.terms.add(entry);
  }

  public void mult(Entry k){
    for (int i = 0; i < terms.size(); i++){
      terms.get(i).mult(k);
    }
  }

  public void scale(int s){
    if (! (s == 1 || s == -1)){
      throw new IllegalArgumentException();
    }
    for (int i = 0; i< terms.size(); i++){
      terms.get(i).scale(s);
    }
  }

  public void scale(Entry s){
    for (int i = 0; i < terms.size(); i++){
      terms.get(i).mult(s);
    }
  }

  public String toString(){
    String result = "";
    for (int i = 0; i < terms.size(); i++){
      result += terms.get(i).toString() + " , ";
    }
    return result;
  }

  // public static double plugIn(Matrix matrix, String exp){
  //   // Scanner scanner = new Scanner(exp);
  //   double result = 0;
  //   int divide = exp.indexOf(",");
  //   String nextTerm;
  //   while (divide != -1){
  //     nextTerm = exp.substring(0, divide);
  //     result += Entry.plugIn(matrix, nextTerm);
  //     exp = exp.substring(divide, exp.length());
  //     divide = exp.indexOf(",");
  //   }
  //   return result;
  // }

  public double plugIn(Matrix matrix){
    double result = 0;
    for (int i = 0; i < terms.size(); i++){
      result += (terms.get(i).plugIn(matrix));
    }
    return result;
  }

  public EntryExpression plugIn(VariableMatrix matrix){
    EntryExpression result = new EntryExpression();
    for (int i = 0; i < terms.size(); i++){
      result.add(terms.get(i).plugIn(matrix));
    }
    return result;
  }

  public Polynomial plugIn(Polynomial[][] ary){
    Polynomial result = Polynomial.zero(ary[0][0].getVar());
    for (int i = 0; i < terms.size(); i++){
      result = result.add(terms.get(i).plugIn(ary));
    }
    return result;
  }

  public static EntryExpression parseEntryExpression(String exp){
    EntryExpression result = new EntryExpression();
    int divide = exp.indexOf(",");
    String nextTerm;
    while (divide != -1){
      nextTerm = exp.substring(0, divide);
      // System.out.println("next term: "+nextTerm);
      result.add(Entry.parseEntry(nextTerm));
      exp = exp.substring(divide+1, exp.length());
      divide = exp.indexOf(",");
    }
    return result;
  }

  public static EntryExpression one(){
    return new EntryExpression(1);
  }

  public static EntryExpression zero(){
    return new EntryExpression();
  }

}

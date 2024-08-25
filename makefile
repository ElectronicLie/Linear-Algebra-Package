run: compile
	java Tester.java

compile: *.java ../polynomials/*.java
	javac *.java
	javac ../polynomials/*.java

generateDet1: compile
	java DeterminantFormulaGenerator.java 1 write

generateDet2: compile
	java DeterminantFormulaGenerator.java 2 write

run: compile
	java Tester.java

compile: *.java ../polynomials/*.java
	javac *.java
	javac ../polynomials/*.java

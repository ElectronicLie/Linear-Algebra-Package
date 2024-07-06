run: Tester.class
	java Tester.java

compile: Tester.class

Tester.class: Tester.java Vector.class Matrix.class SquareMatrix.class Identity.class StochasticMatrix.class
	javac Tester.java

Vector.class: Vector.java
	javac Vector.java

Matrix.class: Matrix.java
	javac Matrix.java

SquareMatrix.class: SquareMatrix.java
	javac SquareMatrix.java

Identity.class:	Identity.java
	javac Identity.java

StochasticMatrix.class: StochasticMatrix.java
	javac StochasticMatrix.java

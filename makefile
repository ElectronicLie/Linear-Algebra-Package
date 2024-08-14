run: compile
	java Tester.java

compile: *.java ../malo/*.java
	javac ../malo/*.java
	javac *.java

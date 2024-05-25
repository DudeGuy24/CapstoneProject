#!/bin/bash

antlr4 LexBasic.g4 
antlr4 antlrLexer.g4 
antlr4 antlrParser.g4 
javac -cp .:/usr/local/lib/antlr-4.13.1-complete.jar Main.java
java Main gram.txt
mv *.class class



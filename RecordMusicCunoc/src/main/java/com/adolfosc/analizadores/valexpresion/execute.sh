#! /bin/bash
echo "STARTING JFLEX COMPILING"
java -jar jflex-full-1.8.2.jar ValExprLexer.jflex
echo "---------"
echo "STARTING CUP COMPILING"

java -jar java-cup-11b.jar -parser ValExprParser -symbols Sym ValExprParser.cup

#! /bin/bash
echo "STARTING JFLEX COMPILING"
java -jar jflex-full-1.8.2.jar ConexionLexer.jflex
echo "---------"
echo "STARTING CUP COMPILING"

java -jar java-cup-11b.jar -parser ConexionParser -symbols Sym ConexionParser.cup

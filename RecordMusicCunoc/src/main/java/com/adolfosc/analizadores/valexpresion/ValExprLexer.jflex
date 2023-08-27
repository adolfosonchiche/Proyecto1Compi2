package com.adolfosc.analizadores.valexpresion;

import java_cup.runtime.*; 
import com.adolfosc.analizadores.valexpresion.Sym.*;
import java.util.List;
import java.util.ArrayList;
import com.adolfosc.analizadores.ErrorSintaxis;

%%

//Configuracion JFLEX
%public
%class ValExprLexer
//%standalone
%line
%column
%cup

//Expresiones Regulares

/*--------------Literales---------------*/
DIGITO              = [0-9]
CARACTER            = \'#?[^\']\'
CADENA              = \"[^\"\\]*\"
NUMERO              = {DIGITO}+([.]{DIGITO}{1,6})?


/*----------ESPACIOs En blanco----------*/
ESPACIO             = " "
SALTOLINEA          = \n|\r|\r\n
ESPACIOBLANCO       = ({ESPACIO}|{SALTOLINEA}| [\t\n])+


/*--------- Codigo Incrustado ---------*/
%{
    private List<ErrorSintaxis> erroresCom;

    private void error(String lexeme) {
        erroresCom.add(new ErrorSintaxis("Lexico","Simbolo no existe en el lenguaje",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }    

    private void errorPrueba(String lexeme, String tipo) {
        erroresCom.add(new ErrorSintaxis("PRUEBA",tipo,String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    public List<ErrorSintaxis> getErroresCom() {
        return erroresCom;
    }

%}

%init{
    erroresCom = new ArrayList<>();
%init}

%%


//Reglas Lexicas
<YYINITIAL> {        
    {ESPACIOBLANCO}         {/*vacio*/}
    
    /*------------ Operadores ------------*/
    "++"                    {return new Symbol(Sym.OPINCR,yyline+1,yycolumn+1, yytext());}
    "--"                    {return new Symbol(Sym.OPDECR,yyline+1,yycolumn+1, yytext());}
    "+"                     {return new Symbol(Sym.SUMA,yyline+1,yycolumn+1, yytext());}
    "-"                     {return new Symbol(Sym.RESTA,yyline+1,yycolumn+1, yytext());}
    "*"                     {return new Symbol(Sym.MULT,yyline+1,yycolumn+1, yytext());}
    "/"                     {return new Symbol(Sym.DIVI,yyline+1,yycolumn+1, yytext());}
    "%"                     {return new Symbol(Sym.OPMOD,yyline+1,yycolumn+1, yytext());}
    "^"                     {return new Symbol(Sym.OPELV,yyline+1,yycolumn+1, yytext());}
    "=="                    {return new Symbol(Sym.DOBIGUAL,yyline+1,yycolumn+1, yytext());}
    "!="                    {return new Symbol(Sym.DIFERENC,yyline+1,yycolumn+1, yytext());}
    ">="                    {return new Symbol(Sym.MAYIG,yyline+1,yycolumn+1, yytext());}
    "<="                    {return new Symbol(Sym.MENIG,yyline+1,yycolumn+1, yytext());}
    "!!"                    {return new Symbol(Sym.OPNULO,yyline+1,yycolumn+1, yytext());}
    ">"                     {return new Symbol(Sym.MAY,yyline+1,yycolumn+1, yytext());}
    "<"                     {return new Symbol(Sym.MEN,yyline+1,yycolumn+1, yytext());}
    "!&&"                   {return new Symbol(Sym.NAND,yyline+1,yycolumn+1, yytext());}
    "!||"                   {return new Symbol(Sym.NOR,yyline+1,yycolumn+1, yytext());}
    "&|"                    {return new Symbol(Sym.XOR,yyline+1,yycolumn+1, yytext());}
    "&&"                    {return new Symbol(Sym.AND,yyline+1,yycolumn+1, yytext());}
    "||"                    {return new Symbol(Sym.OR,yyline+1,yycolumn+1, yytext());}
    "!"                     {return new Symbol(Sym.OPNOT,yyline+1,yycolumn+1, yytext());}      
    "("                     {return new Symbol(Sym.PARI,yyline+1,yycolumn+1, yytext());}      
    ")"                     {return new Symbol(Sym.PARD,yyline+1,yycolumn+1, yytext());}      
    "["                     {return new Symbol(Sym.BRACKI,yyline+1,yycolumn+1, yytext());}
    "]"                     {return new Symbol(Sym.BRACKD,yyline+1,yycolumn+1, yytext());}      
    "{"                     {return new Symbol(Sym.CURLBRACKI,yyline+1,yycolumn+1, yytext());}
    "}"                     {return new Symbol(Sym.CURLBRACKD,yyline+1,yycolumn+1, yytext());}      
    ":"                     {return new Symbol(Sym.DOSPUNT,yyline+1,yycolumn+1, yytext());}      
    ";"                     {return new Symbol(Sym.PUNTCOMA,yyline+1,yycolumn+1, yytext());}      
    ","                     {return new Symbol(Sym.COMA,yyline+1,yycolumn+1, yytext());}      
    "="                     {return new Symbol(Sym.IGUAL,yyline+1,yycolumn+1, yytext());}      

    /*-------- Palabras Reservadas --------*/
    [tT]"rue"               {return new Symbol(Sym.VERDADERO,yyline+1,yycolumn+1, yytext());}
    [vV]"erdadero"          {return new Symbol(Sym.VERDADERO,yyline+1,yycolumn+1, yytext());}
    [fF]"alse"              {return new Symbol(Sym.FALSO,yyline+1,yycolumn+1, yytext());}
    [fF]"also"              {return new Symbol(Sym.FALSO,yyline+1,yycolumn+1, yytext());}
    [nN]"ulo"               {return new Symbol(Sym.NULO,yyline+1,yycolumn+1, yytext());}
    {CADENA}                {return new Symbol(Sym.CADENA,yyline+1,yycolumn+1, yytext());}  
    {CARACTER}              {return new Symbol(Sym.CARACTER,yyline+1,yycolumn+1, yytext());}  
    {NUMERO}                {return new Symbol(Sym.NUMERO,yyline+1,yycolumn+1, yytext());}     
    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(Sym.error,yyline,yycolumn, yytext());}

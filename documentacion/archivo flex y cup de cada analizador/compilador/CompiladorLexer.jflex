package com.adolfosc.analizadores.compilador;

import java_cup.runtime.*; 
import com.adolfosc.analizadores.compilador.Sym.*;
import java.util.List;
import java.util.ArrayList;
import com.adolfosc.analizadores.ErrorSintaxis;

%%

//Configuracion JFLEX
%public
%class CompiladorLexer
//%standalone
%line
%column
%cup

//Expresiones Regulares



/*------------COMENTARIOs------------*/
COMENTARIOLINE       = \>\>[^\n\r]*
COMENTARIOBLOCK      = \<\-[^->]*\-\>
COMENTARIO          = ({COMENTARIOLINE}|{COMENTARIOBLOCK})

/*--------------Literales---------------*/
LETRA               = [a-zA-Z]
DIGITO              = [0-9]
CARACTER            = \'#?[^\']\'
CADENA              = \"[^\"\\]*\"
NUMERO              = {DIGITO}+([.]{DIGITO}{1,6})?


/*----------ESPACIOs En blanco----------*/
ESPACIO             = " "
SALTOLINEA          = \n|\r|\r\n
ESPACIOBLANCO       = ({ESPACIO}|{SALTOLINEA}| [\t\n])+

/*------------IDENTIFICADOR------------*/
IDENTIFICADOR       = ({LETRA}|"_") ({LETRA}|{DIGITO}|"_")*

/*---------------NOTAS----------------*/
NOTAS               = [Dd]"o#"|[Rr]"e#"|[Ff]"a#"|[Ss]"ol#"|[Ll]"a#"|[Dd]"o"|[Rr]"e"|[Mm]"i"|[Ff]"a"|[Ss]"ol"|[Ll]"a"|[Ss]"i"


/*--------- Codigo Incrustado ---------*/
%{
    private List<ErrorSintaxis> erroresCom;
    private int identCant;
    private boolean identar;

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
    identar = true;
    identCant = 0;
%init}

%%


//Reglas Lexicas
<YYINITIAL> {    
    {COMENTARIO}            {/* return new Symbol(Sym.COMENT,yyline+1,yycolumn+1, yytext()); */} 
    {ESPACIOBLANCO}         {/* int val =  if(val >= 1){return new Symbol(Sym.IDENT,yyline+1,yycolumn+1, String.valueOf(val));} */}
    /*-------- Palabras Reservadas --------*/
    [pP]"ista"              {return new Symbol(Sym.PISTA,yyline+1,yycolumn+1, yytext());}
    [tT]"rue"               {return new Symbol(Sym.VERDADERO,yyline+1,yycolumn+1, yytext());}
    [vV]"erdadero"          {return new Symbol(Sym.VERDADERO,yyline+1,yycolumn+1, yytext());}
    [fF]"alse"              {return new Symbol(Sym.FALSO,yyline+1,yycolumn+1, yytext());}
    [fF]"also"              {return new Symbol(Sym.FALSO,yyline+1,yycolumn+1, yytext());}
    [eE]"xtiende"           {return new Symbol(Sym.EXTIENDE,yyline+1,yycolumn+1, yytext());}
    [kK]"eep"               {return new Symbol(Sym.KEEP,yyline+1,yycolumn+1, yytext());}
    [vV]"ar"                {return new Symbol(Sym.VAR,yyline+1,yycolumn+1, yytext());}
    [aA]"rreglo"            {return new Symbol(Sym.ARREGLO,yyline+1,yycolumn+1, yytext());}
    [sS]"i"                 {return new Symbol(Sym.SI,yyline+1,yycolumn+1, yytext());}
    [sS]"ino"               {return new Symbol(Sym.SINO,yyline+1,yycolumn+1, yytext());}
    [sS]"ino si"            {return new Symbol(Sym.SINOSI,yyline+1,yycolumn+1, yytext());}
    [sS]"witch"             {return new Symbol(Sym.SWITCH,yyline+1,yycolumn+1, yytext());}
    [cC]"aso"               {return new Symbol(Sym.CASO,yyline+1,yycolumn+1, yytext());}
    [dD]"efault"            {return new Symbol(Sym.DEFAULT,yyline+1,yycolumn+1, yytext());}
    [pP]"ara"               {return new Symbol(Sym.PARA,yyline+1,yycolumn+1, yytext());}
    [mM]"ientras"           {return new Symbol(Sym.MIENTRAS,yyline+1,yycolumn+1, yytext());}
    [hH]"acer"              {return new Symbol(Sym.HACER,yyline+1,yycolumn+1, yytext());}
    [cC]"ontinuar"          {return new Symbol(Sym.CONTINUAR,yyline+1,yycolumn+1, yytext());}
    [rR]"etorna"            {return new Symbol(Sym.RETORNA,yyline+1,yycolumn+1, yytext());}
    [rR]"eproducir"         {return new Symbol(Sym.REPRODUCIR,yyline+1,yycolumn+1, yytext());}
    [eE]"sperar"            {return new Symbol(Sym.ESPERAR,yyline+1,yycolumn+1, yytext());}
    [sS]"umarizar"          {return new Symbol(Sym.SUMARIZAR,yyline+1,yycolumn+1, yytext());}
    [lL]"ongitud"           {return new Symbol(Sym.LONGITUD,yyline+1,yycolumn+1, yytext());}
    [mM]"ensaje"            {return new Symbol(Sym.MENSAJE,yyline+1,yycolumn+1, yytext());}
    [pP]"rincipal"          {return new Symbol(Sym.PRINCIPAL,yyline+1,yycolumn+1, yytext());}
    [lL]"ista"              {return new Symbol(Sym.LISTA,yyline+1,yycolumn+1, yytext());}
    [nN]"ombre"             {return new Symbol(Sym.NOMBRE,yyline+1,yycolumn+1, yytext());}
    [rR]"andom"             {return new Symbol(Sym.RANDOM,yyline+1,yycolumn+1, yytext());}
    [cC]"ircular"           {return new Symbol(Sym.CIRCULAR,yyline+1,yycolumn+1, yytext());}
    [pP]"istas"             {return new Symbol(Sym.PISTAS,yyline+1,yycolumn+1, yytext());}
    [sS]"alir"              {return new Symbol(Sym.SALIR,yyline+1,yycolumn+1, yytext());}
    [eE]"ntero"             {return new Symbol(Sym.ENTEROSTR,yyline+1,yycolumn+1, yytext());}
    [dD]"oble"              {return new Symbol(Sym.DOBLESTR,yyline+1,yycolumn+1, yytext());}
    [bB]"oolean"            {return new Symbol(Sym.BOOLEANSTR,yyline+1,yycolumn+1, yytext());}
    [cC]"aracter"           {return new Symbol(Sym.CARACTERSTR,yyline+1,yycolumn+1, yytext());}
    [cC]"adena"             {return new Symbol(Sym.CADENASTR,yyline+1,yycolumn+1, yytext());}
    {NOTAS}                 {return new Symbol(Sym.NOTA,yyline+1,yycolumn+1, yytext());}      
    {IDENTIFICADOR}         {return new Symbol(Sym.ID,yyline+1,yycolumn+1, yytext());}  
    {CADENA}                {return new Symbol(Sym.CADENA,yyline+1,yycolumn+1, yytext());}  
    {CARACTER}              {return new Symbol(Sym.CARACTER,yyline+1,yycolumn+1, yytext());}  
    {NUMERO}                {return new Symbol(Sym.NUMERO,yyline+1,yycolumn+1, yytext());}          
    /*------------ Operadores ------------*/
    "++"                    {return new Symbol(Sym.OPINCR,yyline+1,yycolumn+1, yytext());}
    "--"                    {return new Symbol(Sym.OPDECR,yyline+1,yycolumn+1, yytext());}
    "+="                    {return new Symbol(Sym.OPSUMSIM,yyline+1,yycolumn+1, yytext());}
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
    "{"                     {return new Symbol(Sym.LLAVEIZ,yyline+1,yycolumn+1, yytext());}
    "}"                     {return new Symbol(Sym.LLAVEDER,yyline+1,yycolumn+1, yytext());}      
    ":"                     {return new Symbol(Sym.DOSPUNT,yyline+1,yycolumn+1, yytext());}      
    ";"                     {return new Symbol(Sym.PUNTCOMA,yyline+1,yycolumn+1, yytext());}      
    ","                     {return new Symbol(Sym.COMA,yyline+1,yycolumn+1, yytext());}      
    "="                     {return new Symbol(Sym.IGUAL,yyline+1,yycolumn+1, yytext());}      


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(Sym.error,yyline,yycolumn, yytext());}

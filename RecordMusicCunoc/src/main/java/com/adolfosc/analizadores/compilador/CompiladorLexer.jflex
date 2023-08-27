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
NOTAS               = "do#"|"re#"|"fa#"|"sol#"|"la#"|"do"|"re"|"mi"|"fa"|"sol"|"la"|"si"


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

    private int verificarIdentacion(String valor){
        for(char c : valor.toCharArray()) {
            if(c == '\n'){
                this.identCant = 0;
                identar = true;
            } else if (c == ' ' || c == 32) {
                this.identCant++;
            } else if (c == '\t') {
                this.identCant += 4;
            } else {
                identar = false;
            }
        } 
        if(identar == true && this.identCant != 0){
            int cantidadIdent = 0;
            while(this.identCant >= 4){
                cantidadIdent++;
                this.identCant = this.identCant - 4; 
            }
            this.identCant = 0;
            return cantidadIdent;
        }
        return -1;
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
    {COMENTARIO}            {verificarIdentacion(yytext());return new Symbol(Sym.COMENT,yyline+1,yycolumn+1, yytext());} 
    {ESPACIOBLANCO}         {int val = verificarIdentacion(yytext()); if(val >= 1){return new Symbol(Sym.IDENT,yyline+1,yycolumn+1, String.valueOf(val));}}
    /*-------- Palabras Reservadas --------*/
    [pP]"ista"              {verificarIdentacion(yytext());return new Symbol(Sym.PISTA,yyline+1,yycolumn+1, yytext());}
    [tT]"rue"               {verificarIdentacion(yytext());return new Symbol(Sym.VERDADERO,yyline+1,yycolumn+1, yytext());}
    [vV]"erdadero"          {verificarIdentacion(yytext());return new Symbol(Sym.VERDADERO,yyline+1,yycolumn+1, yytext());}
    [fF]"alse"              {verificarIdentacion(yytext());return new Symbol(Sym.FALSO,yyline+1,yycolumn+1, yytext());}
    [fF]"also"              {verificarIdentacion(yytext());return new Symbol(Sym.FALSO,yyline+1,yycolumn+1, yytext());}
    [eE]"xtiende"           {verificarIdentacion(yytext());return new Symbol(Sym.EXTIENDE,yyline+1,yycolumn+1, yytext());}
    [kK]"eep"               {verificarIdentacion(yytext());return new Symbol(Sym.KEEP,yyline+1,yycolumn+1, yytext());}
    [vV]"ar"                {verificarIdentacion(yytext());return new Symbol(Sym.VAR,yyline+1,yycolumn+1, yytext());}
    [aA]"rreglo"            {verificarIdentacion(yytext());return new Symbol(Sym.ARREGLO,yyline+1,yycolumn+1, yytext());}
    [sS]"i"                 {verificarIdentacion(yytext());return new Symbol(Sym.SI,yyline+1,yycolumn+1, yytext());}
    [sS]"ino"               {verificarIdentacion(yytext());return new Symbol(Sym.SINO,yyline+1,yycolumn+1, yytext());}
    [sS]"ino si"            {verificarIdentacion(yytext());return new Symbol(Sym.SINOSI,yyline+1,yycolumn+1, yytext());}
    [sS]"witch"             {verificarIdentacion(yytext());return new Symbol(Sym.SWITCH,yyline+1,yycolumn+1, yytext());}
    [cC]"aso"               {verificarIdentacion(yytext());return new Symbol(Sym.CASO,yyline+1,yycolumn+1, yytext());}
    [dD]"efault"            {verificarIdentacion(yytext());return new Symbol(Sym.DEFAULT,yyline+1,yycolumn+1, yytext());}
    [pP]"ara"               {verificarIdentacion(yytext());return new Symbol(Sym.PARA,yyline+1,yycolumn+1, yytext());}
    [mM]"ientras"           {verificarIdentacion(yytext());return new Symbol(Sym.MIENTRAS,yyline+1,yycolumn+1, yytext());}
    [hH]"acer"              {verificarIdentacion(yytext());return new Symbol(Sym.HACER,yyline+1,yycolumn+1, yytext());}
    [cC]"ontinuar"          {verificarIdentacion(yytext());return new Symbol(Sym.CONTINUAR,yyline+1,yycolumn+1, yytext());}
    [rR]"etorna"            {verificarIdentacion(yytext());return new Symbol(Sym.RETORNA,yyline+1,yycolumn+1, yytext());}
    [rR]"eproducir"         {verificarIdentacion(yytext());return new Symbol(Sym.REPRODUCIR,yyline+1,yycolumn+1, yytext());}
    [eE]"sperar"            {verificarIdentacion(yytext());return new Symbol(Sym.ESPERAR,yyline+1,yycolumn+1, yytext());}
    [sS]"umarizar"          {verificarIdentacion(yytext());return new Symbol(Sym.SUMARIZAR,yyline+1,yycolumn+1, yytext());}
    [lL]"ongitud"           {verificarIdentacion(yytext());return new Symbol(Sym.LONGITUD,yyline+1,yycolumn+1, yytext());}
    [mM]"ensaje"            {verificarIdentacion(yytext());return new Symbol(Sym.MENSAJE,yyline+1,yycolumn+1, yytext());}
    [pP]"rincipal"          {verificarIdentacion(yytext());return new Symbol(Sym.PRINCIPAL,yyline+1,yycolumn+1, yytext());}
    [lL]"ista"              {verificarIdentacion(yytext());return new Symbol(Sym.LISTA,yyline+1,yycolumn+1, yytext());}
    [nN]"ombre"             {verificarIdentacion(yytext());return new Symbol(Sym.NOMBRE,yyline+1,yycolumn+1, yytext());}
    [rR]"andom"             {verificarIdentacion(yytext());return new Symbol(Sym.RANDOM,yyline+1,yycolumn+1, yytext());}
    [cC]"ircular"           {verificarIdentacion(yytext());return new Symbol(Sym.CIRCULAR,yyline+1,yycolumn+1, yytext());}
    [pP]"istas"             {verificarIdentacion(yytext());return new Symbol(Sym.PISTAS,yyline+1,yycolumn+1, yytext());}
    [sS]"alir"              {verificarIdentacion(yytext());return new Symbol(Sym.SALIR,yyline+1,yycolumn+1, yytext());}
    [eE]"ntero"             {verificarIdentacion(yytext());return new Symbol(Sym.ENTEROSTR,yyline+1,yycolumn+1, yytext());}
    [dD]"oble"              {verificarIdentacion(yytext());return new Symbol(Sym.DOBLESTR,yyline+1,yycolumn+1, yytext());}
    [bB]"oolean"            {verificarIdentacion(yytext());return new Symbol(Sym.BOOLEANSTR,yyline+1,yycolumn+1, yytext());}
    [cC]"aracter"           {verificarIdentacion(yytext());return new Symbol(Sym.CARACTERSTR,yyline+1,yycolumn+1, yytext());}
    [cC]"adena"             {verificarIdentacion(yytext());return new Symbol(Sym.CADENASTR,yyline+1,yycolumn+1, yytext());}
    {NOTAS}                 {verificarIdentacion(yytext());return new Symbol(Sym.NOTA,yyline+1,yycolumn+1, yytext());}      
    {IDENTIFICADOR}         {verificarIdentacion(yytext());return new Symbol(Sym.ID,yyline+1,yycolumn+1, yytext());}  
    {CADENA}                {verificarIdentacion(yytext());return new Symbol(Sym.CADENA,yyline+1,yycolumn+1, yytext());}  
    {CARACTER}              {verificarIdentacion(yytext());return new Symbol(Sym.CARACTER,yyline+1,yycolumn+1, yytext());}  
    {NUMERO}                {verificarIdentacion(yytext());return new Symbol(Sym.NUMERO,yyline+1,yycolumn+1, yytext());}          
    /*------------ Operadores ------------*/
    "++"                    {verificarIdentacion(yytext());return new Symbol(Sym.OPINCR,yyline+1,yycolumn+1, yytext());}
    "--"                    {verificarIdentacion(yytext());return new Symbol(Sym.OPDECR,yyline+1,yycolumn+1, yytext());}
    "+="                    {verificarIdentacion(yytext());return new Symbol(Sym.OPSUMSIM,yyline+1,yycolumn+1, yytext());}
    "+"                     {verificarIdentacion(yytext());return new Symbol(Sym.SUMA,yyline+1,yycolumn+1, yytext());}
    "-"                     {verificarIdentacion(yytext());return new Symbol(Sym.RESTA,yyline+1,yycolumn+1, yytext());}
    "*"                     {verificarIdentacion(yytext());return new Symbol(Sym.MULT,yyline+1,yycolumn+1, yytext());}
    "/"                     {verificarIdentacion(yytext());return new Symbol(Sym.DIVI,yyline+1,yycolumn+1, yytext());}
    "%"                     {verificarIdentacion(yytext());return new Symbol(Sym.OPMOD,yyline+1,yycolumn+1, yytext());}
    "^"                     {verificarIdentacion(yytext());return new Symbol(Sym.OPELV,yyline+1,yycolumn+1, yytext());}
    "=="                    {verificarIdentacion(yytext());return new Symbol(Sym.DOBIGUAL,yyline+1,yycolumn+1, yytext());}
    "!="                    {verificarIdentacion(yytext());return new Symbol(Sym.DIFERENC,yyline+1,yycolumn+1, yytext());}
    ">="                    {verificarIdentacion(yytext());return new Symbol(Sym.MAYIG,yyline+1,yycolumn+1, yytext());}
    "<="                    {verificarIdentacion(yytext());return new Symbol(Sym.MENIG,yyline+1,yycolumn+1, yytext());}
    "!!"                    {verificarIdentacion(yytext());return new Symbol(Sym.OPNULO,yyline+1,yycolumn+1, yytext());}
    ">"                     {verificarIdentacion(yytext());return new Symbol(Sym.MAY,yyline+1,yycolumn+1, yytext());}
    "<"                     {verificarIdentacion(yytext());return new Symbol(Sym.MEN,yyline+1,yycolumn+1, yytext());}
    "!&&"                   {verificarIdentacion(yytext());return new Symbol(Sym.NAND,yyline+1,yycolumn+1, yytext());}
    "!||"                   {verificarIdentacion(yytext());return new Symbol(Sym.NOR,yyline+1,yycolumn+1, yytext());}
    "&|"                    {verificarIdentacion(yytext());return new Symbol(Sym.XOR,yyline+1,yycolumn+1, yytext());}
    "&&"                    {verificarIdentacion(yytext());return new Symbol(Sym.AND,yyline+1,yycolumn+1, yytext());}
    "||"                    {verificarIdentacion(yytext());return new Symbol(Sym.OR,yyline+1,yycolumn+1, yytext());}
    "!"                     {verificarIdentacion(yytext());return new Symbol(Sym.OPNOT,yyline+1,yycolumn+1, yytext());}      
    "("                     {verificarIdentacion(yytext());return new Symbol(Sym.PARI,yyline+1,yycolumn+1, yytext());}      
    ")"                     {verificarIdentacion(yytext());return new Symbol(Sym.PARD,yyline+1,yycolumn+1, yytext());}      
    "["                     {verificarIdentacion(yytext());return new Symbol(Sym.BRACKI,yyline+1,yycolumn+1, yytext());}
    "]"                     {verificarIdentacion(yytext());return new Symbol(Sym.BRACKD,yyline+1,yycolumn+1, yytext());}      
    "{"                     {verificarIdentacion(yytext());return new Symbol(Sym.LLAVEIZ,yyline+1,yycolumn+1, yytext());}
    "}"                     {verificarIdentacion(yytext());return new Symbol(Sym.LLAVEDER,yyline+1,yycolumn+1, yytext());}      
    ":"                     {verificarIdentacion(yytext());return new Symbol(Sym.DOSPUNT,yyline+1,yycolumn+1, yytext());}      
    ";"                     {verificarIdentacion(yytext());return new Symbol(Sym.PUNTCOMA,yyline+1,yycolumn+1, yytext());}      
    ","                     {verificarIdentacion(yytext());return new Symbol(Sym.COMA,yyline+1,yycolumn+1, yytext());}      
    "="                     {verificarIdentacion(yytext());return new Symbol(Sym.IGUAL,yyline+1,yycolumn+1, yytext());}      


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(Sym.error,yyline,yycolumn, yytext());}

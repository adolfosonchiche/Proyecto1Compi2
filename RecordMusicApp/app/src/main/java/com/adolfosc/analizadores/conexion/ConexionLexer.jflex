package com.adolfosc.analizadores.conexion;

import java_cup.runtime.*; 
import com.adolfosc.analizadores.conexion.Sym.*;
import java.util.List;
import java.util.ArrayList;
import com.adolfosc.analizadores.ErrorSintaxis;

%%

//Configuracion JFLEX
%public
%class ConexionLexer
//%standalone
%line
%column
%cup

//Expresiones Regulares


/*--------------Literales---------------*/
DIGITO              = [0-9]
CADENA              = \"[^\"\\]*\"
NUMERO              = {DIGITO}+([.]{DIGITO}{1,6})?


/*----------ESPACIOs En blanco----------*/
ESPACIO             = " "
SALTOLINEA          = \n|\r|\r\n
ESPACIOBLANCO       = ({ESPACIO}|{SALTOLINEA}| [\t\n])+



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
%init}

%%


//Reglas Lexicas
<YYINITIAL> {        
    {ESPACIOBLANCO}         {/*VACIO*/}
    /*-------- Palabras Reservadas --------*/
    "lista"                 {return new Symbol(Sym.LISTA,yyline+1,yycolumn+1, yytext());}  
    "pista"                 {return new Symbol(Sym.PISTA,yyline+1,yycolumn+1, yytext());}  
    "pistanueva"            {return new Symbol(Sym.PISTANUEVA,yyline+1,yycolumn+1, yytext());}  
    "pistas"                {return new Symbol(Sym.PISTAS,yyline+1,yycolumn+1, yytext());}  
    "listas"                {return new Symbol(Sym.LISTAS,yyline+1,yycolumn+1, yytext());}  
    "solicitud"             {return new Symbol(Sym.SOLICITUD,yyline+1,yycolumn+1, yytext());}  
    "tipo"                  {return new Symbol(Sym.TIPO,yyline+1,yycolumn+1, yytext());}  
    "nombre"                {return new Symbol(Sym.NOMBRE,yyline+1,yycolumn+1, yytext());}  
    "aleatoria"             {return new Symbol(Sym.ALEATORIA,yyline+1,yycolumn+1, yytext());}  
    "duracion"              {return new Symbol(Sym.DURACION,yyline+1,yycolumn+1, yytext());}  
    "canal"                 {return new Symbol(Sym.CANAL,yyline+1,yycolumn+1, yytext());}  
    "NUMERO"                {return new Symbol(Sym.NUMEROSTR,yyline+1,yycolumn+1, yytext());}  
    "frecuencia"            {return new Symbol(Sym.FRECUENCIA,yyline+1,yycolumn+1, yytext());}  
    "datos"                 {return new Symbol(Sym.DATOS,yyline+1,yycolumn+1, yytext());}  
    "nota"                  {return new Symbol(Sym.NOTA,yyline+1,yycolumn+1, yytext());}  
    "octava"                {return new Symbol(Sym.OCTAVA,yyline+1,yycolumn+1, yytext());}      
    "respuesta"             {return new Symbol(Sym.RESPUESTA,yyline+1,yycolumn+1, yytext());}      
    {CADENA}                {return new Symbol(Sym.CADENA,yyline+1,yycolumn+1, yytext());}      
    {NUMERO}                {return new Symbol(Sym.NUMERO,yyline+1,yycolumn+1, yytext());}          
    /*------------ Operadores ------------*/
    ">"                     {return new Symbol(Sym.MAY,yyline+1,yycolumn+1, yytext());}
    "<"                     {return new Symbol(Sym.MEN,yyline+1,yycolumn+1, yytext());}
    "/"                     {return new Symbol(Sym.BARRA,yyline+1,yycolumn+1, yytext());}     
    "="                     {return new Symbol(Sym.IGUAL,yyline+1,yycolumn+1, yytext());}      


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(Sym.error,yyline,yycolumn, yytext());}
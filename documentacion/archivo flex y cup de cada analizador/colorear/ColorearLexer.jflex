package com.adolfosc.analizadores.colorear;

import java.util.List;
import java.util.ArrayList;
import com.adolfosc.analizadores.ErrorSintaxis;


%%

//Configuracion JFLEX
%public
%class ColorearLexer
%line
%column
%function scanear
%unicode
%char
%standalone
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


/*--------------Operadores-------------*/
MASIGUAL            = "+="
OPINCREMTDECREMENT          = "++"|"--"
OPARIZTMETICOS       = "+"|"-"|"*"|"/"|"%"|"^"
OPRELACIONAL      = "=="|"!="|">"|"<"|">="|"<="|"!!"
OPLOGICO           = "&&"|"!&&"|"||"|"!||"|"&|"|"!"
PARIZ                = "("
PARDER                = ")"
BRACKIZ              = "["
BRACKDER              = "]"
LLAVEIZ          = "{"
LLAVEDER          = "}"
DOSPUNTO             = ":"
PUNTOCOMA            = ";"
COMA                = ","
IGUAL               = "="
OPERADORTODO      = {OPARIZTMETICOS}|{OPRELACIONAL}|{OPLOGICO}|{PARIZ}|{PARDER}|{BRACKIZ}|{BRACKDER}|{LLAVEIZ}|{LLAVEDER}|{DOSPUNTO}|{PUNTOCOMA}|{COMA}|{IGUAL}|{MASIGUAL}|{OPINCREMTDECREMENT}

/*--------Palabras Reservadas--------*/
%ignorecase
PALABRARESERVADA       = "true"|"false"|"extiende"|"keep"|"var"|"arreglo"|"si"|"sino"|"sino si"|"switch"|"caso"|"default"|"para"|"mientras"|"hacer"|"continuar"|"retorna"|"Reproducir"|"Esperar"|"Sumarizar"|"Longitud"|"Mensaje"|"Principal"|"lista"|"nombre"|"random"|"circular"|"pistas"|"salir"





//Codigo Incrustado
%{
    private List<ErrorSintaxis> ErrorSintaxisesCom;
    public PintarPalabra pintar = new PintarPalabra();
    /*------Inicializacion Variables-----*/
    private int identCant;
    private boolean identar;    

    private void ErrorSintaxis(String lexeme) {
        ErrorSintaxisesCom.add(new ErrorSintaxis("Lexico","Simbolo no existe en el lenguaje",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }    

    private void ErrorSintaxisPrueba(String lexeme, String tipo) {
        ErrorSintaxisesCom.add(new ErrorSintaxis("PRUEBA",tipo,String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    public List<ErrorSintaxis> getErrorSintaxisesCom() {
        return ErrorSintaxisesCom;
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
    ErrorSintaxisesCom = new ArrayList<>();
    identar = true;
    identCant = 0;
%init}

%%


//Reglas Lexicas
<YYINITIAL> {            
    {COMENTARIO}            {pintar.pintaGris(yychar,yylength());} 
    {ESPACIOBLANCO}         {/*vacio*/}
    {PALABRARESERVADA}      {pintar.pintaAzul(yychar,yylength());}     
    {IDENTIFICADOR}         {pintar.pintaVerde(yychar,yylength());}  
    {CADENA}                {pintar.pintaNara(yychar,yylength());}  
    {CARACTER}              {pintar.pintaNara(yychar,yylength());}  
    {NUMERO}                {pintar.pintaMora(yychar,yylength());}  
    {OPERADORTODO}          {/*vacio*/}
    

}

/* ErrorSintaxis por cualquier otro simbolo*/
[^]
		{System.out.println("es un ErrorSintaxis:"+yytext());}

package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Expresion;
import com.adolfosc.modelo.music.Instruccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Asignacion extends Instruccion {
    
    private List<Expresion> valoresDecl;  
    private boolean esArreglo;
    private String variable;
    private List<Dimension> dimensiones;
    private boolean esIncremento;

    public Asignacion(String tipoInstruccion, int identacion) {
        super(tipoInstruccion, identacion);
        this.valoresDecl = new ArrayList<>();
        this.dimensiones = new ArrayList<>();
    }
       
    public List<Expresion> getValoresDecl() {
        return valoresDecl;
    }

    public void setValoresDecl(List<Expresion> valoresDecl) {
        this.valoresDecl = valoresDecl;
    }
    
    public void insertarValor(Expresion expresion){
        this.valoresDecl.add(expresion);
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public void setEsArreglo(boolean esArreglo) {
        this.esArreglo = esArreglo;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public List<Dimension> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Dimension> dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    public void insertarDimension(Dimension dimension){
        this.dimensiones.add(dimension);
    }

    public boolean isEsIncremento() {
        return esIncremento;
    }

    public void setEsIncremento(boolean esIncremento) {
        this.esIncremento = esIncremento;
    }
    
}

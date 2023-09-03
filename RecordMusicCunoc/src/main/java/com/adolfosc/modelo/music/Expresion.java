package com.adolfosc.modelo.music;

import com.adolfosc.modelo.instrucciones.Dimension;
import com.adolfosc.modelo.instrucciones.Funcion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Expresion implements Serializable {
    
    private String tipo;
    private String valor;
    private List<Dimension> dimensiones;
    private Funcion funcion;

    public Expresion() {
        this.dimensiones = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Dimension> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Dimension> dimensiones) {
        this.dimensiones = dimensiones;
    }           
    
    public void insertarExpresion(Dimension dimension){
        this.dimensiones.add(dimension);
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }  
    
}

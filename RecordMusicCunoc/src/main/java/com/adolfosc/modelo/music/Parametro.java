package com.adolfosc.modelo.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Parametro implements Serializable {
    
    private String nombre;
    private String tipo;
    private List<Expresion> expresiones;

    public Parametro() {
        this.expresiones = new ArrayList<>();
    }        

    public List<Expresion> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(List<Expresion> expresiones) {
        this.expresiones = expresiones;
    }            
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}

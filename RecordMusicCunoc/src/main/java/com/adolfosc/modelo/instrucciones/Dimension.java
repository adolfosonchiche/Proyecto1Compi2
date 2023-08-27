package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Expresion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Dimension {
    
    List<Expresion> expresiones;

    public Dimension() {
        this.expresiones= new ArrayList<>();
    }

    public List<Expresion> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(List<Expresion> expresiones) {
        this.expresiones = expresiones;
    }    
    
    public void insertarExpresion(Expresion expresion){
        this.expresiones.add(expresion);
    }
    
}

package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Expresion;
import com.adolfosc.modelo.music.Instruccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class CasoC extends Instruccion {
    
    private String tipo;
    private List<Expresion> expresion;
    private List<Instruccion> instrucciones;

    public CasoC(String tipoInstruccion, int identacion) {
        super(tipoInstruccion, identacion);
        this.instrucciones = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Expresion> getExpresion() {
        return expresion;
    }

    public void setExpresion(List<Expresion> expresion) {
        this.expresion = expresion;
    }


    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    
    public void insertarInstruccion(Instruccion instruccion){
        this.instrucciones.add(instruccion);
    }
    
}

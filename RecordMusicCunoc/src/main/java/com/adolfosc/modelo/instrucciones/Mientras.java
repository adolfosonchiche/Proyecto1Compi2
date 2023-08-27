package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Condicion;
import com.adolfosc.modelo.music.Instruccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Mientras extends Instruccion {
    
    private List<Condicion> condiciones;
    private List<String> operadores;
    private List<Instruccion> instrucciones;

    public Mientras(String tipoInstruccion, int identacion) {
        super(tipoInstruccion, identacion);
        this.condiciones = new ArrayList<>();
        this.operadores = new ArrayList<>();
        this.instrucciones = new ArrayList<>();
    }
    
    public List<Condicion> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(List<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public List<String> getOperadores() {
        return operadores;
    }

    public void setOperadores(List<String> operadores) {
        this.operadores = operadores;
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
    
    public void insertarCondicion(Condicion condicion){
        this.condiciones.add(condicion);
    }
    
    public void insertarOperador(String operador){
        this.operadores.add(operador);
    }
    
}

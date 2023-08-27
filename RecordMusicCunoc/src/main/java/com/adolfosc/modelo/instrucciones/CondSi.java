package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Condicion;
import com.adolfosc.modelo.music.Instruccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class CondSi extends Instruccion {
    
    private List<Condicion> condiciones;
    private List<String> operadores;
    private List<CondSi> condsinosi;
    private CondSi condsino;
    private List<Instruccion> instrucciones;

    public CondSi(String tipoInstruccion, int identacion) {
        super(tipoInstruccion, identacion);
        this.condiciones = new ArrayList<>();
        this.operadores = new ArrayList<>();
        this.condsinosi = new ArrayList<>();
        this.instrucciones = new ArrayList<>();
        this.condsino = null;
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

    public List<CondSi> getCondsinosi() {
        return condsinosi;
    }

    public void setCondsinosi(List<CondSi> condsinosi) {
        this.condsinosi = condsinosi;
    }

    public CondSi getCondsino() {
        return condsino;
    }

    public void setCondsino(CondSi condsino) {
        this.condsino = condsino;
    }        
    
    public void insertarCondicion(Condicion condicion){
        this.condiciones.add(condicion);
    }
    
    public void insertarOperador(String operador){
        this.operadores.add(operador);
    }
    
    public void insertarCondicionSinoSi(CondSi condicion){
        this.condsinosi.add(condicion);
    }
    
    public void insertarInstruccion(Instruccion instruccion){
        this.instrucciones.add(instruccion);
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }
    
}

package com.adolfosc.modelo.music;

/**
 *
 * @author hectoradolfo
 */
public class Instruccion {
    
    private String tipoInstruccion;
    private int identacion;      

    public Instruccion(String tipoInstruccion, int identacion) {
        this.tipoInstruccion = tipoInstruccion;
        this.identacion = identacion;
    }
    
    
    public String getTipoInstruccion() {
        return tipoInstruccion;
    }

    public void setTipoInstruccion(String tipoInstruccion) {
        this.tipoInstruccion = tipoInstruccion;
    }

    public int getIdentacion() {
        return identacion;
    }

    public void setIdentacion(int identacion) {
        this.identacion = identacion;
    }
    
}

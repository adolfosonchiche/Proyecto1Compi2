package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Instruccion;

/**
 *
 * @author hectoradolfo
 */
public class IncrDecr extends Instruccion {
    
    private String variable;
    private String tipo;

    public IncrDecr(String tipoInstruccion, int identacion) {
        super(tipoInstruccion, identacion);
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}

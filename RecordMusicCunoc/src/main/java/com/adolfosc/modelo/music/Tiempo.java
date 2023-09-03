package com.adolfosc.modelo.music;

import java.io.Serializable;

/**
 *
 * @author hectoradolfo
 */
public class Tiempo implements Serializable {
    
    private int canal;
    private int tiempo;

    public Tiempo(int canal) {
        this.canal = canal;
        this.tiempo = 0;
    }
    
    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
}

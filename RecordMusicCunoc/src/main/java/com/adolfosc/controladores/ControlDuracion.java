package com.adolfosc.controladores;

import com.adolfosc.modelo.music.Nota;
import com.adolfosc.modelo.music.Tiempo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class ControlDuracion {
    
    private List<Nota> notas;
    private List<Tiempo> tiempos;
    private int duracion;

    public ControlDuracion(List<Nota> notas) {
        this.notas = notas;
        this.tiempos = new ArrayList<>();
        this.duracion = 0;
    }
    
    public void generarDuracion(){
        for (Nota nota : notas) {
            int canal = nota.getCanal();
            int tiempo = nota.getTiempo();
            Tiempo tiem = buscarTiempo(canal);
            if (tiem == null) {
                tiem = new Tiempo(canal);
                tiem.setTiempo(tiempo);
                this.tiempos.add(tiem);
            }else{
                tiem.setTiempo(tiem.getTiempo()+tiempo);
            }
        }
        
        for (Tiempo tiempo : tiempos) {
            int durT = tiempo.getTiempo();
            if (durT>this.duracion) {
                duracion = durT;
            }
        }
    }
    
    private Tiempo buscarTiempo(int canal){
        for (Tiempo tiempo : tiempos) {
            if (tiempo.getCanal() == canal) {
                return tiempo;
            }
        }
        
        return null;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
}

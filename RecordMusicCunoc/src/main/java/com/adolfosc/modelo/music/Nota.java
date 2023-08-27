package com.adolfosc.modelo.music;

/**
 *
 * @author hectoradolfo
 */
public class Nota {
    
    public static final String[] NOTE_NAMES = {"do", "do#", "re", "re#", "mi", "fa", "fa#", "sol", "sol#", "la", "la#", "si"};
    private int frecuencia;
    private String nombre;
    private int octava;
    private int tiempo;    
    private int canal;

    public Nota(String nombre, int octava, int tiempo, int canal) {        
        this.nombre = nombre;
        this.octava = octava;
        this.tiempo = tiempo;
        this.canal = canal;
    }           
    
    public Nota() {
    }
    

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getOctava() {
        return octava;
    }

    public void setOctava(int octava) {
        this.octava = octava;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }
    
        
    public int getNota(){
        int cont = 0;
        if (this.nombre.equals("esperar")) {
            return -1;
        }
        for (String nombreNota : NOTE_NAMES) {
            if (nombreNota.equals(this.nombre)) {
                break;
            }
            cont++;
        }
        
        return (cont+24)+(this.octava*12);
    }
    
}

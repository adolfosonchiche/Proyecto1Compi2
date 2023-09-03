package com.adolfosc.modelo.music;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Pista implements Serializable {
    
    private String nombre;
    private List<String> extensiones;   
    private List<Instruccion> instrucciones;    

    public Pista() {
        this.extensiones = new ArrayList<>();
        this.instrucciones = new ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
    public void setExtension(String nombreExtension){
        this.extensiones.add(nombreExtension);
    }

    public List<String> getExtensiones() {
        return extensiones;
    }        

    public void setExtensiones(List<String> extensiones) {
        this.extensiones = extensiones;
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

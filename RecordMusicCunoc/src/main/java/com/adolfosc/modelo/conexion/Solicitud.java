package com.adolfosc.modelo.conexion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Solicitud {
    
    private String tipo;
    private String nombre;
    private List<Dato> datos;

    public Solicitud() {
        datos = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Dato> getDatos() {
        return datos;
    }

    public void setDatos(List<Dato> datos) {
        this.datos = datos;
    }
    
    public void insertarDato(Dato dato){
        this.datos.add(dato);
    }
    
}

package com.adolfosc.modelo.music;

/**
 *
 * @author hectoradolfo
 */
public class PistaGuardado {
    
    private String codigo;
    private String nombre;
    private int duracion;

    public PistaGuardado(String codigo, String nombre, int duracion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public PistaGuardado() {
    }         

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
}

package com.adolfosc.modelo.valexpresion;

import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Simbolo {
    
    private boolean esKeep;
    private String nombre;
    private String tipo;
    private String valor;
    private boolean esArreglo;
    private boolean esDeclArr;
    List<Integer> dimensiones;
    private int ambito;

    public Simbolo() {
    }

    public Simbolo(boolean esKeep, String nombre, String tipo, String valor, boolean esArreglo, List<Integer> dimensiones) {
        this.esKeep = esKeep;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.esArreglo = esArreglo;
        this.dimensiones = dimensiones;
    }           

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Integer> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Integer> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public void setEsArreglo(boolean esArreglo) {
        this.esArreglo = esArreglo;
    }

    public boolean isEsKeep() {
        return esKeep;
    }

    public void setEsKeep(boolean esKeep) {
        this.esKeep = esKeep;
    }

    public boolean isEsDeclArr() {
        return esDeclArr;
    }

    public void setEsDeclArr(boolean esDeclArr) {
        this.esDeclArr = esDeclArr;
    }        

    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }
    
}

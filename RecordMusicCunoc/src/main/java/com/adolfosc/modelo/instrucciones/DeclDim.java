package com.adolfosc.modelo.instrucciones;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class DeclDim {
    
    private DeclDim declDimPadre;
    private List<DeclDim> declDimHijos;
    private List<Dimension> declaracionesArreglo;

    public DeclDim() {
        this.declaracionesArreglo = new ArrayList<>();
        this.declDimHijos = new ArrayList<>();
    }

    public List<Dimension> getDeclaracionesArreglo() {
        return declaracionesArreglo;
    }

    public void setDeclaracionesArreglo(List<Dimension> declaracionesArreglo) {
        this.declaracionesArreglo = declaracionesArreglo;
    }
      
    public void insertarDimension(Dimension dimension){
        this.declaracionesArreglo.add(dimension);
    }

    public DeclDim getDeclDimPadre() {
        return declDimPadre;
    }

    public void setDeclDimPadre(DeclDim declDimPadre) {
        this.declDimPadre = declDimPadre;
    }

    public List<DeclDim> getDeclDimHijos() {
        return declDimHijos;
    }

    public void setDeclDimHijos(List<DeclDim> declDimHijos) {
        this.declDimHijos = declDimHijos;
    }
    
    public void insertarHijoDeclDim(DeclDim declDim){
        this.declDimHijos.add(declDim);        
    }
    
}

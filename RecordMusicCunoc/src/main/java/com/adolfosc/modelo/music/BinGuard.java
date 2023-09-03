package com.adolfosc.modelo.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class BinGuard implements Serializable {
    
    List<PistaGuardado> pistasGuardadas;
    List<Lista> listasGuardadas;
    

    public BinGuard() {
        this.pistasGuardadas = new ArrayList<>();
        this.listasGuardadas = new ArrayList<>();
    }
    
    public void insertarPista(PistaGuardado pista){
        this.pistasGuardadas.add(pista);
    }        

    public List<PistaGuardado> getPistasGuardadas() {
        return pistasGuardadas;
    }

    public void setPistasGuardadas(List<PistaGuardado> pistasGuardadas) {
        this.pistasGuardadas = pistasGuardadas;
    }
    
    public boolean eliminarPista(String nombre){
        int cont = 0;
        for (PistaGuardado pistaGuardada : pistasGuardadas) {            
            String nom = pistaGuardada.getNombre();
            if (nom.equals(nombre)) {
                this.pistasGuardadas.remove(cont);
                return true;                
            }
            cont++;
        }
        return false;
    }
    
    public void insertarLista(Lista lista){
        this.listasGuardadas.add(lista);
    }

    public List<Lista> getListasGuardadas() {
        return listasGuardadas;
    }

    public void setListasGuardadas(List<Lista> listasGuardadas) {
        this.listasGuardadas = listasGuardadas;
    }        
    
    public boolean eliminarLista(String nombre){
        int cont = 0;
        for (Lista listaGuardada : listasGuardadas) {            
            String nom = listaGuardada.getNombre();
            if (nom.equals(nombre)) {
                this.listasGuardadas.remove(cont);
                return true;                
            }
            cont++;
        }
        return false;
    }
    
}

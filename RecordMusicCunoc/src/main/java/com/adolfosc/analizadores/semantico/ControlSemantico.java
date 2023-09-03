package com.adolfosc.analizadores.semantico;

import com.adolfosc.controladores.ControlGuardado;
import com.adolfosc.modelo.music.BinGuard;
import com.adolfosc.modelo.music.Lista;
import com.adolfosc.modelo.music.PistaGuardado;
import java.util.List;


/**
 *
 * @author hectoradolfo
 */
public class ControlSemantico {
    
    public boolean validarLista(Lista listaVal){
        List<String> pistasLista = listaVal.getPistas();
        int tamanio = pistasLista.size();
        for (int i = 0; i < (tamanio-1); i++) {
            String pista1 = pistasLista.get(i);
            for (int j = i+1; j < tamanio; j++) {
                String pista2 = pistasLista.get(j);                
                if (pista1.equals(pista2)) {
                    return false;                    
                }
            }
        }        
        return validarPistas(pistasLista);
    }
    
    private boolean validarPistas(List<String> pistasLista){
        //buscarPistas
        ControlGuardado controlG = new ControlGuardado();
        controlG.obtenerBinario();
        BinGuard binarioG = controlG.getBinarioGuard();
        List<PistaGuardado> pistasG = binarioG.getPistasGuardadas();
        for (String pistaL : pistasLista) {
            boolean encontrado = false;
            String nomPL = pistaL.replace("\"", "");
            for (PistaGuardado pistaGuard : pistasG) {
                String nomPG = pistaGuard.getNombre().replace("\"", "");
                if (nomPL.equals(nomPG)) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado == false) {
                return false;
            }
        }
        return true;
    }
    
}

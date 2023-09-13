package com.adolfosc.analizadores.semantico;

import com.adolfosc.controladores.ControlGuardado;
import com.adolfosc.modelo.music.BinGuard;
import com.adolfosc.modelo.music.Lista;
import com.adolfosc.modelo.music.PistaGuardado;
import java.util.List;
import javax.swing.JTextArea;


/**
 *
 * @author hectoradolfo
 */
public class ControlSemantico {
    
    public boolean validarLista(Lista listaVal, JTextArea jTextArea1){
        List<String> pistasLista = listaVal.getPistas();
        int tamanio = pistasLista.size();
        for (int i = 0; i < (tamanio-1); i++) {
            String pista1 = pistasLista.get(i);
            for (int j = i+1; j < tamanio; j++) {
                String pista2 = pistasLista.get(j);                
                if (pista1.equals(pista2)) {
                    jTextArea1.append("Error semantico: esta pista -> " + pista1 + " se repite en la lista.");
                    return false;                    
                }
            }
        }        
        return validarPistas(pistasLista, jTextArea1);
    }
    
    private boolean validarPistas(List<String> pistasLista, JTextArea jTextArea1){
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
                jTextArea1.append("Error semantico: esta pista -> " + pistaL + " no existe.");
                return false;
            }
        }
        return true;
    }
    
}

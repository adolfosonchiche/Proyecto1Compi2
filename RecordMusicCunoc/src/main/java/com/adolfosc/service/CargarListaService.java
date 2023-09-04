package com.adolfosc.service;

import com.adolfosc.controladores.ControlGuardado;
import com.adolfosc.modelo.music.BinGuard;
import com.adolfosc.modelo.music.Lista;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 *
 * @author hectoradolfo
 */
public class CargarListaService {
    
    private ControlGuardado controlG;
    private BinGuard binarioG;
    private BinGuard binario;
    
    
    public void cargarListas(JList<String> listaReproduccion) {
        try {
            this.controlG = new ControlGuardado();
            controlG.obtenerBinario();
            this.binarioG = controlG.getBinarioGuard();
            List<Lista> listas = binarioG.getListasGuardadas();
            DefaultListModel modelo = new DefaultListModel();
            for (Lista lista : listas) {
                modelo.addElement(lista.getNombre());
            }
            listaReproduccion.setModel(modelo);
        } catch (Exception e) {

        }
    }
    
    public void cargarListaParaModificar(JList<String> listaR, boolean codigoCargado,
            JTextPane editorLista ) {
        
        try {
            String value = listaR.getSelectedValue();
            this.controlG = new ControlGuardado();
            this.controlG.obtenerBinario();
            binario = controlG.getBinarioGuard();
            List<Lista> listas = binario.getListasGuardadas();
            Lista listaM = null;
            for (Lista lista : listas) {
                String nom = lista.getNombre().replace("\"", "");
                if (nom.equals(value)) {
                    listaM = lista;
                    break;
                }
            }
            if (listaM == null) {
                JOptionPane.showMessageDialog(null, "La lista no se encuentra", "Info", JOptionPane.ERROR_MESSAGE);
            } else {
                editorLista.setText("");
                editorLista.setText(listaM.getCodigo());
                codigoCargado = true;
                JOptionPane.showMessageDialog(null, "El codigo ha sido cargado", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
        }
    }
    
    
    public void eliminarLista(JList<String> listaR) {
        try {
            String value = listaR.getSelectedValue();
            controlG = new ControlGuardado();
            boolean valR = controlG.eliminarLista(value);
            if (valR) {
                //Eliminar pista
                cargarListas(listaR);
                JOptionPane.showMessageDialog(null, "Lista eliminada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Lista:" + value + "no encontrada", "Info", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
        }
    }
    
    public void verPistasDeLista(JList<String> listaR, List<String> listaPista,
            JList<String> listaPistaSelect, JLabel nombreSeleccionadoLista) {
        // Ver pistas en lista
        try {
            String value = listaR.getSelectedValue();
            controlG = new ControlGuardado();
            controlG.obtenerBinario();
            binario = controlG.getBinarioGuard();
            List<Lista> listas = binario.getListasGuardadas();
            Lista listaM = null;
            for (Lista lista : listas) {
                String nom = lista.getNombre().replace("\"", "");
                if (nom.equals(value)) {
                    listaM = lista;
                    break;
                }
            }
            if (listaM == null) {
                JOptionPane.showMessageDialog(null, "La lista no se encuentra", "Info", JOptionPane.ERROR_MESSAGE);
            } else {
                listaPista = listaM.getPistas();
                mostrarPistasEnLista(listaPista, listaPistaSelect);
                nombreSeleccionadoLista.setText(listaM.getNombre());
                JOptionPane.showMessageDialog(null, "El codigo ha sido cargado", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
        }
    }
    
    private void mostrarPistasEnLista(List<String> listaPista, JList<String> listaPistaSelect) {
        DefaultListModel modelo = new DefaultListModel();
        for (String string : listaPista) {
            modelo.addElement(string);
        }
        listaPistaSelect.setModel(modelo);
    }
    
}

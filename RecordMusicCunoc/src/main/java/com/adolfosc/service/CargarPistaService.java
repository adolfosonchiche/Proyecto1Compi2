package com.adolfosc.service;

import com.adolfosc.analizadores.ErrorSintaxis;
import com.adolfosc.analizadores.compilador.CompiladorLexer;
import com.adolfosc.analizadores.compilador.CompiladorParser;
import com.adolfosc.analizadores.semantico.ControlCrearPista;
import com.adolfosc.controladores.ControlDuracion;
import com.adolfosc.controladores.ControlGuardado;
import com.adolfosc.modelo.music.BinGuard;
import com.adolfosc.modelo.music.Nota;
import com.adolfosc.modelo.music.Pista;
import com.adolfosc.modelo.music.PistaGuardado;
import com.adolfosc.ui.JFReporteErrores;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hectoradolfo
 */
public class CargarPistaService {
    
    private ControlGuardado controlG;
    private BinGuard binarioG;
    private CompiladorLexer lexico;
    private CompiladorParser parser;
    private ControlCrearPista crearPista;
    private ControlDuracion controlD;
    private JTextArea jTextArea1 = new JTextArea();
    
    
    public void cargarPistas(JTable tablePista) {
        this.controlG = new ControlGuardado();
        controlG.obtenerBinario();
        this.binarioG = controlG.getBinarioGuard();
        List<PistaGuardado> pistasGuardadas = binarioG.getPistasGuardadas();

        DefaultTableModel model = (DefaultTableModel) tablePista.getModel();
        int rowCount = model.getRowCount();
        //Eliminar todas las filas del modelo
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        for (PistaGuardado pistaG : pistasGuardadas) {
            model.addRow(new Object[]{pistaG.getNombre(), pistaG.getDuracion()});
        }
    }
    
    public boolean cargarPistaParaModificar(JTable listaPista, boolean codigoCargado,
            JTextPane editorPista ) {
        //TODO Modificar
        //Traer el codigo de la pista y mostrarlo en el edit text
        try {
            DefaultTableModel tm = (DefaultTableModel) listaPista.getModel();
            int rowIndex = listaPista.getSelectedRow();
            String cancion = String.valueOf(tm.getValueAt(rowIndex, 0));
            String codigoPista = getModificar(cancion);
            if (codigoPista.equals("No se encontro la pista") == false) {
                editorPista.setText("");
                editorPista.setText(codigoPista);
                codigoCargado = true;
                JOptionPane.showMessageDialog(null, "El codigo ha sido cargado", "Info", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, codigoPista, "Info", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    private String getModificar(String nombre) {
        String valR = "";
        ControlGuardado controlG = new ControlGuardado();
        controlG.obtenerBinario();
        BinGuard binarioGuard = controlG.getBinarioGuard();
        List<PistaGuardado> pistasGuardadas = binarioGuard.getPistasGuardadas();
        PistaGuardado pistaG = null;
        for (PistaGuardado pistaGuardada : pistasGuardadas) {
            String nomP = pistaGuardada.getNombre();
            if (nomP.equals(nombre)) {
                pistaG = pistaGuardada;
                break;
            }
        }
        if (pistaG == null) {
            valR = "No se encontro la pista";
        } else {
            valR = pistaG.getCodigo();
            System.out.println("Cargado Correctamente");
        }
        return valR;
    }
    
    public void eliminarPista(JTable listaPista) {
        try {
            DefaultTableModel tm = (DefaultTableModel) listaPista.getModel();
            int rowIndex = listaPista.getSelectedRow();
            String cancion = String.valueOf(tm.getValueAt(rowIndex, 0));
            ControlGuardado controlG = new ControlGuardado();
            boolean valR = controlG.eliminarPista(cancion);
            if (valR) {
                //Eliminar pista                 
                JOptionPane.showMessageDialog(null, "Pista eliminada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Pista:" + cancion + "no encontrada", "Info", JOptionPane.ERROR_MESSAGE);
            }
            cargarPistas(listaPista);
        } catch (Exception e) {
        }
    }
    
    public ControlDuracion getSeleccionado(String nombre, List<Nota> notasPistaRep,
            int duraPista) {
        controlG = new ControlGuardado();
        controlG.obtenerBinario();
        BinGuard binarioGuard = controlG.getBinarioGuard();
        List<PistaGuardado> pistasGuardadas = binarioGuard.getPistasGuardadas();
        PistaGuardado pistaG = null;
        for (PistaGuardado pistaGuardada : pistasGuardadas) {
            String nomP = pistaGuardada.getNombre();
            if (nomP.equals(nombre)) {
                pistaG = pistaGuardada;
                break;
            }
        }
        if (pistaG == null) {
            return null;
        } else {
            try {
                String entrada = pistaG.getCodigo();
                List<Pista> pistas = null;
                List<ErrorSintaxis> errores = new ArrayList<>();
                StringReader reader = new StringReader(entrada);
                lexico = new CompiladorLexer(reader);
                parser = new CompiladorParser(lexico);
                try {
                    parser.parse();
                    errores = parser.getErroresCom();
                    pistas = parser.getPistas();
                } catch (Exception ex) {
                    System.out.println("Error irrecuperable");
                    System.out.println("Causa: " + ex.getCause());
                    System.out.println("Causa2: " + ex.toString());
                }
                if (errores.size() > 0) {
                    JFReporteErrores jfReporteErrores = new JFReporteErrores(errores);
                    jfReporteErrores.setVisible(true);
                } else {
                    for (Pista pista : pistas) {
                        this.crearPista = new ControlCrearPista(pista);
                        this.crearPista.initPista(jTextArea1);
                        notasPistaRep = this.crearPista.getNotas();
                        //Obtener Duracion
                        this.controlD = new ControlDuracion(notasPistaRep);
                        this.controlD.generarDuracion();
                        duraPista = controlD.getDuracion();
                        //System.out.println("time:  " + duraPista);

                    }
                    return this.controlD;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }
    
}

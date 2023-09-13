package com.adolfosc.analizadores.service;

import com.adolfosc.analizadores.ErrorSintaxis;
import com.adolfosc.analizadores.compilador.CompiladorLexer;
import com.adolfosc.analizadores.compilador.CompiladorParser;
import com.adolfosc.analizadores.semantico.ControlCrearPista;
import com.adolfosc.analizadores.semantico.ControlSemantico;
import com.adolfosc.conexion_socket.Servidor;
import com.adolfosc.controladores.ControlDuracion;
import com.adolfosc.controladores.ControlGuardado;
import com.adolfosc.modelo.music.Lista;
import com.adolfosc.modelo.music.Nota;
import com.adolfosc.modelo.music.Pista;
import com.adolfosc.ui.JFReporteErrores;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author hectoradolfo
 */
public class CompilarCodigo {
    
    
    public void compilar(String codigo, Lista lista, List<ErrorSintaxis>  errores, 
            boolean codigoCargado, List<Nota> notasPistaRep, List<String> mensajes,
            JTextArea jTextArea1, boolean esLista) {
        try {
            String entrada = codigo;
            List<Pista> pistas = null;
            lista = null;
            StringReader reader = new StringReader(entrada);
            CompiladorLexer lexico = new CompiladorLexer(reader);
            CompiladorParser parser = new CompiladorParser(lexico);
            try {
                parser.parse();
                errores = parser.getErroresCom();
                lista = parser.getLista();
                /*if (!esLista) {
                    jTextArea1.setText("En este editor no se pueden compilar código para lista de reproducción. ");
                    return;
                }*/
                if (lista != null && !esLista) {
                    jTextArea1.setText("En este editor no se pueden compilar código para las pistas.");
                    return;
                }
                pistas = parser.getPistas();
            } catch (Exception ex) {
                Logger.getLogger(CompilarCodigo.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error irrecuperable");
                System.out.println("Causa: " + ex.getCause());
                System.out.println("Causa2: " + ex.toString());
                jTextArea1.setText("se encontraron errores!!. \n" + ex);
            }
            if (errores.size() > 0) {
                System.out.println("se encontraron erroes " + errores.size());
                JFReporteErrores jfReporteErrores = new JFReporteErrores(errores);
                jfReporteErrores.setVisible(true);
            } else {
                if (esLista) {
                    System.out.println("--");
                    //Validar lista y guardar la pista
                    ControlSemantico semantico = new ControlSemantico();
                    boolean valdList = semantico.validarLista(lista, jTextArea1);
                    if (valdList) {
                        //guardar lista
                        ControlGuardado controlG = new ControlGuardado();
                        if (codigoCargado == false) {
                            lista.setCodigo(entrada);
                            controlG.guardarLista(lista);
                            jTextArea1.append("se compilo correctamente!!.");
                            JOptionPane.showMessageDialog(null, "Lista guardada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            //Modificar Lista
                            lista.setCodigo(entrada);
                            modificarLista(lista);
                            jTextArea1.append("se compilo correctamente!!.");
                            JOptionPane.showMessageDialog(null, "Lista modificada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }
                } else {
                    for (Pista pista : pistas) {
                        ControlCrearPista ctrCrearPista = new ControlCrearPista(pista);
                        ctrCrearPista.initPista();
                        notasPistaRep = ctrCrearPista.getNotas();
                        mensajes = ctrCrearPista.getMensajes();
                        System.out.println("----- " + pista.getInstrucciones().size());
                        escribirMensajes(mensajes, jTextArea1);
                        //Obtener Duracion
                        ControlDuracion controlD = new ControlDuracion(notasPistaRep);
                        controlD.generarDuracion();
                        int duracion = controlD.getDuracion();
                        //Guardar Pista
                        if (codigoCargado == false) {
                            guardarPistaEnBinario(entrada, pista.getNombre(), duracion);
                            jTextArea1.append("se compilo correctamente!!.");
                            JOptionPane.showMessageDialog(null, "Pista guardada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            modificarPistaEnBinario(entrada, pista.getNombre(), duracion);
                            codigoCargado = false;
                            jTextArea1.append("se compilo correctamente!!.");
                            JOptionPane.showMessageDialog(null, "Pista modificada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                        System.out.println("Pista: " + pista.getNombre());
                        List<String> ext = pista.getExtensiones();
                        for (String string : ext) {
                            System.out.println("Extiende: " + string);
                        }
                    }
                }
                System.out.println("Compilado Correctamente");
            }
        } catch (Exception e) {
             Logger.getLogger(CompilarCodigo.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e);
        }
    }
    
    private void modificarLista(Lista lista) {
        ControlGuardado controlG = new ControlGuardado();
        controlG.modificarLista(lista);
    }
    
    private void escribirMensajes(List<String> mensajes, JTextArea jTextArea1) {
        String men = "";
        System.out.println("hola mundo des mensajesass");
        for (String mensaje : mensajes) {
            System.out.println("....." + mensaje);
            men += mensaje + "\n";
        }
        jTextArea1.append(men);
    }
    
    private void guardarPistaEnBinario(String codigo, String nombre, int duracion) {
        ControlGuardado controlG = new ControlGuardado();
        controlG.guardarPista(codigo, nombre, duracion);
    }

    private void modificarPistaEnBinario(String codigo, String nombre, int duracion) {
        ControlGuardado controlG = new ControlGuardado();
        controlG.modificarPista(codigo, nombre, duracion);
    }
    
}

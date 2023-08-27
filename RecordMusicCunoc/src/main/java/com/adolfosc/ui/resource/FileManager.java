package com.adolfosc.ui.resource;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author hectoradolfo
 */
public class FileManager {

    private String nuevoPath;
    private boolean opcion;

    /**
     *
     * @param fileChooser
     * @param pathActual
     * @param txtAreaCode
     */
    public void openFile(JFileChooser fileChooser, String pathActual, JTextArea txtAreaCode) {
        this.nuevoPath = "";
        this.opcion = true;
        try {
            if (!pathActual.equals("")) {
                int option = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios?");

                switch (option) {
                    case JOptionPane.YES_OPTION:     //si elige que si
                        saveChangedFile(pathActual, txtAreaCode.getText());
                        break;//se guarda el archivo

                    case JOptionPane.CANCEL_OPTION:  //si elige cancelar
                        System.out.println("no se guardo nada");
                        this.nuevoPath = pathActual;
                        opcion = false;
                        break;
                    //se cancela esta operación
                    //en otro caso se continúa con la operación y no se guarda el documento actual
                }
            }

            if (isOpcion()) {

                fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivo de texto", "txt, *"));
                //fileChooser.setAcceptAllFileFilterUsed(false);
                int seleccion = fileChooser.showOpenDialog(fileChooser);

                try {
                    if (seleccion == APPROVE_OPTION) {
                        nuevoPath = fileChooser.getSelectedFile().getPath();
                        System.out.println("es " + nuevoPath);

                        // lectura para texto
                        try (FileReader fileReader = new FileReader(nuevoPath);
                                BufferedReader reader = new BufferedReader(fileReader)) {
                            String contenido = "";
                            String linea = reader.readLine();
                            while (linea != null) {
                                contenido = contenido + linea + "\n";
                                linea = reader.readLine();
                            }
                            txtAreaCode.setText(contenido);
                        } catch (Exception e) {
                            System.out.println("Excepcion con archivo ");
                        }

                    } else {
                        this.nuevoPath = pathActual;
                        this.opcion = false;
                    }

                } catch (Exception e) {
                }
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveChangedFile(String path, String textoIndigo) {
        FileWriter save = null;
        try (PrintWriter printer = new PrintWriter(path);) {
            printer.print(textoIndigo);
        } catch (FileNotFoundException ex) {
            System.out.println("error: " + ex);
        }
    }

    public String saveFile(String textoIndigo) {
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt", "*");
        filechooser.setFileFilter(filter);
        filechooser.showSaveDialog(filechooser);
        File guarda = filechooser.getSelectedFile();
        FileWriter save = null;
        String val = "";

        if (guarda != null) {
            val = guarda.toString();
            try (PrintWriter printer = new PrintWriter(guarda);) {
                printer.print(textoIndigo);
            } catch (FileNotFoundException ex) {
                System.out.println("error: " + ex);
            }
        }

        return val;
    }

    /**
     * @return the nuevoPath
     */
    public String getNuevoPath() {
        return nuevoPath;
    }

    /**
     * @return the opcion
     */
    public boolean isOpcion() {
        return opcion;
    }

    
}

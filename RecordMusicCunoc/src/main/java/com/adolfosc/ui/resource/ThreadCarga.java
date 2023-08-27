package com.adolfosc.ui.resource;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;

/**
 *
 * @author hectoradolfo
 */
public class ThreadCarga extends Thread {

    private Thread hilo;
    private Object objeto = new Object();
    private boolean pideParar = false;
    //JTextField texto;
    private JProgressBar barra;
    private int min;
    private int max;
    private JButton btnPlay;

    public ThreadCarga(JProgressBar progres, int min, int max,
            JButton btnPlay) {
        this.barra = progres;
        this.min = min;
        this.max = max;
        this.btnPlay = btnPlay;
    }

    @Override
    public void run() {

        barra.setValue(min);
        barra.setMinimum(min);
        barra.setMaximum(max);

        for (int i = min; i <= max; i++) {
            barra.setValue(i);
            try {
                hilo.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadCarga.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        barra.setValue(0);
        ImageIcon open3 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/play.png");
        btnPlay.setIcon(open3);
        //hilo = null;
    }

}

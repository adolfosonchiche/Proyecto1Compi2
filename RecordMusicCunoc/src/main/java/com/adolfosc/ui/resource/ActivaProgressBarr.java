package com.adolfosc.ui.resource;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;

/**
 *
 * @author hectoradolfo
 */
public class ActivaProgressBarr {

    private ThreadCarga hilo;
    private int opcion;
    private int min, max;
    private int activo = 0;

    public ActivaProgressBarr(int opcion, int min, int max) {
        this.opcion = opcion;
        this.min = min;
        this.max = max;
    }

    public void startProgress(JProgressBar progressBar, JButton btnPlay) {

        //Runnable runnable = new Runnable() {
        //public void run() {
        switch (getOpcion()) {
            case 0:
                if (activo != 0) {
                    hilo.stop();
                    this.activo = 1;
                }
                ImageIcon open = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/pause.png");
                btnPlay.setIcon(open);
                hilo = new ThreadCarga(progressBar, getMin(), getMax(), btnPlay);
                hilo.start();
                break;

            case 1:
                ImageIcon open1 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/play.png");
                btnPlay.setIcon(open1);
                hilo.suspend();
                //hilo.stop();
                break;
            case 2:
                ImageIcon open2 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/pause.png");
                btnPlay.setIcon(open2);
                hilo.resume();
                break;

            case 3:
                ImageIcon open3 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/play.png");
                btnPlay.setIcon(open3);
                hilo.stop();
                progressBar.setValue(0);
                //hilo.stop();
                break;
        }
    }

    /**
     * @return the opcion
     */
    public int getOpcion() {
        return opcion;
    }

    /**
     * @param opcion the opcion to set
     */
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}

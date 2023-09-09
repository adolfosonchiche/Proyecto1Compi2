package com.adolfosc.ui.resource;

import com.adolfosc.controladores.ControlReproducir;
import com.adolfosc.modelo.music.Nota;
import com.adolfosc.ui.RecordMusicUi;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;

/**
 *
 * @author hectoradolfo
 */
public class ActivaProgressBarr {

    private ThreadCarga hilo;
    private Thread hilo2;
    private int opcion;
    private int min, max;
    private int activo = 0;
    private ControlReproducir controlR1;

    public ActivaProgressBarr(int opcion, int min, int max) {
        this.opcion = opcion;
        this.min = min;
        this.max = max;
    }

    public void startProgress(JProgressBar progressBar, JButton btnPlay,
            List<Nota> notas) {

        //Runnable runnable = new Runnable() {
        //public void run() {
        try {
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
                    System.out.println("cantidad de notas " + notas.size());
                    ReprodPista repPista = null;
                    controlR1 = new ControlReproducir(notas);
                    repPista = new ReprodPista();
                    hilo2 = new Thread(repPista);
                    hilo2.start();
                    break;

                case 1:
                    ImageIcon open1 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/play.png");
                    btnPlay.setIcon(open1);
                    hilo.suspend();
                    hilo2.suspend();
                    //hilo.stop();
                    break;
                case 2:
                    ImageIcon open2 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/pause.png");
                    btnPlay.setIcon(open2);
                    hilo.resume();
                    hilo2.resume();
                    break;

                case 3:
                    ImageIcon open3 = new ImageIcon("src/main/java/com/adolfosc/resource/imagen/play.png");
                    btnPlay.setIcon(open3);
                    hilo.stop();
                    hilo2.stop();
                    progressBar.setValue(0);
                    //hilo.stop();
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
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

    class ReprodPista implements Runnable {

        private Sequencer sequencer;

        @Override
        public void run() {
            try {
                this.sequencer = MidiSystem.getSequencer();
                sequencer.open();
                Sequence sequence = null;
                try {
                    sequence = controlR1.crearSecuencia();
                } catch (Throwable ex) {
                    Logger.getLogger(ControlReproducir.class.getName()).log(Level.SEVERE, null, ex);
                }
                sequencer.setSequence(sequence);
                sequencer.start();
                while (sequencer.isRunning()) {
                    Thread.sleep(1000);
                }
                sequencer.stop();
            } catch (Exception e) {

            }
        }

    }

}

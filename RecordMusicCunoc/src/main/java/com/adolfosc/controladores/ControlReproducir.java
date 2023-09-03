package com.adolfosc.controladores;

import com.adolfosc.modelo.music.Nota;
import com.adolfosc.modelo.music.Tiempo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author hectoradolfo
 */
public class ControlReproducir {
    
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private List<Nota> notas;
    private List<Tiempo> tiempos;

    public ControlReproducir(List<Nota> notas) {
        this.notas = notas;
        this.tiempos = new ArrayList<>();
    }

    
    private Sequencer sequencer;
    public void reproducir() {
        try {
            this.sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = null;
            try {
                sequence = crearSecuencia();
            } catch (Throwable ex) {
                Logger.getLogger(ControlReproducir.class.getName()).log(Level.SEVERE, null, ex);
            }
            sequencer.setSequence(sequence);
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(1000L);
            }
            sequencer.stop();
        } catch (Exception e) {

        }
    }
    
    public void parar(){
        if (sequencer != null) {
            sequencer.stop();
        }
    }

    public Sequence crearSecuencia() {
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 1000);
            sequence.createTrack();
            Track track = sequence.createTrack();
            for (Nota nota : notas) {
                int canal = nota.getCanal();
                Tiempo tiempoR = obtenerTiempoSegunCanal(canal);
                if (tiempoR == null) {
                    Tiempo tiempoN = new Tiempo(canal);
                    this.tiempos.add(tiempoN);
                    tiempoR = tiempoN;
                }
                int numNota = nota.getNota();
                int octava = nota.getOctava();
                if (numNota != -1) {
                    track.add(createNoteOnEvent(numNota, tiempoR.getTiempo(), octava));
                    track.add(createNoteOffEvent(numNota, (tiempoR.getTiempo() + nota.getTiempo()), octava));
                }
                tiempoR.setTiempo(tiempoR.getTiempo() + nota.getTiempo());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return sequence;
    }

    private Tiempo obtenerTiempoSegunCanal(int canal) {
        for (Tiempo tiempo : tiempos) {
            if (tiempo.getCanal() == canal) {
                return tiempo;
            }
        }
        return null;
    }

    private MidiEvent createNoteOnEvent(int nKey, long lTick, int canal) {
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(ShortMessage.NOTE_ON, canal, nKey, 100);
            MidiEvent event = new MidiEvent(a, lTick);
            return event;
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(ControlReproducir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private MidiEvent createNoteOffEvent(int nKey, long lTick, int canal) {
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(ShortMessage.NOTE_OFF, canal, nKey, 100);
            MidiEvent event = new MidiEvent(a, lTick);
            return event;
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(ControlReproducir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}

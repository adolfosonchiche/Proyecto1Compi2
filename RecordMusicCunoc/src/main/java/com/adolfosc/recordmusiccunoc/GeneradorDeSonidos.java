/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.adolfosc.recordmusiccunoc;

import javax.sound.sampled.*;

/**
 *
 * @author hectoradolfo
 */
public class GeneradorDeSonidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Define los parámetros del sonido
            float sampleRate = 44100.0f; // Tasa de muestreo
            int duration = 3; // Duración en segundos
            double frequency = 440.0; // Frecuencia (nota A4)

            // Crea un arreglo de bytes para almacenar el sonido generado
            byte[] buffer = new byte[2 * duration * (int) sampleRate];

            // Rellena el arreglo de bytes con datos de audio (sinusoide simple)
            for (int i = 0; i < buffer.length; i += 2) {
                double angle = 2.0 * Math.PI * frequency * i / sampleRate;
                short sample = (short) (Short.MAX_VALUE * Math.sin(angle));
                buffer[i] = (byte) (sample & 0x00FF);
                buffer[i + 1] = (byte) ((sample & 0xFF00) >> 8);
            }

            // Crea una línea de reproducción de audio
            AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();

            // Reproduce el sonido generado
            line.write(buffer, 0, buffer.length);
            line.drain();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

package com.adolfosc.controlador

import android.media.midi.MidiReceiver
import android.os.Handler
import android.os.Looper

class MidiPlayer(private val midiReceiver: MidiReceiver) {

    private val handler = Handler(Looper.getMainLooper())

    // Función para convertir una nota en evento MIDI
    private fun sendNoteEvent(note: String, velocity: Int, duration: Int) {
        val noteNumbers = mapOf(
            "do" to 60, "re" to 62, "mi" to 64, "fa" to 65,
            "sol" to 67, "la" to 69, "si" to 71 // Mapea las notas a números MIDI
        )

        val noteNumber = noteNumbers[note] ?: return

        val timestamp = System.nanoTime()
        val noteOn = byteArrayOf(0x90.toByte(), noteNumber.toByte(), velocity.toByte())
        val noteOff = byteArrayOf(0x80.toByte(), noteNumber.toByte(), 0x00)

        // Envía el evento "Note On"
        midiReceiver.send(noteOn, 0, noteOn.size, timestamp)

        // Programa un evento "Note Off" después del tiempo de duración
        handler.postDelayed({
            midiReceiver.send(noteOff, 0, noteOff.size, timestamp)
        }, duration.toLong())
    }

    // Función para reproducir la lista de notas
    fun playNotes(noteList: List<Pair<String, Int>>, velocity: Int) {
        for ((note, duration) in noteList) {
            sendNoteEvent(note, velocity, duration)
        }
    }
}

package com.adolfosc.recordmusic

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.midi.MidiOutputPort
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adolfosc.analizadores.ErrorSintaxis
import com.adolfosc.analizadores.conexion.ConexionLexer
import com.adolfosc.analizadores.conexion.ConexionParser
import com.adolfosc.modelo.conexion.Respuesta
import com.adolfosc.modelo.music.Nota
import com.google.android.material.snackbar.Snackbar
import java.io.StringReader


class ActivityTrackPlayer : AppCompatActivity() {

    private lateinit var edPista: TextView
    private lateinit var imgBtnPlay: ImageButton
    private var nombrePista:String = ""
    private var mensajeRec: String = ""
    private var listaNotas:ArrayList<Nota> = ArrayList<Nota>()
    //private lateinit var midiManager: MidiManager
    private var outputPort: MidiOutputPort? = null
    var vector = arrayOfNulls<MediaPlayer>(5)
    var listaMediaPlayer = mutableListOf<MediaPlayer>()
    var tiempos = mutableListOf<Int>()
    var  NOTE_NAMES =
        arrayOf("do", "do#", "re", "re#", "mi", "fa", "fa#", "sol", "sol#", "la", "la#", "si", "esperar")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_player)

        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setIcon(R.drawable.tocar)

        this.edPista = findViewById<View>(R.id.tvNombrePista) as TextView
        this.imgBtnPlay = findViewById<View>(R.id.imgBtnPlayPista) as ImageButton
        var mensaje = intent.getStringExtra("mensaje") //pista
        var nombrePista = intent.getStringExtra("pista") //pista

        this.edPista.text = nombrePista
        if (mensaje != null) {
            mensajeRec =mensaje
        }
        compilar()
    }




    private fun compilar(){
        try {
            var respuesta = Respuesta()
            var errores:List<ErrorSintaxis> = ArrayList()
            var reader = StringReader(mensajeRec)
            var lexer = ConexionLexer(reader)
            var parser = ConexionParser(lexer)

            try {
                parser.parse()
                errores = parser.errorSintaxis
                respuesta = parser.respuesta
            }catch (ex:Exception){

            }
            if (errores.isEmpty() ){
                if (respuesta != null){
                    validarRespuesta(respuesta)
                }
            }else{
                //Hay errores
            }
        }catch (e :Exception){
            println(e)
        }
    }



    private fun validarRespuesta(respuesta: Respuesta){

        var dos = MediaPlayer.create(this, R.raw.dodo)
        var fas = MediaPlayer.create(this, R.raw.fa)
        var fafas = MediaPlayer.create(this, R.raw.fafa)
        var la = MediaPlayer.create(this, R.raw.la)
        var mis = MediaPlayer.create(this, R.raw.mi)
        var res = MediaPlayer.create(this, R.raw.re)
        var reres = MediaPlayer.create(this, R.raw.rere)
        var sis = MediaPlayer.create(this, R.raw.si)
        var sols = MediaPlayer.create(this, R.raw.sol)
        var solsols = MediaPlayer.create(this, R.raw.solsol)
        var espera = MediaPlayer.create(this, R.raw.espera)


        listaNotas = respuesta.getNotas() as ArrayList<Nota>
        tiempos.clear();
        listaMediaPlayer.clear();

        for (n in listaNotas){
            var tipo = n.getNombre()?.replace("\"","")
            when (tipo) {
                NOTE_NAMES.get(0), NOTE_NAMES.get(1) -> {
                    listaMediaPlayer.add(dos)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(2) -> {
                    listaMediaPlayer.add(res)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(3) -> {
                    listaMediaPlayer.add(reres)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(4) -> {
                    listaMediaPlayer.add(mis)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(5) -> {
                    listaMediaPlayer.add(fas)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(6) -> {
                    listaMediaPlayer.add(fafas)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(7) -> {
                    listaMediaPlayer.add(sols)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(8) -> {
                    listaMediaPlayer.add(solsols)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(9), NOTE_NAMES.get(10) -> {
                    listaMediaPlayer.add(la)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(11) -> {
                    listaMediaPlayer.add(sis)
                    tiempos.add(n.getTiempo())
                }
                NOTE_NAMES.get(12) -> {
                    listaMediaPlayer.add(espera)
                    tiempos.add(n.getTiempo())
                }
                else -> throw AssertionError()
            }


            var nomb = n.getNombre()
            var num = n.getTiempo()
            println("Nota: $nomb, NumN: $num")
        }
        /*
        * listaMediaPlayer.add(sols)
        listaMediaPlayer.add(sis)
        listaMediaPlayer.add(reres)*/
        println("Notas recibidas")
    }



    var position = 0;

    fun reproducirPista(view: View?) {

        if (view != null) {
            Snackbar.make(view, "pista reproducida", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (listaMediaPlayer.get(position).isPlaying) {
            Toast.makeText(this, "pausa", Toast.LENGTH_LONG).show()
            this.imgBtnPlay.setImageResource(R.drawable.play)
            listaMediaPlayer.get(position).pause()
        } else {
            Toast.makeText(this, "reproduciendo", Toast.LENGTH_LONG).show()
            this.imgBtnPlay.setImageResource(R.drawable.pause)

            for ((index, nota) in listaMediaPlayer.withIndex()) {
                position = index;
                nota.isLooping = true
                nota.start()
               // nota.seekTo()
                Thread.sleep(tiempos[index].toLong())

                nota.stop()
               // nota.seekTo(0) // Reiniciar la reproducción
            }

            //listaMediaPlayer.get(position).start();
           // this.listaMediaPlayer.get(position).setNextMediaPlayer(listaMediaPlayer.get(position+1))
        }


        position = 0;


        //notas.
        // Reproducir las notas en secuencia
        /*for ((index, nota) in listaMediaPlayer.withIndex()) {
            nota.start()

            Thread.sleep(tiempos[index].toLong())
           // nota.pause()
            nota.seekTo(0) // Reiniciar la reproducción
        }*/
    }

    fun stopPista(view: View?) {
        if (listaMediaPlayer.get(position) != null) {
            listaMediaPlayer.get(position).stop();
            this.imgBtnPlay.setImageResource(R.drawable.play)

            position = 0
            this.compilar()
            Toast.makeText(this, "stop", Toast.LENGTH_LONG).show()
        }
    }
}





/*internal class ReprodPista : Runnable {
    private var sequencer: Sequencer? = null
    override fun run() {
        try {
            sequencer = MidiSystem.getSequencer()
            sequencer.open()
            var sequence: Sequence<*>? = null
            try {
                sequence = controlR1.crearSecuencia()
            } catch (ex: Throwable) {
                Logger.getLogger(ActivityTrackPlayer::class.java.getName())
                    .log(Level.SEVERE, null, ex)
            }
            sequencer.setSequence(sequence)
            sequencer.start()
            while (sequencer.isRunning()) {
                Thread.sleep(1000)
            }
            sequencer.stop()
        } catch (e: java.lang.Exception) {
        }
    }
}*/


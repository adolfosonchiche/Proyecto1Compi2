package com.adolfosc.recordmusic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.adolfosc.conexion_socket.service.CompilarService
import com.adolfosc.modelo.conexion.CantidadRespuesta
import com.google.android.material.snackbar.Snackbar
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class ActivityPlayList : AppCompatActivity(), Runnable {

    private var mensaje: String = ""
    private var nomLista : String? = ""
    private var mensajeRecivid : String? = null
    private var nombreListas: ArrayList<String>? = null
    private var compiladorService : CompilarService?  = null
    private var etNombreLista: EditText? = null
    private var listaPlayList: ListView? = null
    private var datos: List<CantidadRespuesta>? = null

    private var host = "192.168.1.115" //IP DE LA COMPUTADORA O SERVIDOR 192.168.1.115
    private val port = 5000
    private var thread: Thread? = null
    private var inp: DataInputStream? = null
    private var out: DataOutputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setIcon(R.drawable.record)
        setContentView(R.layout.activity_play_list)
        this.nombreListas = ArrayList<String>()
        this.listaPlayList = findViewById<View>(R.id.lvPlayList) as ListView

        //obtener datos del activity anterior
        var mensaje = intent.getStringExtra("mensaje")
        this.host = intent.getStringExtra("host").toString()
        println(this.host)
        mensajeRecivid = mensaje
        mostrarListas()

        var adapterList: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.list_view_music, this.nombreListas!!)
        listaPlayList!!.setAdapter(adapterList)

        //funcionalidad del listVIew cuando se selecciona un nombre u opcion
        listaPlayList!!.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
                nomLista = nombreListas!!.get(position)
                irListaPistas(view, nomLista!!, position)
        })
    }

    private fun mostrarListas(){
        println("ingresando al metodo para llenar pistas")
        compiladorService = CompilarService()
        println("compilando mensaje recibido " + mensajeRecivid)
        datos = compiladorService!!.compilarMensaje(mensajeRecivid)
        if (datos != null) {
            for(dato in datos!!){
                var nomb = dato.getNombre()
                var cantidad = dato.getCantidad()
                var nom = "Lista: $nomb - No. pista: $cantidad"
                //Cantidad es numero de canciones
                //this.nombreListas.push
                nombreListas!!.add(nom)
                println("Nombre: $nomb")
                println("Cantidad: $cantidad")
            }
        }
        if (nombreListas!!.isEmpty()) {
            nombreListas!!.add("no existen listas de reproduccion")
        }
        println("terminando de llenar listas")
    }

    fun solicitarPista(view: View?) {
        this.etNombreLista = findViewById(R.id.etNombreListaLista)
        var textoEntrada = this.etNombreLista!!.text.toString()
        if (textoEntrada == null || textoEntrada.isEmpty()) {
            if (view != null) {
                Snackbar.make(view, "Tienes que escribir el nombre de una lista", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        } else {
            this.irListaPistas(view!!, textoEntrada, -1)
        }

    }

    fun irListaPistas(view: View, nomLista: String, position: Int) {
        var text = "";
        if (position > -1) {
            text = datos!!.get(position).getNombre().toString();
        } else {
            text = "\"$nomLista\""
        }
        mensaje = "<solicitud> \n <tipo> lista </tipo> <nombre>$text</nombre> \n </solicitud>"
        thread =  Thread(this);
        this.thread?.start()
    }

    override fun run() {
        try {
            var valorEnviar: String = mensaje
            var sc = Socket(host, port)

            inp = DataInputStream(sc!!.getInputStream())
            out = DataOutputStream(sc!!.getOutputStream())

            //Envio un mensaje al servidor
            out!!.writeUTF(valorEnviar)

            //Recibo el mensaje del servidor
            var mensajeRe: String = inp!!.readUTF()
            sc.close()

            val intent = Intent(this, ActivityTrackList::class.java)
            intent.putExtra("mensaje", mensajeRe)
            intent.putExtra("host", this.host)
            //this.thread!!.stop()
            //finish()
            startActivity(intent)
        } catch (e: IOException) {
            //Toast.makeText(this, "error en conexion Conexion$e", Toast.LENGTH_LONG).show()
            println("no hay red disponible....$e")
        }
        // Inicia la segunda actividad cuando se produce un evento (por ejemplo, clic en un botón)
    }
}
package com.adolfosc.recordmusic

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.adolfosc.conexion_socket.Cliente
import com.adolfosc.recordmusic.databinding.ActivityMainBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket


class MainActivity : AppCompatActivity(), Runnable {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var cliente : Cliente?  = null

    private val host = "192.168.1.115" //IP DE LA COMPUTADORA O SERVIDOR 192.168.1.115
    private val port = 5000
    private var thread: Thread? = null
    private var inp: DataInputStream? = null
    private var out: DataOutputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setIcon(R.drawable.record)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun conectar(view: View?) {


       /* this.cliente = Cliente()
        CoroutineScope(Dispatchers.IO).launch {
            var mensajeRecivid = cliente!!.run(valorEnviar)

        }*/
        thread =  Thread(this);
        this.thread?.start()


    }

    fun newTrack(view: View?) {

        /*this.cliente = Cliente()
        CoroutineScope(Dispatchers.IO).launch {
            cliente!!.run()
        }*/
        val intent = Intent(this, ActivityNewPista::class.java)

        startActivity(intent)
        // Inicia la segunda actividad cuando se produce un evento (por ejemplo, clic en un botón)

    }


    override fun run() {
        try {
            var textoEntrada = ""
            var valorEnviar:String = "<solicitud> \n <tipo> lista </tipo> <nombre>\"$textoEntrada\"</nombre> \n </solicitud>"
            var sc = Socket(host, port)

            inp = DataInputStream(sc!!.getInputStream())
            out = DataOutputStream(sc!!.getOutputStream())


            //Envio un mensaje al servidor
            out!!.writeUTF(valorEnviar)

            //Recibo el mensaje del servidor
            var mensajeRe: String = inp!!.readUTF()
            sc.close()

            val intent = Intent(this, ActivityPlayList::class.java)
            intent.putExtra("mensaje", mensajeRe)
            //finish()
            startActivity(intent)
        } catch (e: IOException) {
            //Toast.makeText(this, "error en conexion Conexion$e", Toast.LENGTH_LONG).show()
            println("no hay red disponible....$e")
        }
        // Inicia la segunda actividad cuando se produce un evento (por ejemplo, clic en un botón)
    }
}
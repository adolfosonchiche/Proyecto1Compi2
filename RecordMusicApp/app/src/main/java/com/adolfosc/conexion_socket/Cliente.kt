package com.adolfosc.conexion_socket

import android.widget.Toast
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.logging.Level
import java.util.logging.Logger


class Cliente {

    private val host = "192.168.1.115" //IP DE LA COMPUTADORA O SERVIDOR 192.168.1.115

    private val port = 5000
    private val portRecived = 5001
    private val sc: Socket? = null

    fun run() {
        val inp: DataInputStream
        val out: DataOutputStream
        var valor = "te amo mucho"
        println("holamundo")
        try {
            //Creo el socket para conectarme con el cliente
            val sc = Socket(host, port)
            inp = DataInputStream(sc.getInputStream())
            out = DataOutputStream(sc.getOutputStream())

            //Envio un mensaje al cliente
            //out.writeUTF("¡Hola mundo desde el cliente!")
            out.writeUTF(valor)

            //Recibo el mensaje del servidor
            val mensaje: String = inp.readUTF()
            //Compilar Mensaje
            println(mensaje)
            sc.close()
        } catch (ex: IOException) {
            println("error \n" + ex)
            //Logger.getLogger(MainActivity::class.java.getName()).log(Level.SEVERE, null, ex)
        }
    }
}
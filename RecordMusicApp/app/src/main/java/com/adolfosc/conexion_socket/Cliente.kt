package com.adolfosc.conexion_socket

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.logging.Level
import java.util.logging.Logger


class Cliente: Runnable {

    private val host = "192.168.1.115" //IP DE LA COMPUTADORA O SERVIDOR 192.168.1.115

    private val port = 5000
    private val portRecived = 5001
    private var sc: Socket? = null
    private var thread: Thread? = null
    private var servidor: ServerSocket? = null
    private var inp: DataInputStream? = null
    private var out: DataOutputStream? = null

    constructor() {
        thread =  Thread(this);
    }


    fun iniciarServidor() {
        this.thread?.start()
    }

    override fun run() {
        // this.server.startServer(txtaLog, typeSwitch);
        try {
            //Creamos el socket del servidor
            servidor = ServerSocket(port)
            println("Servidor iniciado")

            //Siempre estara escuchando peticiones
            //while (true) {

                //Espero a que un cliente se conecte
                sc = servidor!!.accept()
                println("Cliente conectado")
                inp = DataInputStream(sc!!.getInputStream())
                out = DataOutputStream(sc!!.getOutputStream())

                out!!.writeUTF("¡Hola mundo desde el cliente!")

                //Recibo el mensaje del servidor

                //Recibo el mensaje del servidor
                val mensaje: String = inp!!.readUTF()

                println(mensaje)

                sc!!.close()
                println("Cliente desconectado")
          //  }
        } catch (ex: IOException) {
        }
    }














/*
    fun run(mensajeSend : String): String? {
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
            out.writeUTF(mensajeSend)

            //Recibo el mensaje del servidor
            val mensaje: String = inp.readUTF()
            //Compilar Mensaje
            println(mensaje)
            sc.close()
            return mensaje;
        } catch (ex: IOException) {
            println("error \n" + ex)
            //Logger.getLogger(MainActivity::class.java.getName()).log(Level.SEVERE, null, ex)
        }
        return null;
    }*/
}
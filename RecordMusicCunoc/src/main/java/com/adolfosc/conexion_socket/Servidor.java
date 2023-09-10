package com.adolfosc.conexion_socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hectoradolfo
 */
public class Servidor implements Runnable {

    private String host = "localhost";
    private int port = 5000;
    private int portSend = 5001;
    private Thread thread;
    private ServerSocket servidor;
    private Socket sc;
    private DataInputStream in;
    private DataOutputStream out;

    public Servidor() {
        thread = new Thread(this);
    }
    
    public void iniciarServidor() {
        this.thread.start();
    }

    @Override
    public void run() {
        // this.server.startServer(txtaLog, typeSwitch);

        try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(port);
            System.out.println("Servidor iniciado");

            //Siempre estara escuchando peticiones
            while (true) {

                //Espero a que un cliente se conecte
                sc = servidor.accept();

                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                //Leo el mensaje que me envia
                String mensaje = in.readUTF();
                //Leer mensaje y validarlo 
                /*ControlServidor controlServ = new ControlServidor(mensaje);
                controlServ.compilarMensaje();
                String respuesta = controlServ.getRespuesta();*/
                
                System.out.println(mensaje);

                //Le envio un mensaje
                //Enviar respuesta
//                out.writeUTF("¡Hola mundo desde el servidor!");
                out.writeUTF("respuesta del servidor");

                //Cierro el socket
                sc.close();
                System.out.println("Cliente desconectado");

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

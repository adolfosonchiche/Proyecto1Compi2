package com.adolfosc.conexion_socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author hectoradolfo
 */
public class Servidor implements Runnable {

    private String host = "localhost";
    private int port = 5000;
    private int portSend = 5001;
    private Thread thread;

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
            ServerSocket served = new ServerSocket(port);
            System.out.println("servidor iniciado");
            String ip, message;

            while (true) {
                try (Socket socket = served.accept()) {
                    ObjectInputStream dataIn = new ObjectInputStream(socket.getInputStream());
                    Object as = dataIn.readObject();

                    Trama trama = (Trama) as;
                    message = trama.getMessage();
                    ip = trama.getIp();
                    System.out.println("mensaje recivido: \n" + message);

                    try ( //socket para enviar al destinatario
                            Socket sendData = new Socket(ip, portSend)) {
                        ObjectOutputStream tramaReenvio = new ObjectOutputStream(sendData.getOutputStream());
                        tramaReenvio.writeObject(message);
                    }

                }

            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("error \n" + ex);
        }
    }

}

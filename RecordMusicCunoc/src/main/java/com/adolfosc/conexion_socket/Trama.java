package com.adolfosc.conexion_socket;

import java.io.Serializable;

/**
 *
 * @author hectoradolfo
 */
public class Trama implements Serializable{
    
    private String ip;
    private String message;

    public Trama(String ip, String message) {
        this.ip = ip;
        this.message = message;
    }

    public Trama() {
    }

    public String getIp() {
        return ip;
    }

    public String getMessage() {
        return message;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMessage(String message) {
        this.message = message;
    }
        
}

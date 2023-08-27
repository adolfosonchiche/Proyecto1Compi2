package com.adolfosc.modelo.conexion;

import com.adolfosc.modelo.music.Nota;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Respuesta {
    
    private String tipo;
    private String nombV;
    private List<CantidadRespuesta> valores;
    private List<Nota> notas;

    public Respuesta() {
        this.valores = new ArrayList<>();
        this.notas = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombV() {
        return nombV;
    }

    public void setNombV(String nombV) {
        this.nombV = nombV;
    }

    public List<CantidadRespuesta> getValores() {
        return valores;
    }

    public void setValores(List<CantidadRespuesta> valores) {
        this.valores = valores;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
    
    public void insertarValor(CantidadRespuesta valor){
        this.valores.add(valor);
    }
    
    public void insertarNota(Nota nota){
        this.notas.add(nota);
    }
}

package com.adolfosc.controladores;

import com.adolfosc.modelo.music.BinGuard;
import com.adolfosc.modelo.music.Lista;
import com.adolfosc.modelo.music.PistaGuardado;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class ControlGuardado {

    private BinGuard binarioGuard;

    public ControlGuardado() {
        binarioGuard = new BinGuard();
    }

    public void guardarPista(String codigo, String nombre, int duracion) {
        obtenerBinario();
        if (binarioGuard == null) {
            binarioGuard = new BinGuard();
        }
        PistaGuardado pista = new PistaGuardado(codigo, nombre, duracion);
        binarioGuard.insertarPista(pista);
        guardarBinario(nombre);
    }

    public void obtenerBinario() {
        try {
            ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream("objetos.dat"));
            binarioGuard = (BinGuard) leyendoFichero.readObject();
            leyendoFichero.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void guardarBinario(String nombre) {
        try {
            ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("objetos.dat"));
            escribiendoFichero.writeObject(this.binarioGuard);
            escribiendoFichero.close();
        } catch (Exception e) {
            System.out.println( "no se pudo generar el archivo: error --> \n"+e);
        }
    }

    public BinGuard getBinarioGuard() {
        return binarioGuard;
    }

    public void setBinarioGuard(BinGuard binarioGuard) {
        this.binarioGuard = binarioGuard;
    }

    public void guardarLista(Lista lista) {
        obtenerBinario();
        if (binarioGuard == null) {
            binarioGuard = new BinGuard();
        }
        this.binarioGuard.insertarLista(lista);
        guardarBinario(lista.getNombre());
    }

    public void modificarPista(String codigo, String nombre, int duracion) {
        obtenerBinario();
        if (binarioGuard == null) {
            binarioGuard = new BinGuard();
        }
        List<PistaGuardado> pistas = binarioGuard.getPistasGuardadas();
        for (PistaGuardado pista : pistas) {
            String nom1 = pista.getNombre().replace("\"", "");
            String nom2 = nombre.replace("\"", "");
            if (nom1.equals(nom2)) {
                pista.setCodigo(codigo);
                pista.setDuracion(duracion);
                break;
            }
        }
        guardarBinario(nombre);
    }

    public void modificarLista(Lista lista) {
        obtenerBinario();
        if (binarioGuard == null) {
            binarioGuard = new BinGuard();
        }
        List<Lista> listas = binarioGuard.getListasGuardadas();
        for (Lista lista1 : listas) {
            String nom1 = lista.getNombre().replace("\"", "");
            String nom2 = lista1.getNombre().replace("\"", "");
            if (nom1.equals(nom2)) {
                lista1.setCircular(lista.isCircular());
                lista1.setRandom(lista.isRandom());
                lista1.setPistas(lista.getPistas());
                lista1.setCodigo(lista.getCodigo());
                break;
            }
        }
        guardarBinario(lista.getNombre());
    }

    public boolean eliminarPista(String nombre) {
        obtenerBinario();
        boolean boolR = this.binarioGuard.eliminarPista(nombre);
        guardarBinario(nombre);
        return boolR;
    }

    public boolean eliminarLista(String nombre) {
        obtenerBinario();
        boolean boolR = this.binarioGuard.eliminarLista(nombre);
        guardarBinario(nombre);
        return boolR;
    }

}

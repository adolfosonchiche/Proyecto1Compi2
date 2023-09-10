package com.adolfosc.modelo.music

class Nota {

    val NOTE_NAMES =
        arrayOf("do", "do#", "re", "re#", "mi", "fa", "fa#", "sol", "sol#", "la", "la#", "si")
    private var frecuencia = 0
    private var nombre: String? = null
    private var octava = 0
    private var tiempo = 0
    private var canal = 0



    constructor(nombre: String?, octava: Int, tiempo: Int, canal: Int) {
        this.nombre = nombre
        this.octava = octava
        this.tiempo = tiempo
        this.canal = canal
    }

    fun Nota() {}


    fun getFrecuencia(): Int {
        return frecuencia
    }

    fun setFrecuencia(frecuencia: Int) {
        this.frecuencia = frecuencia
    }

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }


    fun getTiempo(): Int {
        return tiempo
    }

    fun setTiempo(tiempo: Int) {
        this.tiempo = tiempo
    }

    fun getOctava(): Int {
        return octava
    }

    fun setOctava(octava: Int) {
        this.octava = octava
    }

    fun getCanal(): Int {
        return canal
    }

    fun setCanal(canal: Int) {
        this.canal = canal
    }


    fun getNota(): Int {
        var cont = 0
        if (nombre == "esperar") {
            return -1
        }
        for (nombreNota in NOTE_NAMES) {
            if (nombreNota == nombre) {
                break
            }
            cont++
        }
        return cont + 24 + octava * 12
    }
}
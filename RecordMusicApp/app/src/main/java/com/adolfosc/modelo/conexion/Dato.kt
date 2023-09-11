package com.adolfosc.modelo.conexion

class Dato {

    private var canal: String? = null
    private var nota: String? = null
    private var octava: String? = null
    private var duracion: String? = null

    constructor() {
        canal = ""
        nota = ""
        octava = ""
        duracion = ""
    }

    fun Dato() {
        canal = ""
        nota = ""
        octava = ""
        duracion = ""
    }

    fun getCanal(): String? {
        return canal
    }

    fun setCanal(canal: String?) {
        this.canal = canal
    }

    fun getNota(): String? {
        return nota
    }

    fun setNota(nota: String?) {
        this.nota = nota
    }

    fun getOctava(): String? {
        return octava
    }

    fun setOctava(octava: String?) {
        this.octava = octava
    }

    fun getDuracion(): String? {
        return duracion
    }

    fun setDuracion(duracion: String?) {
        this.duracion = duracion
    }
}
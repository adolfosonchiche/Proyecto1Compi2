package com.adolfosc.modelo.conexion

class Solicitud {

    private var tipo: String? = null
    private var nombre: String? = null
    private var datos: MutableList<Dato>? = null

    constructor() {
        datos = ArrayList()
    }
    fun Solicitud() {
        datos = ArrayList()
    }

    fun getTipo(): String? {
        return tipo
    }

    fun setTipo(tipo: String?) {
        this.tipo = tipo
    }

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }

    fun getDatos(): List<Dato>? {
        return datos
    }

    fun setDatos(datos: MutableList<Dato>?) {
        this.datos = datos
    }

    fun insertarDato(dato: Dato) {
        datos!!.add(dato)
    }
}
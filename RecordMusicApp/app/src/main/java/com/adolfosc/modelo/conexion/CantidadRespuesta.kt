package com.adolfosc.modelo.conexion

class CantidadRespuesta {

    private var nombre: String? = null
    private var cantidad: String? = null

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }

    fun getCantidad(): String? {
        return cantidad
    }

    fun setCantidad(cantidad: String?) {
        this.cantidad = cantidad
    }
}
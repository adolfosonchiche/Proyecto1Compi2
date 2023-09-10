package com.adolfosc.modelo.conexion

import com.adolfosc.modelo.music.Nota

class Respuesta {

    private var tipo: String? = null
    private var nombV: String? = null
    private var valores: MutableList<CantidadRespuesta>? = null
    private var notas: MutableList<Nota>? = null

    fun Respuesta() {
        valores = ArrayList()
        notas = ArrayList()
    }

    fun getTipo(): String? {
        return tipo
    }

    fun setTipo(tipo: String?) {
        this.tipo = tipo
    }

    fun getNombV(): String? {
        return nombV
    }

    fun setNombV(nombV: String?) {
        this.nombV = nombV
    }

    fun getValores(): List<CantidadRespuesta>? {
        return valores
    }

    fun setValores(valores: MutableList<CantidadRespuesta>?) {
        this.valores = valores
    }

    fun getNotas(): List<Nota>? {
        return notas
    }

    fun setNotas(notas: MutableList<Nota>?) {
        this.notas = notas
    }

    fun insertarValor(valor: CantidadRespuesta) {
        valores!!.add(valor)
    }

    fun insertarNota(nota: Nota) {
        notas!!.add(nota)
    }
}
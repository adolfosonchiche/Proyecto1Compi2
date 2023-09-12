package com.adolfosc.modelo.music

import java.io.Serializable

class Tiempo: Serializable {

    private var canal = 0
    private var tiempo = 0

    constructor() {
        this.canal = canal
        tiempo = 0
    }

    fun Tiempo(canal: Int) {
        this.canal = canal
        tiempo = 0
    }

    fun getCanal(): Int {
        return canal
    }

    fun setCanal(canal: Int) {
        this.canal = canal
    }

    fun getTiempo(): Int {
        return tiempo
    }

    fun setTiempo(tiempo: Int) {
        this.tiempo = tiempo
    }
}
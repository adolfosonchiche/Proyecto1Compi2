package com.adolfosc.analizadores

class ErrorSintaxis {

    private var tipo: String? = null
    private var desc: String? = null
    private var lin: String? = null
    private var col: String? = null
    private var lex: String? = null

    constructor(tipo: String?, desc: String?, lin: String?, col: String?, lex: String?) {
        this.tipo = tipo
        this.desc = desc
        this.lin = lin
        this.col = col
        this.lex = lex
    }

    fun getTipo(): String? {
        return tipo
    }

    fun setTipo(tipo: String?) {
        this.tipo = tipo
    }

    fun getDesc(): String? {
        return desc
    }

    fun setDesc(desc: String?) {
        this.desc = desc
    }

    fun getLin(): String? {
        return lin
    }

    fun setLin(lin: String?) {
        this.lin = lin
    }

    fun getCol(): String? {
        return col
    }

    fun setCol(col: String?) {
        this.col = col
    }

    fun getLex(): String? {
        return lex
    }

    fun setLex(lex: String?) {
        this.lex = lex
    }
}
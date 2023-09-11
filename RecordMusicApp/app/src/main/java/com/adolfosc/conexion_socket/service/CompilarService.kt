package com.adolfosc.conexion_socket.service

import com.adolfosc.analizadores.ErrorSintaxis
import com.adolfosc.analizadores.conexion.ConexionLexer
import com.adolfosc.analizadores.conexion.ConexionParser
import com.adolfosc.modelo.conexion.CantidadRespuesta
import com.adolfosc.modelo.conexion.Respuesta
import java.io.StringReader
import java.util.logging.Level
import java.util.logging.Logger

class CompilarService {

    fun compilarMensaje(mensaje:String?): List<CantidadRespuesta>? {
        println("iniciando la compilacion")
        println(mensaje)
        var mensajes = "<solicitud> \n" +
                " <tipo> lista </tipo> <nombre>\"\"</nombre> \n" +
                " </solicitud>"
        try {
            println("iniciando la compilacion")
            var respuesta = Respuesta()
            var errores:List<ErrorSintaxis> = ArrayList()

            var reader =  StringReader(mensaje);
            var lexico =  ConexionLexer(reader);
            var parser =    ConexionParser(lexico);
            try {
                parser.parse();

                println("no hay errores1")
                errores = parser.errorSintaxis
                println("no hay errores2")
                respuesta = parser.respuesta
                println("no hay errores3")
                println(respuesta.getValores()!!.size)
            }catch (ex:Exception){
                Logger.getLogger(CompilarService::class.java.getName()).log(Level.SEVERE, null, ex)
                println("error parser: ")
                println(ex.message)
            }
            if (respuesta != null){
                // validarRespuesta(respuesta)
                return respuesta.getValores();
            }
        }catch (e :Exception){
            println("error en la compilacion")
            println(e)
        }
        return  null;
    }

    /*private fun validarRespuesta(respuesta: Respuesta){
        var tipo = respuesta.getTipo()
        when(tipo){
            "pista" ->{
                //Es una pista
                tipoTabla = "pista"
                mostrarPistaUnica(respuesta);
            }
            "pistas" ->{
                //Son varias pistas
                tipoTabla = "pistas"
                mostrarPistas(respuesta.valores)
            }
            "lista" ->{
                //Es una lista
                tipoTabla = "lista"
                mostrarPistasDeUnaLista(respuesta.valores)
            }
            "listas" ->{
                //Son varias listas
                tipoTabla = "listas"
                mostrarListas(respuesta.valores)
            }
        }
    }*/

   /* private fun mostrarPistas(datos:List<ValorR>){
        val datosT = java.util.ArrayList<Array<String>>()
        for(dato in datos){
            var nomb = dato.nombre.replace("\"","")
            //Cantidad es duracion
            var cantidad = dato.cantidad
            println("Nombre: $nomb")
            println("Cantidad: $cantidad")
            //Insertar las pistas en la tabla

            val item = arrayOf(nomb, cantidad)
            datosT.add(item)
        }
        reiniciarTabla("Nombre Pista","Duracion")
        tableDy.addData(datosT)
    }



    private fun mostrarPistasDeUnaLista(datos:List<ValorR>){
        pistasLista = ArrayList<String>()
        val datosT = java.util.ArrayList<Array<String>>()
        for(dato in datos){
            var nomb = dato.nombre.replace("\"","")
            println("Nombre: $nomb")
            val item = arrayOf(nomb,"")
            datosT.add(item)
            //Agregar dato nombre a la lista de pistas para reproducir luego
            pistasLista.add(nomb)
        }
        reiniciarTabla("Nombre Pista","")
        tableDy.addData(datosT)
    }

    private fun mostrarPistaUnica(respuesta:Respuesta){
        pistasLista = ArrayList<String>()
        var datos = respuesta.notas
        val datosT = java.util.ArrayList<Array<String>>()
        for(dato in datos){
            var notaD = dato.nombre.replace("\"","")
            var octaD = dato.octava
            var tiemD = dato.tiempo
            var canaD = dato.canal
            val itemT = arrayOf(notaD,octaD.toString(),tiemD.toString(),canaD.toString())
            datosT.add(itemT)
            //Agregar dato nombre a la lista de pistas para reproducir luego
            nombrePistaRep = respuesta.nombV
        }
        reiniciarTabla2("Nota","Octava","Tiempo","Canal")
        tableDy.addData(datosT)
    }*/
}
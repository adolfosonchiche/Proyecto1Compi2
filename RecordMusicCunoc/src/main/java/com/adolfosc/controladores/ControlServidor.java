package com.adolfosc.controladores;

import com.adolfosc.analizadores.ErrorSintaxis;
import com.adolfosc.analizadores.compilador.CompiladorLexer;
import com.adolfosc.analizadores.compilador.CompiladorParser;
import com.adolfosc.analizadores.conexion.ConexionLexer;
import com.adolfosc.analizadores.conexion.ConexionParser;
import com.adolfosc.analizadores.semantico.ControlCrearPista;
import com.adolfosc.modelo.conexion.Dato;
import com.adolfosc.modelo.conexion.Solicitud;
import com.adolfosc.modelo.music.BinGuard;
import com.adolfosc.modelo.music.Lista;
import com.adolfosc.modelo.music.Nota;
import com.adolfosc.modelo.music.Pista;
import com.adolfosc.modelo.music.PistaGuardado;
import com.adolfosc.ui.JFReporteErrores;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class ControlServidor {
    
    private String mensaje;
    private String respuesta;

    public ControlServidor(String mensaje) {
        this.mensaje = mensaje;
        this.respuesta = "";
    }

    public void compilarMensaje() {
        try {
            String entrada = this.mensaje;
            Solicitud solicitud = null;
            List<ErrorSintaxis> errores = new ArrayList<>();
            StringReader reader = new StringReader(entrada);
            ConexionLexer lexico = new ConexionLexer(reader);
            ConexionParser parser = new ConexionParser(lexico);
            try {
                parser.parse();
                errores = parser.getErrorSintaxis();
                solicitud = parser.getSolicitud();
                validarSolicitud(solicitud);
            } catch (Exception ex) {
                System.out.println("Error irrecuperable");
                System.out.println("Causa: " + ex.getCause());
                System.out.println("Causa2: " + ex.toString());
            }
            if (errores.size() > 0) {
                //JFReporteErrores jfReporteErrores = new JFReporteErrores(errores);
                //jfReporteErrores.setVisible(true);
            } else {

                System.out.println("Tipo Sol:" + solicitud.getTipo());
                System.out.println("Nombre Sol:" + solicitud.getNombre());
                System.out.println("Compilado Correctamente");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void validarSolicitud(Solicitud solicitud) {
        String tipo = solicitud.getTipo();
        String nombre = solicitud.getNombre().replace("\"", "");
        ControlGuardado controlG = new ControlGuardado();
        controlG.obtenerBinario();
        BinGuard binarioGuard = controlG.getBinarioGuard();        
        switch (tipo) {
            case "LISTA":
                if (nombre.isEmpty()) {
                    generarRespuestaListas(binarioGuard.getListasGuardadas());
                } else {
                    Lista lista = buscarLista(binarioGuard.getListasGuardadas(), nombre);
                    generarRespuestaPistasDeLista(lista);
                }
                break;
            case "PISTA":
                if (nombre.isEmpty()) {
                    generarRespuestaPistas(binarioGuard.getPistasGuardadas());
                } else {
                    PistaGuardado pistaG = buscarPista(binarioGuard.getPistasGuardadas(), nombre);
                    generarRespuestaPistaUnica(pistaG);
                }
                break;
            case "PISTANUEVA":
                generarNuevaPista(solicitud);
                break;

        }

    }

    private Lista buscarLista(List<Lista> listas, String nombre) {
        for (Lista lista : listas) {
            String nomL = lista.getNombre().replace("\"", "");
            if (nomL.equals(nombre.replace("\"", ""))) {
                return lista;
            }
        }
        return null;
    }

    public void generarRespuestaListas(List<Lista> listas) {
        respuesta = "<respuesta>\n";
        respuesta += "<listas>\n";
        for (Lista lista : listas) {
            String nomb = lista.getNombre();
            int tamanio = lista.getPistas().size();
            respuesta += "< lista nombre = \"" + nomb + "\" pistas = " + tamanio + ">\n";
        }
        respuesta += "</listas>\n";
        respuesta += "</respuesta>";
    }

    public void generarRespuestaPistasDeLista(Lista lista) {
        List<String> listaPistas = lista.getPistas();
        String valA = "";
        if (lista.isRandom()) {
            valA = "si";
        } else {
            valA = "no";
        }
        respuesta = "<respuesta>\n";
        respuesta += "<lista nombre = \"" + lista.getNombre().replace("\"", "") + "\" aleatoria = \"" + valA + "\">\n";
        for (String pista : listaPistas) {
            respuesta += "<pista nombre = \"" + pista + "\">\n";
        }
        respuesta += "</lista>";
        respuesta += "</respuesta>";
    }

    public void generarRespuestaPistas(List<PistaGuardado> pistas) {
        respuesta = "<respuesta>\n";
        respuesta += "<pistas>\n";
        for (PistaGuardado pista : pistas) {
            String nomb = pista.getNombre();
            int duracion = pista.getDuracion();
            respuesta += "< pista nombre = \"" + nomb + "\" duracion= " + duracion + ">\n";
        }
        respuesta += "</pistas>\n";
        respuesta += "</respuesta>";
    }

    public void generarRespuestaPistaUnica(PistaGuardado pista) {
        String nombre = pista.getNombre().replace("\"", "");
        respuesta = "<respuesta>\n";
        respuesta += "<pista nombre = \"" + nombre + "\">\n";
        List<Nota> notas = obtenerNotasPista(pista);
        for (Nota nota : notas) {
            String nomb = nota.getNombre();
            int duracion = nota.getTiempo();
            int frec = nota.getOctava();
            int canal = nota.getCanal();
            respuesta += "<nota nombre = \"" + nomb + "\" duracion = " + duracion + " frecuencia = " + frec + " canal = " + canal + ">\n";
        }
        respuesta += "</pista>\n";
        respuesta += "</respuesta>";
    }

    private PistaGuardado buscarPista(List<PistaGuardado> pistas, String nombre) {
        for (PistaGuardado pista : pistas) {
            String nombP = pista.getNombre().replace("\"", "");
            if (nombP.equals(nombre)) {
                return pista;
            }
        }
        return null;
    }

    private List<Nota> obtenerNotasPista(PistaGuardado pistaG) {
        List<Nota> notas = new ArrayList<>();
        String valR = "";
        if (pistaG == null) {
            valR = "No se encontro la pista";
        } else {
            try {
                String entrada = pistaG.getCodigo();
                List<Pista> pistas = null;
                List<ErrorSintaxis> errores = new ArrayList<>();
                StringReader reader = new StringReader(entrada);
                CompiladorLexer lexico = new CompiladorLexer(reader);
                CompiladorParser parser = new CompiladorParser(lexico);
                try {
                    parser.parse();
                    errores = parser.getErroresCom();
                    pistas = parser.getPistas();
                } catch (Exception ex) {
                    System.out.println("Error irrecuperable");
                    System.out.println("Causa: " + ex.getCause());
                    System.out.println("Causa2: " + ex.toString());
                }
                if (errores.size() > 0) {
                    JFReporteErrores jfReporteErrores = new JFReporteErrores(errores);
                    jfReporteErrores.setVisible(true);
                } else {
                    for (Pista pista : pistas) {
                        ControlCrearPista crearPista = new ControlCrearPista(pista);
                        crearPista.initPista();
                        notas = crearPista.getNotas();
                    }
                    valR = "Cargado Correctamente";
                    System.out.println("Cargado Correctamente");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return notas;
    }

    public void generarNuevaPista(Solicitud solicitud) {
        List<Dato> datos = solicitud.getDatos();
        String codigo = "";
        codigo += "Pista " + solicitud.getNombre().replace("\"", "") + "\n";
        codigo += "    principal()\n";

        for (Dato dato : datos) {
            String nota = dato.getNota().replace("\"", "").toLowerCase();
            String octava = dato.getOctava().replace("\"", "");
            String duracion = dato.getDuracion().replace("\"", "");
            String canal = dato.getCanal().replace("\"", "");
            codigo += "        Reproducir(" + nota + ", " + octava + ", " + duracion + "," + canal + ")\n";
        }

        // guardar pista, obtener duracion
        compilarNuevaPista(codigo,solicitud.getNombre().replace("\"", ""));
    }

    public void compilarNuevaPista(String entrada,String nombre) {
        String valR = "";
        try {
            List<Pista> pistas = null;
            List<ErrorSintaxis> errores = new ArrayList<>();
            StringReader reader = new StringReader(entrada);
            CompiladorLexer lexico = new CompiladorLexer(reader);
            CompiladorParser parser = new CompiladorParser(lexico);
            try {
                parser.parse();
                errores = parser.getErroresCom();
                pistas = parser.getPistas();
            } catch (Exception ex) {
                System.out.println("Error irrecuperable");
                System.out.println("Causa: " + ex.getCause());
                System.out.println("Causa2: " + ex.toString());
            }
            if (errores.size() > 0) {
                JFReporteErrores jfReporteErrores = new JFReporteErrores(errores);
                jfReporteErrores.setVisible(true);
            } else {
                for (Pista pista : pistas) {
                    ControlCrearPista crearPista = new ControlCrearPista(pista);
                    crearPista.initPista();
                    List<Nota> notasPistaRep = crearPista.getNotas();
                    //Obtener Duracion
                    ControlDuracion controlD = new ControlDuracion(notasPistaRep);
                    controlD.generarDuracion();
                    int durPista = controlD.getDuracion();

                    //Guardar pista
                    ControlGuardado controlG = new ControlGuardado();
                    controlG.guardarPista(entrada, nombre, durPista);
                }
                valR = "Cargado Correctamente";
                System.out.println("Cargado Correctamente");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getRespuesta() {
        return respuesta;
    }
    
}

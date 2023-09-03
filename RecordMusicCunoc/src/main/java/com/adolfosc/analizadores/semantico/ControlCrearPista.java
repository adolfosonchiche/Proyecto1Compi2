package com.adolfosc.analizadores.semantico;

import com.adolfosc.analizadores.ErrorSintaxis;
import com.adolfosc.analizadores.valexpresion.ValExprLexer;
import com.adolfosc.analizadores.valexpresion.ValExprParser;
import com.adolfosc.modelo.instrucciones.Asignacion;
import com.adolfosc.modelo.instrucciones.CasoC;
import com.adolfosc.modelo.instrucciones.CondSi;
import com.adolfosc.modelo.instrucciones.DeclDim;
import com.adolfosc.modelo.instrucciones.Declaracion;
import com.adolfosc.modelo.instrucciones.Dimension;
import com.adolfosc.modelo.instrucciones.Funcion;
import com.adolfosc.modelo.instrucciones.IncrDecr;
import com.adolfosc.modelo.instrucciones.Mientras;
import com.adolfosc.modelo.instrucciones.Para;
import com.adolfosc.modelo.instrucciones.SwitchC;
import com.adolfosc.modelo.music.Condicion;
import com.adolfosc.modelo.music.Expresion;
import com.adolfosc.modelo.music.Instruccion;
import com.adolfosc.modelo.music.Nota;
import com.adolfosc.modelo.music.Parametro;
import com.adolfosc.modelo.music.Pista;
import com.adolfosc.modelo.valexpresion.ExprVal;
import com.adolfosc.modelo.valexpresion.Simbolo;
import com.adolfosc.ui.JFReporteErrores;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author hectoradolfo
 */
public class ControlCrearPista {
    
    private Pista pista;
    private List<Simbolo> simbolosInit;
    private List<Funcion> funcionesInit;
    private List<Nota> notas;
    private int ambito;
    private List<String> mensajes;

    public ControlCrearPista(Pista pista) {
        this.pista = pista;
        this.simbolosInit = new ArrayList<>();
        this.funcionesInit = new ArrayList<>();
        this.notas = new ArrayList<>();
        this.mensajes = new ArrayList<>();
        this.ambito = 0;
    }

    public void initPista() {
        inicializarPista();
    }

    private void inicializarPista() {
        List<Instruccion> instrucciones = pista.getInstrucciones();
        for (Instruccion instruccion : instrucciones) {
            enviarInstruccionValidacion(instruccion);
        }

        //Buscar funcion Principal
        //Verificar que no sea keep
        //Iniciar proceso
        for (Funcion funcion : this.funcionesInit) {
            if (funcion.getNombre().equalsIgnoreCase("principal")) {
                if (funcion.isEsKeep() == false) {
                    ejecutarFuncion(funcion, null);
                }
                break;
            }
        }
    }

    private String ejecutarFuncion(Funcion funcion, List<Parametro> parametros) {
        String valR = "";
        this.ambito++;
        List<Parametro> parFun = funcion.getParametros();
        if (parFun.size() > 0) {
            int contPar = 0;
            for (Parametro parametro : parametros) {
                //crear un simbolo
                String nombre = parFun.get(contPar).getNombre();
                String tipo = parFun.get(contPar).getTipo();
                Simbolo simVar = new Simbolo();
                simVar.setNombre(nombre);
                simVar.setTipo(tipo);
                String valor = "";
                List<Expresion> expresiones = parametro.getExpresiones();
                if (expresiones.isEmpty() == false) {
                    valor = obtenerValor(expresiones);
                }
                simVar.setValor(valor);
                simVar.setEsArreglo(false);
                simVar.setEsDeclArr(false);
                simVar.setAmbito(this.ambito);
                simVar.setEsKeep(false);
                //Agregar a simsinit
                this.simbolosInit.add(simVar);
            }
        }
        List<Instruccion> instrucciones = funcion.getInstrucciones();
        boolean romper = false;
        String valFun = "";
        for (Instruccion instruccion : instrucciones) {
            String tipo = instruccion.getTipoInstruccion();
            switch (tipo) {
                case "declaracion":
                    validarDeclaracionInit((Declaracion) instruccion);
                    break;
                case "asignacion":
                    validarAsignacion((Asignacion) instruccion);
                    break;
                case "si":
                    valFun = ejecutarSi((CondSi) instruccion);
                    break;
                case "para":
                    valFun = ejecutarPara((Para) instruccion);
                    break;
                case "mientras":
                    valFun = ejecutarMientras((Mientras) instruccion);
                    break;
                case "hacer":
                    valFun = ejecutarHacer((Mientras) instruccion);
                    break;
                case "switch":
                    valFun = ejecutarSwitch((SwitchC) instruccion);
                    break;
                case "incrDecr":
                    ejecutarIncrDecr((IncrDecr) instruccion);
                    break;
                case "funcion":
                    valFun = validarFuncion((Funcion) instruccion);
                    //Validar el resultado que devolvio la funcion
                    if (valFun.equals("salirFF")) {
                        romper = true;
                    } else if (valFun.contains("retornaFF")) {
                        String valoresR[] = valFun.split(",,!!");
                        try {
                            valR = valoresR[1];
                        } catch (Exception e) {
                        }
                        romper = true;
                    } else if (valFun.contains("continuarFF")) {
                        romper = true;
                    } else {
                        valR = valFun;
                    }
                    break;
            }
            if (valFun.contains("retornaFF")) {
                String valoresR[] = valFun.split(",,!!");
                try {
                    valR = valoresR[1];
                } catch (Exception e) {
                }
                romper = true;
            }
            if (romper) {
                break;
            }
        }
        cerrarVariablesAmbito();
        this.ambito--;
        return valR;
    }

    private String validarFuncion(Funcion funcion) {
        String valR = "";
        List<Parametro> parametros = funcion.getParametros();
        int tamanioPar = parametros.size();
        String nombre = funcion.getNombre();
        boolean encontrado = false;
        for (Funcion funcion1 : this.funcionesInit) {
            if (funcion1.getNombre().equals(nombre)) {
                if (funcion1.getParametros().size() == tamanioPar) {
                    valR = ejecutarFuncion(funcion1, parametros);
                    encontrado = true;
                    break;
                }
            }
        }
        if (encontrado == false) {
            String nombre2 = funcion.getNombre().toLowerCase();
            switch (nombre2) {
                case "continuar":
                    valR = "continuarFF";
                    break;
                case "sumarizar":
                    //Obtener todos los simbolos del arreglo  
                    valR = ejecutarSumarizar(funcion);
                    break;
                case "longitud":
                    valR = ejecutarLongitud(funcion);
                    break;
                case "mensaje":
                    ejecutarMensaje(funcion);
                    break;
                case "reproducir":
                    valR = ejecutarReproducir(funcion);
                    break;
                case "esperar":
                    valR = ejecutarEsperar(funcion);
                    break;
                case "retorna":
                    valR = "retornaFF,,!!" + ejecutarRetorna(funcion);
                    break;
                case "salir":
                    valR = "salirFF";
                    break;
            }
        }
        return valR;
    }

    private String ejecutarIncrDecr(IncrDecr incrDecr) {
        String valR = "";
        String tipo = incrDecr.getTipo();
        if (tipo.equals("++")) {
            Simbolo simA = buscarEnSimbolos(incrDecr.getVariable(), this.ambito);
            aumentarValorSimbolo(simA);
        } else {
            Simbolo simA = buscarEnSimbolos(incrDecr.getVariable(), this.ambito);
            disminuirValorSimbolo(simA);
        }
        return valR;
    }

    private String ejecutarRetorna(Funcion funcion) {
        String valR = "";
        Parametro par1 = funcion.getParametros().get(0);
        valR = obtenerValor(par1.getExpresiones());
        return valR;
    }

    private String ejecutarEsperar(Funcion funcion) {
        List<Parametro> parametros = funcion.getParametros();
        Parametro par1 = parametros.get(0);
        Parametro par2 = parametros.get(1);

        String valorTiempo = obtenerValor(par1.getExpresiones());
        String valorCanal = obtenerValor(par2.getExpresiones());

        //Crear la nota:String nombre, int octava, int tiempo, int canal        
        int tiempo = Integer.valueOf(valorTiempo);
        int canal = Integer.valueOf(valorCanal);

        Nota nota = new Nota("esperar", 1, tiempo, canal);
        //Ingresar nota
        this.notas.add(nota);

        return valorTiempo;
    }

    private String ejecutarReproducir(Funcion funcion) {
        List<Parametro> parametros = funcion.getParametros();

        Parametro par1 = parametros.get(0);
        Parametro par2 = parametros.get(1);
        Parametro par3 = parametros.get(2);
        Parametro par4 = parametros.get(3);

        String valorNota = par1.getExpresiones().get(0).getValor();
        String valorOctava = obtenerValor(par2.getExpresiones());
        String valorTiempo = obtenerValor(par3.getExpresiones());
        String valorCanal = obtenerValor(par4.getExpresiones());

        //Crear la nota:String nombre, int octava, int tiempo, int canal
        int octava = parseStringToInt(valorOctava);
        int tiempo = parseStringToInt(valorTiempo);
        int canal = parseStringToInt(valorCanal);

        Nota nota = new Nota(valorNota, octava, tiempo, canal);
        //Ingresar nota
        this.notas.add(nota);

        return valorTiempo;
    }

    private int parseStringToInt(String val) {
        int valR = 0;
        Double valD = Double.parseDouble(val);
        valR = (int) Math.round(valD);
        return valR;
    }

    private void ejecutarMensaje(Funcion funcion) {
        Parametro par1 = funcion.getParametros().get(0);
        String valor = obtenerValor(par1.getExpresiones());
        this.mensajes.add(valor);
    }

    private String ejecutarSumarizar(Funcion funcion) {
        String valR = "";
        Parametro parametro = funcion.getParametros().get(0);
        String valPar = obtenerValor(parametro.getExpresiones());
        List<Simbolo> simbolosArr = obtenerTodoSimbolosArreglo(valPar);
        String nuevoVal = "";
        int contV = 0;
        for (Simbolo simbolo : simbolosArr) {
            if (contV == 0) {
                nuevoVal += simbolo.getValor();
            } else {
                nuevoVal += "+" + simbolo.getValor();
            }

        }
        valR = obtenerValExpr(nuevoVal);
        return valR;
    }

    private String ejecutarLongitud(Funcion funcion) {
        String valR = "";
        Parametro parametro = funcion.getParametros().get(0);
        Expresion expresion1 = parametro.getExpresiones().get(0);
        if (expresion1.getTipo().equals("cadena")) {
            int tamanio = expresion1.getValor().length();
            valR = String.valueOf(tamanio);
        } else {
            Simbolo simArr = null;
            String nombre = expresion1.getValor();
            int tamanio = this.simbolosInit.size();
            for (int i = 0; i < tamanio; i++) {
                Simbolo simbolo = this.simbolosInit.get(i);
                if (simbolo.getNombre().equals(nombre) && simbolo.getAmbito() <= this.ambito) {
                    if (simbolo.isEsArreglo()) {
                        if (simbolo.isEsDeclArr()) {
                            //Revisar que solo tenga una dimension
                            List<Integer> dimensiones = simbolo.getDimensiones();
                            if (dimensiones.size() == 1) {
                                simArr = simbolo;
                            } else {
                                simArr = simbolo;
                            }
                            break;
                        }
                    }
                }
            }
            if (simArr == null) {
                valR = "0";
            } else {
                valR = String.valueOf(simArr.getDimensiones().get(0));
            }
        }
        return valR;
    }

    private List<Simbolo> obtenerTodoSimbolosArreglo(String nombre) {
        List<Simbolo> simbolosR = new ArrayList<>();
        int tamanio = this.simbolosInit.size();
        boolean solo1Dim = false;
        for (int i = 0; i < tamanio; i++) {
            Simbolo simbolo = this.simbolosInit.get(i);
            if (simbolo.getNombre().equals(nombre) && simbolo.getAmbito() <= this.ambito) {
                if (simbolo.isEsArreglo()) {
                    if (simbolo.isEsDeclArr()) {
                        //Revisar que solo tenga una dimension
                        List<Integer> dimensiones = simbolo.getDimensiones();
                        if (dimensiones.size() == 1) {
                            solo1Dim = true;
                        } else {
                            solo1Dim = false;
                        }
                        break;
                    }
                }
            }
        }
        if (solo1Dim) {
            //Buscar todas las inicializaciones
            for (int i = 0; i < tamanio; i++) {
                Simbolo simbolo = this.simbolosInit.get(i);
                if (simbolo.getNombre().equals(nombre) && simbolo.getAmbito() <= this.ambito) {
                    if (simbolo.isEsArreglo()) {
                        if (simbolo.isEsDeclArr() == false) {
                            simbolosR.add(simbolo);
                        }
                    }
                }
            }
        }
        simbolosR = ordenarSimbolosDimension(simbolosR);
        return simbolosR;
    }

    private List<Simbolo> ordenarSimbolosDimension(List<Simbolo> simbolos) {
        List<Simbolo> simsR = new ArrayList<>();
        Collections.sort(simbolos, new Comparator<Simbolo>() {
            @Override
            public int compare(Simbolo o1, Simbolo o2) {
                //comparar dimension
                Integer dim1 = o1.getDimensiones().get(0);
                Integer dim2 = o2.getDimensiones().get(0);
                return dim1.compareTo(dim2);
            }
        });
        return simsR;
    }

    private String ejecutarInstrucciones(List<Instruccion> instrucciones) {
        String valR = "";
        boolean romper = false;
        String valFun = "";
        for (Instruccion instruccion : instrucciones) {
            String tipo = instruccion.getTipoInstruccion();
            switch (tipo) {
                case "declaracion":
                    validarDeclaracionInit((Declaracion) instruccion);
                    break;
                case "asignacion":
                    validarAsignacion((Asignacion) instruccion);
                    break;
                case "si":
                    valFun = ejecutarSi((CondSi) instruccion);
                    break;
                case "para":
                    valFun = ejecutarPara((Para) instruccion);
                    break;
                case "mientras":
                    valFun = ejecutarMientras((Mientras) instruccion);
                    break;
                case "hacer":
                    valFun = ejecutarHacer((Mientras) instruccion);
                    break;
                case "incrDecr":
                    ejecutarIncrDecr((IncrDecr) instruccion);
                    break;
                case "switch":
                    valFun = ejecutarSwitch((SwitchC) instruccion);
                    break;
                case "funcion":
                    valFun = validarFuncion((Funcion) instruccion);
                    //Validar el resultado que devolvio la funcion
                    if (valFun.equals("salirFF")) {
                        romper = true;
                    } else if (valFun.contains("retornaFF")) {
                        valR = valFun;
                        romper = true;
                    } else if (valFun.contains("continuarFF")) {
                        romper = true;
                    } else {
                        valR = valFun;
                    }
                    break;
            }
            if (valFun.contains("retornaFF")) {
                valR = valFun;
                romper = true;
            }
            if (romper) {
                break;
            }
        }
        return valR;
    }

    private void cerrarVariablesAmbito() {
        int tamano = this.simbolosInit.size();
        for (int i = tamano; i > 0; i--) {
            Simbolo sim = this.simbolosInit.get(i - 1);
            int ambitoS = sim.getAmbito();
            if (ambitoS == this.ambito) {
                this.simbolosInit.remove(i - 1);
            }
        }
    }

    private void enviarInstruccionValidacion(Instruccion instruccion) {
        String tipo = instruccion.getTipoInstruccion();
        switch (tipo) {
            case "declaracion":
                validarDeclaracionInit((Declaracion) instruccion);
                break;
            case "funcion":
                agregarFuncion((Funcion) instruccion);
                break;
        }
    }

    private String ejecutarSi(CondSi condisionSi) {
        this.ambito++;
        String valR = "";
        List<Expresion> expresionesCond = obtenerCondicion(condisionSi.getCondiciones(), condisionSi.getOperadores());
        String valCond = obtenerValor(expresionesCond);
        if (valCond.equalsIgnoreCase("true") || valCond.equalsIgnoreCase("verdadero")) {
            //Se realizaran las acciones del si
            String valorR = ejecutarInstrucciones(condisionSi.getInstrucciones());
            if (valorR.contains("retornaFF")) {
                valR = valorR;
            }
        } else {
            boolean romper = false;
            List<CondSi> sinossi = condisionSi.getCondsinosi();
            for (CondSi condSinosi : sinossi) {
                List<Expresion> expresionesCond2 = obtenerCondicion(condSinosi.getCondiciones(), condSinosi.getOperadores());
                String valCond2 = obtenerValor(expresionesCond2);
                if (valCond2.equalsIgnoreCase("true") || valCond2.equalsIgnoreCase("verdadero")) {
                    //Realizar las acciones del sinosi
                    String valorR = ejecutarInstrucciones(condSinosi.getInstrucciones());
                    if (valorR.contains("retornaFF")) {
                        valR = valorR;
                    }
                    romper = true;
                }
                if (romper) {
                    break;
                }
            }
            if (romper == false) {
                CondSi sino = condisionSi.getCondsino();
                if (sino != null) {
                    String valorR = ejecutarInstrucciones(sino.getInstrucciones());
                    if (valorR.contains("retornaFF")) {
                        valR = valorR;
                    }
                }
            }
        }
        cerrarVariablesAmbito();
        this.ambito--;
        return valR;
    }

    private String ejecutarSwitch(SwitchC switchC) {
        String valR = "";
        this.ambito++;
        Expresion vExpr = switchC.getVariable().get(0);
        String variable = vExpr.getValor();

        Simbolo simVar = buscarEnSimbolos(variable, this.ambito);
        boolean encontrado = false;
        if (simVar != null) {
            List<CasoC> casos = switchC.getCasos();
            String valE = simVar.getValor();
            for (int i = 0; i < casos.size(); i++) {
                CasoC caso = casos.get(i);
                String valorCaso = obtenerValor(caso.getExpresion());
                if (valorCaso.equals(valE)) {
                    String valorR = ejecutarInstrucciones(caso.getInstrucciones());
                    if (valorR.contains("retornaFF")) {
                        valR = valorR;
                    }
                    encontrado = true;
                    break;
                }
            }
            if (encontrado == false) {
                for (int i = 0; i < casos.size(); i++) {
                    CasoC caso = casos.get(i);
                    String tipo = caso.getTipo();
                    if (tipo.equals("default")) {
                        String valorR = ejecutarInstrucciones(caso.getInstrucciones());
                        if (valorR.contains("retornaFF")) {
                            valR = valorR;
                        }
                    }
                }
            }
        }
        cerrarVariablesAmbito();
        this.ambito--;
        return valR;
    }

    private String ejecutarPara(Para para) {
        String valR = "";
        this.ambito++;
        Asignacion asig = para.getAsignacion();
        String variable = asig.getVariable();
        Simbolo simVar = buscarEnSimbolos(variable, this.ambito);
        if (simVar == null) {
            //Crear el simbolo 
            simVar = new Simbolo();
            simVar.setNombre(variable);
            simVar.setTipo("entero");
            String valor = "0";
            List<Expresion> expresiones = asig.getValoresDecl();
            if (expresiones.isEmpty() == false) {
                valor = obtenerValor(expresiones);
            }
            simVar.setValor(valor);

            simVar.setEsArreglo(false);
            simVar.setEsDeclArr(false);
            simVar.setAmbito(this.ambito);
            simVar.setEsKeep(false);
            this.simbolosInit.add(simVar);
        } else {
            String valor = "0";
            List<Expresion> expresiones = asig.getValoresDecl();
            if (expresiones.isEmpty() == false) {
                valor = obtenerValor(expresiones);
            }
            simVar.setValor(valor);
        }

        boolean condicionSalir = true;
        List<Expresion> valoresExprCond = obtenerCondicion(para.getCondiciones(), para.getOperadores());
        while (condicionSalir) {
            String valor = obtenerValor(valoresExprCond);
            if (valor.equalsIgnoreCase("false") || valor.equalsIgnoreCase("falso")) {
                condicionSalir = false;
                break;
            }
            //Aumentar o disminuir el valor
            String opAD = para.getOperadorVar();
            if (opAD.equals("++")) {
                aumentarValorSimbolo(simVar);
            } else {
                disminuirValorSimbolo(simVar);
            }
            //Realizar intrucciones
            String valorR = ejecutarInstrucciones(para.getInstrucciones());
            if (valorR.contains("retornaFF")) {
                valR = valorR;
            }
        }

        cerrarVariablesAmbito();
        this.ambito--;
        return valR;
    }

    private String ejecutarMientras(Mientras mientras) {
        String valR = "";
        this.ambito++;
        boolean condicionSalir = true;
        List<Expresion> valoresExprCond = obtenerCondicion(mientras.getCondiciones(), mientras.getOperadores());
        while (condicionSalir) {
            String valor = obtenerValor(valoresExprCond);
            if (valor.equalsIgnoreCase("false") || valor.equalsIgnoreCase("falso")) {
                condicionSalir = false;
                break;
            }
            //Realizar intrucciones 
            String valorR = ejecutarInstrucciones(mientras.getInstrucciones());
            if (valorR.contains("retornaFF")) {
                valR = valorR;
            }
        }
        cerrarVariablesAmbito();
        this.ambito--;
        return valR;
    }

    private String ejecutarHacer(Mientras mientras) {
        String valR = "";
        this.ambito++;
        boolean condicionSalir = true;
        List<Expresion> valoresExprCond = obtenerCondicion(mientras.getCondiciones(), mientras.getOperadores());
        while (condicionSalir) {
            //Realizar intrucciones 
            String valorR = ejecutarInstrucciones(mientras.getInstrucciones());
            if (valorR.contains("retornaFF")) {
                valR = valorR;
                condicionSalir = false;
                break;
            }
            String valor = obtenerValor(valoresExprCond);
            if (valor.equalsIgnoreCase("false") || valor.equalsIgnoreCase("falso")) {
                condicionSalir = false;
                break;
            }
        }
        cerrarVariablesAmbito();
        this.ambito--;
        return valR;
    }

    private void aumentarValorSimbolo(Simbolo sim) {
        try {
            int valorSim = Integer.parseInt(sim.getValor());
            valorSim++;
            sim.setValor(String.valueOf(valorSim));
        } catch (Exception e) {

        }
    }

    private void disminuirValorSimbolo(Simbolo sim) {
        try {
            int valorSim = Integer.parseInt(sim.getValor());
            valorSim--;
            sim.setValor(String.valueOf(valorSim));
        } catch (Exception e) {

        }
    }

    private List<Expresion> obtenerCondicion(List<Condicion> condiciones, List<String> operadores) {
        List<Expresion> expresiones = new ArrayList<>();
        int contOp = 0;
        for (Condicion condicion : condiciones) {
            if (contOp > 0) {
                String operador = operadores.get(contOp);
                Expresion exprOp = new Expresion();
                exprOp.setTipo("op");
                exprOp.setValor(operador);
                expresiones.add(exprOp);
                contOp++;
            }
            List<Expresion> expr1 = condicion.getExpresion1();
            List<Expresion> expr2 = condicion.getExpresion2();
            if (condicion.isNuloExpr1()) {
                Expresion expresion = new Expresion();
                expresion.setTipo("boolean");
                expresion.setValor("!!");
                expresiones.add(expresion);
            }
            for (Expresion expresion : expr1) {
                expresiones.add(expresion);
            }

            if (expr2 != null && expr2.isEmpty()==false) {
                String operador = operadores.get(contOp);
                Expresion exprOp = new Expresion();
                exprOp.setTipo("op");
                exprOp.setValor(operador);
                expresiones.add(exprOp);
                contOp++;

                if (condicion.isNuloExpr2()) {
                    Expresion expresion = new Expresion();
                    expresion.setTipo("boolean");
                    expresion.setValor("!!");
                    expresiones.add(expresion);
                }
                for (Expresion expresion : expr2) {
                    expresiones.add(expresion);
                }
            }
        }
        return expresiones;
    }

    private void validarAsignacion(Asignacion asignacion) {
        if (asignacion.isEsArreglo()) {
            List<Dimension> dimensiones = asignacion.getDimensiones();
            List<Integer> dimen = new ArrayList<>();
            for (Dimension dimension : dimensiones) {
                String valor = "0";
                List<Expresion> expresiones = dimension.getExpresiones();
                if (expresiones.isEmpty() == false) {
                    valor = obtenerValor(expresiones);
                }
                int valDim = Integer.parseInt(valor);
                dimen.add(valDim);
            }
            Simbolo sim2 = buscarEnSimbolosArreglo(asignacion.getVariable(), dimen);
            if (sim2 != null) {
                String valor = "";
                List<Expresion> expresiones = asignacion.getValoresDecl();
                if (expresiones.isEmpty() == false) {
                    valor = obtenerValor(expresiones);
                }
                if (asignacion.isEsIncremento()) {
                    incrementarAsignacion(valor,sim2);
                }else{
                    sim2.setValor(valor);
                }  
            }

        } else {
            Simbolo simA = buscarEnSimbolos(asignacion.getVariable(), this.ambito);
            if (simA != null) {
                String valor = "";
                List<Expresion> expresiones = asignacion.getValoresDecl();
                if (expresiones.isEmpty() == false) {
                    valor = obtenerValor(expresiones);
                }
                
                if (asignacion.isEsIncremento()) {
                    incrementarAsignacion(valor,simA);
                }else{
                    simA.setValor(valor);
                }                
            }
        }
    }
    
    private void incrementarAsignacion(String valor,Simbolo simA){
        String tipo = simA.getTipo();
        String valA = simA.getValor();
        if (tipo.equals("cadena")) {
            valA = valA.replace("\"", "");
            simA.setValor(valA+valor);
        }else if(tipo.equals("entero")){
            int valA2 = parseStringToInt(valA);
            int valor2 = parseStringToInt(valor);
            int total = valA2+valor2;
            simA.setValor(String.valueOf(total));
        }else if(tipo.equals("doble")){
            double valA2 = Double.parseDouble(valA);
            double valor2 = Double.parseDouble(valor);
            double total = valA2+valor2;
            simA.setValor(String.valueOf(total));
        }
    }

    private void validarDeclaracionInit(Declaracion declaracion) {
        String tipo = declaracion.getTipoDecl();
        boolean esKeep = declaracion.isEsKeep();
        //Validar si es arreglo
        if (declaracion.getDimensiones().isEmpty()) {
            String valor = "nulo";
            if (declaracion.getValoresDecl().isEmpty() == false) {
                valor = obtenerValor(declaracion.getValoresDecl());
            }
            List<String> variables = declaracion.getVariables();
            for (String variable : variables) {
                Simbolo simbolo = new Simbolo();
                simbolo.setNombre(variable);
                simbolo.setTipo(tipo);
                simbolo.setValor(valor);
                simbolo.setEsArreglo(false);
                simbolo.setEsDeclArr(false);
                simbolo.setAmbito(this.ambito);
                if (esKeep) {
                    simbolo.setEsKeep(true);
                } else {
                    simbolo.setEsKeep(false);
                }
                this.simbolosInit.add(simbolo);
            }
        } else {
            //Obtener dimensiones
            List<Dimension> dimensiones = declaracion.getDimensiones();
            List<Integer> dimen = new ArrayList<>();
            for (Dimension dimension : dimensiones) {
                List<Expresion> expresiones = dimension.getExpresiones();
                String val = obtenerValor(expresiones);
                int valDim = Integer.parseInt(val);
                dimen.add(valDim);
            }
            String valor = "nulo";
            List<String> variables = declaracion.getVariables();
            List<Simbolo> simsVar = new ArrayList<>();
            for (String variable : variables) {
                Simbolo simbolo = new Simbolo();
                simbolo.setNombre(variable);
                simbolo.setTipo(tipo);
                simbolo.setValor(valor);
                simbolo.setDimensiones(dimen);
                simbolo.setEsArreglo(true);
                simbolo.setEsDeclArr(true);
                simbolo.setAmbito(this.ambito);
                if (esKeep) {
                    simbolo.setEsKeep(true);
                } else {
                    simbolo.setEsKeep(false);
                }
                this.simbolosInit.add(simbolo);
                simsVar.add(simbolo);
            }
            if (declaracion.getDeclaracionDimension() != null) {
                for (Simbolo simbolo : simsVar) {
                    declaracionArreglo(declaracion.getDeclaracionDimension(), "", simbolo);
                }
            }

        }

    }

    private void declaracionArreglo(DeclDim declDim, String dim, Simbolo sim) {
        List<DeclDim> hijos = declDim.getDeclDimHijos();
        int i = 0;
        for (DeclDim hijo : hijos) {
            String dim2 = "";
            if (dim.equals("")) {
                dim2 = String.valueOf(i);
            } else {
                dim2 = dim + "," + i;
            }
            declaracionArreglo(hijo, dim2, sim);
            i++;
        }
        List<Dimension> dimensiones = declDim.getDeclaracionesArreglo();
        int a = 0;
        for (Dimension dimension : dimensiones) {
            Simbolo simbolo = new Simbolo();
            simbolo.setNombre(sim.getNombre());
            simbolo.setTipo(sim.getTipo());

            String valor = "nulo";

            List<Expresion> expresiones = dimension.getExpresiones();
            if (expresiones.isEmpty() == false) {
                valor = obtenerValor(expresiones);
            }
            simbolo.setValor(valor);

            List<Integer> dimen = new ArrayList<>();
            String[] parts = dim.split(",");
            for (int j = 0; j < parts.length; j++) {
                int dimP = Integer.parseInt(parts[j]);
                dimen.add(dimP);
            }
            dimen.add(a);
            simbolo.setDimensiones(dimen);
            simbolo.setEsArreglo(true);
            simbolo.setEsDeclArr(false);
            simbolo.setAmbito(this.ambito);
            if (sim.isEsKeep()) {
                simbolo.setEsKeep(true);
            } else {
                simbolo.setEsKeep(false);
            }
            this.simbolosInit.add(simbolo);
        }
    }

    private String obtenerValor(List<Expresion> valoresExpresion) {
        String valores = "";
        for (Expresion expresion : valoresExpresion) {
            String tipo = expresion.getTipo();
            switch (tipo) {
                case "identificador":
                    //Buscar en simbolos   
                    Simbolo sim = buscarEnSimbolos(expresion.getValor(), this.ambito);
                    String val = "";
                    if (sim != null) {
                        val =  sim.getValor();
                    }
                    if (val.length() == 0) {
                        valores += "nulo";
                    } else {
                        if (sim.getTipo().contains("cadena")) {
                            valores += "\"" +val.replace("\"", "") + "\"";
                        }else{
                            valores += val;
                        }
                        
                    }
                    break;
                case "arreglo":
                    List<Dimension> dimensiones = expresion.getDimensiones();
                    List<Integer> dimen = new ArrayList<>();
                    for (Dimension dimension : dimensiones) {
                        String valor = "0";
                        List<Expresion> expresiones = dimension.getExpresiones();
                        if (expresiones.isEmpty() == false) {
                            valor = obtenerValor(expresiones);
                        }
                        int valDim = Integer.parseInt(valor);
                        dimen.add(valDim);
                    }
                    Simbolo sim2 = buscarEnSimbolosArreglo(expresion.getValor(), dimen);
                    String val2 = sim2.getValor();
                    if (val2.length() == 0) {
                        valores += "nulo";
                    } else {
                        if (sim2.getTipo().contains("cadena")) {
                            valores += "\"" +val2.replace("\"", "") + "\"";
                        }else{
                            valores += val2;
                        }                        
                    }
                    valores += sim2.getValor();
                    //Buscar es simbolos, obtener el valor de las dimensiones
                    break;
                case "funcion":
                    Funcion funExpr = expresion.getFuncion();
                    String valR = validarFuncion(funExpr);
                    if (valR.length() == 0) {
                        valores += "nulo";
                    } else {
                        valores += valR;
                    }
                    //realizar la funcion, obtener el valor de los parametros
                    break;
                case "cadena":
                    valores += "\"" +expresion.getValor().replace("\"", "") + "\"";
                    break;
                default:
                    valores += expresion.getValor();
            }
        }

        String valorR = obtenerValExpr(valores);
        return valorR;
    }

    private Simbolo buscarEnSimbolos(String nombre, int ambito) {
        Simbolo simR = null;
        int tamano = this.simbolosInit.size();
        for (int i = tamano; i > 0; i--) {
            Simbolo sim = this.simbolosInit.get(i - 1);
            if (sim.getNombre().equals(nombre)) {
                if (sim.getAmbito() <= ambito) {
                    return sim;
                }
            }
        }
        return simR;
    }

    private Simbolo buscarEnSimbolosArreglo(String nombre, List<Integer> dimensiones) {
        Simbolo simR = null;
        int tamano = this.simbolosInit.size();
        for (int i = 0; i < tamano; i++) {
            Simbolo sim = this.simbolosInit.get(i - 1);
            if (sim.getNombre().equals(nombre) && sim.isEsDeclArr() == false) {
                boolean esIgual = true;
                List<Integer> dimensiones2 = sim.getDimensiones();
                for (int j = 0; j < dimensiones.size(); j++) {
                    int dim1 = dimensiones.get(j);
                    int dim2 = dimensiones2.get(j);
                    if (dim1 != dim2) {
                        esIgual = false;
                        break;
                    }
                }
                if (esIgual) {
                    return sim;
                }
            }
        }
        return simR;
    }

    private String obtenerValExpr(String entrada) {
        String valR = "";
        try {
            ExprVal valor = null;
            List<ErrorSintaxis> errores = new ArrayList<>();
            StringReader reader = new StringReader(entrada);
            ValExprLexer lexico = new ValExprLexer(reader);
            ValExprParser parser = new ValExprParser(lexico);
            try {
                parser.parse();
                errores = parser.getErroresCom();
                valor = parser.getExpresionResp();
            } catch (Exception ex) {
                System.out.println("Error irrecuperable");
                System.out.println("Causa: " + ex.getCause());
                System.out.println("Causa2: " + ex.toString());
            }
            if (errores.size() > 0) {
                JFReporteErrores jfReporteErrores = new JFReporteErrores(errores);
                jfReporteErrores.setVisible(true);
            } else {
                valR = valor.getVal();
//                System.out.println("Valor:" +valor.getVal());
//                System.out.println("Tipo:" +valor.getTipo());
//                System.out.println("Compilado Correctamente");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return valR;
    }

    private void agregarSimbolo() {
        Simbolo simbolo = new Simbolo();
        this.simbolosInit.add(simbolo);
    }

    private void agregarFuncion(Funcion funcion) {
        this.funcionesInit.add(funcion);
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    
}

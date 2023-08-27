package com.adolfosc.modelo.instrucciones;

import com.adolfosc.modelo.music.Expresion;
import com.adolfosc.modelo.music.Instruccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Declaracion extends Instruccion {
    
    private String tipoDecl;
    private boolean esKeep;
    private boolean esArreglo;
    private List<String> variables;
    private List<Dimension> dimensiones;
    private List<Expresion> valoresDecl;   
    private DeclDim declaracionDimension;

    public Declaracion(String tipoInstruccion, int identacion) {
        super(tipoInstruccion, identacion);
        this.variables = new ArrayList<>();
        this.dimensiones = new ArrayList<>();
        this.valoresDecl = new ArrayList<>(); 
    }
    
    public String getTipoDecl() {
        return tipoDecl;
    }

    public void setTipoDecl(String tipoDecl) {
        this.tipoDecl = tipoDecl;
    }

    public boolean isEsKeep() {
        return esKeep;
    }

    public void setEsKeep(boolean esKeep) {
        this.esKeep = esKeep;
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public void setEsArreglo(boolean esArreglo) {
        this.esArreglo = esArreglo;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public List<Dimension> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Dimension> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public List<Expresion> getValoresDecl() {
        return valoresDecl;
    }

    public void setValoresDecl(List<Expresion> valoresDecl) {
        this.valoresDecl = valoresDecl;
    }           
    
    public void insertarValorDecl(Expresion valor){
        this.valoresDecl.add(valor);
    }
    
    public void insertarDimension(Dimension dimension){
        this.dimensiones.add(dimension);
    }
    
    public void insertarVariable(String variable){
        this.variables.add(variable);
    }

    public DeclDim getDeclaracionDimension() {
        return declaracionDimension;
    }

    public void setDeclaracionDimension(DeclDim declaracionDimension) {
        this.declaracionDimension = declaracionDimension;
    }
    
}

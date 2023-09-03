package com.adolfosc.modelo.music;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hectoradolfo
 */
public class Condicion implements Serializable {
    
    private boolean nuloExpr1;
    private boolean nuloExpr2;
    private List<Expresion> expresion1;
    private List<Expresion> expresion2;

    public boolean isNuloExpr1() {
        return nuloExpr1;
    }

    public void setNuloExpr1(boolean nuloExpr1) {
        this.nuloExpr1 = nuloExpr1;
    }

    public boolean isNuloExpr2() {
        return nuloExpr2;
    }

    public void setNuloExpr2(boolean nuloExpr2) {
        this.nuloExpr2 = nuloExpr2;
    }

    public List<Expresion> getExpresion1() {
        return expresion1;
    }

    public void setExpresion1(List<Expresion> expresion1) {
        this.expresion1 = expresion1;
    }

    public List<Expresion> getExpresion2() {
        return expresion2;
    }

    public void setExpresion2(List<Expresion> expresion2) {
        this.expresion2 = expresion2;
    }    
}

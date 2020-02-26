/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import AST.Nodo;
import Entorno.Entorno;
import Expresion.Expresion;
import Instruccion.Instruccion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Case implements Instruccion {

    private Expresion exp;
    private LinkedList<Nodo> sentencias;
    private int linea;
    private int columna;

    public Case(Expresion exp, LinkedList<Nodo> sentencias, int linea, int columna) {
        this.exp = exp;
        this.sentencias = sentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if (getSentencias() == null) {
            return null;
        }
        for (Nodo n : getSentencias()) {
            if (n instanceof Instruccion) {
                Object result = ((Instruccion) n).ejecutar(e);
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                } else if (result != null) {
                    return result;
                }
            } else if (n instanceof Expresion) {
                Object exp = ((Expresion) n).getValor(e);
                if (exp instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) exp);
                } else if (exp != null) {
                    return exp;
                }
            }
        }
        return null;
    }

    @Override
    public int linea() {
        return this.getLinea();
    }

    @Override
    public int columna() {
        return this.getColumna();
    }

    /**
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
    }

    /**
     * @return the sentencias
     */
    public LinkedList<Nodo> getSentencias() {
        return sentencias;
    }

    /**
     * @param sentencias the sentencias to set
     */
    public void setSentencias(LinkedList<Nodo> sentencias) {
        this.sentencias = sentencias;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

}

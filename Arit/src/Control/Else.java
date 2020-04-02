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
public class Else implements Instruccion {

    private LinkedList<Nodo> sentencias;
    int linea, columna;

    public Else(LinkedList<Nodo> sentencias, int linea, int columna) {
        this.sentencias = sentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Entorno global = new Entorno(e);
        if (sentencias == null) {
            return null;
        }
        for (Nodo n : sentencias) {
            if (n instanceof Instruccion) {
                Object result = ((Instruccion) n).ejecutar(global);
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                } else if (result != null) {
                    return result;
                }
            } else if (n instanceof Expresion) {
                Object exp = ((Expresion) n).getValor(global);
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
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
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

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Else \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        for (Nodo n : sentencias) {
            nueva.append(n.toDot(this.hashCode()));
        }
        return nueva.toString();
    }

}

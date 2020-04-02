/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import AST.Nodo;
import Entorno.Entorno;
import Instruccion.Instruccion;
import Expresion.Expresion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class ElseIf implements Instruccion {

    private Expresion condicion;
    private LinkedList<Nodo> sentencias;
    private int linea;
    private int columna;

    public ElseIf(Expresion condicion, LinkedList<Nodo> sentencias, int linea, int columna) {
        this.condicion = condicion;
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
                Object result = ((Expresion) n).getValor(global);
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                } else if (result != null) {
                    return result;
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

    /**
     * @return the condicion
     */
    public Expresion getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
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

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Else_IF \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append("node").append(this.hashCode() + 1).append("[label=\"Condicion \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        nueva.append("node").append(this.hashCode() + 2).append("[label=\"Cuerpo \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 2).append(";\n");
        nueva.append(condicion.toDot(this.hashCode() + 1));
        for (Nodo n : sentencias) {
            nueva.append(n.toDot(this.hashCode() + 2));
        }
        return nueva.toString();
    }

}

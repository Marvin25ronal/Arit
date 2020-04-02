/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Objetos.Nulo;

/**
 *
 * @author marvi
 */
public class Literal implements Expresion {

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }

    /**
     * @return the tipo
     */
    public TipoExp getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoExp tipo) {
        this.tipo = tipo;
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

    private Object valor;
    private TipoExp tipo;
    private int linea;
    private int columna;

    public Literal(Object valor, TipoExp tipo, int linea, int columna) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        /*if (valor instanceof Nulo) {
            return ((Nulo) valor).getValor(e);
        }*/
        return this.getValor();
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return this.getTipo();
    }

    @Override
    public int linea() {
        return this.getLinea();
    }

    @Override
    public int columna() {
        return this.getColumna();
    }

    @Override
    public String toString() {
        return getValor().toString();
    }

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Exp \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append("node").append(this.hashCode()+1).append("[label=\"").append(valor.toString()).append(" \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode()+1).append(";\n");
        return nueva.toString();
    }

}

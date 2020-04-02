/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Expresion.TipoExp.Tipos;

/**
 *
 * @author marvi
 */
public class Default implements Expresion {

    int linea, columna;

    public Default(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        return this;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return new TipoExp(Tipos.NULO);
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Exp \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append("node").append(this.hashCode() + 1).append("[label=\"Default \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        return nueva.toString();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Expresion.Expresion;
import Objetos.Nulo;
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class Print implements Instruccion {

    /**
     * @return the toPrint
     */
    public Expresion getToPrint() {
        return toPrint;
    }

    /**
     * @param toPrint the toPrint to set
     */
    public void setToPrint(Expresion toPrint) {
        this.toPrint = toPrint;
    }

    private Expresion toPrint;
    int linea, columna;

    public Print(Expresion toPrint, int linea, int columna) {
        this.toPrint = toPrint;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Object val = getToPrint().getValor(e);
        //
        if (val == null) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No retorno nada el objeto del print", linea, columna));
            val = "null";
        }
        if (val instanceof Reportes.Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Reportes.Errores) val);
            return null;
        }
        if (val instanceof Nulo) {
            Globales.VarGlobales.getInstance().getConsola().append(((Nulo) val).toString());
            return null;
        }
        Globales.VarGlobales.getInstance().getConsola().append(val.toString() + "\n");
        return null;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("nodo").append(padre + 1).append("[label=\"Print \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];");
        nueva.append("node").append(padre).append("->node").append(padre + 1).append(";\n");
        nueva.append(toPrint.toDot(padre + 1));
        return nueva.toString();
    }

}

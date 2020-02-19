/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import com.sun.xml.internal.bind.v2.TODO;

/**
 *
 * @author marvi
 */
public class Print implements Intruccion {

    private int linea, columna;
    Expresion.Expresion toPrint;

    public Print(int linea, int columna, Expresion.Expresion toprint) {
        this.linea = linea;
        this.columna = columna;
        this.toPrint = toprint;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Object val = toPrint.getValor(e);
        //
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

}

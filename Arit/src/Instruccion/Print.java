/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Expresion.Expresion;
import com.sun.xml.internal.bind.v2.TODO;

/**
 *
 * @author marvi
 */
public class Print implements Instruccion {

    Expresion toPrint;
    int linea,columna;

    public Print(Expresion toPrint, int linea, int columna) {
        this.toPrint = toPrint;
        this.linea = linea;
        this.columna = columna;
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

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
import com.sun.xml.internal.bind.v2.TODO;

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
        if (val instanceof Reportes.Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Reportes.Errores) val);
            return null;
        }
        if (val instanceof Nulo) {
            Errores nuevo = new Errores(Errores.TipoError.SEMANTICO, "Valor NULO", linea, columna);
            Globales.VarGlobales.getInstance().AgregarEU(nuevo);
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

}

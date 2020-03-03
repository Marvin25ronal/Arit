/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class Identificador implements Expresion {

    private String val;
    private int linea;
    private int columna;

    public Identificador(String val, int linea, int columna) {
        this.val = val.toLowerCase();
        this.linea = linea;
        this.columna = columna;
    }

    /**
     * @return the val
     */
    public String getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(String val) {
        this.val = val;
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
    public Object getValor(Entorno e) {
        if (e.ExisteVariable(val)) {
            return e.get(val);
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se encontro la variable", linea, columna);
        }
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        Object s = getValor(e);
        if (s == null|| s instanceof Errores) {
            return null;

        }
        return ((Simbolo)s).getTipo();
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

}

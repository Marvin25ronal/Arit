/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Expresion.TipoExp.Tipos;
import Objetos.Vector;
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class AccesoUnico implements Expresion {

    private Expresion indice;
    private int linea;
    private int columna;
    private Object objeto;

    public AccesoUnico(Expresion indice, int linea, int columna) {
        this.indice = indice;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (indice.getTipo(e).tp != Tipos.INTEGER) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser de tipo numerico", linea, columna);
        }
        Object i = indice.getValor(e);
        if (i instanceof Errores) {
            return i;
        }
        if (objeto instanceof Vector) {
            return AccesoVector(e);
        }
        return null;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * @return the indice
     */
    public Expresion getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Expresion indice) {
        this.indice = indice;
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

    /**
     * @return the objeto
     */
    public Object getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    private Object AccesoVector(Entorno e) {
        return null;
    }

}

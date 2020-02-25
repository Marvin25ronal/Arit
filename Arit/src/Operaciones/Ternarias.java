/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operaciones;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class Ternarias implements Expresion {

    private Expresion cond;
    private Expresion isTrue;
    private Expresion isFalse;
    int linea, columna;

    public Ternarias(Expresion cond, Expresion isTrue, Expresion isFalse, int linea, int columna) {
        this.cond = cond;
        this.isTrue = isTrue;
        this.isFalse = isFalse;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        Object con = cond.getValor(e);
        if (con == null || con instanceof Errores) {
            return con;
        }
        if (cond.getTipo(e).tp == Tipos.BOOLEAN) {
            return Boolean.parseBoolean(con.toString()) ? isTrue.getValor(e) : isFalse.getValor(e);
        } else {
            //validar matrices jajaja
            return new Errores(Errores.TipoError.SEMANTICO, "La condicion tiene que ser de tipo boolean", linea, columna);
        }
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        Object condicion = cond.getValor(e);
        if (condicion instanceof Errores) {
            return null;
        } else if (condicion == null) {
            return null;
        } else {
            return (boolean) condicion ? isTrue.getTipo(e) : isFalse.getTipo(e);
        }
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
     * @return the cond
     */
    public Expresion getCond() {
        return cond;
    }

    /**
     * @param cond the cond to set
     */
    public void setCond(Expresion cond) {
        this.cond = cond;
    }

    /**
     * @return the isTrue
     */
    public Expresion getIsTrue() {
        return isTrue;
    }

    /**
     * @param isTrue the isTrue to set
     */
    public void setIsTrue(Expresion isTrue) {
        this.isTrue = isTrue;
    }

    /**
     * @return the isFalse
     */
    public Expresion getIsFalse() {
        return isFalse;
    }

    /**
     * @param isFalse the isFalse to set
     */
    public void setIsFalse(Expresion isFalse) {
        this.isFalse = isFalse;
    }
}

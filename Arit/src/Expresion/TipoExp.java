/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;

/**
 *
 * @author marvi
 */
public class TipoExp {

    public enum Tipos {
        INTEGER, BOOLEAN, STRING, NUMERIC, NULO, VECTOR
    }
    public Tipos tp;

    public TipoExp(Tipos tp) {
        this.tp = tp;
    }

    public boolean isNumeric() {
        return tp == Tipos.NUMERIC;
    }

    public boolean esNumero() {
        return tp == Tipos.INTEGER || tp == Tipos.NUMERIC;
    }

    public boolean isBoolean() {
        return tp == Tipos.BOOLEAN;
    }

    public boolean isString() {
        return tp == Tipos.STRING;
    }

    public boolean isInt() {
        return tp == Tipos.INTEGER;
    }

    public boolean isNulo() {
        return tp == Tipos.NULO;
    }

    public boolean isVector() {
        return tp == Tipos.VECTOR;
    }

    public boolean isPrimitive(Entorno e) {
        if (tp == Tipos.VECTOR) {
            return false;
        }
        return true;
    }

}

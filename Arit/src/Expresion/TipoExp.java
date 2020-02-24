/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

/**
 *
 * @author marvi
 */
public class TipoExp {

    public enum Tipos {
        INTEGER, BOOLEAN, STRING, NUMERIC, NULO,VECTOR
    }
    public Tipos tp;
    public String tr = "";

    public TipoExp(Tipos tp) {
        this.tp = tp;
    }

    public TipoExp(String tr) {
        this.tr = tr;
    }

    public TipoExp(Tipos tp, String tr) {
        this.tp = tp;
        this.tr = tr;
    }

    public boolean isNumeric() {
        return  tp == Tipos.NUMERIC;
    }
    public boolean esNumero(){
        return tp==Tipos.INTEGER||tp==Tipos.NUMERIC;
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
    public boolean isNulo(){
        return tp==Tipos.NULO;
    }

}

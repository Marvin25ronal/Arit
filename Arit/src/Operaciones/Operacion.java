/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operaciones;

import Expresion.Expresion;
import Expresion.TipoExp;

/**
 *
 * @author marvi
 */
public abstract class Operacion implements Expresion {

    public enum Operador {
        SUMA, RESTA, DIVISION, MULTIPLICACION, POTENCIA, OR, AND, NOT, MAYOR, MENOR, MAYOR_IGUAL, MENOR_IGUAL, IGUAL_IGUAL, DISTINTO, MODULO
    }
    public Expresion op1;
    public Expresion op2;
    public Operador op;
    public Object valor2;

    public Operacion(Expresion op1, Expresion op2, Operador op, Object valor2) {
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
        this.valor2 = valor2;
    }

    public Operacion(Expresion op1, Expresion op2, Operador op) {
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
    }

    public abstract TipoExp max(TipoExp a, TipoExp b);

    public boolean OperadorInteger(Expresion op1, Expresion op2, Entorno.Entorno e) {
        return op1.getTipo(e).isInt() || op2.getTipo(e).isInt();
    }

    public boolean OperadorBoolean(Expresion op1, Expresion op2, Entorno.Entorno e) {
        return op1.getTipo(e).isBoolean() || op2.getTipo(e).isBoolean();
    }

    public boolean OperadorString(Expresion op1, Expresion op2, Entorno.Entorno e) {
        return op1.getTipo(e).isString() || op2.getTipo(e).isString();
    }

    public boolean OperadorNulo(Expresion op1, Expresion op2, Entorno.Entorno e) {
        return op1.getTipo(e).isNulo() || op2.getTipo(e).isNulo();
    }

    public boolean OperadorDouble(Expresion op1, Expresion op2, Entorno.Entorno e) {
        return op1.getTipo(e).isNumeric() || op2.getTipo(e).isNumeric();
    }
}

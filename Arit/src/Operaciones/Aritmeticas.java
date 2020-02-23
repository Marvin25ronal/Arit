/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operaciones;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class Aritmeticas extends Operacion {

    int linea, columna;

    public Aritmeticas(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op);
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public TipoExp max(TipoExp a, TipoExp b) {
        return tipoDominante(a, b);
    }

    private TipoExp tipoDominante(TipoExp t1, TipoExp t2) {
        if (t1 == null || t2 == null) {
            return null;
        }
        if (t1.isNulo() || t2.isNulo()) {
            return new TipoExp(Tipos.NULO);
        }
        if (t1.esNumero() && t2.esNumero() && op == Operador.POTENCIA) {
            return new TipoExp(Tipos.NUMERIC);
        }

        if (t1.isString() || t2.isString()) {
            return new TipoExp(Tipos.STRING);
        } else if (t1.isBoolean() || t2.isBoolean()) {
            return new TipoExp(Tipos.BOOLEAN);
        } else if (t1.isNumeric() || t2.isNumeric()) {
            return new TipoExp(Tipos.NUMERIC);
        } else if (t1.isInt() || t2.isInt()) {
            return new TipoExp(Tipos.INTEGER);
        }
        return null;
    }

    @Override
    public Object getValor(Entorno e) {
        Object r = val(e);
        if (r instanceof Literal) {
            return ((Literal) r).getValor(e);
        }
        return r;
    }

    private Object val(Entorno e) {
        if (op1 == null || op2 == null) {
            return null;
        }
        if (op1 instanceof Errores) {
            return op1;
        } else if (op2 instanceof Errores) {
            return op2;
        }
        switch (op) {
            case SUMA:
                return new Suma(op1, op2, op, linea, columna).ejecutar(e);
            case RESTA:
                return new Resta(op1, op2, op, linea, columna).ejecutar(e);
            case MULTIPLICACION:
                return new Multiplicacion(op1, op2, op, linea, columna).ejecutar(e);
            case DIVISION:
                return new Division(op1, op2, op, linea, columna).ejecutar(e);
            case POTENCIA:
                return new Potencia(op1, op2, op, linea, columna).ejecutar(e);
            case MODULO:
                return new Modulo(op1, op2, op, linea, columna).Ejecutar(e);
        }
        return null;
    }

    @Override
    public TipoExp getTipo(Entorno e) {

        return tipoDominante(op1.getTipo(e), op2.getTipo(e));
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

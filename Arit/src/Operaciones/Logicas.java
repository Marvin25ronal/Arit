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
public class Logicas extends Operacion {

    int linea, columna;

    public Logicas(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op);
        this.linea = linea;
        this.columna = columna;
    }

    private boolean Correctos(Entorno e) {
        return this.op1.getTipo(e).tp == Tipos.BOOLEAN && this.op2.getTipo(e).tp == Tipos.BOOLEAN;
    }

    @Override
    public TipoExp max(TipoExp a, TipoExp b) {
        return tipoDominante(a, b);
    }

    private TipoExp tipoDominante(TipoExp t1, TipoExp t2) {
        if (t1.isString() || t2.isString()) {
            return null;
        } else if (t1.esNumero() || t2.esNumero()) {
            return null;
        } else if (t1.isNulo() || t2.isNulo()) {
            return null;
        } else if (t1.isBoolean() || t2.isBoolean()) {
            return new TipoExp(Tipos.BOOLEAN);
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
        TipoExp top1 = op1.getTipo(e);
        TipoExp top2 = op2.getTipo(e);
        TipoExp aux = max(top1, top2);
        Object valor1 = op1.getValor(e);
        Object valor2 = op2.getValor(e);
        if (valor1 instanceof Errores) {
            return valor1;
        } else if (valor2 instanceof Errores) {
            return valor2;
        }
        if (aux == null) {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden comparar otros tipos que no sean booleanos", linea, columna);
        }
        if (Correctos(e)) {
            switch (op) {
                case AND:
                    return new Literal((Boolean.parseBoolean(valor1.toString()) && (Boolean.parseBoolean(valor2.toString()))), new TipoExp(Tipos.BOOLEAN), linea, columna);
                case OR:
                    return new Literal((Boolean.parseBoolean(valor1.toString()) || (Boolean.parseBoolean(valor2.toString()))), new TipoExp(Tipos.BOOLEAN), linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden comparar tipos que no sean booleanos ", linea, columna);
        }
        return null;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return new TipoExp(Tipos.BOOLEAN);
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

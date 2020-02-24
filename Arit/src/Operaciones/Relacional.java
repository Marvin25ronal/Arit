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
public class Relacional extends Operacion {

    int linea, columna;

    public Relacional(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op);
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public TipoExp max(TipoExp a, TipoExp b) {
        return tipoDominante(a, b);
    }

    private TipoExp tipoDominante(TipoExp t1, TipoExp t2) {
        return new TipoExp(Tipos.BOOLEAN);
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
        Object valor1 = op1.getValor(e);
        Object valor2 = op2.getValor(e);
        if (valor1 instanceof Errores) {
            return valor1;
        } else if (valor2 instanceof Errores) {
            return valor2;
        }
        if (top1.isNulo() && top2.isNulo()) {
            switch (op) {
                case IGUAL_IGUAL:
                    return new Literal(true, new TipoExp(Tipos.BOOLEAN), linea, columna);
                case DISTINTO:
                    return new Literal(false, new TipoExp(Tipos.BOOLEAN), linea, columna);
                default:
                    return new Errores(Errores.TipoError.SEMANTICO, "No se pueden comparar nulos con esos operadores", linea, columna);
            }
        } else if (top1.isString() || top2.isString()) {
            String cad1 = valor1.toString();
            String cad2 = valor2.toString();
            switch (op) {
                case IGUAL_IGUAL:
                    return new Literal(cad1.equals(cad2), new TipoExp(Tipos.BOOLEAN), linea, columna);
                case DISTINTO:
                    return new Literal(cad1.equals(cad2), new TipoExp(Tipos.BOOLEAN), linea, columna);
                case MAYOR:
                    return new Literal(valorcad(cad1) > valorcad(cad2), new TipoExp(Tipos.BOOLEAN), linea, columna);
                case MENOR:
                    return new Literal(valorcad(cad1) < valorcad(cad2), new TipoExp(Tipos.BOOLEAN), linea, columna);
                case MAYOR_IGUAL:
                    return new Literal(valorcad(cad1) >= valorcad(cad2), new TipoExp(Tipos.BOOLEAN), linea, columna);
                case MENOR_IGUAL:
                    return new Literal(valorcad(cad1) <= valorcad(cad2), new TipoExp(Tipos.BOOLEAN), linea, columna);
            }

        } else if (top1.esNumero() || top2.esNumero()) {

        }
        return null;
    }

    private int valorcad(String a) {
        int cada = 0;
        for (int i = 0; i < a.length(); i++) {
            cada += (int) a.charAt(i);
        }
        return cada;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return new TipoExp(Tipos.BOOLEAN);
    }

    @Override
    public int linea() {
        return this.columna;
    }

    @Override
    public int columna() {
        return this.columna;
    }

}

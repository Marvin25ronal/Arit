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
public class Unarias extends Operacion {

    int linea, columna;

    public Unarias(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op);
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public TipoExp max(TipoExp a, TipoExp b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if (op1 == null) {
            return null;
        }
        Object valor = op1.getValor(e);
        TipoExp tip = op1.getTipo(e);
        if (valor instanceof Errores) {
            return valor;
        } else {
            //comparamos vectores
            if (tip.tp == Tipos.VECTOR) {

            } else {
                if (tip.esNumero() && (Double.parseDouble(valor.toString()) % 1 != 0)) {
                    tip.tp = TipoExp.Tipos.NUMERIC;
                }
                if (op == Operador.NOT) {
                    switch (tip.tp) {
                        case BOOLEAN:
                            return new Literal(!Boolean.parseBoolean(valor.toString()), new TipoExp(Tipos.BOOLEAN), linea, columna);
                        default:
                            return new Errores(Errores.TipoError.SEMANTICO, "No se pude hacer la negacion de ese tipo de operadores", linea, columna);
                    }

                } else {
                    switch (tip.tp) {
                        case NUMERIC:
                            return new Literal(-Double.parseDouble(valor.toString()), new TipoExp(Tipos.NUMERIC), linea, columna);
                        case INTEGER:
                            return new Literal(-Integer.parseInt(valor.toString()), new TipoExp(Tipos.INTEGER), linea, columna);
                        default:
                            return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer la negacion de ese tipo", linea, columna);
                    }
                }

            }

        }
        return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer esa operacion Unarios", linea, columna);

    }

    @Override
    public TipoExp getTipo(Entorno e) {
        if (op1 == null) {
            return null;
        }
        return op1.getTipo(e);
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

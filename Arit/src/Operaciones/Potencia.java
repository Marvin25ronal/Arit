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
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class Potencia extends Aritmeticas {

    public Potencia(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op, linea, columna);
    }

    public Object ejecutar(Entorno e) {
        TipoExp top1 = op1.getTipo(e);
        TipoExp top2 = op2.getTipo(e);
        TipoExp aux = max(top1, top2);

        //Comparar si son vectores, listas, matrices o array
        Object valor1 = op1.getValor(e);
        Object valor2 = op2.getValor(e);
        if (valor1 instanceof Errores) {
            return valor1;
        } else if (valor2 instanceof Errores) {
            return valor2;
        }
        //esto para cuando la division trae algun numero raro
        if (aux.esNumero()&& (Double.parseDouble(valor1.toString()) % 1 != 0 || Double.parseDouble(valor2.toString()) % 1 != 0)) {
            aux.tp = TipoExp.Tipos.NUMERIC;      
        }
        switch (aux.tp) {
            case NULO:
                return new Errores(Errores.TipoError.SEMANTICO, "No se puede ELEVAR valores NULOS", linea, columna);
            case INTEGER:
                return new Literal(Math.pow(Double.parseDouble(valor1.toString()), Double.parseDouble(valor2.toString())), new TipoExp(TipoExp.Tipos.NUMERIC), linea, columna);
            case NUMERIC:
                return new Literal(Math.pow(Double.parseDouble(valor1.toString()), Double.parseDouble(valor2.toString())), new TipoExp(TipoExp.Tipos.NUMERIC), linea, columna);
            default:
                return new Errores(Errores.TipoError.SEMANTICO, "No se pueden ELEVAR ese tipo de objetos", linea, columna);
        }

    }
}

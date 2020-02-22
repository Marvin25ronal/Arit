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
public class Division extends Aritmeticas {

    public Division(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
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

        switch (aux.tp) {
            case NULO:
                return new Errores(Errores.TipoError.SEMANTICO, "No se puede DIVIDIR valores NULOS", linea, columna);
            case INTEGER:
                return Double.parseDouble(valor1.toString()) % Double.parseDouble(valor2.toString()) == 0 ? new Literal(Integer.parseInt(valor1.toString()) / Integer.parseInt(valor2.toString()), new TipoExp(TipoExp.Tipos.INTEGER), linea, columna) : new Literal(Double.parseDouble(valor1.toString()) / Double.parseDouble(valor2.toString()), new TipoExp(TipoExp.Tipos.NUMERIC), linea, columna);
            case NUMERIC:
                return new Literal(Double.parseDouble(valor1.toString()) / Double.parseDouble(valor2.toString()), new TipoExp(TipoExp.Tipos.NUMERIC), linea, columna);
            default:
                return new Errores(Errores.TipoError.SEMANTICO, "No se pueden DIVIDIR ese tipo de objetos", linea, columna);
        }

    }
}

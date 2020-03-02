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
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;

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
        if (aux.esNumero() && (Double.parseDouble(valor1.toString()) % 1 != 0 || Double.parseDouble(valor2.toString()) % 1 != 0)) {
            aux.tp = TipoExp.Tipos.NUMERIC;
        }
        if (aux.tp == TipoExp.Tipos.VECTOR) {
            //suma de vectores
            //Caso 1 solo uno es vector
            //Caso 2 ambos son vectores
            if (top1.isVector() && top2.isVector()) {
                return PotenciaVectoresVectores((Vector) valor1, (Vector) valor2, e);
            }
            return top1.isVector() ? PotenciaVectores((Vector) valor1, top2, valor2, e, true) : PotenciaVectores((Vector) valor2, top1, valor1, e, false);
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

    private Object PotenciaVectores(Vector v, TipoExp tipoexp, Object valorsumando, Entorno e, boolean primero) {
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        LinkedList<Object> NuevoVal = new LinkedList<>();
        Literal l = null;
        Object aux = null;
        for (int i = 0; i < lista.size(); i++) {
            l = (Literal) lista.get(i);
            aux = primero ? new Potencia(l, new Literal(valorsumando, tipoexp, linea, columna), op, linea, columna).ejecutar(e) : new Potencia(new Literal(valorsumando, tipoexp, linea, columna), l, op, linea, columna).ejecutar(e);
            if (aux instanceof Errores) {
                return aux;
            }
            NuevoVal.add(aux);
        }
        Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), max(v.getTiposecundario(), tipoexp), NuevoVal);
        return nuevo;
    }

    private Object PotenciaVectoresVectores(Vector v1, Vector v2, Entorno e) {
        LinkedList<Object> a = Globales.VarGlobales.getInstance().clonarListaVector(v1.getDimensiones(), e);
        LinkedList<Object> b = Globales.VarGlobales.getInstance().clonarListaVector(v2.getDimensiones(), e);
        LinkedList<Object> nuevos = new LinkedList<>();
        Object res;
        if (a.size() == b.size()) {
            for (int i = 0; i < a.size(); i++) {
                res = new Potencia((Literal) a.get(i), (Literal) b.get(i), op, linea, columna).ejecutar(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (a.size() == 1) {
            for (int i = 0; i < b.size(); i++) {
                res = new Potencia((Literal) a.get(0), (Literal) b.get(i), op, linea, columna).ejecutar(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (b.size() == 1) {
            for (int i = 0; i < a.size(); i++) {
                res = new Potencia((Literal) a.get(i), (Literal) b.get(0), op, linea, columna).ejecutar(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden elevar vectores que no sean de un elemento o igual elementos", linea, columna);
        }

    }
}

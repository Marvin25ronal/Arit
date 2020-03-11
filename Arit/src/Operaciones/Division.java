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
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Division extends Aritmeticas {

    public Division(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op, linea, columna);
    }

    public Object ejecutar(Entorno e) {
        

        //Comparar si son vectores, listas, matrices o array
        Object valor1 = op1.getValor(e);
        Object valor2 = op2.getValor(e);
        if (valor1 instanceof Errores) {
            return valor1;
        } else if (valor2 instanceof Errores) {
            return valor2;
        }
        TipoExp top1 = Globales.VarGlobales.getInstance().obtenerTipo(valor1, e);
        TipoExp top2 = Globales.VarGlobales.getInstance().obtenerTipo(valor2, e);
        TipoExp aux = max(top1, top2);
        //esto para cuando la division trae algun numero raro
        if (aux.esNumero() && (Double.parseDouble(valor1.toString()) % 1 != 0 || Double.parseDouble(valor2.toString()) % 1 != 0)) {
            aux.tp = TipoExp.Tipos.NUMERIC;
            //reglas de la division
            double aux2 = Double.parseDouble(valor2.toString());
            if (aux2 == 0) {
                return new Errores(Errores.TipoError.SEMANTICO, "No se puede dividir entre 0 ", linea, columna);
            }
        }
        if (aux.tp == TipoExp.Tipos.VECTOR) {
            //suma de vectores
            //Caso 1 solo uno es vector
            //Caso 2 ambos son vectores
            if (top1.isVector() && top2.isVector()) {
                return DivisionVectoresVectores((EstructuraLineal) valor1, (EstructuraLineal) valor2, e);
            }
            return top1.isVector() ? DivisionVectores((EstructuraLineal) valor1, top2, valor2, e, true) : DivisionVectores((EstructuraLineal) valor2, top1, valor1, e, false);
        } else if (aux.isMatrix()) {
            //suma de matriz con matrices
            if (top1.isMatrix() && top2.isMatrix()) {
                return DivisionMatriz_Matriz((Matrix) valor1, (Matrix) valor2, e);
            } //con un vector
            else if (top1.isVector() || top2.isVector()) //con un primitivo
            {
                return top1.isMatrix() ? DivisionMatriz_Vector((Matrix) valor1, (EstructuraLineal) valor2, e) : DivisionMatriz_Vector((Matrix) valor2, (EstructuraLineal) valor1, e);
            } else if (top1.isPrimitive(e) || top2.isPrimitive(e)) {
                return top1.isMatrix() ? DivisionMatriz_Primitivo((Matrix) valor1, valor2, e) : DivisionMatriz_Primitivo((Matrix) valor2, valor1, e);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La matriz no puede dividir con ese tipo de objeto " + top1.toString() + top2.toString(), linea, columna);
            }
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
    private Object DivisionMatriz_Primitivo(Matrix a, Object b, Entorno e) {
        LinkedList<LinkedList<Object>> columnas = new LinkedList<>();
        TipoExp tipoO = Globales.VarGlobales.getInstance().obtenerTipo(b, e);
        TipoExp dominante = max(a.getTiposecundario(), tipoO);
        Literal lb = new Literal(b, tipoO, linea, this.columna);
        for (int i = 0; i < a.getColumna(); i++) {
            LinkedList<Object> filas = new LinkedList<>();
            for (int j = 0; j < a.getFila(); j++) {
                EstructuraLineal va = (EstructuraLineal) a.getColumnas().get(i).get(j);
                Object res = new Division((Expresion) va.getDimensiones().get(0), (Expresion) lb, op, linea, columna).getValor(e);
                if (res instanceof Errores) {
                    return res;
                }
                TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                res = CastearValor(dominante, res, origen);
                Literal nueva = new Literal(res, dominante, linea, columna);
                LinkedList<Object> dato = new LinkedList<>();
                dato.add(nueva);
                EstructuraLineal vector = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(dominante.tp), dato);
                filas.add(vector);
            }
            columnas.add(filas);
        }
        Matrix nm = new Matrix(columnas, new TipoExp(TipoExp.Tipos.MATRIX), dominante, "", a.getColumna(), a.getFila());
        return nm;
    }

    private Object DivisionMatriz_Vector(Matrix a, EstructuraLineal b, Entorno e) {
        if (b.getDimensiones().size() == 1) {
            LinkedList<LinkedList<Object>> columnas = new LinkedList<>();
            TipoExp dominante = max(a.getTiposecundario(), b.getTiposecundario());
            Literal lb = (Literal) b.getDimensiones().get(0);
            for (int i = 0; i < a.getColumna(); i++) {
                LinkedList<Object> filas = new LinkedList<>();
                for (int j = 0; j < a.getFila(); j++) {
                    EstructuraLineal va = (EstructuraLineal) a.getColumnas().get(i).get(j);
                    Object res = new Division((Expresion) va.getDimensiones().get(0), (Expresion) lb, op, linea, columna).getValor(e);
                    if (res instanceof Errores) {
                        return res;
                    }
                    TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                    res = CastearValor(dominante, res, origen);
                    Literal nueva = new Literal(res, dominante, linea, columna);
                    LinkedList<Object> dato = new LinkedList<>();
                    dato.add(nueva);
                    EstructuraLineal vector = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(dominante.tp), dato);
                    filas.add(vector);
                }
                columnas.add(filas);
            }
            Matrix nm = new Matrix(columnas, new TipoExp(TipoExp.Tipos.MATRIX), dominante, "", a.getColumna(), a.getFila());
            return nm;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que tener un elemento ", linea, columna);
        }
    }

    private Object DivisionMatriz_Matriz(Matrix a, Matrix b, Entorno e) {
        //tienen que ser del mismo tam
        if ((a.getColumna() == b.getColumna()) && (a.getFila() == b.getFila())) {
            LinkedList<LinkedList<Object>> columnas = new LinkedList<>();
            TipoExp dominante = max(a.getTiposecundario(), b.getTiposecundario());
            for (int i = 0; i < a.getColumna(); i++) {
                LinkedList<Object> filas = new LinkedList<>();
                for (int j = 0; j < a.getFila(); j++) {
                    EstructuraLineal va = (EstructuraLineal) a.getColumnas().get(i).get(j);
                    EstructuraLineal vb = (EstructuraLineal) b.getColumnas().get(i).get(j);
                    Object res = new Division((Expresion) va.getDimensiones().get(0), (Expresion) vb.getDimensiones().get(0), op, linea, columna).getValor(e);
                    if (res instanceof Errores) {
                        return res;
                    }
                    TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                    res = CastearValor(dominante, res, origen);
                    Literal nueva = new Literal(res, dominante, linea, columna);
                    LinkedList<Object> dato = new LinkedList<>();
                    dato.add(nueva);
                    EstructuraLineal vector = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(dominante.tp), dato);
                    filas.add(vector);
                }
                columnas.add(filas);
            }
            Matrix nm = new Matrix(columnas, new TipoExp(TipoExp.Tipos.MATRIX), dominante, "", a.getColumna(), a.getFila());
            return nm;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se puede dividir matrices que no sean del mismo tamaño", linea, columna);
        }
    }
    private Object DivisionVectores(EstructuraLineal v, TipoExp tipoexp, Object valorsumando, Entorno e, boolean primero) {
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        LinkedList<Object> NuevoVal = new LinkedList<>();
        Literal l = null;
        Object aux = null;
        for (int i = 0; i < lista.size(); i++) {
            l = (Literal) lista.get(i);
            aux = primero ? new Division(l, new Literal(valorsumando, tipoexp, linea, columna), op, linea, columna).ejecutar(e) : new Division(new Literal(valorsumando, tipoexp, linea, columna), l, op, linea, columna).ejecutar(e);
            if (aux instanceof Errores) {
                return aux;
            }
            NuevoVal.add(aux);
        }
        EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), max(v.getTiposecundario(), tipoexp), NuevoVal);
        return nuevo;
    }

    private Object DivisionVectoresVectores(EstructuraLineal v1, EstructuraLineal v2, Entorno e) {
        LinkedList<Object> a = Globales.VarGlobales.getInstance().clonarListaVector(v1.getDimensiones(), e);
        LinkedList<Object> b = Globales.VarGlobales.getInstance().clonarListaVector(v2.getDimensiones(), e);
        LinkedList<Object> nuevos = new LinkedList<>();
        Object res;
        if (a.size() == b.size()) {
            for (int i = 0; i < a.size(); i++) {
                res = new Division((Literal) a.get(i), (Literal) b.get(i), op, linea, columna).ejecutar(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (a.size() == 1) {
            for (int i = 0; i < b.size(); i++) {
                res = new Division((Literal) a.get(0), (Literal) b.get(i), op, linea, columna).ejecutar(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (b.size() == 1) {
            for (int i = 0; i < a.size(); i++) {
                res = new Division((Literal) a.get(i), (Literal) b.get(0), op, linea, columna).ejecutar(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden division vectores que no sean de un elemento o igual elementos", linea, columna);
        }

    }
}

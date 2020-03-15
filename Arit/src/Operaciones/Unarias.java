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
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Reportes.Errores;
import java.util.LinkedList;

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

        if (valor instanceof Errores) {
            return valor;
        } else {
            TipoExp tip = Globales.VarGlobales.getInstance().obtenerTipo(valor, e);
            //comparamos vectores
            if (tip.tp == Tipos.VECTOR) {
                if (op == Operador.NOT) {
                    return NotVectores((EstructuraLineal) valor, e);
                } else {
                    return MenosVectores((EstructuraLineal) valor, e);
                }
            } else if (tip.isMatrix()) {
                if (op == Operador.NOT) {
                    return NotMatriz((Matrix) valor, e);
                } else {
                    return MenosMatriz((Matrix) valor, e);
                }
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
        //return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer esa operacion Unarios", linea, columna);

    }

    private Object NotMatriz(Matrix m, Entorno e) {
        LinkedList<LinkedList<Object>> nuevosval = new LinkedList<>();
        for (int i = 0; i < m.getColumna(); i++) {
            LinkedList<Object> filas = new LinkedList<>();
            for (int j = 0; j < m.getFila(); j++) {
                EstructuraLineal va = (EstructuraLineal) m.getColumnas().get(i).get(j);
                Object res = new Unarias((Expresion) va.getDimensiones().get(0), null, op, linea, columna).getValor(e);
                if (res instanceof Errores) {
                    ((Errores) res).setMensaje("No se pudo hacer Not con  la matriz, por los tipos que no son booleanos");
                    return res;
                }
                TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                //res = CastearValor(dominante, res, origen);
                Literal nueva = new Literal(res, new TipoExp(Tipos.BOOLEAN), linea, columna);
                LinkedList<Object> dato = new LinkedList<>();
                dato.add(nueva);
                EstructuraLineal vector = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.BOOLEAN), dato);
                filas.add(vector);
            }
            nuevosval.add(filas);
        }
        Matrix nm = new Matrix(nuevosval, new TipoExp(Tipos.MATRIX), new TipoExp(Tipos.BOOLEAN), "", m.getColumna(), m.getFila());
        return nm;
    }

    private Object NotVectores(EstructuraLineal v, Entorno e) {
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        LinkedList<Object> NuevoVal = new LinkedList<>();
        Literal l = null;
        Object aux = null;
        for (int i = 0; i < lista.size(); i++) {
            l = (Literal) lista.get(i);
            aux = new Unarias(l, null, op, linea, columna).val(e);
            if (aux instanceof Errores) {
                ((Errores) aux).setMensaje("No se pudo hacer Not con  el arreglo, por los tipos que no son booleanos");
                return aux;
            }
            NuevoVal.add(aux);
        }
        EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), v.getTiposecundario(), NuevoVal);
        return nuevo;
    }

    private Object MenosVectores(EstructuraLineal v, Entorno e) {
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        LinkedList<Object> NuevoVal = new LinkedList<>();
        Literal l = null;
        Object aux = null;
        for (int i = 0; i < lista.size(); i++) {
            l = (Literal) lista.get(i);
            aux = new Multiplicacion(new Literal(-1, new TipoExp(Tipos.INTEGER), linea, columna), l, Operador.MULTIPLICACION, linea, columna).ejecutar(e);
            if (aux instanceof Errores) {
                ((Errores) aux).setMensaje("No se pudo negar la matriz, por los tipos que no son numericos");
                return aux;
            }
            NuevoVal.add(aux);
        }
        EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), v.getTiposecundario(), NuevoVal);
        return nuevo;
    }

    private Object MenosMatriz(Matrix m, Entorno e) {
        LinkedList<LinkedList<Object>> nuevosval = new LinkedList<>();
        TipoExp ddd = null;
        for (int i = 0; i < m.getColumna(); i++) {
            LinkedList<Object> filas = new LinkedList<>();
            for (int j = 0; j < m.getFila(); j++) {
                EstructuraLineal va = (EstructuraLineal) m.getColumnas().get(i).get(j);
                Object res = new Multiplicacion(new Literal(-1, new TipoExp(Tipos.INTEGER), linea, columna), (Expresion) va.getDimensiones().get(0), Operador.MULTIPLICACION, linea, columna).getValor(e);
                if (res instanceof Errores) {
                    ((Errores) res).setMensaje("No se pudo hacer Not con  la matriz, por los tipos que no son booleanos");
                    return res;
                }
                TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                ddd = origen;
                //res = CastearValor(dominante, res, origen);
                Literal nueva = new Literal(res, origen, linea, columna);
                LinkedList<Object> dato = new LinkedList<>();
                dato.add(nueva);
                EstructuraLineal vector = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), origen, dato);
                filas.add(vector);
            }
            nuevosval.add(filas);
        }
        Matrix nm = new Matrix(nuevosval, new TipoExp(Tipos.MATRIX), ddd, "", m.getColumna(), m.getFila());
        return nm;
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

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Exp \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append("node").append(op.hashCode()).append("[label=\"Operador{").append(op.toString()).append("} \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(op.hashCode()).append(";\n");
        nueva.append(op1.toDot(this.hashCode()));
        return nueva.toString();
    }

}

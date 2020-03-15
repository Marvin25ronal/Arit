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
import Objetos.Nulo;
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
        if (t1.isMatrix() || t2.isMatrix()) {
            return new TipoExp(Tipos.MATRIX);
        } else if (t1.isList() || t2.isList()) {
            return new TipoExp(Tipos.LISTA);
        } else if (t1.isVector() || t2.isVector()) {
            return new TipoExp(Tipos.VECTOR);
        } else if (t1.isString() || t2.isString()) {
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

    public Object CastearValor(TipoExp tdestino, Object valor, TipoExp torigen) {
        if (null != tdestino.tp) {
            switch (tdestino.tp) {
                case STRING:
                    if (valor instanceof Nulo) {
                        return valor;
                    } else {
                        return valor.toString();
                    }
                case NUMERIC:
                    if (valor instanceof Nulo) {
                        return 0.0;
                    } else if (torigen.tp == Tipos.BOOLEAN) {
                        boolean b = Boolean.parseBoolean(valor.toString());
                        return b ? 1.0 : 0.00;
                    } else {
                        return Double.parseDouble(valor.toString());
                    }
                case INTEGER:
                    if (torigen.isBoolean()) {
                        return Boolean.parseBoolean(valor.toString()) ? 1 : 0;
                    }
                    return valor instanceof Nulo ? 0 : Integer.parseInt(valor.toString());
                case BOOLEAN:
                    if (valor instanceof Nulo) {
                        return false;
                    } else {
                        return Boolean.parseBoolean(valor.toString());
                    }
                default:
                    break;
            }
        }
        return null;
    }

    @Override
    public String toDot(int padre) {

        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Exp \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        if (op1 != null && op2 != null) {
            nueva.append(op1.toDot(this.hashCode()));
            nueva.append("node").append(this.hashCode() + 1).append("[label=\"Simbolo{").append(op.toString()).append("} \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
            nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
            nueva.append(op2.toDot(this.hashCode()));
        } else {
            nueva.append("node").append(this.hashCode() + 1).append("[label=\"Simbolo{").append(op.toString()).append("} \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
            nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
            nueva.append(op1.toDot(this.hashCode()));
        }
        return nueva.toString();
    }

}

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
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;

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
        if (t1.isVector() || t2.isVector()) {
            return new TipoExp(Tipos.VECTOR);
        }
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

        Object valor1 = op1.getValor(e);
        Object valor2 = op2.getValor(e);
        if (valor1 instanceof Errores) {
            return valor1;
        } else if (valor2 instanceof Errores) {
            return valor2;
        }
        TipoExp top1 = op1.getTipo(e);
        TipoExp top2 = op2.getTipo(e);
        if (max(top1, top2).isVector()) {
            if (top1.isVector() && top2.isVector()) {
                return RelacionalVectoresVectores((Vector) valor1, (Vector) valor2, e);
            }
            return top1.isVector() ? RelacionalVector((Vector) valor1, top2, valor2, e, true) : RelacionalVector((Vector) valor2, top1, valor1, e, false);
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
        } else if (top1.isString() && top2.isString()) {
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
            if (top1.esNumero() && top2.esNumero()) {
                double a = Double.parseDouble(valor1.toString());
                double b = Double.parseDouble(valor2.toString());
                switch (op) {
                    case IGUAL_IGUAL:
                        return new Literal(a == b, new TipoExp(Tipos.BOOLEAN), linea, columna);
                    case DISTINTO:
                        return new Literal(a != b, new TipoExp(Tipos.BOOLEAN), linea, columna);
                    case MAYOR:
                        return new Literal(a > b, new TipoExp(Tipos.BOOLEAN), linea, columna);
                    case MENOR:
                        return new Literal(a < b, new TipoExp(Tipos.BOOLEAN), linea, columna);
                    case MAYOR_IGUAL:
                        return new Literal(a >= b, new TipoExp(Tipos.BOOLEAN), linea, columna);
                    case MENOR_IGUAL:
                        return new Literal(a <= b, new TipoExp(Tipos.BOOLEAN), linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "No se puede comparar operadores que no sean numero", linea, columna);
            }
        }
        return null;
    }

    private Object RelacionalVector(Vector v, TipoExp tipoexp, Object valor, Entorno e, boolean primero) {
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        LinkedList<Object> nuevo = new LinkedList<>();
        Literal l;
        Object aux;
        for (int i = 0; i < lista.size(); i++) {
            l = (Literal) lista.get(i);
            aux = primero ? new Relacional(l, new Literal(valor, tipoexp, linea, columna), op, linea, columna).val(e) : new Relacional(new Literal(valor, tipoexp, linea, columna), l, op, linea, columna).val(e);
            if (aux instanceof Errores) {
                return aux;
            }
            nuevo.add(aux);
        }
        Vector nuevov = new Vector("", new TipoExp(Tipos.VECTOR), max(v.getTiposecundario(), tipoexp), nuevo);
        return nuevov;
    }

    private Object RelacionalVectoresVectores(Vector v1, Vector v2, Entorno e) {
        LinkedList<Object> a = Globales.VarGlobales.getInstance().clonarListaVector(v1.getDimensiones(), e);
        LinkedList<Object> b = Globales.VarGlobales.getInstance().clonarListaVector(v2.getDimensiones(), e);
        LinkedList<Object> nuevos = new LinkedList<>();
        Object res;
        if (a.size() == b.size()) {
            for (int i = 0; i < a.size(); i++) {
                res = new Relacional((Literal) a.get(i), (Literal) b.get(i), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (a.size() == 1) {
            for (int i = 0; i < b.size(); i++) {
                res = new Relacional((Literal) a.get(0), (Literal) b.get(i), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (b.size() == 1) {
            for (int i = 0; i < a.size(); i++) {
                res = new Relacional((Literal) a.get(i), (Literal) b.get(0), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden hacer operaciones relacionales con  vectores que no sean de un elemento o igual elementos", linea, columna);
        }
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
        if (op1.getTipo(e).isVector() || op2.getTipo(e).isVector()) {
            return new TipoExp(Tipos.VECTOR);
        }
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

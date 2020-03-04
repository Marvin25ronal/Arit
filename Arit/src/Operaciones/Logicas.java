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
public class Logicas extends Operacion {

    int linea, columna;

    public Logicas(Expresion op1, Expresion op2, Operador op, int linea, int columna) {
        super(op1, op2, op);
        this.linea = linea;
        this.columna = columna;
    }

    private boolean Correctos(TipoExp t1,TipoExp t2) {
        return t1.tp == Tipos.BOOLEAN && t2.tp == Tipos.BOOLEAN;
    }

    @Override
    public TipoExp max(TipoExp a, TipoExp b) {
        return tipoDominante(a, b);
    }

    private TipoExp tipoDominante(TipoExp t1, TipoExp t2) {
        if (t1.isVector() || t2.isVector()) {
            return new TipoExp(Tipos.VECTOR);
        } else if (t1.isString() || t2.isString()) {
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
        if (aux == null) {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden comparar otros tipos que no sean booleanos", linea, columna);
        } else if (aux.isVector()) {
            if (top1.isVector() && top2.isVector()) {
                return LogicasVectoresVectores((Vector) valor1, (Vector) valor2, e);
            }
            return top1.isVector() ? LogicasVector((Vector) valor1, top2, valor2, e) : LogicasVector((Vector) valor2, top1, valor1, e);
        }
        if (Correctos(top1,top2)) {
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

    private Object LogicasVectoresVectores(Vector v1, Vector v2, Entorno e) {
        LinkedList<Object> a = Globales.VarGlobales.getInstance().clonarListaVector(v1.getDimensiones(), e);
        LinkedList<Object> b = Globales.VarGlobales.getInstance().clonarListaVector(v2.getDimensiones(), e);
        LinkedList<Object> nuevos = new LinkedList<>();
        Object res;
        if (a.size() == b.size()) {
            for (int i = 0; i < a.size(); i++) {
                res = new Logicas((Literal) a.get(i), (Literal) b.get(i), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (a.size() == 1) {
            for (int i = 0; i < b.size(); i++) {
                res = new Logicas((Literal) a.get(0), (Literal) b.get(i), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (b.size() == 1) {
            for (int i = 0; i < a.size(); i++) {
                res = new Logicas((Literal) a.get(i), (Literal) b.get(0), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden hacer operaciones logicas  vectores que no sean de un elemento o igual elementos", linea, columna);
        }
    }

    private Object LogicasVector(Vector v, TipoExp tipoexp, Object valor, Entorno e) {
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        LinkedList<Object> nuevo = new LinkedList<>();
        Literal l;
        Object aux;
        for (int i = 0; i < lista.size(); i++) {
            l = (Literal) lista.get(i);
            aux = new Logicas(l, new Literal(valor, tipoexp, linea, columna), op, linea, columna).val(e);
            if (aux instanceof Errores) {
                return aux;
            }
            nuevo.add(aux);
        }
        Vector nuevov = new Vector("", new TipoExp(Tipos.VECTOR), max(v.getTiposecundario(), tipoexp), nuevo);
        return nuevov;
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
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

}

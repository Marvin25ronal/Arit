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
import Objetos.Nulo;
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

    private boolean Correctos(TipoExp t1, TipoExp t2) {
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
                return LogicasVectoresVectores((EstructuraLineal) valor1, (EstructuraLineal) valor2, e);
            }
            return top1.isVector() ? LogicasVector((EstructuraLineal) valor1, top2, valor2, e) : LogicasVector((EstructuraLineal) valor2, top1, valor1, e);
        } else if (aux.isMatrix()) {
            //suma de matriz con matrices
            if (top1.isMatrix() && top2.isMatrix()) {
                return LogicasMatriz_Matriz((Matrix) valor1, (Matrix) valor2, e);
            } //con un vector
            else if (top1.isVector() || top2.isVector()) //con un primitivo
            {
                return top1.isMatrix() ? LogicaMatriz_Vector((Matrix) valor1, (EstructuraLineal) valor2, e) : LogicaMatriz_Vector((Matrix) valor2, (EstructuraLineal) valor1, e);
            } else if (top1.isPrimitive(e) || top2.isPrimitive(e)) {
                return top1.isMatrix() ? LogicaMatriz_Primitivo((Matrix) valor1, valor2, e) : LogicaMatriz_Primitivo((Matrix) valor2, valor1, e);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La matriz no puede hacer operaciones logicas con ese tipo de objeto " + top1.toString() + top2.toString(), linea, columna);
            }
        }
        if (Correctos(top1, top2)) {
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

    private Object LogicaMatriz_Primitivo(Matrix a, Object b, Entorno e) {
        LinkedList<LinkedList<Object>> columnas = new LinkedList<>();
        TipoExp tipoO = Globales.VarGlobales.getInstance().obtenerTipo(b, e);
        TipoExp dominante = max(a.getTiposecundario(), tipoO);
        Literal lb = new Literal(b, tipoO, linea, this.columna);
        for (int i = 0; i < a.getColumna(); i++) {
            LinkedList<Object> filas = new LinkedList<>();
            for (int j = 0; j < a.getFila(); j++) {
                EstructuraLineal va = (EstructuraLineal) a.getColumnas().get(i).get(j);
                Object res = new Logicas((Expresion) va.getDimensiones().get(0), (Expresion) lb, op, linea, columna).getValor(e);
                if (res instanceof Errores) {
                    return res;
                }
                TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                res = CastearValor(dominante, res, origen);
                Literal nueva = new Literal(res, dominante, linea, columna);
                LinkedList<Object> dato = new LinkedList<>();
                dato.add(nueva);
                EstructuraLineal vector = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(dominante.tp), dato);
                filas.add(vector);
            }
            columnas.add(filas);
        }
        Matrix nm = new Matrix(columnas, new TipoExp(Tipos.MATRIX), dominante, "", a.getColumna(), a.getFila());
        return nm;
    }

    private Object LogicaMatriz_Vector(Matrix a, EstructuraLineal b, Entorno e) {
        if (b.getDimensiones().size() == 1) {
            LinkedList<LinkedList<Object>> columnas = new LinkedList<>();
            TipoExp dominante = max(a.getTiposecundario(), b.getTiposecundario());
            Literal lb = (Literal) b.getDimensiones().get(0);
            for (int i = 0; i < a.getColumna(); i++) {
                LinkedList<Object> filas = new LinkedList<>();
                for (int j = 0; j < a.getFila(); j++) {
                    EstructuraLineal va = (EstructuraLineal) a.getColumnas().get(i).get(j);
                    Object res = new Logicas((Expresion) va.getDimensiones().get(0), (Expresion) lb, op, linea, columna).getValor(e);
                    if (res instanceof Errores) {
                        return res;
                    }
                    TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                    res = CastearValor(dominante, res, origen);
                    Literal nueva = new Literal(res, dominante, linea, columna);
                    LinkedList<Object> dato = new LinkedList<>();
                    dato.add(nueva);
                    EstructuraLineal vector = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(dominante.tp), dato);
                    filas.add(vector);
                }
                columnas.add(filas);
            }
            Matrix nm = new Matrix(columnas, new TipoExp(Tipos.MATRIX), dominante, "", a.getColumna(), a.getFila());
            return nm;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que tener un elemento ", linea, columna);
        }
    }

    private Object LogicasMatriz_Matriz(Matrix a, Matrix b, Entorno e) {
        //tienen que ser del mismo tam
        if ((a.getColumna() == b.getColumna()) && (a.getFila() == b.getFila())) {
            LinkedList<LinkedList<Object>> columnas = new LinkedList<>();
            TipoExp dominante = max(a.getTiposecundario(), b.getTiposecundario());
            for (int i = 0; i < a.getColumna(); i++) {
                LinkedList<Object> filas = new LinkedList<>();
                for (int j = 0; j < a.getFila(); j++) {
                    EstructuraLineal va = (EstructuraLineal) a.getColumnas().get(i).get(j);
                    EstructuraLineal vb = (EstructuraLineal) b.getColumnas().get(i).get(j);
                    Object res = new Logicas((Expresion) va.getDimensiones().get(0), (Expresion) vb.getDimensiones().get(0), op, linea, columna).getValor(e);
                    if (res instanceof Errores) {
                        return res;
                    }
                    TipoExp origen = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
                    res = CastearValor(dominante, res, origen);
                    Literal nueva = new Literal(res, dominante, linea, columna);
                    LinkedList<Object> dato = new LinkedList<>();
                    dato.add(nueva);
                    EstructuraLineal vector = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(dominante.tp), dato);
                    filas.add(vector);
                }
                columnas.add(filas);
            }
            Matrix nm = new Matrix(columnas, new TipoExp(Tipos.MATRIX), dominante, "", a.getColumna(), a.getFila());
            return nm;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer operaciones logicas con  matrices que no sean del mismo tamaño", linea, columna);
        }
    }

    private Object LogicasVectoresVectores(EstructuraLineal v1, EstructuraLineal v2, Entorno e) {
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
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (a.size() == 1) {
            for (int i = 0; i < b.size(); i++) {
                res = new Logicas((Literal) a.get(0), (Literal) b.get(i), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else if (b.size() == 1) {
            for (int i = 0; i < a.size(); i++) {
                res = new Logicas((Literal) a.get(i), (Literal) b.get(0), op, linea, columna).val(e);
                if (res instanceof Errores) {
                    return res;
                }
                nuevos.add(res);
            }
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), max(v1.getTiposecundario(), v2.getTiposecundario()), nuevos);
            return nuevo;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden hacer operaciones logicas  vectores que no sean de un elemento o igual elementos", linea, columna);
        }
    }

    private Object LogicasVector(EstructuraLineal v, TipoExp tipoexp, Object valor, Entorno e) {
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
        EstructuraLineal nuevov = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), max(v.getTiposecundario(), tipoexp), nuevo);
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

}

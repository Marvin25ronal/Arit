/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.TipoExp.Tipos;

import Objetos.Matrix;
import Objetos.Nulo;
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class CrearMatriz {

    private LinkedList<Expresion> parametros;
    private LinkedList<Expresion> dimensiones;
    private int linea;
    private int columna;

    public CrearMatriz(LinkedList<Expresion> parametros, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.parametros = parametros;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    public Object Ejecutar(Entorno e) {
        if (parametros.size() == 3) {
            Object datos = parametros.get(0).getValor(e);
            Object nrow = parametros.get(1).getValor(e);
            TipoExp tfila = Globales.VarGlobales.getInstance().obtenerTipo(nrow, e);
            Object ncol = parametros.get(2).getValor(e);
            TipoExp tcol = Globales.VarGlobales.getInstance().obtenerTipo(ncol, e);
            if (datos instanceof Errores) {
                return datos;
            } else if (nrow instanceof Errores) {
                return nrow;
            } else if (ncol instanceof Errores) {
                return ncol;
            }

            TipoExp tipo = Globales.VarGlobales.getInstance().obtenerTipo(datos, e);
            if (tipo.isList()) {
                return new Errores(Errores.TipoError.SEMANTICO, "La matriz solo puede contener numeros primitivos y vectores", linea, columna);
            }
            if (!tfila.isInt() || !tcol.isInt()) {
                if (tfila.isVector()) {
                    EstructuraLineal fila = (EstructuraLineal) nrow;
                    if (fila.getDimensiones().size() == 1) {
                        if (fila.getTiposecundario().isInt()) {
                            nrow = fila.getDimensiones().get(0);
                        } else {
                            return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que ser de tipo numerico en la fila", linea, columna);
                        }
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que contener solo un elemento para las filas", linea, columna);
                    }
                }
                if (tcol.isVector()) {
                    EstructuraLineal col = (EstructuraLineal) ncol;
                    if (col.getDimensiones().size() == 1) {
                        if (col.getTiposecundario().isInt()) {
                            ncol = col.getDimensiones().get(0);
                        } else {
                            return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que ser de tipo numerico en la columna", linea, columna);
                        }
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que contener solo un elemento para las columna", linea, columna);
                    }
                }
                return new Errores(Errores.TipoError.SEMANTICO, "Los parametros de fila y columna tienen que ser de tipo INTEGER ", linea, columna);
            }
            if (tipo.isPrimitive(e)) {
                return ConstruirMatriz_Primitivo(e, datos, tipo, Integer.parseInt(ncol.toString()), Integer.parseInt(nrow.toString()));
            } else if (tipo.isVector()) {
                return ConstruirMatriz_Vector(e, datos, tipo, Integer.parseInt(ncol.toString()), Integer.parseInt(nrow.toString()));
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La matriz no se pudo crear por el tipo de dato", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "Los parametros son incorectos para crear la matriz ", linea, columna);
        }

    }

    private Object ConstruirMatriz_Vector(Entorno e, Object valor, TipoExp tipo, int col, int fil) {
        if (col < 1 || fil < 1) {
            return new Errores(Errores.TipoError.SEMANTICO, "Los valores tienen que ser mayor de 0", linea, columna);
        }
        LinkedList<LinkedList<Object>> matriz = new LinkedList<>();
        EstructuraLineal v = (EstructuraLineal) valor;
        LinkedList<Object> valores = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        int k = 0;
        for (int i = 0; i < col; i++) {
            LinkedList<Object> nueva = new LinkedList<>();
            for (int j = 0; j < fil; j++) {
                Literal aux = (Literal) valores.get(k);
                Literal nuevaL = new Literal(aux.getValor(e), new TipoExp(aux.getTipo(e).tp), aux.getLinea(), aux.getColumna());
                LinkedList<Object> nlist = new LinkedList<>();
                nlist.add(nuevaL);
                EstructuraLineal nuevov = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(aux.getTipo().tp), nlist);
                nueva.add(nuevov);
                k++;
                if (k == valores.size()) {
                    k = 0;
                }
            }
            matriz.add(nueva);
        }
        Matrix m = new Matrix(matriz, new TipoExp(Tipos.MATRIX),new TipoExp(v.getTiposecundario().tp), "", col, fil);
        if (!dimensiones.isEmpty()) {
            Entorno eaux = new Entorno(e);
            eaux.add("aux", (Simbolo) m);
            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
            return nuevoA.getValor(eaux);
        }
        return m;
    }

    private Object ConstruirMatriz_Primitivo(Entorno e, Object valor, TipoExp tipo, int col,int fil) {
        LinkedList<LinkedList<Object>> matriz = new LinkedList<>();
        for (int i = 0; i < col; i++) {
            LinkedList<Object> nueva = new LinkedList<>();
            for (int j = 0; j < fil; j++) {
                Literal nueval = new Literal(valor, tipo, linea, columna);
                LinkedList<Object> lista = new LinkedList<>();
                lista.add(nueval);
                EstructuraLineal nuevov = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), tipo, lista);
                nueva.add(nuevov);
            }
            matriz.add(nueva);
        }
        Matrix m = new Matrix(matriz, new TipoExp(Tipos.MATRIX), tipo, "", col, fil);
        if (!dimensiones.isEmpty()) {
            Entorno eaux = new Entorno(e);
            eaux.add("aux", (Simbolo) m);
            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
            return nuevoA.getValor(eaux);
        }
        return m;
    }

    private Object CastearValor(TipoExp tdestino, Object valor, TipoExp torigen) {
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
                    } else if (torigen.tp == TipoExp.Tipos.BOOLEAN) {
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
                case LISTA:
                    return valor;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<Expresion> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the dimensiones
     */
    public LinkedList<Expresion> getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(LinkedList<Expresion> dimensiones) {
        this.dimensiones = dimensiones;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

}

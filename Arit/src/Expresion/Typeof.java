/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.TipoExp.Tipos;
import Objetos.Array;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Typeof implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;
    LinkedList<Expresion> dimensiones;

    public Typeof(Expresion exp, int linea, int columna, LinkedList<Expresion> dimensiones) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
        this.dimensiones = dimensiones;
    }

    @Override
    public Object getValor(Entorno e) {
        Object valor = exp.getValor(e);
        if (valor instanceof Errores) {
            return valor;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(valor, e);
        if (t.isVector()) {
            EstructuraLineal aux = (EstructuraLineal) valor;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), aux.getTiposecundario().toString(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else if (t.isList()) {
            return new EstructuraLineal(new TipoExp(Tipos.STRING), t.toString(), linea, columna);
        } else if (t.isMatrix()) {
            Matrix aux = (Matrix) valor;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), aux.getTiposecundario().toString(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else if (t.isArrya()) {
            Array aux = (Array) valor;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), aux.getTiposecundario().toString(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else if (t.isPrimitive(e)) {
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), t.toString(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        }
        return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer typeof de eso", linea, columna);
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return new TipoExp(Tipos.VECTOR);
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

    /**
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
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

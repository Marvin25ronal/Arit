/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Expresion.Acceso;
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
public class AsignacionPosicion implements Instruccion {

    private Expresion acc;
    private Expresion valor;
    private int linea;
    private int columna;

    public AsignacionPosicion(Expresion acc, Expresion valor, int linea, int columna) {
        this.acc = acc;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Acceso ac = (Acceso) acc;
        TipoExp tvalor = valor.getTipo(e);
        if(ac.getIndices().size()==1){
            ac.setIncremento(true);
        }
        Object obj = ac.getValor(e);
        if (obj instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) obj);
            return null;
        }
        //me lo regresa en un vector de 1
        Object val = valor.getValor(e);
        if (val instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) val);
            return null;
        }
        if (obj instanceof Vector) {
            if (tvalor.isPrimitive(e)) {
                return ReasignarVector_Primitivo(e, val, tvalor, (Vector) obj);
            } else {
                return ReasignarVector_Vector(e, val, tvalor, (Vector) obj);
            }
        }

        /*
        //da problema mejor se clona y hacemos esto distinto
        Existe la variable
        El tipo puede aceptar eso
        Agregarlo
         */
        return null;
    }

    private Object ReasignarVector_Primitivo(Entorno e, Object setvalor, TipoExp t, Vector v) {
        TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, t);
        ((Literal) v.getDimensiones().get(0)).valor = setvalor;
        return null;
    }

    private Object ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t, Vector v) {
        if (setvalor instanceof Vector) {

        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden meter otras estructuras en un vector", linea, columna);
        }
        return null;
    }

    private TipoExp Dominante_Vector(LinkedList<Object> l, Entorno e, TipoExp tul) {
        Literal li = (Literal) l.get(0);
        if (tul.tp == Tipos.STRING || li.getTipo(e).tp == Tipos.STRING) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.NUMERIC || li.getTipo(e).tp == Tipos.NUMERIC) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.INTEGER || li.getTipo(e).tp == Tipos.INTEGER) {
            return new TipoExp(Tipos.INTEGER);
        } else if (tul.tp == Tipos.BOOLEAN && li.getTipo(e).tp == Tipos.BOOLEAN) {
            return new TipoExp(Tipos.BOOLEAN);
        }
        return null;
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
     * @return the acc
     */
    public Expresion getAcc() {
        return acc;
    }

    /**
     * @param acc the acc to set
     */
    public void setAcc(Acceso acc) {
        this.acc = acc;
    }

    /**
     * @return the valor
     */
    public Expresion getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Expresion valor) {
        this.valor = valor;
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

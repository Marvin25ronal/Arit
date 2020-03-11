/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion.Funciones;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Acceso;
import Expresion.Expresion;
import Expresion.Identificador;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class StringLength implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public StringLength(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() != 1) {
            return new Errores(Errores.TipoError.SEMANTICO, "La cantidad de parametros del StringLength son incorrector", linea, columna);
        }
        Object res = exp.get(0).getValor(e);
        if (res instanceof Errores) {
            return res;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
        if (t.isVector()) {
            EstructuraLineal v = (EstructuraLineal) res;
            if (v.getTiposecundario().isString()) {
                if (v.getDimensiones().size() != 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene mas de un elemento", linea, columna);
                }
                Literal aux = (Literal) v.getDimensiones().get(0);
                EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), aux.getValor(e).toString().length(), linea, columna);
                if (!dimensiones.isEmpty()) {
                    Entorno eaux = new Entorno(e);
                    eaux.add("aux", (Simbolo) nueva);
                    Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                    return nuevoA.getValor(eaux);
                }
                return nueva;
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo STRING", linea, columna);
            }
        } else if (t.isString()) {
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), res.toString().length(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "El objeto no es de tipo string", linea, columna);
        }
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return new TipoExp(Tipos.VECTOR);
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

}

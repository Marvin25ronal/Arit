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
public class ToLowerCase implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;
    boolean uper = false;

    public ToLowerCase(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna, boolean uper) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
        this.uper = uper;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() == 1) {
            Object a = exp.get(0).getValor(e);
            if (a instanceof Errores) {
                return a;
            }
            TipoExp ta = Globales.VarGlobales.getInstance().obtenerTipo(a, e);
            if (ta.isVector()) {
                EstructuraLineal va = (EstructuraLineal) a;
                if (va.getTiposecundario().isString()) {
                    if (va.getDimensiones().size() > 1) {
                        return new Errores(Errores.TipoError.SEMANTICO, "El vector solo tiene que tener un elemento", linea, columna);
                    }
                    Literal l = (Literal) va.getDimensiones().get(0);
                    if (uper) {
                        EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), l.getValor().toString().toUpperCase(), linea, columna);
                        if (!dimensiones.isEmpty()) {
                            Entorno eaux = new Entorno(e);
                            eaux.add("aux", (Simbolo) nueva);
                            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                            return nuevoA.getValor(eaux);
                        }
                        return nueva;
                    } else {
                        EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), l.getValor().toString().toLowerCase(), linea, columna);
                        if (!dimensiones.isEmpty()) {
                            Entorno eaux = new Entorno(e);
                            eaux.add("aux", (Simbolo) nueva);
                            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                            return nuevoA.getValor(eaux);
                        }
                        return nueva;
                    }
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo STRING", linea, columna);
                }
            } else if (ta.isString()) {
                if (uper) {

                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), a.toString().toUpperCase(), linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;
                } else {

                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), a.toString().toLowerCase(), linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El objeto no es de tipo STRING", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La cantidad de parametros es incorrecta ", linea, columna);
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

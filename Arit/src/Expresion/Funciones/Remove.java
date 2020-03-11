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
public class Remove implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Remove(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() == 2) {
            Object a = exp.get(0).getValor(e);
            Object b = exp.get(1).getValor(e);
            if (a instanceof Errores) {
                return a;
            } else if (b instanceof Errores) {
                return b;
            }
            TipoExp ta = Globales.VarGlobales.getInstance().obtenerTipo(a, e);
            TipoExp tb = Globales.VarGlobales.getInstance().obtenerTipo(b, e);
            if (ta.isString()) {
                if (tb.isString()) {
                    String resultante = a.toString().replace(b.toString(), "");
                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), resultante, linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;

                } else if (tb.isVector()) {
                    EstructuraLineal vb = (EstructuraLineal) b;
                    if (vb.getDimensiones().size() > 1) {
                        return new Errores(Errores.TipoError.SEMANTICO, "El segundo vector tiene mas de un elemento ", linea, columna);
                    }
                    Literal l = (Literal) vb.getDimensiones().get(0);
                    String resultante = a.toString().replace(l.getValor().toString(), "");
                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), resultante, linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El segundo elemento no es de tipo STRING", linea, columna);
                }
            } else if (ta.isVector()) {
                EstructuraLineal va = (EstructuraLineal) a;
                if (va.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El primer vector tiene mas de un elemento", linea, columna);
                }
                Literal l = (Literal) va.getDimensiones().get(0);
                if (tb.isString()) {
                    String resultante = l.getValor().toString().replace(b.toString(), "");
                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), resultante, linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;
                } else if (tb.isVector()) {
                    EstructuraLineal vb = (EstructuraLineal) b;
                    if (vb.getDimensiones().size() > 1) {
                        return new Errores(Errores.TipoError.SEMANTICO, "El segundo vector tiene mas de un elemento ", linea, columna);
                    }
                    Literal lb = (Literal) vb.getDimensiones().get(0);
                    String resultante = l.getValor().toString().replace(lb.getValor().toString(), "");
                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.STRING), resultante, linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El segundo elemento no es de tipo STRING", linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El primer elemento no es de tipo STRING", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La funcion no cuenta con la misma cantidad de parametros ", linea, columna);
        }
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

}

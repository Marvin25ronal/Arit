/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entorno.Entorno;
import Expresion.Expresion;
import Instruccion.Instruccion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Switch implements Instruccion {

    Expresion condicion;
    LinkedList<Instruccion> Lcase;
    int linea, columna;

    public Switch(Expresion condicion, LinkedList<Instruccion> Lcase, int linea, int columna) {
        this.condicion = condicion;
        this.Lcase = Lcase;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Entorno local = new Entorno(e);
        Object val = condicion.getValor(local);
        boolean entro = false;
        if (val instanceof Errores) {
            return val;
        }
        if (Lcase == null) {
            return null;
        }
        for (Instruccion n : Lcase) {
            Object t = null;
            if (n instanceof Case) {
                t = ((Case) n).getExp().getValor(local);
                if (t instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) t);
                    continue;
                }
            } else if (n instanceof Else) {
                t = val;
            }
            if (entro || val.equals(t)) {
                Object ret = n.ejecutar(local);
                if (ret instanceof Break) {
                    return null;
                } else if (ret instanceof Return) {
                    return ret;
                } else if (ret instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) ret);
                    continue;
                }
                entro = true;
            }
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

}

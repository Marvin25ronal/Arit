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
import Expresion.TipoExp;
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
public class Length implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Length(LinkedList<Expresion> exp, LinkedList<Expresion> dimensicones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensicones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() != 1) {
            return new Errores(Errores.TipoError.SEMANTICO, "La cantidad de parametros es incorrecta", linea, columna);
        }
        Object val = exp.get(0).getValor(e);
        if (val instanceof Errores) {
            return val;
        }
        TipoExp tipo = Globales.VarGlobales.getInstance().obtenerTipo(val, e);
        if (tipo.isVector()) {
            EstructuraLineal v = (EstructuraLineal) val;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), v.getDimensiones().size(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else if (tipo.isList()) {
            EstructuraLineal l = (EstructuraLineal) val;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), l.getDimensiones().size(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else if (tipo.isMatrix()) {
            Matrix m = (Matrix) val;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), m.getColumna() * m.getFila(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else if (tipo.isArrya()) {
            int contador = 1;
            Array arreglo = (Array) val;
            for (int i = 0; i < arreglo.getDimensiones().size(); i++) {
                contador *= arreglo.getDimensiones().get(i);
            }
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), contador, linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No es una estructura para usar el length ", linea, columna);
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

    @Override
    public String toDot(int padre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

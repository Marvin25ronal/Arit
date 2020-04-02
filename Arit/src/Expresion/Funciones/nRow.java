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
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class nRow implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public nRow(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() != 1) {
            return new Errores(Errores.TipoError.SEMANTICO, "La cantidad de parametros es incorrecto ", linea, columna);
        }
        Object res = exp.get(0).getValor(e);
        if (res instanceof Errores) {
            return res;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(res, e);
        if (t.isMatrix()) {
            Matrix m = (Matrix) res;
            EstructuraLineal nueva = new EstructuraLineal(new TipoExp(TipoExp.Tipos.INTEGER), m.getFila(), linea, columna);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "El objeto no es de tipo matriz", linea, columna);
        }
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return new TipoExp(TipoExp.Tipos.VECTOR);
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

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
public class trunk implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public trunk(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() == 1) {
            Object a = exp.get(0).getValor(e);
            if (a instanceof Errores) {
                return a;
            }
            TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(a, e);
            if (t.isVector()) {
                EstructuraLineal v = (EstructuraLineal) a;
                if (v.getTiposecundario().esNumero()) {
                    Literal l = (Literal) v.getDimensiones().get(0);
                    double valor = Double.parseDouble(l.getValor().toString());
                    int casteado = (int) (valor-(valor%1));
                    EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), casteado, linea, columna);
                    if (!dimensiones.isEmpty()) {
                        Entorno eaux = new Entorno(e);
                        eaux.add("aux", (Simbolo) nueva);
                        Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                        return nuevoA.getValor(eaux);
                    }
                    return nueva;
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo INTEGER", linea, columna);
                }
            } else if (t.esNumero()) {
                double valor = Double.parseDouble(a.toString());
                int casteado = (int) (valor-(valor%1));
                EstructuraLineal nueva = new EstructuraLineal(new TipoExp(Tipos.INTEGER), casteado, linea, columna);
                if (!dimensiones.isEmpty()) {
                    Entorno eaux = new Entorno(e);
                    eaux.add("aux", (Simbolo) nueva);
                    Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                    return nuevoA.getValor(eaux);
                }
                return nueva;
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El elemento no es de tipo INTEGER", linea, columna);
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
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

    @Override
    public String toDot(int padre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

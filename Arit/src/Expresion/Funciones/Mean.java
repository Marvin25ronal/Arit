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
public class Mean implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Mean(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (exp.size() == 2) {
            Object v = exp.get(0).getValor(e);
            Object trim = exp.get(1).getValor(e);
            if (v instanceof Errores) {
                return v;
            } else if (trim instanceof Errores) {
                return trim;
            }
            TipoExp tv = Globales.VarGlobales.getInstance().obtenerTipo(v, e);
            TipoExp ttrim = Globales.VarGlobales.getInstance().obtenerTipo(trim, e);
            if (tv.isVector()) {
                EstructuraLineal vector = (EstructuraLineal) v;
                if (vector.getTiposecundario().esNumero()) {
                    if (ttrim.isVector()) {
                        EstructuraLineal vtrim = (EstructuraLineal) trim;
                        if (vtrim.getDimensiones().size() != 1) {
                            return new Errores(Errores.TipoError.SEMANTICO, "El valor de trim tiene mas de un elemento", linea, columna);
                        }
                        if (vtrim.getTiposecundario().esNumero()) {
                            double valortrim = Double.parseDouble(vtrim.getDimensiones().get(0).toString());
                            return HacerMedia(e, vector, valortrim);
                        } else {
                            return new Errores(Errores.TipoError.SEMANTICO, "El valor de trim no es de tipo NUMERIC o INTEGER", linea, columna);
                        }
                    } else if (ttrim.esNumero()) {
                        Double valortrim = Double.parseDouble(trim.toString());
                        return HacerMedia(e, vector, valortrim);
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "El trim no es de tipo NUMERIC o INTEGER", linea, columna);
                    }
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer MEAN con un vector que no sea numerico", linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El primer parametro no es de tipo vector", linea, columna);
            }
        } else if (exp.size() == 1) {
            Object v = exp.get(0).getValor(e);
            if (v instanceof Errores) {
                return v;
            }
            TipoExp tv = Globales.VarGlobales.getInstance().obtenerTipo(v, e);
            if (tv.isVector()) {
                EstructuraLineal vector = (EstructuraLineal) v;
                if (vector.getTiposecundario().esNumero()) {
                    return HacerMedia(e, vector);
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "No se puede hacer MEAN con un vector que no sea numerico", linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El primer parametro no es de tipo vector", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La cantidad de parametros es incorrecta", linea, columna);
        }
    }

    private Object HacerMedia(Entorno e, EstructuraLineal v, double trim) {
        double res = 0;
        int contador = 0;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            Literal l = (Literal) v.getDimensiones().get(i);
            Double val = Double.parseDouble(l.getValor(e).toString());
            if (!(val < trim)) {
                res += val;
                contador++;
            }
        }
        res = res / contador;
        TipoExp t = null;
        Object nres=0;
        if (res % 1 != 0) {
            //es numerico
            t = new TipoExp(Tipos.NUMERIC);
            nres=res;
        } else {
            t = new TipoExp(Tipos.INTEGER);
            nres=(int)res;
        }
        EstructuraLineal nueva = new EstructuraLineal(t, nres, linea, columna);
        if (!dimensiones.isEmpty()) {
            Entorno eaux = new Entorno(e);
            eaux.add("aux", (Simbolo) nueva);
            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
            return nuevoA.getValor(eaux);
        }
        return nueva;
    }

    private Object HacerMedia(Entorno e, EstructuraLineal v) {
        double res = 0;
        int contador = 0;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            Literal l = (Literal) v.getDimensiones().get(i);
            Double val = Double.parseDouble(l.getValor(e).toString());
            res += val;
            contador++;

        }
        res = res / contador;
        TipoExp t = null;
        Object nres=null;
        if (res % 1 != 0) {
            //es numerico
            t = new TipoExp(Tipos.NUMERIC);
            nres=res;
        } else {
            t = new TipoExp(Tipos.INTEGER);
            nres=(int)res;
        }
        EstructuraLineal nueva = new EstructuraLineal(t, nres, linea, columna);
        if (!dimensiones.isEmpty()) {
            Entorno eaux = new Entorno(e);
            eaux.add("aux", (Simbolo) nueva);
            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
            return nuevoA.getValor(eaux);
        }
        return nueva;
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

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
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Mode implements Expresion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Mode(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
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
                            return HacerMode(e, vector, valortrim);
                        } else {
                            return new Errores(Errores.TipoError.SEMANTICO, "El valor de trim no es de tipo NUMERIC o INTEGER", linea, columna);
                        }
                    } else if (ttrim.esNumero()) {
                        Double valortrim = Double.parseDouble(trim.toString());
                        return HacerMode(e, vector, valortrim);
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
                    return HacerMode(e, vector);
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

    private Object HacerMode(Entorno e, EstructuraLineal v, double trim) {
        double res = 0;
        LinkedList<Double> datos = new LinkedList<>();
        for (Object l : v.getDimensiones()) {
            Literal aux = (Literal) l;
            double dato = Double.parseDouble(aux.getValor(e).toString());
            if (!(dato < trim)) {
                datos.add(dato);
            }

        }
        Collections.sort(datos);
        if (datos.size() > 1) {
            int maxCount = 0;
            double maxValue = 0.00;

            for (int i = 0; i < datos.size(); ++i) {
                int count = 0;
                for (int j = 0; j < datos.size(); ++j) {
                    if (datos.get(i).equals(datos.get(j))) {
                        ++count;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = datos.get(i);
                }
            }
            res = maxValue;

        } else {
            res = datos.get(0);
        }
        TipoExp t = null;
        Object nres = null;
        if (res % 1 != 0) {
            //es numerico
            t = new TipoExp(TipoExp.Tipos.NUMERIC);
            nres = res;
        } else {
            t = new TipoExp(TipoExp.Tipos.INTEGER);
            nres = (int) res;
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

    private Object HacerMode(Entorno e, EstructuraLineal v) {
        double res = 0;
        LinkedList<Double> datos = new LinkedList<>();
        for (Object l : v.getDimensiones()) {
            Literal aux = (Literal) l;
            double dato = Double.parseDouble(aux.getValor(e).toString());
            datos.add(dato);
        }
        Collections.sort(datos);
        if (datos.size() > 1) {
            int maxCount = 0;
            double maxValue = 0;
            for (int i = 0; i < datos.size(); ++i) {
                int count = 0;
                for (int j = 0; j < datos.size(); ++j) {
                    if (datos.get(j).equals(datos.get(i))) {
                        ++count;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = datos.get(i);
                }
            }
            res = maxValue;

        } else {
            res = datos.get(0);
        }
        TipoExp t = null;
        Object nres = null;
        if (res % 1 != 0) {
            //es numerico
            t = new TipoExp(TipoExp.Tipos.NUMERIC);
            nres = res;
        } else {
            t = new TipoExp(TipoExp.Tipos.INTEGER);
            nres = (int) res;
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

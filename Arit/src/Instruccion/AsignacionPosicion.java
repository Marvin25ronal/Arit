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
import Objetos.Nulo;
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
        
        if (ac.getIndices().size() == 1) {
            ac.setIncremento(true);
        }
        Object obj = ac.getValor(e);
        if (obj instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) obj);
            return null;
        }
        //me lo regresa en un vector de 1
        Object val = valor.getValor(e);
        TipoExp tvalor = Globales.VarGlobales.getInstance().obtenerTipo(val, e);
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
        ((Literal) v.getDimensiones().get(0)).valor = CastearValor(nuevot, setvalor, t);
        ((Literal) v.getDimensiones().get(0)).tipo = nuevot;
        CastearVector(nuevot, e);
        return null;
    }
    
    private Object ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t, Vector v) {
        if (setvalor instanceof Vector) {
            Vector set = (Vector) setvalor;
            if (set.getDimensiones().size() == 1) {
                LinkedList<Object> listaAux = Globales.VarGlobales.getInstance().clonarListaVector(set.getDimensiones(), e);
                Literal aux = (Literal) listaAux.get(0);//--Retorna un arreglo con un elemento que es una literal
                TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, aux.getTipo(e));
                ((Literal) v.getDimensiones().get(0)).valor = CastearValor(nuevot, aux.valor, aux.getTipo(e));
                ((Literal) v.getDimensiones().get(0)).tipo = nuevot;
                CastearVector(nuevot, e);
                
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector solo puede contener vectores de tamanio 1", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden meter otras estructuras en un vector", linea, columna);
        }
        return null;
    }
    
    private void CastearVector(TipoExp t, Entorno e) {
        Vector v = (Vector) e.get(((Acceso) acc).getId().getVal());
        Literal aux;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            aux = (Literal) v.getDimensiones().get(i);
            aux.valor = CastearValor(t, aux.getValor(e), aux.getTipo(e));
            aux.tipo = t;
        }
    }
    
    private Object CastearValor(TipoExp tdestino, Object valor, TipoExp torigen) {
        if (null != tdestino.tp) {
            switch (tdestino.tp) {
                case STRING:
                    if (valor instanceof Nulo) {
                        return valor;
                    } else {
                        return valor.toString();
                    }
                case NUMERIC:
                    if (valor instanceof Nulo) {
                        return 0.0;
                    } else if (torigen.tp == Tipos.BOOLEAN) {
                        boolean b = Boolean.parseBoolean(valor.toString());
                        return b ? 1.0 : 0.00;
                    } else {
                        return Double.parseDouble(valor.toString());
                    }
                case INTEGER:
                    if (torigen.isBoolean()) {
                        return Boolean.parseBoolean(valor.toString()) ? 1 : 0;
                    }
                    return valor instanceof Nulo ? 0 : Integer.parseInt(valor.toString());
                case BOOLEAN:
                    if (valor instanceof Nulo) {
                        return false;
                    } else {
                        return Boolean.parseBoolean(valor.toString());
                    }
                default:
                    break;
            }
        }
        return null;
    }
    
    private TipoExp Dominante_Vector(LinkedList<Object> l, Entorno e, TipoExp tul) {
        Literal li = (Literal) l.get(0);
        if (tul.tp == Tipos.NULO || li.getTipo(e).tp == Tipos.NULO) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.STRING || li.getTipo(e).tp == Tipos.STRING) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.NUMERIC || li.getTipo(e).tp == Tipos.NUMERIC) {
            return new TipoExp(Tipos.NUMERIC);
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

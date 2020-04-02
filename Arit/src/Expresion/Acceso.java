/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Objetos.Array;

import Objetos.Matrix;
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Acceso implements Expresion {

    private Identificador id;
    private LinkedList<Expresion> indices;
    private int linea;
    private int columna;
    private boolean incremento;

    public Acceso(Identificador id, LinkedList<Expresion> indices, int linea, int columna) {
        this.id = id;
        this.indices = indices;
        this.linea = linea;
        this.columna = columna;
        this.incremento = false;
    }

    @Override
    public Object getValor(Entorno e) {
        Object s = getId().getValor(e);
        if (s instanceof Errores) {
            return s;
        }
        Simbolo sim = (Simbolo) s;
        if (sim.getTipo().isVector()) {
            Object vector = s;
            for (Expresion exp : getIndices()) {
                if (exp instanceof AccesoUnico) {
                    AccesoUnico aux = (AccesoUnico) exp;
                    aux.setObjeto(vector);
                    aux.setIncremento(incremento);
                    vector = aux.getValor(e);
                } else if (exp instanceof AccesoDoble) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector no se puede invocar con acceso doble", getLinea(), getColumna());
                } else if (exp instanceof Acceso4) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector no se puede invocar con un acceso 4", linea, columna);
                }
                if (vector instanceof Errores) {
                    return vector;
                }

            }
            return vector;
        } else if (sim.getTipo().isList()) {
            Object lista = s;
            for (Expresion exp : getIndices()) {
                if (exp instanceof AccesoUnico) {
                    AccesoUnico aux = (AccesoUnico) exp;
                    aux.setObjeto(lista);
                    aux.setIncremento(incremento);
                    lista = aux.getValor(e);
                } else if (exp instanceof AccesoDoble) {
                    AccesoDoble aux = (AccesoDoble) exp;
                    aux.setObjeto(lista);
                    aux.setIncremento(incremento);
                    lista = aux.getValor(e);
                } else if (exp instanceof Acceso4) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La lista no tiene acceso de tipo 4", linea, columna);
                }
                if (lista instanceof Errores) {
                    return lista;
                }
            }
            return lista;
        } else if (s instanceof Matrix) {
            Object matrix = s;
            for (Expresion exp : getIndices()) {
                if (exp instanceof AccesoUnico) {
                    AccesoUnico aux = (AccesoUnico) exp;
                    aux.setObjeto(matrix);
                    matrix = aux.getValor(e);
                } else if (exp instanceof AccesoDoble) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La matriz no tiene acceso de tipo 2", linea, columna);
                } else if (exp instanceof Acceso4) {
                    Acceso4 aux = (Acceso4) exp;
                    aux.setObjeto(matrix);
                    matrix = aux.getValor(e);
                }
                if (matrix instanceof Errores) {
                    return matrix;
                }
            }
            return matrix;
        } else if (s instanceof Array) {
            //ver si tiene como minimo el tama;o final
            Array array = (Array) s;
            Object obj = array;
            if (getIndices().size() >= array.getDimensiones().size()) {
                int i;
                Expresion exp;
                int j = 0;
                for (i = array.getDimensiones().size() - 1; i >= 0; i--) {
                    exp = getIndices().get(i);
                    if (exp instanceof AccesoUnico) {
                        AccesoUnico aux = (AccesoUnico) exp;
                        aux.setObjeto(obj);
                        aux.setArray(true);
                        aux.setIndiceArray(array.getDimensiones().get(j));
                        j++;
                        obj = aux.getValor(e);
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "El array solo tiene tipo de acceso 1", linea, columna);
                    }
                    if (obj instanceof Errores) {
                        return obj;
                    }
                }
                obj = ((LinkedList<Object>) obj).get(0);
                if (getIndices().size() == array.getDimensiones().size()) {
                    return obj;
                }
                LinkedList<Expresion> indices2 = new LinkedList<Expresion>();
                for (int k = array.getDimensiones().size(); k < getIndices().size(); k++) {
                    indices2.add(getIndices().get(k));
                }
                Entorno nuevoE = new Entorno(e);
                nuevoE.add("aux", (Simbolo) obj);
                Acceso n = new Acceso(new Identificador("aux", linea, columna), indices2, linea, columna);
                return n.getValor(nuevoE);

            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El acceso al ARRAY tiene que ser de todas sus dimensiones ", linea, columna);
            }
        }
        return null;

    }

    @Override
    public TipoExp getTipo(Entorno e) {
        Simbolo s = e.get(id.getVal());
        if (s != null) {
            if (s instanceof EstructuraLineal) {
                return ((EstructuraLineal) s).getTipo();
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

    /**
     * @return the id
     */
    public Identificador getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Identificador id) {
        this.id = id;
    }

    /**
     * @return the indices
     */
    public LinkedList<Expresion> getIndices() {
        return indices;
    }

    /**
     * @param indices the indices to set
     */
    public void setIndices(LinkedList<Expresion> indices) {
        this.indices = indices;
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

    /**
     * @return the incremento
     */
    public boolean isIncremento() {
        return incremento;
    }

    /**
     * @param incremento the incremento to set
     */
    public void setIncremento(boolean incremento) {
        this.incremento = incremento;
    }

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Acceso \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append(id.toDot(this.hashCode()));
        for (Expresion n : getIndices()) {
            nueva.append(n.toDot(this.hashCode()));
        }
        return nueva.toString();
    }

}

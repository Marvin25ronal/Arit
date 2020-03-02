/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Expresion.TipoExp.Tipos;
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class AccesoUnico implements Expresion {

    private Expresion indice;
    private int linea;
    private int columna;
    private Object objeto;
    private boolean incremento;

    public AccesoUnico(Expresion indice, int linea, int columna) {
        this.indice = indice;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        Object i = getIndice().getValor(e);
        if (getIndice().getTipo(e).tp == Tipos.VECTOR) {
            //solo tenga un valor
            Vector v = (Vector) i;
            if (v.getTiposecundario().tp != Tipos.INTEGER) {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo INTEGER", linea, columna);
            }
            if (v.getDimensiones().size() == 1) {

                indice = (Expresion) v.getDimensiones().get(0);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector de indice es de mayor tama;o que 1", linea, columna);
            }
        }
        if (getIndice().getTipo(e).tp != Tipos.INTEGER) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser de tipo numerico", getLinea(), getColumna());
        }

        if (i instanceof Errores) {
            return i;
        }
        if (getObjeto() instanceof Vector) {
            return AccesoVector(e);
        }
        return null;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int linea() {
        return this.getLinea();
    }

    @Override
    public int columna() {
        return this.getColumna();
    }

    /**
     * @return the indice
     */
    public Expresion getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Expresion indice) {
        this.indice = indice;
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
     * @return the objeto
     */
    public Object getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    private Object AccesoVector(Entorno e) {
        Vector v = (Vector) getObjeto();
        int inde = Integer.parseInt(getIndice().getValor(e).toString());
        if (inde <= 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", getLinea(), getColumna());
        } else if (inde > v.getTam()) {
            if (incremento) {
                return AccesoVectorIncremento(v, inde);
            }
            return new Errores(Errores.TipoError.SEMANTICO, "Se paso del indice del vector ", getLinea(), getColumna());
        }
        inde--;
        LinkedList<Object> lista = new LinkedList<Object>();
        lista.add(v.getDimensiones().get(inde));
        return new Vector(v.getId(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), lista);
    }

    private Object AccesoVectorIncremento(Vector v, int inde) {
        for (int i = v.getTam(); i < inde; i++) {
            v.getDimensiones().add(ObtenerDefault(v.getTiposecundario()));
        }
        inde--;
        LinkedList<Object> lista = new LinkedList<>();
        lista.add(v.getDimensiones().get(inde));
        return new Vector(v.getId(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), lista);
    }

    private Literal ObtenerDefault(TipoExp t) {
        switch (t.tp) {
            case BOOLEAN:
                return new Literal(false, t, linea, columna);
            case INTEGER:
                return new Literal(0, t, linea, columna);
            case STRING:
                return new Literal("", t, linea, columna);
            case NUMERIC:
                return new Literal(0.0, t, linea, columna);

        }
        return null;
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

}

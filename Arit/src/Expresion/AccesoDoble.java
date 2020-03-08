/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.TipoExp.Tipos;

import Objetos.Nulo;
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class AccesoDoble implements Expresion {

    private Expresion indice;
    private int linea;
    private int columna;
    private Object objeto;
    private boolean incremento;

    public AccesoDoble(Expresion indice, int linea, int columna) {
        this.indice = indice;
        this.linea = linea;
        this.columna = columna;

    }

    @Override
    public Object getValor(Entorno e) {
        Object i = getIndice().getValor(e);
        if (i instanceof Errores) {
            return i;
        }
        TipoExp tipo = Globales.VarGlobales.getInstance().obtenerTipo(i, e);
        if (tipo.isVector()) {
            EstructuraLineal v = (EstructuraLineal) i;
            if (v.getTiposecundario().tp != Tipos.INTEGER) {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo INTEGER", linea, columna);
            }
            if (v.getDimensiones().size() == 1) {
                indice = (Expresion) v.getDimensiones().get(0);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vetor de indice es de mayor tamanio que 1 " + tipo.tp, linea, columna);
            }

        } else if (tipo.tp != Tipos.INTEGER) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser de tipo numerico, el tipo es " + tipo.tp.toString(), linea, columna);
        }
        Simbolo s = (Simbolo) getObjeto();
        if (s.getTipo().isList()) {
            return AccesoLista(e);
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "El objeto no tiene doble acceso", linea, columna);
        }

    }

    private Object AccesoLista(Entorno e) {
        EstructuraLineal l = (EstructuraLineal) getObjeto();
        int inde = Integer.parseInt(getIndice().getValor(e).toString());
        if (inde <= 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", linea, columna);
        } else if (inde > l.getDimensiones().size()) {
            return AccesoListaIncremento(l, inde);
        }
        inde--;
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(l);
        Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        Globales.VarGlobales.getInstance().getAnterior().setAcceso(2);
        return l.getDimensiones().get(inde);
    }

    private Object AccesoListaIncremento(EstructuraLineal l, int inde) {
        for (int i = l.getDimensiones().size(); i < inde; i++) {
            LinkedList<Object> li = new LinkedList<>();
            li.add(new Literal(new Nulo(linea, columna), new TipoExp(Tipos.STRING), linea, columna));
            EstructuraLineal v = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), li);
            l.getDimensiones().add(v);
        }
        inde--;
        Simbolo s = (Simbolo) l.getDimensiones().get(inde);
        if (s.getTipo().isVector()) {
            Globales.VarGlobales.getInstance().getAnterior().setAnterior(l);
            Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
            Globales.VarGlobales.getInstance().getAnterior().setAcceso(2);
            //return new EstructuraLineal(lista, new TipoExp(Tipos.LISTA), null, "");
            return l.getDimensiones().get(inde);
        } else if (s.getTipo().isList()) {
           
            Globales.VarGlobales.getInstance().getAnterior().setAnterior(l);
            Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
            Globales.VarGlobales.getInstance().getAnterior().setAcceso(2);
            //return new EstructuraLineal(lista, new TipoExp(Tipos.LISTA), null, "");
            return l.getDimensiones().get(inde);
        }
        return null;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

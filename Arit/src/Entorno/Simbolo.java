/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import Expresion.TipoExp;

/**
 *
 * @author marvi
 */
public abstract class Simbolo {

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

    private TipoExp tipo;
    private TipoExp tiposecundario;
    private String id;
    private int linea;
    private int columna;
    public Simbolo(TipoExp tipo, TipoExp tiposecundario,String id,int linea,int columna) {
        this.tipo = tipo;
        this.id=id;
        this.tiposecundario = tiposecundario;
        this.linea=linea;
        this.columna=columna;
    }

    /**
     * @return the tipo
     */
    public TipoExp getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoExp tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tiposecundario
     */
    public TipoExp getTiposecundario() {
        return tiposecundario;
    }

    /**
     * @param tiposecundario the tiposecundario to set
     */
    public void setTiposecundario(TipoExp tiposecundario) {
        this.tiposecundario = tiposecundario;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}

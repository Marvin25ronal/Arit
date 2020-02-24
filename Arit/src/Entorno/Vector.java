/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import Expresion.TipoExp;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Vector extends Simbolo {

    /**
     * @return the tam
     */
    public int getTam() {
        return tam;
    }

    /**
     * @param tam the tam to set
     */
    public void setTam(int tam) {
        this.tam = tam;
    }

    private String id;
    private LinkedList<Object> dimensiones;
    private int tam;

    /**
     * @return the id
     */
    public Vector(TipoExp tipo, TipoExp tipoprimitivo, LinkedList<Object> dimensiones) {
        super(tipo, tipoprimitivo);
        this.dimensiones = dimensiones;
        this.tam = dimensiones.size();

    }

    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the dimensiones
     */
    public LinkedList<Object> getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(LinkedList<Object> dimensiones) {
        this.dimensiones = dimensiones;
    }

    /**
     * @return the dimension
     */
   
}

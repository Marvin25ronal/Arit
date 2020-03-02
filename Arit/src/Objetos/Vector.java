/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Entorno.Simbolo;
import Expresion.Literal;
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
    private LinkedList<Object> dimensiones;

    public int getTam() {
        return dimensiones.size();
    }

    /**
     * @param tam the tam to set
     */
    /**
     * @return the id
     */
    public Vector(String id, TipoExp tipo, TipoExp tipoprimitivo, LinkedList<Object> dimensiones) {
        super(tipo, tipoprimitivo, id);
        this.dimensiones = dimensiones;

    }

    /**
     * @param id the id to set
     */
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

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append("[");
        for (int i = 0; i < dimensiones.size(); i++) {
            Literal l = (Literal) dimensiones.get(i);
            cadena.append(l.valor);
            if (i < dimensiones.size() - 1) {
                cadena.append(",");
            }
        }
        cadena.append("]");
        return cadena.toString();
    }

    /**
     * @return the dimension
     */
}

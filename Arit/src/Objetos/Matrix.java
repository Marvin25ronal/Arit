/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Entorno.Simbolo;
import Expresion.TipoExp;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Matrix extends Simbolo{
    private int columna;
    private int fila;
    private LinkedList<LinkedList<Object>> columnas;
    public Matrix(TipoExp tipo, TipoExp tiposecundario, String id) {
        super(tipo, tiposecundario, id);
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
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the columnas
     */
    public LinkedList<LinkedList<Object>> getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(LinkedList<LinkedList<Object>> columnas) {
        this.columnas = columnas;
    }
    
}

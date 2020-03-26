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
public class Matrix extends Simbolo {

    private int columna;
    private int fila;
    private LinkedList<LinkedList<Object>> columnas;

    public Matrix(LinkedList<LinkedList<Object>> columnas, TipoExp tipo, TipoExp tiposecundario, String id, int columna, int fila) {
        super(tipo, tiposecundario, id,0,0);
        this.columna = columna;
        this.fila = fila;
        this.columnas = columnas;
    }
    public Matrix(LinkedList<LinkedList<Object>> columnas, TipoExp tipo, TipoExp tiposecundario, String id, int columna, int fila,int linea,int columnasssss) {
        super(tipo, tiposecundario, id,linea,columnasssss);
        this.columna = columna;
        this.fila = fila;
        this.columnas = columnas;
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

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        for (int j = 0; j < fila; j++) {
            cadena.append("[");
            cadena.append(j);
            cadena.append("]     ");
            for (int i = 0; i <columna; i++) {
                cadena.append(columnas.get(i).get(j).toString());
                cadena.append("   ||   ");
            }
            cadena.append("\n");
        }

        return cadena.toString();
    }

}

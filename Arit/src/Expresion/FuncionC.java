/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class FuncionC implements Expresion{
    private LinkedList<Expresion>parametros;
    private LinkedList<Expresion>Dimensiones;
    private int linea;
    private int columna;

    public FuncionC(LinkedList<Expresion> parametros, LinkedList<Expresion> Dimensiones, int linea, int columna) {
        this.parametros = parametros;
        this.Dimensiones = Dimensiones;
        this.linea = linea;
        this.columna = columna;
    }
    @Override
    public Object getValor(Entorno e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<Expresion> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the Dimensiones
     */
    public LinkedList<Expresion> getDimensiones() {
        return Dimensiones;
    }

    /**
     * @param Dimensiones the Dimensiones to set
     */
    public void setDimensiones(LinkedList<Expresion> Dimensiones) {
        this.Dimensiones = Dimensiones;
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

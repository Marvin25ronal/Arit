/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import AST.Nodo;
import Control.Break;
import Control.Continue;
import Control.Return;
import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Expresion;
import Expresion.Identificador;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Instruccion.DecAsig;
import Instruccion.Instruccion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Funcion extends Simbolo implements Instruccion {

    private LinkedList<Nodo> Sentencias;
    private LinkedList<Object> parametros;
    private int linea;
    private int columna;
    private LinkedList<Object> valores;

    public Funcion(LinkedList<Nodo> Sentencias, LinkedList<Object> parametros, int linea, int columna, TipoExp tipo, TipoExp tiposecundario, String id) {
        super(tipo, tiposecundario, id);
        this.Sentencias = Sentencias;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        for (Nodo n : getSentencias()) {
            if (n instanceof Instruccion) {
                Object result = ((Instruccion) n).ejecutar(e);
                if (result instanceof Errores) {
                    return result;
                } else if (result instanceof Continue) {
                    Continue con = (Continue) result;
                    return new Errores(Errores.TipoError.SEMANTICO, "Instruccion Continue y no se encuentra en un ciclo ", con.linea(), con.columna());
                } else if (result instanceof Break) {
                    Break b = (Break) result;
                    return new Errores(Errores.TipoError.SEMANTICO, "Instruccion Break y no se encuentra en un Switch ", b.linea(), b.columna());
                }else if(result!=null){
                    return result;
                }
            } else if (n instanceof Expresion) {
                Object result = ((Expresion) n).getValor(e);
                if (result instanceof Errores) {
                    return result;
                }else if(result!=null){
                    return result;
                    
                }
            }
        }
        return null;
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
     * @return the Sentencias
     */
    public LinkedList<Nodo> getSentencias() {
        return Sentencias;
    }

    /**
     * @param Sentencias the Sentencias to set
     */
    public void setSentencias(LinkedList<Nodo> Sentencias) {
        this.Sentencias = Sentencias;
    }

    /**
     * @return the parametros
     */
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
     * @return the parametros
     */
    public LinkedList<Object> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<Object> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the valores
     */
    public LinkedList<Object> getValores() {
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(LinkedList<Object> valores) {
        this.valores = valores;
    }

}

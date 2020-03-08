/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import AST.Nodo;
import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Identificador;
import Expresion.Llamadas;
import Expresion.TipoExp;
import Objetos.Funcion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class DecFuncion implements Instruccion {

    private LinkedList<Object> parametros;
    private Identificador id;
    private LinkedList<Nodo> sentencias;
    private int linea;
    private int columna;

    public DecFuncion(LinkedList<Object> parametros, Identificador id, LinkedList<Nodo> sentencias, int linea, int columna) {
        this.parametros = parametros;
        this.id = id;
        this.sentencias = sentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        //Verificar si los parametros no son iguales
        //Verificar si existe la funcion
        //Insertar
        Llamadas l = new Llamadas(null, null, null);
        if (l.Nativa(id.getVal().toLowerCase())) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se puede declarar la funcion porque es nativa ", linea, columna));
            return null;
        }
        if (ParametrosNoRepetidos(e)) {
            if ((e.ExisteVariable("Funcion_" + id.getVal())) == false) {
                Funcion nueva = new Funcion(sentencias, parametros, linea, columna, new TipoExp(TipoExp.Tipos.FUNCION), null, id.getVal());
                e.add("Funcion_" + id.getVal(), nueva);
            } else {
                Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La funcion ya existe " + id.getVal(), linea, columna));
                return null;
            }
        } else {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La funcion cuenta con identificadores repetidos en sus parametros", linea, columna));
            return null;
        }

        return null;
    }

    private boolean ParametrosNoRepetidos(Entorno e) {
        Identificador a = null, b = null;
        for (int i = 0; i < parametros.size(); i++) {
            if (parametros.get(i) instanceof Identificador) {
                a = (Identificador) parametros.get(i);
            } else if (parametros.get(i) instanceof DecAsig) {
                a = ((DecAsig) parametros.get(i)).getId();
            }
            for (int j = i + 1; j < parametros.size(); j++) {
                if (parametros.get(j) instanceof Identificador) {
                    b = (Identificador) parametros.get(j);
                } else if (parametros.get(j) instanceof DecAsig) {
                    b = ((DecAsig) parametros.get(j)).getId();
                }
                if (a.getVal().equals(b.getVal())) {
                    return false;
                }
            }
        }
        return true;
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
     * @return the sentencias
     */
    public LinkedList<Nodo> getSentencias() {
        return sentencias;
    }

    /**
     * @param sentencias the sentencias to set
     */
    public void setSentencias(LinkedList<Nodo> sentencias) {
        this.sentencias = sentencias;
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

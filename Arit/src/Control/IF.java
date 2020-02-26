/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import AST.Nodo;
import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.TipoExp;
import Instruccion.Instruccion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class IF implements Instruccion {

    private Expresion condicion;
    private LinkedList<Nodo> sentencias;
    private LinkedList<Instruccion> Lifs;
    private int linea;
    private int columna;

    public IF(Expresion condicion, LinkedList<Nodo> sentencias, LinkedList<Instruccion> Lifs, int linea, int columna) {
        this.condicion = condicion;
        this.sentencias = sentencias;
        this.Lifs = Lifs;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        boolean entro = false;
        Object ifacutal = condicion.getValor(e);
        if (ifacutal instanceof Errores) {
            return ifacutal;
        }
        if (condicion.getTipo(e).isBoolean()) {
            if (Boolean.parseBoolean(ifacutal.toString())) {
                Entorno global = new Entorno(e);
                entro = true;
                if (sentencias != null) {
                    for (Nodo n : sentencias) {
                        if (n instanceof Instruccion) {
                            Object result = ((Instruccion) n).ejecutar(global);
                            if (result instanceof Errores) {
                                Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                            } else if (result != null) {
                                //break continue y esas cosas
                                return result;
                            }
                        } else if (n instanceof Expresion) {
                            Object result = ((Expresion) n).getValor(global);
                            if (result instanceof Errores) {
                                Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                            }
                        }
                    }
                }
            }
            if (Lifs != null) {
                for (Instruccion n : Lifs) {
                    
                    if (n instanceof ElseIf) {
                        ifacutal = ((ElseIf) n).getCondicion().getValor(e);
                        if (ifacutal instanceof Errores) {
                            Globales.VarGlobales.getInstance().AgregarEU((Errores) ifacutal);
                            continue;
                        }
                        if (!((ElseIf) n).getCondicion().getTipo(e).isBoolean()) {
                            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La condicion no es booleana del else if", ((ElseIf) n).getLinea(), ((ElseIf) n).getColumna()));
                            continue;
                        }
                    } else if (n instanceof Else) {
                        ifacutal = true;
                    }
                    if (Boolean.parseBoolean(ifacutal.toString()) && entro == false) {
                        entro = true;
                        n.ejecutar(e);
                    }

                }
            }
        } else {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La condicion no es booleana", linea, columna));
        }
        return null;
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
     * @return the condicion
     */
    public Expresion getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
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
     * @return the Lifs
     */
    public LinkedList<Instruccion> getLifs() {
        return Lifs;
    }

    /**
     * @param Lifs the Lifs to set
     */
    public void setLifs(LinkedList<Instruccion> Lifs) {
        this.Lifs = Lifs;
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
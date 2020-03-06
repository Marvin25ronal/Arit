/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import Objetos.Array;
import Objetos.Funcion;
import Objetos.Lista;
import Objetos.Matrix;
import Objetos.Vector;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author marvi
 */
public class Entorno {

    public Entorno padre;
    public HashMap<String, Simbolo> tabla;

    public Entorno(Entorno padre) {
        this.padre = padre;
        tabla = new HashMap<>();
    }

    public boolean ExisteVariable(String id) {
        for (Entorno e = this; e != null; e = e.getPadre()) {
            Simbolo encontrado = e.tabla.get(id.toLowerCase());
            if (encontrado != null) {
                return true;
            }
        }
        return false;
    }

    public Entorno getGlobal() {
        Entorno ant = null;
        for (Entorno e = this; e != null; e = e.getPadre()) {
            ant = e;
        }
        return ant;
    }

    private Entorno getPadre() {
        return this.padre;
    }

    public void add(String id, Simbolo s) {
        tabla.put(id.toLowerCase(), s);
    }

    public void Actualizar(String id, Simbolo valor) {
        for (Entorno e = this; e != null; e = e.getPadre()) {
            Simbolo encontrado = e.tabla.get(id.toLowerCase());
            if (encontrado != null) {
                e.tabla.put(id.toLowerCase(), valor);
                return;
            }
        }

    }

    public Simbolo get(String id) {
        for (Entorno e = this; e != null; e = e.getPadre()) {
            Simbolo encontrado = e.tabla.get(id.toLowerCase());
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public Entorno Copiar() {
        Stack<Entorno> pilaE = new Stack<>();
        for (Entorno aux = this; aux != null; aux = aux.getPadre()) {
            Entorno enuevo = new Entorno(null);
            for (HashMap.Entry<String, Simbolo> env : aux.tabla.entrySet()) {
                if (env.getValue() instanceof Vector) {
                    Vector v = (Vector) env.getValue();
                    Vector nuevoV = new Vector(v.getId(), v.getTipo(), v.getTiposecundario(), Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), aux));
                    enuevo.add(nuevoV.getId(), nuevoV);
                } else if (env.getValue() instanceof Lista) {

                } else if (env.getValue() instanceof Array) {

                } else if (env.getValue() instanceof Matrix) {

                } else if (env.getValue() instanceof Funcion) {
                    Funcion f = (Funcion) env.getValue();
                    Funcion fn = new Funcion(f.getSentencias(), f.getParametros(), f.getLinea(), f.getColumna(), f.getTipo(), f.getTiposecundario(),f.getId());
                    enuevo.add("Funcion_"+fn.getId(), fn);
                }
            }
            pilaE.add(enuevo);
        }
        Entorno anterior = null;
        Entorno aux = null;

        while (!pilaE.isEmpty()) {
            aux = pilaE.pop();
            aux.padre = anterior;
            anterior = aux;

        }
        return aux;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import Objetos.Array;
import Objetos.Funcion;

import Objetos.Matrix;
import Objetos.EstructuraLineal;
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
    public boolean ExisteEnEntorno(String id){
        Simbolo en=this.tabla.get(id.toLowerCase());
        if(en!=null){
            return true;
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

   
}

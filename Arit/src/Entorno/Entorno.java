/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import java.util.HashMap;
import java.util.LinkedList;

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
            Simbolo encontrado = e.tabla.get(id);
            if (encontrado != null) {
                return true;
            }
        }
        return false;
    }
    
    private Entorno getPadre() {
        return this.padre;
    }
    
    public void add(String id, Simbolo s) {
        tabla.put(id, s);
    }

    public void Remover(String id) {
        tabla.remove(id);
    }

    public Simbolo get(String id) {
        for (Entorno e = this; e != null; e = e.getPadre()) {
            Simbolo encontrado = e.tabla.get(id);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import java.util.HashMap;

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
    
}

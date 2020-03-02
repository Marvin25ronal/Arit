/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Entorno.Simbolo;
import Expresion.TipoExp;

/**
 *
 * @author marvi
 */
public class Array extends Simbolo{
    
    public Array(TipoExp tipo, TipoExp tiposecundario, String id) {
        super(tipo, tiposecundario, id);
    }
    
}

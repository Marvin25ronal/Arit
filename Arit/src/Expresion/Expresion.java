/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import AST.Nodo;
import Entorno.Entorno;

/**
 *
 * @author User
 */
public interface Expresion extends Nodo {
    Object getValor(Entorno e);
    TipoExp getTipo(Entorno e);
}

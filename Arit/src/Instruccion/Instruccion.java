/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import AST.Nodo;
import Entorno.Entorno;

/**
 *
 * @author marvi
 */
public interface Instruccion extends Nodo{
    Object ejecutar(Entorno e);
}

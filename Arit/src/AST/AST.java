/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import Entorno.Entorno;
import Expresion.Expresion;
import java.util.LinkedList;
import Instruccion.Instruccion;

/**
 *
 * @author marvi
 */
public class AST {
    LinkedList<Nodo>Acciones;

    public AST(LinkedList<Nodo> Acciones) {
        this.Acciones = Acciones;
    }
    public Object ejecutar(){
        Entorno global=new Entorno(null);
        Globales.VarGlobales.getInstance().getConsola().append("Salida--------\n");
        for(Nodo n : Acciones){
            if(n instanceof Instruccion){
                Object result=((Instruccion)((Instruccion) n).ejecutar(global));
             
            }else if(n instanceof Expresion){
                Expresion exp=(Expresion)n;
                Object result=exp.getValor(global);
            }
        }
        return null;
    }
}

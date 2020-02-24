/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Identificador;
import Expresion.TipoExp.Tipos;

/**
 *
 * @author marvi
 */
public class DecAsig implements Instruccion {

    Expresion valor;
    Identificador id;
    int linea, columna;

    public DecAsig(Expresion valor, Identificador id, int linea, int columna) {
        this.valor = valor;
        this.id = id;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if(valor.getTipo(e).tp==Tipos.NULO){
            //cuando es nulo
        }else if(isPrimitive(e)){
            //se crea el arreglo con los nuevos valores
        }else{
            //el vector va a cambiar
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
    private boolean isPrimitive(Entorno e){
        if(valor.getTipo(e).tp==Tipos.VECTOR){
            return false;
        }
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Entorno.Entorno;
import Expresion.TipoExp;

/**
 *
 * @author marvi
 */
public class Nulo implements Expresion.Expresion {

    TipoExp tipo;
    int linea, columna;

    public Nulo(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
        tipo = new TipoExp(TipoExp.Tipos.NULO);
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public Object getValor(Entorno e) {
        return this;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return tipo;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

}

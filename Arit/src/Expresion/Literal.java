/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Objetos.Nulo;

/**
 *
 * @author marvi
 */
public class Literal implements Expresion {

    public Object valor;
    public TipoExp tipo;
    int linea;
    int columna;

    public Literal(Object valor, TipoExp tipo, int linea, int columna) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        /*if (valor instanceof Nulo) {
            return ((Nulo) valor).getValor(e);
        }*/
        return this.valor;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        return this.tipo;
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

}

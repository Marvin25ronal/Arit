/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;

/**
 *
 * @author marvi
 */
public class Return implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;

    public Return(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        return this;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        if (exp == null) {
            return new TipoExp(Tipos.NULO);
        }
        return exp.getTipo(e); 
    }

    @Override
    public int linea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int columna() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Objetos.Nulo;
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;

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

        Object val = exp.getValor(e);
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(val, e);
        if (val instanceof Literal) {
            Literal l = (Literal) val;
            LinkedList<Object> dimensiones = new LinkedList<>();
            dimensiones.add(l);
            return new Vector("", new TipoExp(Tipos.VECTOR), l.getTipo(e), dimensiones);
        } else if (val instanceof Errores) {
            return val;
        } else if (t.isPrimitive(e)) {
            LinkedList<Object> dimensiones = new LinkedList<>();
            dimensiones.add(new Literal(val, t, linea, columna));
            return new Vector("", new TipoExp(Tipos.VECTOR), t, dimensiones);
        } else if (val != null) {
            return val;
        }
        return null;

    }

    @Override
    public TipoExp getTipo(Entorno e
    ) {
        if (exp == null) {
            return new TipoExp(Tipos.NULO);
        }
        return exp.getTipo(e);
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
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

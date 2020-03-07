/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Expresion.TipoExp.Tipos;
import Objetos.Matrix;
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Acceso4 implements Expresion {

    private Expresion AcFila;
    private Expresion AcColumna;
    private Object objeto;
    private int linea;
    private int columna;

    public Acceso4(Expresion AcFila, Expresion AcColumna, int linea, int columna) {
        this.AcFila = AcFila;
        this.AcColumna = AcColumna;

        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        if (AcFila != null && AcColumna != null) {
            return Acceso1(e);
        } else if (AcFila == null && AcColumna != null) {
            return Acceso3(e);
        } else if (AcFila != null && AcColumna == null) {
            return Acceso2(e);
        }
        return null;
    }

    private Object Acceso3(Entorno e) {
        Object objcol = AcColumna.getValor(e);
        if (objcol instanceof Errores) {
            return objcol;
        }
        TipoExp tfila = Globales.VarGlobales.getInstance().obtenerTipo(objcol, e);
        if (tfila.isVector()) {
            Vector v = (Vector) objcol;
            if (v.getTiposecundario().isInt()) {
                if (v.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector de la columna tiene mas de un elemento", linea, columna);
                }
                Literal l = (Literal) v.getDimensiones().get(0);
                objcol = l;
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector indice de la columna no es de tipo INTEGER", linea, columna);
            }
        } else if (!tfila.isInt()) {
            return new Errores(Errores.TipoError.SEMANTICO, "el indice de la columna no es de tipo INTEGER", linea, columna);
        }
        int indiCol = Integer.parseInt(objcol.toString()) - 1;
        Matrix matriz = (Matrix) objeto;
        if (indiCol >= matriz.getColumna()) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice de la columna es mas grande que la matriz ", linea, columna);
        } else if (indiCol < 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice inicia en 1", linea, columna);
        }
        LinkedList<Object> elementos = new LinkedList<>();
        for (int i = 0; i < matriz.getFila(); i++) {
            Vector v = (Vector) matriz.getColumnas().get(indiCol).get(i);
            elementos.add(v.getDimensiones().getFirst());
        }
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(matriz);
        //Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        return new Vector("", new TipoExp(Tipos.VECTOR), new TipoExp(matriz.getTiposecundario().tp), elementos);
    }

    private Object Acceso2(Entorno e) {
        Object objfila = AcFila.getValor(e);
        if (objfila instanceof Errores) {
            return objfila;
        }
        TipoExp tfila = Globales.VarGlobales.getInstance().obtenerTipo(objfila, e);
        if (tfila.isVector()) {
            Vector v = (Vector) objfila;
            if (v.getTiposecundario().isInt()) {
                if (v.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector de la fila tiene mas de un elemento", linea, columna);
                }
                Literal l = (Literal) v.getDimensiones().get(0);
                objfila = l;
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector indice de la fila no es de tipo INTEGER", linea, columna);
            }
        } else if (!tfila.isInt()) {
            return new Errores(Errores.TipoError.SEMANTICO, "el indice de la fila no es de tipo INTEGER", linea, columna);
        }
        int indiFila = Integer.parseInt(objfila.toString()) - 1;
        Matrix matriz = (Matrix) objeto;
        if (indiFila >= matriz.getFila()) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice de la fila es mas grande que la matriz ", linea, columna);
        } else if (indiFila < 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice inicia en 1", linea, columna);
        }
        LinkedList<Object> elementos = new LinkedList<>();
        for (int i = 0; i < matriz.getColumna(); i++) {
            Vector v = (Vector) matriz.getColumnas().get(i).get(indiFila);
            elementos.add(v.getDimensiones().getFirst());
        }
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(matriz);
        return new Vector("", new TipoExp(Tipos.VECTOR), new TipoExp(matriz.getTiposecundario().tp), elementos);
    }

    private Object Acceso1(Entorno e) {
        Object objfila = AcFila.getValor(e);
        Object objcol = AcColumna.getValor(e);
        if (objfila instanceof Errores) {
            return objfila;
        } else if (objcol instanceof Errores) {
            return objcol;
        }
        TipoExp tfila = Globales.VarGlobales.getInstance().obtenerTipo(objfila, e);
        TipoExp tcol = Globales.VarGlobales.getInstance().obtenerTipo(objcol, e);
        if (tfila.isVector()) {
            Vector v = (Vector) objfila;
            if (v.getTiposecundario().isInt()) {
                if (v.getDimensiones().size() != 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector de la fila tiene mas de un elemento", linea, columna);
                }
                Literal l = (Literal) v.getDimensiones().get(0);
                objfila = l;
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector de indice de la fila no es de tipo INTEGER", linea, columna);
            }
        } else if (!tfila.isInt()) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice de la fila no es de tipo INTEGER ", linea, columna);
        }
        if (tcol.isVector()) {
            Vector v = (Vector) objcol;
            if (v.getTiposecundario().isInt()) {
                if (v.getDimensiones().size() != 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector de la columna tiene mas de un elemento", linea, columna);
                }
                Literal l = (Literal) v.getDimensiones().get(0);
                objcol = l;
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector de indice de la columna no es de tipo INTEGER", linea, columna);
            }
        } else if (!tcol.isInt()) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice de la columna no es de tipo INTEGER", linea, columna);
        }
        int indiFila = Integer.parseInt(objfila.toString()) - 1;
        int indiCol = Integer.parseInt(objcol.toString()) - 1;
        Matrix matriz = (Matrix) objeto;
        if (indiFila < 0 || indiCol < 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor o igual a 1", linea, columna);
        }
        if (indiFila >= matriz.getFila()) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice de la fila es mas grande al tamanio de las filas de la matriz", linea, columna);
        } else if (indiCol >= matriz.getColumna()) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice de la fila es mas grande al tamanio de las columnas de la matriz", linea, columna);
        }
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(matriz);
        return matriz.getColumnas().get(indiCol).get(indiFila);
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int linea() {
        return this.getLinea();
    }

    @Override
    public int columna() {
        return this.getColumna();
    }

    /**
     * @return the AcFila
     */
    public Expresion getAcFila() {
        return AcFila;
    }

    /**
     * @param AcFila the AcFila to set
     */
    public void setAcFila(Expresion AcFila) {
        this.AcFila = AcFila;
    }

    /**
     * @return the AcColumna
     */
    public Expresion getAcColumna() {
        return AcColumna;
    }

    /**
     * @param AcColumna the AcColumna to set
     */
    public void setAcColumna(Expresion AcColumna) {
        this.AcColumna = AcColumna;
    }

    /**
     * @return the objeto
     */
    public Object getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    /**
     * @return the fila
     */
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.TipoExp.Tipos;
import Objetos.Array;

import Objetos.Matrix;
import Objetos.Nulo;
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class AccesoUnico implements Expresion {

    /**
     * @return the array
     */
    public boolean isArray() {
        return array;
    }

    /**
     * @param array the array to set
     */
    public void setArray(boolean array) {
        this.array = array;
    }

    private Expresion indice;
    private Expresion indiceAuxiliar;
    private int linea;
    private int columna;
    private Object objeto;
    private boolean incremento;
    private boolean array;
    private int IndiceArray;

    public AccesoUnico(Expresion indice, int linea, int columna) {
        this.indice = indice;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValor(Entorno e) {
        indiceAuxiliar=indice;
        Object i = getIndice().getValor(e);
        if (i instanceof Errores) {
            return i;
        }
        TipoExp tipo = Globales.VarGlobales.getInstance().obtenerTipo(i, e);

        if (tipo.tp == Tipos.VECTOR) {
            //solo tenga un valor
            EstructuraLineal v = (EstructuraLineal) i;
            if (v.getTiposecundario().tp != Tipos.INTEGER) {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo INTEGER", linea, columna);
            }
            if (v.getDimensiones().size() == 1) {

                indiceAuxiliar = (Expresion) v.getDimensiones().get(0);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector de indice es de mayor tama;o que 1", linea, columna);
            }
        } else if (tipo.tp != Tipos.INTEGER) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser de tipo numerico el tipo es " + tipo.tp, getLinea(), getColumna());
        }
        if (isArray()) {
            return AccesoArray(e);
        }
        Simbolo s = (Simbolo) getObjeto();
        if (s.getTipo().isVector()) {
            return AccesoVector(e);
        } else if (s.getTipo().isList()) {
            return AccesoLista(e);
        } else if (s.getTipo().isMatrix()) {
            return AccesoMatriz(e);
        }
        return null;
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
     * @return the indice
     */
    public Expresion getIndice() {
        return indiceAuxiliar;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Expresion indice) {
        this.indice = indice;
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

    private Object AccesoMatriz(Entorno e) {
        Matrix matriz = (Matrix) getObjeto();
        int inde = Integer.parseInt(getIndice().getValor(e).toString());
        if (inde <= 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", linea, columna);
        } else if (inde > matriz.getColumna() * matriz.getFila()) {
            return new Errores(Errores.TipoError.SEMANTICO, "se paso del indice de la matriz", linea, columna);
        }
        inde--;
        for (int i = 0; i < matriz.getColumna(); i++) {
            for (int j = 0; j < matriz.getFila(); j++) {
                if (inde == 0) {
                    return matriz.getColumnas().get(i).get(j);
                }
                inde--;
            }
        }
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(matriz);
        Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        return null;
    }

    private Object AccesoArray(Entorno e) {
        if (getObjeto() instanceof Array) {
            Array array = (Array) getObjeto();
            int inde = Integer.parseInt(getIndice().getValor(e).toString());
            if (inde <= 0) {
                return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", linea, columna);
            } else if (inde > IndiceArray) {
                return new Errores(Errores.TipoError.SEMANTICO, "Se paso del indice en el ARRAY ", linea, columna);
            }
            inde--;
            Globales.VarGlobales.getInstance().getAnterior().setAnterior(array);
            Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
            return array.getArreglo().get(inde);
        } else {
            //lista
            LinkedList<Object> lista = (LinkedList<Object>) getObjeto();
            int inde = Integer.parseInt(getIndice().getValor(e).toString());
            if (inde <= 0) {
                return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", linea, columna);
            } else if (inde > IndiceArray) {
                return new Errores(Errores.TipoError.SEMANTICO, "Se paso del indice en el ARRAY ", linea, columna);
            }
            inde--;
            return lista.get(inde);
        }
    }

    private Object AccesoVector(Entorno e) {
        EstructuraLineal v = (EstructuraLineal) getObjeto();
        int inde = Integer.parseInt(getIndice().getValor(e).toString());
        if (inde <= 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", getLinea(), getColumna());
        } else if (inde > v.getTam()) {
            if (incremento) {
                return AccesoVectorIncremento(v, inde);
            }
            return new Errores(Errores.TipoError.SEMANTICO, "Se paso del indice del vector ", getLinea(), getColumna());
        }
        inde--;
        LinkedList<Object> lista = new LinkedList<Object>();
        lista.add(v.getDimensiones().get(inde));
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(v);
        Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        Globales.VarGlobales.getInstance().getAnterior().setAcceso(1);
        return new EstructuraLineal(v.getId(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), lista);
    }

    private Object AccesoLista(Entorno e) {
        EstructuraLineal l = (EstructuraLineal) getObjeto();
        int inde = Integer.parseInt(getIndice().getValor(e).toString());
        if (inde <= 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "El indice tiene que ser mayor a 0", linea, columna);
        } else if (inde > l.getDimensiones().size()) {
            if (incremento) {
                return AccesoListaIncremento(l, inde);
            }
            return new Errores(Errores.TipoError.SEMANTICO, "Se paso del indice de la lista", getLinea(), getColumna());
        }

        inde--;
        LinkedList<Object> lista = new LinkedList<>();
        lista.add(l.getDimensiones().get(inde));
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(l);
        Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        Globales.VarGlobales.getInstance().getAnterior().setAcceso(1);
        //return new Lista(lista, new TipoExp(Tipos.LISTA), null, "");
        return new EstructuraLineal("", new TipoExp(Tipos.LISTA), null, lista);
    }

    private Object AccesoVectorIncremento(EstructuraLineal v, int inde) {
        for (int i = v.getTam(); i < inde; i++) {
            v.getDimensiones().add(ObtenerDefault(v.getTiposecundario()));
        }
        inde--;
        LinkedList<Object> lista = new LinkedList<>();
        lista.add(v.getDimensiones().get(inde));
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(v);
        Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        Globales.VarGlobales.getInstance().getAnterior().setAcceso(1);
        return new EstructuraLineal(v.getId(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), lista);
    }

    private Object AccesoListaIncremento(EstructuraLineal l, int inde) {
        for (int i = l.getDimensiones().size(); i < inde; i++) {
            LinkedList<Object> li = new LinkedList<>();
            li.add(new Literal(new Nulo(linea, columna), new TipoExp(Tipos.STRING), linea, columna));
            EstructuraLineal v = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), li);
            l.getDimensiones().add(v);
        }
        inde--;
        LinkedList<Object> lista = new LinkedList<>();
        lista.add(l.getDimensiones().get(inde));
        Globales.VarGlobales.getInstance().getAnterior().setAnterior(l);
        Globales.VarGlobales.getInstance().getAnterior().setIndice(inde);
        Globales.VarGlobales.getInstance().getAnterior().setAcceso(1);
        //return new Lista(lista, new TipoExp(Tipos.LISTA), null, "");
        return new EstructuraLineal("", new TipoExp(Tipos.LISTA), null, lista);
    }

    private Literal ObtenerDefault(TipoExp t) {
        switch (t.tp) {
            case BOOLEAN:
                return new Literal(false, t, linea, columna);
            case INTEGER:
                return new Literal(0, t, linea, columna);
            case STRING:
                return new Literal(new Nulo(linea, columna), t, linea, columna);
            case NUMERIC:
                return new Literal(0.0, t, linea, columna);

        }
        return null;
    }

    /**
     * @return the incremento
     */
    public boolean isIncremento() {
        return incremento;
    }

    /**
     * @param incremento the incremento to set
     */
    public void setIncremento(boolean incremento) {
        this.incremento = incremento;
    }

    /**
     * @return the IndiceArray
     */
    public int getIndiceArray() {
        return IndiceArray;
    }

    /**
     * @param IndiceArray the IndiceArray to set
     */
    public void setIndiceArray(int IndiceArray) {
        this.IndiceArray = IndiceArray;
    }

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Acceso1[] \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append(indice.toDot(this.hashCode()));
        return nueva.toString();
    }

}

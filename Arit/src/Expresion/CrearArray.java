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
import Objetos.EstructuraLineal;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class CrearArray {

    private LinkedList<Expresion> parametros;
    private LinkedList<Expresion> dimensiones;
    private int linea;
    private int columna;
    int indice = 0;

    public CrearArray(LinkedList<Expresion> parametros, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.parametros = parametros;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    public Object Ejecutar(Entorno e) {
        if (parametros.size() == 2) {
            Object datos = parametros.get(0).getValor(e);
            Object dim = parametros.get(1).getValor(e);
            if (datos instanceof Errores) {
                return datos;
            } else if (dim instanceof Errores) {
                return dim;
            }
            TipoExp tvec = Globales.VarGlobales.getInstance().obtenerTipo(datos, e);
            TipoExp tdim = Globales.VarGlobales.getInstance().obtenerTipo(dim, e);
            if (tvec.isVector() || tvec.isList()) {
                //el dato a meter es un vector o lista
                return Array_EstructuraL(e, dim, tdim, datos, tvec);
            } else if (tvec.isPrimitive(e)) {
                Literal l = new Literal(datos, tvec, linea, columna);
                LinkedList<Object> d = new LinkedList<>();
                d.add(l);
                EstructuraLineal nueva = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), tvec, d);
                return Array_EstructuraL(e, dim, tdim, nueva, nueva.getTipo());
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "Este tipo de elemento no puede contener los array", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "Los parametros son incorrectos del arreglo", linea, columna);
        }
    }

    private Object Array_EstructuraL(Entorno e, Object dim, TipoExp tdim, Object datos, TipoExp tvec) {
        EstructuraLineal d = (EstructuraLineal) datos;
        LinkedList<Object> metiendo = null;
        LinkedList<Integer> listad = new LinkedList<>();
        LinkedList<Object> arreglo = new LinkedList<>();
        TipoExp tarreglo = null;
        if (tdim.isVector() || tdim.isList()) {
            EstructuraLineal Edim = (EstructuraLineal) dim;
            if (Edim.getTipo().isVector()) {
                if (Edim.getTiposecundario().isInt()) {
                    for (int i = 0; i < Edim.getDimensiones().size(); i++) {
                        int newindice = Integer.parseInt(((Literal) Edim.getDimensiones().get(i)).getValor().toString());
                        listad.addFirst(newindice);
                    }
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que ser de tipo entero", linea, columna);
                }
            } else {
                if (Edim.ListaIgual(e)) {
                    if (((Simbolo) Edim.getDimensiones().get(0)).getTiposecundario().isInt()) {
                        for (int i = 0; i < Edim.getDimensiones().size(); i++) {
                            EstructuraLineal aux = (EstructuraLineal) Edim.getDimensiones().get(i);
                            int newindice = Integer.parseInt(((Literal) aux.getDimensiones().get(0)).getValor().toString());
                            listad.addFirst(newindice);
                        }
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "Los tipos de las listas tienen que ser INTEGER", linea, columna);
                    }
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "La lista tiene que tener el mismo tipo de elementos", linea, columna);
                }
            }

        } else if (tdim.isInt()) {
            int newindice = Integer.parseInt(dim.toString());
            listad.addFirst(newindice);
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "Las dimensiones tienen que ser un arreglo", linea, columna);
        }
        if (tvec.isVector()) {
            metiendo = Globales.VarGlobales.getInstance().clonarListaVector(d.getDimensiones(), e);
            //son literalaes
            for (int i = 0; i < metiendo.size(); i++) {
                LinkedList<Object> n = new LinkedList<>();
                n.add(metiendo.get(i));
                EstructuraLineal nueval = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), ((Literal) metiendo.get(i)).getTipo(), n);
                metiendo.set(i, nueval);
            }
            tarreglo = new TipoExp(d.getTiposecundario().tp);
        } else {
            metiendo = Globales.VarGlobales.getInstance().CopiarLista(e, d.getDimensiones());
            tarreglo = ((EstructuraLineal) d.getDimensiones().get(0)).getTiposecundario();
        }
        CrearLista(arreglo, indice, listad);
        LlenarLista(arreglo, metiendo);

        Array nuevo = new Array(new TipoExp(Tipos.ARRAY), tarreglo, "", arreglo, listad);
        return nuevo;
    }

    private void CrearLista(LinkedList<Object> padre, int indice, LinkedList<Integer> dimensiones) {
        if (indice == dimensiones.size()) {
            return;
        }
        int tam = dimensiones.get(indice);
        for (int i = 0; i < tam; i++) {
            LinkedList<Object> nueva = new LinkedList<>();
            CrearLista(nueva, indice + 1, dimensiones);
            padre.add(nueva);
        }
    }

    private void LlenarLista(LinkedList<Object> padre, LinkedList<Object> datos) {
        if (padre.size() == 0) {
            //aqui las meto
            if (indice == datos.size()) {
                indice = 0;
            }
            padre.add(datos.get(indice));
            indice++;
        } else {
            for (int i = 0; i < padre.size(); i++) {
                LlenarLista((LinkedList<Object>) padre.get(i), datos);
            }
        }
    }

    /**
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<Expresion> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the dimensiones
     */
    public LinkedList<Expresion> getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(LinkedList<Expresion> dimensiones) {
        this.dimensiones = dimensiones;
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

}

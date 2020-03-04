/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Expresion;
import Expresion.Identificador;
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
public class DecAsig implements Instruccion {

    private Expresion valor;
    private Identificador id;
    private int linea;
    private int columna;

    public DecAsig(Expresion valor, Identificador id, int linea, int columna) {
        this.valor = valor;
        this.id = id;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if (getValor() == null) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se pudo declarar", getLinea(), getColumna()));
             return null;
        }
        Object setvalor = getValor().getValor(e);
        if (setvalor instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) setvalor);
            return null;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(setvalor, e);
        if (t.tp == Tipos.NULO) {
            //cuando es nulo
            if (e.ExisteVariable(getId().getVal())) {
                ReasignarVector_Nulo(e);
            } else {
                CrearVector_Nulo(e);
            }
        } else if (isPrimitive(e)) {
            //se crea el arreglo con los nuevos valores
            /*
            Reglas--------------
            primero ver si no existe antes para reasignar valor
            agregarla a la tabla
             */
            if (e.ExisteVariable(getId().getVal())) {
                //se reasigna
                ReasignarVector_Primitivo(e, setvalor, t);
            } else {
                //arreglo nuevo
                CrearNuevoVector_Primitivo(e, setvalor, t);
            }
        } else {
            //el vector va a cambiar cuando son una lista de valores
            if (t.isVector()) {
                if (e.ExisteVariable(getId().getVal())) {
                    ReasignarVector_Vector(e, setvalor, t);
                } else {
                    CrearNuevoVector_Vector(e, setvalor, t);
                }
            }
        }
        return null;
    }

    private void CrearNuevoVector_Primitivo(Entorno e, Object setvalor, TipoExp t) {
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(setvalor, t, getLinea(), getColumna());
        datos.add(nueva);
        Vector nuevo = new Vector(getId().getVal(), new TipoExp(Tipos.VECTOR), t, datos);
        e.add(getId().getVal(), nuevo);
    }

    private void CrearVector_Nulo(Entorno e) {
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(new Nulo(getLinea(), getColumna()), new TipoExp(Tipos.NULO), getLinea(), getColumna());
        datos.add(nueva);
        Vector nuevo = new Vector(getId().getVal(), new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), datos);
        e.add(getId().getVal(), nuevo);
    }

    private void ReasignarVector_Nulo(Entorno e) {
        Simbolo s = e.get(getId().getVal());
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(new Nulo(getLinea(), getColumna()), new TipoExp(Tipos.NULO), getLinea(), getColumna());
        datos.add(nueva);
        Vector nuevo = new Vector(getId().getVal(), new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), datos);
        e.Actualizar(getId().getVal(), nuevo);
    }

    private void ReasignarVector_Primitivo(Entorno e, Object setvalor, TipoExp t) {
        //Verificar si es un vector
        Simbolo s = e.get(getId().getVal());
        if (s.getTipo().tp == Tipos.VECTOR) {
            LinkedList<Object> datos = new LinkedList<>();
            Literal nueva = new Literal(setvalor, t, getLinea(), getColumna());
            datos.add(nueva);
            Vector v = new Vector(getId().getVal(), new TipoExp(Tipos.VECTOR), t, datos);
            e.Actualizar(getId().getVal(), v);

        }
    }

    private void CrearNuevoVector_Vector(Entorno e, Object setvalor, TipoExp t) {
        Vector v = (Vector) setvalor;
        LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        Vector nuevo = new Vector(getId().getVal(), new TipoExp(Tipos.VECTOR), t, datos);
        e.add(getId().getVal(), nuevo);
    }

    private void ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t) {
        //a un vector solo se le puede asignar un vector
        Vector aux = (Vector) setvalor;
        LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(aux.getDimensiones(), e);
        Vector v = new Vector(getId().getVal(), new TipoExp(Tipos.VECTOR), aux.getTiposecundario(), datos);
        e.Actualizar(getId().getVal(), v);
    }

    @Override
    public int linea() {
        return this.getLinea();
    }

    @Override
    public int columna() {
        return this.getColumna();
    }

    private boolean isPrimitive(Entorno e) {
        if (getValor().getTipo(e).tp == Tipos.VECTOR) {
            return false;
        }
        return true;
    }

    /**
     * @return the valor
     */
    public Expresion getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Expresion valor) {
        this.valor = valor;
    }

    /**
     * @return the id
     */
    public Identificador getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Identificador id) {
        this.id = id;
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

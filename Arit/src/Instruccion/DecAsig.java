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
        if (valor == null) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se pudo declarar", linea, columna));
        }
        Object setvalor = valor.getValor(e);
        if (setvalor instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) setvalor);
        }
        TipoExp t = valor.getTipo(e);
        if (t.tp == Tipos.NULO) {
            //cuando es nulo
            if (e.ExisteVariable(id.getVal())) {
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
            if (e.ExisteVariable(id.getVal())) {
                //se reasigna
                ReasignarVector_Primitivo(e, setvalor, t);
            } else {
                //arreglo nuevo
                CrearNuevoVector_Primitivo(e, setvalor, t);
            }
        } else {
            //el vector va a cambiar cuando son una lista de valores
            if (t.isVector()) {
                if (e.ExisteVariable(id.getVal())) {
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
        Literal nueva = new Literal(setvalor, t, linea, columna);
        datos.add(nueva);
        Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), t, datos);
        e.add(id.getVal(), nuevo);
    }

    private void CrearVector_Nulo(Entorno e) {
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(new Nulo(linea, columna), new TipoExp(Tipos.NULO), linea, columna);
        datos.add(nueva);
        Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), datos);
        e.add(id.getVal(), nuevo);
    }

    private void ReasignarVector_Nulo(Entorno e) {
        Simbolo s = e.get(id.getVal());
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(new Nulo(linea, columna), new TipoExp(Tipos.NULO), linea, columna);
        //Vector nuevo=new Vector(id, this, this, dimensiones)
    }

    private void ReasignarVector_Primitivo(Entorno e, Object setvalor, TipoExp t) {
        //Verificar si es un vector
        Simbolo s = e.get(id.getVal());
        if (s.getTipo().tp == Tipos.VECTOR) {
            LinkedList<Object> datos = new LinkedList<>();
            Literal nueva = new Literal(setvalor, t, linea, columna);
            datos.add(nueva);
            Vector v = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), t, datos);
            e.Actualizar(id.getVal(), v);

        }
    }

    private void CrearNuevoVector_Vector(Entorno e, Object setvalor, TipoExp t) {
        Vector v = (Vector) setvalor;
        LinkedList<Object> datos = (LinkedList<Object>) v.getDimensiones().clone();
        Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), t, datos);
        e.add(id.getVal(), nuevo);
    }

    private void ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t) {
        //a un vector solo se le puede asignar un vector
        Vector aux = (Vector) setvalor;
        LinkedList<Object> datos = new LinkedList<>(aux.getDimensiones());
        Vector v = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), aux.getTiposecundario(), datos);
        e.Actualizar(id.getVal(), v);
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

    private boolean isPrimitive(Entorno e) {
        if (valor.getTipo(e).tp == Tipos.VECTOR) {
            return false;
        }
        return true;
    }

}

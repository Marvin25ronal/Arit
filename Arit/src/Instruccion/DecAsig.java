/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Identificador;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
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
        if (valor.getTipo(e).tp == Tipos.NULO) {
            //cuando es nulo

        } else if (isPrimitive(e)) {
            //se crea el arreglo con los nuevos valores
            /*
            Reglas--------------
            primero ver si no existe antes para reasignar valor
            agregarla a la tabla
             */
            if (e.ExisteVariable(id.getVal())) {
                //se reasigna
            } else {
                //arreglo nuevo
                LinkedList<Object> datos = new LinkedList<>();
                Literal nueva = new Literal(setvalor, valor.getTipo(e), linea, columna);
                datos.add(nueva);
                Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), valor.getTipo(e), datos);
                e.add(id.getVal(),nuevo);
            }
        } else {
            //el vector va a cambiar cuando son una lista de valores
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

    private boolean isPrimitive(Entorno e) {
        if (valor.getTipo(e).tp == Tipos.VECTOR) {
            return false;
        }
        return true;
    }

}

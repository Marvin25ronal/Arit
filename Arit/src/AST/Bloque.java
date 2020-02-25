/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import Control.Return;
import Entorno.Entorno;
import Expresion.Expresion;
import Instruccion.Instruccion;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Bloque implements Instruccion {

    private LinkedList<Nodo> Acciones;

    public Bloque(LinkedList<Nodo> Acciones) {
        this.Acciones = Acciones;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Entorno global = new Entorno(null);
        //Globales.VarGlobales.getInstance().getConsola().append("Salida--------\n");
        for (Nodo n : getAcciones()) {
            if (n instanceof Instruccion) {
                Object result = ((Instruccion) ((Instruccion) n).ejecutar(global));
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU(((Errores) result));
                } else if (result != null) {
                    return result;
                }

            } else if (n instanceof Expresion) {
                Expresion exp = (Expresion) n;
                Object result = exp.getValor(global);
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU(((Errores) result));
                } else if(result !=null){
                    return result;
                }
            }
        }
        return null;
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
     * @return the Acciones
     */
    public LinkedList<Nodo> getAcciones() {
        return Acciones;
    }

    /**
     * @param Acciones the Acciones to set
     */
    public void setAcciones(LinkedList<Nodo> Acciones) {
        this.Acciones = Acciones;
    }

}

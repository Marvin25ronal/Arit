/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import Control.Return;
import Entorno.Entorno;
import Expresion.Expresion;
import Instruccion.DecFuncion;
import java.util.LinkedList;
import Instruccion.Instruccion;
import Reportes.Errores;

/**
 *
 * @author marvi
 */
public class AST {

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
    private LinkedList<Nodo> Acciones;

    public AST(LinkedList<Nodo> Acciones) {
        this.Acciones = Acciones;
    }

    public Object ejecutar() {
        Entorno global = new Entorno(null);
        Globales.VarGlobales.getInstance().getConsola().append("Salida--------\n");
        DeclararFunc(global);
        for (Nodo n : getAcciones()) {
            if (n instanceof Instruccion) {
                if (n instanceof DecFuncion) {
                    continue;
                }
                Object result = (((Instruccion) n).ejecutar(global));
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU(((Errores) result));
                } else if (result instanceof Control.Continue) {
                    Control.Continue aux = ((Control.Continue) result);
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Continue esta en un ambito que no es el correcto", aux.getLinea(), aux.getColumna()));
                } else if (result instanceof Control.Break) {
                    Control.Break aux = (Control.Break) result;
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Break esta en un ambito que no es el correcto", aux.getLinea(), aux.getColumna()));
                }

            } else if (n instanceof Expresion) {
                Expresion exp = (Expresion) n;
                Object result = exp.getValor(global);
                if (result instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU(((Errores) result));
                } else if (result instanceof Return) {
                    Return aux = (Return) result;
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Return esta en un ambito que no es el correcto", aux.getLinea(), aux.getColumna()));
                }
            }
        }
        return null;
    }

    private void DeclararFunc(Entorno e) {
        for (Nodo n : getAcciones()) {
            if (n instanceof DecFuncion) {
                ((DecFuncion) n).ejecutar(e);
            }
        }
    }

    public String HacerDot() {
        StringBuilder cad = new StringBuilder();
        cad.append("digraph G{\n"
                + "node[shape=\"box\",color=\"white\"];");
        cad.append("node"+this.hashCode()+"[label=\"raiz\",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        for (Nodo n : getAcciones()) {
            cad.append(n.toDot(this.hashCode()));
        }
        cad.append("}");
        return cad.toString();
    }
}

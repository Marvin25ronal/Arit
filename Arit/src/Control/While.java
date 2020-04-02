/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import AST.Nodo;
import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Literal;
import Expresion.TipoExp;
import Instruccion.Instruccion;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class While implements Instruccion {

    Expresion exp;
    LinkedList<Nodo> sentencias;
    int linea, columna;

    public While(Expresion exp, LinkedList<Nodo> sentencias, int linea, int columna) {
        this.exp = exp;
        this.sentencias = sentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Object cond = exp.getValor(e);
        if (cond instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) cond);
            return null;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(cond, e);
        if (t.isVector()) {
            EstructuraLineal v = (EstructuraLineal) cond;
            if (v.getTiposecundario().isBoolean()) {
                cond = ((Literal) v.getDimensiones().get(0)).getValor(e);
            } else {
                Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo BOOLEAN", linea, columna));
                return null;
            }
        } else if (t.isMatrix()) {
            Matrix m = (Matrix) cond;
            if (m.getTiposecundario().isBoolean()) {
                cond = ((Literal) m.getColumnas().get(0).get(0)).getValor(e);
            } else {
                Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La matriz no es de tipo BOOLEAN", linea, columna));
                return null;
            }
        } else if (!t.isBoolean()) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La condicion no es de tipo BOOLEANA", linea, columna));
            return null;
        }
        while (Boolean.parseBoolean(cond.toString())) {
            Entorno local = new Entorno(e);
            for (Nodo n : sentencias) {
                if (n instanceof Instruccion) {
                    Object result = ((Instruccion) n).ejecutar(local);
                    if (result instanceof Errores) {
                        Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                    } else if (result instanceof Break) {
                        return null;
                    } else if (result instanceof Continue) {
                        break;
                    } else if (result != null) {
                        //break continue y esas cosas
                        return result;
                    }
                } else if (n instanceof Expresion) {
                    Object result = ((Expresion) n).getValor(local);
                    if (result instanceof Errores) {
                        Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                    } else if (result != null) {
                        return result;
                    }
                }
            }
            cond = exp.getValor(e);
            if (cond instanceof Errores) {
                Globales.VarGlobales.getInstance().AgregarEU((Errores) cond);
                return null;
            }
            TipoExp t2 = Globales.VarGlobales.getInstance().obtenerTipo(cond, e);
            if (t2.isVector()) {
                EstructuraLineal v = (EstructuraLineal) cond;
                if (v.getTiposecundario().isBoolean()) {
                    cond = ((Literal) v.getDimensiones().get(0)).getValor(e);
                } else {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "El vector no es de tipo BOOLEAN", linea, columna));
                    return null;
                }
            } else if (t2.isMatrix()) {
                Matrix m = (Matrix) cond;
                if (m.getTiposecundario().isBoolean()) {
                    cond = ((Literal) m.getColumnas().get(0).get(0)).getValor(e);
                } else {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La matriz no es de tipo BOOLEAN", linea, columna));
                    return null;
                }
            } else if (!t2.isBoolean()) {
                Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "La condicion no es de tipo BOOLEANA", linea, columna));
                return null;
            }
        }
        return null;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Do_While \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append("node").append(this.hashCode() + 2).append("[label=\"Condicion \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 2).append(";\n");
        nueva.append(exp.toDot(this.hashCode() + 2));
        nueva.append("node").append(this.hashCode() + 1).append("[label=\"Cuerpo \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        for (Nodo n : sentencias) {
            nueva.append(n.toDot(this.hashCode() + 1));
        }
        return nueva.toString();
    }

}

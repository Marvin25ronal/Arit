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
public class Switch implements Instruccion {

    Expresion condicion;
    LinkedList<Instruccion> Lcase;
    int linea, columna;

    public Switch(Expresion condicion, LinkedList<Instruccion> Lcase, int linea, int columna) {
        this.condicion = condicion;
        this.Lcase = Lcase;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Entorno local = new Entorno(e);
        Object val = condicion.getValor(local);
        boolean entro = false;
        if (val instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) val);
            return null;
        }
        TipoExp tipo = Globales.VarGlobales.getInstance().obtenerTipo(val, e);
        if (Lcase == null) {
            return null;
        }
        if (tipo.isVector()) {
            EstructuraLineal v = (EstructuraLineal) val;
            val = ((Literal) v.getDimensiones().get(0)).getValor(e);
        } else if (tipo.isMatrix()) {
            Matrix m = (Matrix) val;
            val = ((Literal) m.getColumnas().get(0).get(0)).getValor(e);
        }
        for (Instruccion n : Lcase) {
            Object t = null;
            if (n instanceof Case) {
                t = ((Case) n).getExp().getValor(local);
                if (t instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) t);
                    continue;
                }
                TipoExp tt = Globales.VarGlobales.getInstance().obtenerTipo(t, local);
                if (tt.isVector()) {
                    EstructuraLineal v = (EstructuraLineal) t;
                    t = ((Literal) v.getDimensiones().get(0)).getValor(local);
                } else if (tt.isMatrix()) {
                    Matrix m = (Matrix) t;
                    t = ((Literal) m.getColumnas().get(0).get(0)).getValor(local);
                }
            } else if (n instanceof Else) {
                t = val;
            }
            if (entro || val.equals(t)) {
                Object ret = n.ejecutar(local);
                if (ret instanceof Break) {
                    return null;
                } else if (ret instanceof Return) {
                    return ret;
                } else if (ret instanceof Errores) {
                    Globales.VarGlobales.getInstance().AgregarEU((Errores) ret);
                    continue;
                } else if (ret != null) {
                    //Castear(e, t, new TipoExp(TipoExp.Tipos.VECTOR));
                    return ret;
                }
                entro = true;
            }
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

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Switch \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append("node").append(this.hashCode() + 1).append("[label=\"Condicion \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        nueva.append(condicion.toDot(this.hashCode() + 1));
        nueva.append("node").append(this.hashCode() + 2).append("[label=\"L_Case \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 2).append(";\n");
        for (Nodo n : Lcase) {
            nueva.append(n.toDot(this.hashCode() + 2));
        }
        return nueva.toString();
    }

}

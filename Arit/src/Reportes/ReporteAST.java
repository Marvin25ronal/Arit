/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import AST.AST;
import AST.Nodo;
import Expresion.Expresion;
import Expresion.Literal;
import Instruccion.Print;
import Objetos.Nulo;
import Operaciones.Aritmeticas;
import Operaciones.Operacion;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class ReporteAST {

    AST raiz;
    StringBuilder cadena = new StringBuilder();
    int correlativo = 0;
    public String codigo = "";

    public ReporteAST(AST raiz) {
        this.raiz = raiz;
    }

    public void IniciarReporte() {
        LinkedList<Nodo> aux = raiz.getAcciones();
        cadena.append("nodo0[label=\"raiz\",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        for (Nodo n : aux) {
            graficar(n, 0);
        }
        String codigo = "digraph G{\n";
        codigo += "node[shape=\"box\",color=\"white\"];\n";
        codigo += cadena.toString() + "}";
        this.codigo = codigo;
    }

    private void graficar(Nodo raiz, int padre) {
        if (raiz instanceof Print) {
            TraducirImprimir(raiz, padre);
        } else if (raiz instanceof Expresion) {
            TraducirExpresion(raiz, padre);
        }
    }

    private void TraducirImprimir(Nodo raiz, int padre) {
        nombrenodo("Instruccion", padre);
        nombrenodo("Print", correlativo);
        graficar(((Print) raiz).getToPrint(), correlativo);
    }

    private void TraducirExpresion(Nodo raiz, int padre) {
        if (raiz instanceof Literal) {
            nombrenodo("EXP", padre);
            TraducirLiteral(raiz,correlativo);
        } else if (raiz instanceof Aritmeticas) {
            Aritmeticas aux = (Aritmeticas) raiz;
            if (aux.op2 == null) {
                //solo tiene un operador
                //TODO 
            } else {
                nombrenodo("EXP", padre);
                graficar(aux.op1, padre+1);
                nombrenodo("{TOKEN} "+aux.obtenerSignocadena(), padre+1);
                graficar(aux.op2, padre+1);
            }
        }else if(raiz instanceof Nulo){
            nombrenodo("EXP", padre);
            nombrenodo("{TOKEN} NULL",correlativo);
        }
    }
  

    private void TraducirLiteral(Nodo raiz, int padre) {
        nombrenodo("{TOKEN} " + ((Literal) raiz).getValor().toString(), padre);
    }

    private void nombrenodo(String nombre, int padre) {
        cadena.append("nodo" + (correlativo + 1) + "[label=\"" + nombre + " \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        cadena.append("nodo" + padre + "->nodo" + (correlativo + 1) + "\n");
        correlativo++;
    }
}

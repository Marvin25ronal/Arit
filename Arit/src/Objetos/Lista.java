/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Entorno.Simbolo;
import Expresion.TipoExp;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class Lista extends Simbolo {

    private LinkedList<Object> lista;

    public Lista(LinkedList<Object> lista, TipoExp tipo, TipoExp tiposecundario, String id) {
        super(tipo, tiposecundario, id);
        this.lista = lista;
    }

    /**
     * @return the lista
     */
    public LinkedList<Object> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(LinkedList<Object> lista) {
        this.lista = lista;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("{");
        for (int i = 0; i < lista.size(); i++) {
            Object aux = lista.get(i);
            s.append(aux.toString());
            //s.append(",");
              if (i < lista.size() - 1) {
                s.append(",");
            }
        }
        s.append("}");
        return s.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}

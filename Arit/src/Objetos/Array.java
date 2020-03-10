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
public class Array extends Simbolo {

    /**
     * @return the arreglo
     */
    public LinkedList<Object> getArreglo() {
        return arreglo;
    }

    /**
     * @param arreglo the arreglo to set
     */
    public void setArreglo(LinkedList<Object> arreglo) {
        this.arreglo = arreglo;
    }

    private LinkedList<Object> arreglo;
    private LinkedList<Integer> dimensiones;

    public Array(TipoExp tipo, TipoExp tiposecundario, String id, LinkedList<Object> arreglo, LinkedList<Integer> dimensiones) {
        super(tipo, tiposecundario, id);
        this.arreglo = arreglo;
        this.dimensiones = dimensiones;
    }

    /**
     * @return the arreglo
     */
    /**
     * @return the dimensiones
     */
    public LinkedList<Integer> getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(LinkedList<Integer> dimensiones) {
        this.dimensiones = dimensiones;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        GenerarCad(cadena);
        return cadena.toString();
    }

    private void GenerarCad(StringBuilder s) {
        int dim = 0;
        if (getDimensiones().size() > 1) {
            GenerarCad(s, dim, "", getArreglo());
        } else {
            //imprimimos los datos
            for (int i = 0; i < getArreglo().size(); i++) {
                s.append(getArreglo().get(i).toString());
                if (i != getArreglo().size() - 1) {
                    s.append(",");
                }
            }
        }
    }

    private void GenerarCad(StringBuilder s, int dim, String encabezado, LinkedList<Object> arr) {
        if (getDimensiones().size() - dim == 2) {
            //cuando faltan solo 2
            encabezado = "," + encabezado;
            s.append(encabezado).append("\n\n");
            for (int j = 0; j < getDimensiones().get(dim + 1); j++) {
                s.append("[");
                s.append(j);
                s.append("]     ");
                for (int i = 0; i < getDimensiones().get(dim); i++) {
                    LinkedList<Object> lista = (LinkedList<Object>) arr.get(i);
                    LinkedList<Object> ultima = (LinkedList<Object>) lista.get(j);
                    s.append(ultima.get(0).toString());
                    s.append("   ||   ");
                }
                s.append("\n");
            }
            s.append("\n\n");
           
        } else {
            int indice = getDimensiones().get(dim);
            for (int i = 0; i < indice; i++) {
                String aux=encabezado;
                encabezado = "," + (i + 1)+encabezado;
                GenerarCad(s, dim + 1, encabezado, (LinkedList<Object>) arr.get(i));
                encabezado=aux;
            }

        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Entorno.Simbolo;
import Expresion.Literal;
import Expresion.TipoExp;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class EstructuraLineal extends Simbolo {

    /**
     * @return the tam
     */
    private LinkedList<Object> dimensiones;

    public int getTam() {
        return dimensiones.size();
    }

    /**
     * @param tam the tam to set
     */
    /**
     * @return the id
     */
    public EstructuraLineal(String id, TipoExp tipo, TipoExp tipoprimitivo, LinkedList<Object> dimensiones) {
        super(tipo, tipoprimitivo, id);
        this.dimensiones = dimensiones;
    }
    public EstructuraLineal(TipoExp tiposecundario,Object valor,int linea,int columna){
        super(new TipoExp(TipoExp.Tipos.VECTOR),tiposecundario,"");
        this.dimensiones=new LinkedList<>();
        Literal l=new Literal(valor, tiposecundario, linea, columna);
        dimensiones.add(l);
        
    }

    /**
     * @param id the id to set
     */
    /**
     * @return the dimensiones
     */
    public LinkedList<Object> getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(LinkedList<Object> dimensiones) {
        this.dimensiones = dimensiones;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        if (getTipo().isVector()) {
            cadena.append("[");
            for (int i = 0; i < dimensiones.size(); i++) {
                Literal l = (Literal) dimensiones.get(i);
                cadena.append(l.getValor());
                if (i < dimensiones.size() - 1) {
                    cadena.append(",");
                }
            }
            cadena.append("]");
            // return cadena.toString();
        } else if (getTipo().isList()) {

            cadena.append("{");
            for (int i = 0; i < getDimensiones().size(); i++) {
                Object aux = getDimensiones().get(i);
                cadena.append(aux.toString());
                //s.append(",");
                if (i < dimensiones.size() - 1) {
                    cadena.append(",");
                }
            }
            cadena.append("}");
            //return cadena.toString(); //To change body of generated methods, choose Tools | Templates.
        }
        return cadena.toString();

    }
    public boolean ListaIgual(Entorno.Entorno e){
        TipoExp t=null;
        for(int i=0;i<dimensiones.size();i++){
            if(t==null){
                t=((Simbolo)dimensiones.get(i)).getTipo();
            }else if(t.tp!=((Simbolo)dimensiones.get(i)).getTipo().tp){
               return false;
            }
        }
        return true;
    }

    /**
     * @return the dimension
     */
}

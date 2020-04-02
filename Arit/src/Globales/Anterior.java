/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Globales;

/**
 *
 * @author marvi
 */
 public class Anterior {
    private Object anterior;
    private int indice;
    private int acceso;

    public Anterior(Object anterior, int indice) {
        this.anterior = anterior;
        this.indice = indice;
    }

    /**
     * @return the anterior
     */
    public Object getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Object anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * @return the acceso
     */
    public int getAcceso() {
        return acceso;
    }

    /**
     * @param acceso the acceso to set
     */
    public void setAcceso(int acceso) {
        this.acceso = acceso;
    }
    
}

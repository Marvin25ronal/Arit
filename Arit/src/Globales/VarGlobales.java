/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Globales;

import java.util.LinkedList;
import Reportes.Errores;
import javax.swing.JTextArea;

/**
 *
 * @author marvi
 */
public final class VarGlobales {

    /**
     * @return the instancia
     */
    public static VarGlobales getInstancia() {
        return instancia;
    }

    private LinkedList<Errores> listaE;
    StringBuilder s = new StringBuilder();
    private JTextArea consola;
    private static VarGlobales instancia;

    private VarGlobales() {
        listaE = new LinkedList<>();
    }

    public void setConsola(JTextArea consola) {
        this.consola = consola;
    }

    public JTextArea getConsola() {
        return this.consola;
    }

    public static VarGlobales getInstance() {
        if (getInstancia() == null) {
            instancia = new VarGlobales();
        }
        return getInstancia();
    }

    public void LimpiarLista() {
        this.listaE.clear();
    }

    public void AgregarErrores(LinkedList<Errores> listanueva) {
        for (int i = 0; i < listanueva.size(); i++) {
            this.listaE.add(listanueva.get(i));
        }
    }

    public void AgregarEU(Errores e) {
        this.consola.append(e.toString()+"\n");
        this.listaE.add(e);
    }
    

    public String cadE() {
        s.setLength(0);
        for (int i = 0; i < 0; i++) {
            s.append(this.listaE.get(i).toString() + "\n");
        }
        return s.toString();
    }
}

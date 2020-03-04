/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Globales;

import Entorno.Entorno;
import Expresion.Literal;
import Expresion.TipoExp;
import Objetos.Nulo;
import Objetos.Vector;
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
        this.consola.append(e.toString() + "\n");
        this.listaE.add(e);
    }

    public String cadE() {
        s.setLength(0);
        for (int i = 0; i < 0; i++) {
            s.append(this.listaE.get(i).toString() + "\n");
        }
        return s.toString();
    }

    public LinkedList<Object> clonarListaVector(LinkedList<Object> referencia, Entorno e) {
        LinkedList<Object> l = new LinkedList<>();
        for (int i = 0; i < referencia.size(); i++) {
            Literal aux = (Literal) referencia.get(i);
            Literal nueva = new Literal(aux.getValor(e), aux.getTipo(e), aux.linea(), aux.columna());
            l.add(nueva);
        }
        return l;
    }

    public TipoExp obtenerTipo(Object t, Entorno e) {
        if (t instanceof Literal) {
            return ((Literal) t).getTipo(e);
        } else if (t instanceof Vector) {
            return ((Vector) t).getTipo();
        } else if (t instanceof Nulo) {
            return ((Nulo) t).getTipo(e);
        } else if (t.getClass().getTypeName().equals(Double.class.getTypeName())) {
            return new TipoExp(TipoExp.Tipos.NUMERIC);
        } else if (t.getClass().getTypeName().equals(String.class.getTypeName())) {
            return new TipoExp(TipoExp.Tipos.STRING);
        } else if (t.getClass().getTypeName().equals(Integer.class.getTypeName())) {
            return new TipoExp(TipoExp.Tipos.INTEGER);
        } else if (t.getClass().getTypeName().equals(Boolean.class.getTypeName())) {
            return new TipoExp(TipoExp.Tipos.BOOLEAN);
        }
        return null;
    }
}

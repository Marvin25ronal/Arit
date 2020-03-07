/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Globales;

import Entorno.Entorno;
import Expresion.Literal;
import Expresion.TipoExp;
import Objetos.Lista;
import Objetos.Matrix;
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
     * @return the anterior
     */
    public Anterior getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Anterior anterior) {
        this.anterior = anterior;
    }

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
    private Anterior anterior;

    private VarGlobales() {
        listaE = new LinkedList<>();
        anterior = new Anterior(null, 0);
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
            Literal nueva = new Literal(aux.getValor(e), new TipoExp(aux.getTipo(e).tp), aux.linea(), aux.columna());
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
        } else if (t instanceof Lista) {
            return new TipoExp(TipoExp.Tipos.LISTA);
        } else if (t instanceof Matrix) {
            return new TipoExp(TipoExp.Tipos.MATRIX);
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

    public LinkedList<Object> CopiarLista(Entorno e, LinkedList<Object> referencia) {
        LinkedList<Object> nueva = new LinkedList<>();
        Object temp = null;
        for (int i = 0; i < referencia.size(); i++) {
            temp = referencia.get(i);
            if (temp instanceof Vector) {
                Vector aux = (Vector) temp;
                LinkedList<Object> element = clonarListaVector(aux.getDimensiones(), e);
                Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(aux.getTiposecundario().tp), element);
                nueva.add(nuevo);
            } else if (temp instanceof Lista) {
                Lista aux = (Lista) temp;
                LinkedList<Object> element = CopiarLista(e, aux.getLista());
                Lista nuevo = new Lista(element, new TipoExp(TipoExp.Tipos.LISTA), null, "");
                nueva.add(nuevo);
            } else if (temp instanceof Literal) {
                Literal pasando = (Literal) temp;
                Literal nueval = new Literal(pasando.getValor(e), new TipoExp(pasando.getTipo(e).tp), pasando.linea(), pasando.columna());
                LinkedList<Object> element = new LinkedList<>();
                element.add(nueval);
                Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(pasando.getTipo(e).tp), element);
                nueva.add(nuevo);
            }
        }
        return nueva;
    }

    public LinkedList<LinkedList<Object>> CopiarMatrix(Entorno e, LinkedList<LinkedList<Object>> referencia) {
        LinkedList<LinkedList<Object>> nuvamatrix = new LinkedList<>();
        for (int i = 0; i < referencia.size(); i++) {
            LinkedList<Object> aux = referencia.get(i);
            
            LinkedList<Object> Nuevascol = new LinkedList<>();
            for (int j = 0; j < aux.size(); j++) {
                Vector v = (Vector) aux.get(j);
                LinkedList<Object> nv = clonarListaVector(v.getDimensiones(), e);
                Vector nuevo = new Vector("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(v.getTiposecundario().tp), nv);
                Nuevascol.add(nuevo);
            }
            nuvamatrix.add(Nuevascol);
        }
        return nuvamatrix;
    }
}

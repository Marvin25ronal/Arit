/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Globales;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Literal;
import Expresion.TipoExp;
import Objetos.Array;

import Objetos.Matrix;
import Objetos.Nulo;
import Objetos.EstructuraLineal;
import java.util.LinkedList;
import Reportes.Errores;
import java.util.HashMap;
import javax.swing.JTextArea;

/**
 *
 * @author marvi
 */
public final class VarGlobales {

    /**
     * @return the listaEntornos
     */


    /**
     * @return the listaE
     */
    
    public LinkedList<Errores> getListaE() {
        return listaE;
    }

    /**
     * @param listaE the listaE to set
     */
    public void setListaE(LinkedList<Errores> listaE) {
        this.listaE = listaE;
    }

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
        this.getListaE().clear();
        
    }

    public void AgregarErrores(LinkedList<Errores> listanueva) {
        for (int i = 0; i < listanueva.size(); i++) {
            this.getListaE().add(listanueva.get(i));
        }
    }

    public void AgregarEU(Errores e) {
        this.consola.append(e.toString() + "\n");
        this.getListaE().add(e);
    }

    public String cadE() {
        s.setLength(0);
        for (int i = 0; i < 0; i++) {
            s.append(this.getListaE().get(i).toString() + "\n");
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
        } else if (t instanceof EstructuraLineal) {
            Simbolo s = (Simbolo) t;
            if (s.getTipo().isVector()) {
                return ((EstructuraLineal) t).getTipo();
            } else if (s.getTipo().isList()) {
                return new TipoExp(TipoExp.Tipos.LISTA);
            }
        } else if (t instanceof Nulo) {
            return ((Nulo) t).getTipo(e);
        } else if (t instanceof Array) {
            return new TipoExp(TipoExp.Tipos.ARRAY);
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
            if (temp instanceof Literal) {
                Literal pasando = (Literal) temp;
                Literal nueval = new Literal(pasando.getValor(e), new TipoExp(pasando.getTipo(e).tp), pasando.linea(), pasando.columna());
                LinkedList<Object> element = new LinkedList<>();
                element.add(nueval);
                EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(pasando.getTipo(e).tp), element);
                nueva.add(nuevo);
            } else if (((Simbolo) temp).getTipo().isVector()) {
                EstructuraLineal aux = (EstructuraLineal) temp;
                LinkedList<Object> element = clonarListaVector(aux.getDimensiones(), e);
                EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(aux.getTiposecundario().tp), element);
                nueva.add(nuevo);
            } else if (((Simbolo) temp).getTipo().isList()) {
                EstructuraLineal aux = (EstructuraLineal) temp;
                LinkedList<Object> element = CopiarLista(e, aux.getDimensiones());
                //EstructuraLineal nuevo = new Est(element, new TipoExp(TipoExp.Tipos.LISTA), null, "");
                EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.LISTA), null, element);
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
                EstructuraLineal v = (EstructuraLineal) aux.get(j);
                LinkedList<Object> nv = clonarListaVector(v.getDimensiones(), e);
                EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(v.getTiposecundario().tp), nv);
                Nuevascol.add(nuevo);
            }
            nuvamatrix.add(Nuevascol);
        }
        return nuvamatrix;
    }

    public LinkedList<Object> CopiarArray(Entorno e, LinkedList<Object> referencia) {
        LinkedList<Object> nueva = new LinkedList<>();
        CopiarArray2(e, referencia, nueva);
        return nueva;
    }

    private void CopiarArray2(Entorno e, LinkedList<Object> referencia, LinkedList<Object> padre) {
        if (referencia.get(0) instanceof EstructuraLineal) {
            //ya son estructuras
            Object temp = null;
            for (int i = 0; i < referencia.size(); i++) {
                temp = referencia.get(i);
                if (temp instanceof Literal) {
                    Literal pasando = (Literal) temp;
                    Literal nueval = new Literal(pasando.getValor(e), new TipoExp(pasando.getTipo(e).tp), pasando.linea(), pasando.columna());
                    LinkedList<Object> element = new LinkedList<>();
                    element.add(nueval);
                    EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(pasando.getTipo(e).tp), element);
                    padre.add(nuevo);
                } else if (((Simbolo) temp).getTipo().isVector()) {
                    EstructuraLineal aux = (EstructuraLineal) temp;
                    LinkedList<Object> element = clonarListaVector(aux.getDimensiones(), e);
                    EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.VECTOR), new TipoExp(aux.getTiposecundario().tp), element);
                    padre.add(nuevo);
                } else if (((Simbolo) temp).getTipo().isList()) {
                    EstructuraLineal aux = (EstructuraLineal) temp;
                    LinkedList<Object> element = CopiarLista(e, aux.getDimensiones());
                    //EstructuraLineal nuevo = new Est(element, new TipoExp(TipoExp.Tipos.LISTA), null, "");
                    EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(TipoExp.Tipos.LISTA), null, element);
                    padre.add(nuevo);
                }
            }
        } else {
            for (int i = 0; i < referencia.size(); i++) {
                LinkedList<Object> nueva = new LinkedList<>();
                CopiarArray2(e, (LinkedList<Object>) referencia.get(i), nueva);
                padre.add(nueva);
            }
        }
    }
}

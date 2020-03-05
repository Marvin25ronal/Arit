/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.TipoExp.Tipos;
import Instruccion.DecAsig;
import Instruccion.Print;
import Objetos.Funcion;
import Objetos.Lista;
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author marvi
 */
public class Llamadas implements Expresion {

    private Identificador id;
    private LinkedList<Expresion> parametros;
    private LinkedList<Expresion> dimensiones;

    public Llamadas(Identificador id, LinkedList<Expresion> parametros, LinkedList<Expresion> dimensiones) {
        this.id = id;
        this.parametros = parametros;
        this.dimensiones = dimensiones;
    }

    @Override
    public Object getValor(Entorno e) {
        //Entorno nuevoE = new Entorno(e.getGlobal());
        if (Nativa(id.getVal())) {
            return HacerNativa(e);
        } else {
            if (e.ExisteVariable("Funcion_" + id.getVal())) {
                Funcion f = (Funcion) e.get("Funcion_" + id.getVal());
                if (f.getParametros().size() == parametros.size()) {
                    Object pasar = PasarVariables(e, f);
                    if (pasar instanceof Errores) {
                        return pasar;
                    }
                    Entorno nuevoE = (Entorno) pasar;
                    if (!dimensiones.isEmpty()) {
                        Object resul = f.ejecutar(nuevoE);
                        if (resul instanceof Errores) {
                            return resul;
                        } else {
                            Entorno eaux = new Entorno(nuevoE);
                            eaux.add("aux", (Simbolo) resul);
                            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                            return nuevoA.getValor(eaux);
                        }
                    } else {
                        return f.ejecutar(nuevoE);
                    }
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "La cantidad de parametros es incorrecta ", linea(), columna());
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "Funcion no declarada ", id.getLinea(), columna());
            }
        }
    }

    private Object HacerNativa(Entorno e) {
        switch (id.getVal().toLowerCase()) {
            case "print":
                return HacerPrint(e);
            case "c":
            case "list":
                return HacerLista(e);

        }
        return null;
    }

    private Object HacerLista(Entorno e) {
        LinkedList<Object> elementos = new LinkedList<>();
        Object aux = null;
        for (int i = 0; i < parametros.size(); i++) {
            aux = parametros.get(i).getValor(e);
            if (aux instanceof Errores) {
                return aux;
            } else if (aux instanceof Literal) {
                Literal l = (Literal) aux;
                LinkedList<Object> nueval = new LinkedList<>();
                nueval.add(l);
                Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), l.getTipo(e), nueval);
                elementos.add(nuevo);
            } else if (aux instanceof Vector) {
                Vector v = (Vector) aux;
                elementos.add(v);
            } else if (aux instanceof Lista) {
                Lista l = (Lista) aux;
                /*if (l.getLista().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "Las listas solo pueden contener un elemento en sus nodos", linea(), columna());
                }*/
                elementos.add(l);
            } else if (Globales.VarGlobales.getInstance().obtenerTipo(aux, e).isPrimitive(e)) {
                Literal l = new Literal(aux, new TipoExp(Globales.VarGlobales.getInstance().obtenerTipo(aux, e).tp), linea(), columna());
                LinkedList<Object>valores=new LinkedList<>();
                valores.add(l);
                Vector nuevo=new Vector("", new TipoExp(Tipos.VECTOR),l.getTipo(e),valores);
                elementos.add(nuevo);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "Las listas no soportan este objeto", linea(), columna());
            }

        }
        Lista nuevaLista = new Lista(elementos, new TipoExp(Tipos.LISTA), null, "");
        return nuevaLista;
    }

    private Object HacerPrint(Entorno e) {
        if (parametros.size() == 1) {
            if (dimensiones.isEmpty()) {
                return new Print(parametros.get(0), id.getLinea(), id.getColumna()).ejecutar(e);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La funcion print no devuelve nada para acceder por dimensiones", linea(), columna());
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La funcion print no cuenta con esa cantidad de parametros", id.getLinea(), id.getColumna());
        }

    }

    private Object PasarVariables(Entorno e, Funcion f) {
        Entorno enuevo = new Entorno(e.getGlobal());
        Object valor;
        for (int i = 0; i < parametros.size(); i++) {
            valor = parametros.get(i).getValor(e);
            TipoExp tipo = Globales.VarGlobales.getInstance().obtenerTipo(valor, e);
            if (valor instanceof Errores) {
                return valor;
            }
            if (f.getParametros().get(i) instanceof DecAsig) {
                DecAsig ndec = (DecAsig) f.getParametros().get(i);
                Object res = ndec.ejecutar(enuevo);
                if (res instanceof Errores) {
                    return res;
                }
                if (!(valor instanceof Default)) {
                    Identificador id = ndec.getId();
                    Crear(valor, enuevo, id, e, i, true, tipo);
                }
            } else if (f.getParametros().get(i) instanceof Identificador) {
                Identificador id = (Identificador) f.getParametros().get(i);
                Crear(valor, enuevo, id, e, i, false, tipo);
            }
        }
        return enuevo;
    }

    private Object Crear(Object valor, Entorno enuevo, Identificador id, Entorno e, int i, boolean actualizar, TipoExp tipo) {
        if (valor instanceof Literal) {
            Literal l = (Literal) valor;
            LinkedList<Object> datos = new LinkedList<>();
            datos.add(l);
            Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), l.getTipo(e), datos);
            if (actualizar) {
                enuevo.Actualizar(id.getVal(), nuevo);
            } else {
                enuevo.add(id.getVal(), nuevo);
            }
        } else if (valor instanceof Vector) {
            Vector copia = (Vector) valor;
            LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(copia.getDimensiones(), e);
            Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), copia.getTiposecundario(), lista);
            if (actualizar) {
                enuevo.Actualizar(id.getVal(), nuevo);
            } else {
                enuevo.add(id.getVal(), nuevo);
            }
        } else if (valor instanceof Lista) {

        } else if (tipo.isPrimitive(e)) {
            Literal l = new Literal(valor, tipo, linea(), columna());
            LinkedList<Object> lista = new LinkedList<>();
            lista.add(l);
            Vector nuevo = new Vector(id.getVal(), new TipoExp(Tipos.VECTOR), l.getTipo(e), lista);
            if (actualizar) {
                enuevo.Actualizar(id.getVal(), nuevo);
            } else {
                enuevo.add(id.getVal(), nuevo);
            }
        }
        return null;
    }

    @Override
    public TipoExp getTipo(Entorno e) {
        Entorno aux = e.Copiar();
        JTextArea consola = Globales.VarGlobales.getInstance().getConsola();
        Globales.VarGlobales.getInstance().setConsola(new JTextArea());
        Object valor = getValor(aux);
        if (valor instanceof Vector) {
            Globales.VarGlobales.getInstance().setConsola(consola);
            return new TipoExp(Tipos.VECTOR);
        } else if (valor instanceof Literal) {
            Globales.VarGlobales.getInstance().setConsola(consola);
            return ((Literal) valor).getTipo(aux);
        }
        Globales.VarGlobales.getInstance().setConsola(consola);
        return null;
    }

    @Override
    public int linea() {
        return id.linea();
    }

    @Override
    public int columna() {
        return id.columna();
    }

    /**
     * @return the id
     */
    public Identificador getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Identificador id) {
        this.id = id;
    }

    /**
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<Expresion> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the dimensiones
     */
    public LinkedList<Expresion> getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(LinkedList<Expresion> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public boolean Nativa(String id) {
        switch (id.toLowerCase()) {
            case "print":
                return true;
            case "typeof":
                return true;
            case "c":
                return true;
            case "list":
                return true;
            default:
                return false;
        }
    }

}

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

import Objetos.Nulo;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Reportes.Errores;
import java.util.LinkedList;
import javax.swing.JTextArea;
import sun.misc.Queue;

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
                return HacerFuncionC(e);
            case "list":
                return HacerLista(e);
            case "matrix":
                return new CrearMatriz(parametros, dimensiones, linea(), columna()).Ejecutar(e);

        }
        return null;
    }

    private Object HacerFuncionC(Entorno e) {
        TipoExp tipodominante = null;
        TipoExp tipoObjeto = null;
        LinkedList<Object> cola = new LinkedList<>();
        for (int i = 0; i < parametros.size(); i++) {
            Object aux = parametros.get(i).getValor(e);
            if (aux instanceof Errores) {
                return aux;
            }
            tipodominante = TipoDominante(tipodominante, Globales.VarGlobales.getInstance().obtenerTipo(aux, e));
            if (tipodominante.isVector()) {
                if (tipoObjeto == null) {
                    tipoObjeto = new TipoExp(Tipos.VECTOR);
                } else {
                    if (!tipoObjeto.isList()) {
                        tipoObjeto = new TipoExp(Tipos.VECTOR);
                    }
                }
                tipodominante = TipoDominante(tipodominante, ((EstructuraLineal) aux).getTiposecundario());
            } else if (tipodominante.isList()) {

                tipoObjeto = new TipoExp(Tipos.LISTA);
                tipodominante = TipoDominante(tipodominante, Globales.VarGlobales.getInstance().obtenerTipo(aux, e));
            }
            if (tipodominante == null) {
                return new Errores(Errores.TipoError.SEMANTICO, "La funcion c no puede contener ese tipo de objetos en el parametro " + (i + 1), linea(), columna());
            }
            cola.add(aux);
        }

        //Parte para hacer el objeto
        if (tipoObjeto == null) {
            //es un vector
            LinkedList<Object> valores = new LinkedList<>();
            while (!cola.isEmpty()) {
                Object objeto = cola.removeFirst();
                if (objeto instanceof Literal) {
                    Literal aux = (Literal) objeto;
                    Literal nueva = new Literal(CastearValor(tipodominante, aux.getValor(e), aux.getTipo(e)), tipodominante, linea(), columna());
                    valores.add(nueva);
                } else {
                    Literal nueva = new Literal(CastearValor(tipodominante, objeto, Globales.VarGlobales.getInstance().obtenerTipo(objeto, e)), tipodominante, linea(), columna());
                    valores.add(nueva);
                }
            }
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) new EstructuraLineal("", new TipoExp(Tipos.VECTOR), tipodominante, valores));
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return new EstructuraLineal("", new TipoExp(Tipos.VECTOR), tipodominante, valores);
        } else if (tipoObjeto.isVector()) {
            //es un vector con mas vectores
            LinkedList<Object> valores = new LinkedList<>();
            while (!cola.isEmpty()) {
                Object objeto = cola.removeFirst();
                if (objeto instanceof Literal) {
                    Literal aux = (Literal) objeto;
                    Literal nueva = new Literal(CastearValor(tipodominante, aux.getValor(e), aux.getTipo(e)), tipodominante, linea(), columna());
                    valores.add(nueva);
                } else if (objeto instanceof EstructuraLineal) {
                    EstructuraLineal aux = (EstructuraLineal) objeto;
                    LinkedList<Object> datospasando = Globales.VarGlobales.getInstance().clonarListaVector(aux.getDimensiones(), e);
                    Literal laux = null;
                    for (int i = 0; i < datospasando.size(); i++) {
                        laux = (Literal) datospasando.get(i);
                        laux.setValor(CastearValor(tipodominante, laux.getValor(e), laux.getTipo(e)));
                        laux.setTipo(tipodominante);
                        valores.add(datospasando.get(i));
                    }
                } else {
                    Literal nueva = new Literal(CastearValor(tipodominante, objeto, Globales.VarGlobales.getInstance().obtenerTipo(objeto, e)), tipodominante, linea(), columna());
                    valores.add(nueva);
                }
            }
            EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), tipodominante, valores);
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nuevo);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nuevo;
        } else {
            //es lista
            LinkedList<Object> valores = new LinkedList<>();
            while (!cola.isEmpty()) {
                Object objeto = cola.removeFirst();
                TipoExp tipodo = Globales.VarGlobales.getInstance().obtenerTipo(objeto, e);
                if (objeto instanceof Literal) {
                    Literal aux = (Literal) objeto;
                    Literal nueva = new Literal(CastearValor(tipodominante, aux.getValor(e), aux.getTipo(e)), tipodominante, linea(), columna());
                    valores.add(nueva);
                } else if (tipodo.isVector()) {
                    EstructuraLineal aux = (EstructuraLineal) objeto;
                    LinkedList<Object> datospasando = Globales.VarGlobales.getInstance().clonarListaVector(aux.getDimensiones(), e);
                    Literal laux = null;
                    for (int i = 0; i < datospasando.size(); i++) {
                        laux = (Literal) datospasando.get(i);
                        laux.setValor(CastearValor(tipodominante, laux.getValor(e), laux.getTipo(e)));
                        laux.setTipo(tipodominante);
                        valores.add(datospasando.get(i));
                    }
                } else if (tipodo.isList()) {
                    EstructuraLineal aux = (EstructuraLineal) objeto;
                    LinkedList<Object> datospasando = Globales.VarGlobales.getInstance().CopiarLista(e, aux.getDimensiones());
                    for (int i = 0; i < datospasando.size(); i++) {
                        valores.add(datospasando.get(i));
                    }
                } else {
                    Literal nueva = new Literal(CastearValor(tipodominante, objeto, Globales.VarGlobales.getInstance().obtenerTipo(objeto, e)), tipodominante, linea(), columna());
                    valores.add(nueva);
                }
            }
            EstructuraLineal nueva = new EstructuraLineal("", new TipoExp(Tipos.LISTA), null, valores);
            //Lista nueva = new Lista(valores, new TipoExp(Tipos.LISTA), null, "");
            if (!dimensiones.isEmpty()) {
                Entorno eaux = new Entorno(e);
                eaux.add("aux", (Simbolo) nueva);
                Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
                return nuevoA.getValor(eaux);
            }
            return nueva;

        }
    }

    private Object CastearValor(TipoExp tdestino, Object valor, TipoExp torigen) {
        if (null != tdestino.tp) {
            switch (tdestino.tp) {
                case STRING:
                    if (valor instanceof Nulo) {
                        return valor;
                    } else {
                        return valor.toString();
                    }
                case NUMERIC:
                    if (valor instanceof Nulo) {
                        return 0.0;
                    } else if (torigen.tp == Tipos.BOOLEAN) {
                        boolean b = Boolean.parseBoolean(valor.toString());
                        return b ? 1.0 : 0.00;
                    } else {
                        return Double.parseDouble(valor.toString());
                    }
                case INTEGER:
                    if (torigen.isBoolean()) {
                        return Boolean.parseBoolean(valor.toString()) ? 1 : 0;
                    }
                    return valor instanceof Nulo ? 0 : Integer.parseInt(valor.toString());
                case BOOLEAN:
                    if (valor instanceof Nulo) {
                        return false;
                    } else {
                        return Boolean.parseBoolean(valor.toString());
                    }
                case LISTA:
                    return valor;
                default:
                    break;
            }
        }
        return null;
    }

    private TipoExp TipoDominante(TipoExp t, TipoExp nuevot) {
        if (t == null) {
            return nuevot;
        }
        if (t.isList() || nuevot.isList()) {
            return new TipoExp(Tipos.LISTA);
        } else if (t.isString() || nuevot.isString()) {
            return new TipoExp(Tipos.STRING);
        } else if (t.isNumeric() || nuevot.isNumeric()) {
            return new TipoExp(Tipos.NUMERIC);
        } else if (t.isInt() || nuevot.isInt()) {
            return new TipoExp(Tipos.INTEGER);
        } else if (t.isBoolean() || nuevot.isBoolean()) {
            return new TipoExp(Tipos.BOOLEAN);
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
                EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), l.getTipo(e), nueval);
                elementos.add(nuevo);
            } else if (aux instanceof EstructuraLineal) {
                EstructuraLineal v = (EstructuraLineal) aux;
                elementos.add(v);
            } else if (Globales.VarGlobales.getInstance().obtenerTipo(aux, e).isPrimitive(e)) {
                Literal l = new Literal(aux, new TipoExp(Globales.VarGlobales.getInstance().obtenerTipo(aux, e).tp), linea(), columna());
                LinkedList<Object> valores = new LinkedList<>();
                valores.add(l);
                EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), l.getTipo(e), valores);
                elementos.add(nuevo);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "Las listas no soportan este objeto", linea(), columna());
            }

        }
        EstructuraLineal nuevaLista = new EstructuraLineal("", new TipoExp(Tipos.LISTA), null, elementos);
        //Lista nuevaLista = new Lista(elementos, new TipoExp(Tipos.LISTA), null, "");
        if (!dimensiones.isEmpty()) {
            Entorno eaux = new Entorno(e);
            eaux.add("aux", (Simbolo) nuevaLista);
            Acceso nuevoA = new Acceso(new Identificador("aux", 0, 0), dimensiones, 0, 0);
            return nuevoA.getValor(eaux);
        }
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
                Object res = ndec.EjecutarFuncion(e,enuevo);
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
            EstructuraLineal nuevo = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), l.getTipo(e), datos);
            if (actualizar) {
                enuevo.Actualizar(id.getVal(), nuevo);
            } else {
                enuevo.add(id.getVal(), nuevo);
            }
        } else if (tipo.isVector()) {
            EstructuraLineal copia = (EstructuraLineal) valor;
            LinkedList<Object> lista = Globales.VarGlobales.getInstance().clonarListaVector(copia.getDimensiones(), e);
            EstructuraLineal nuevo = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), copia.getTiposecundario(), lista);
            if (actualizar) {
                enuevo.Actualizar(id.getVal(), nuevo);
            } else {
                enuevo.add(id.getVal(), nuevo);
            }
        } else if (tipo.isList()) {
            EstructuraLineal copia = (EstructuraLineal) valor;
            LinkedList<Object> lista = Globales.VarGlobales.getInstance().CopiarLista(e, copia.getDimensiones());
            //Lista nueva = new Lista(lista, new TipoExp(Tipos.LISTA), null, id.getVal());
            EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.LISTA), null, lista);
            if (actualizar) {
                //ambito venga default
                enuevo.Actualizar(id.getVal(), nueva);
            } else {
                enuevo.add(id.getVal(), nueva);
            }
        } else if (tipo.isMatrix()) {
            Matrix copia = (Matrix) valor;
            LinkedList<LinkedList<Object>> matriz = Globales.VarGlobales.getInstance().CopiarMatrix(e, copia.getColumnas());
            Matrix nueva = new Matrix(matriz, new TipoExp(Tipos.MATRIX), new TipoExp(copia.getTiposecundario().tp), id.getVal(), copia.getColumna(), copia.getFila());
            if(actualizar){
                enuevo.Actualizar(id.getVal(), nueva);
            }else{
                enuevo.add(id.getVal(), nueva);
            }
        } else if (tipo.isPrimitive(e)) {
            Literal l = new Literal(valor, tipo, linea(), columna());
            LinkedList<Object> lista = new LinkedList<>();
            lista.add(l);
            EstructuraLineal nuevo = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), l.getTipo(e), lista);
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
            case "matrix":
                return true;
            default:
                return false;
        }
    }

}

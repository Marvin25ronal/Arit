/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Acceso;
import Expresion.Expresion;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Globales.Anterior;
import Objetos.Lista;
import Objetos.Matrix;
import Objetos.Nulo;
import Objetos.Vector;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class AsignacionPosicion implements Instruccion {

    private Expresion acc;
    private Expresion valor;
    private int linea;
    private int columna;

    public AsignacionPosicion(Expresion acc, Expresion valor, int linea, int columna) {
        this.acc = acc;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Acceso ac = (Acceso) acc;

        if (ac.getIndices().size() == 1) {
            ac.setIncremento(true);
        }
        Object val = valor.getValor(e);
        TipoExp tvalor = Globales.VarGlobales.getInstance().obtenerTipo(val, e);
        if (val instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) val);
            return null;
        }
        Object obj = ac.getValor(e);
        if (obj instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) obj);
            return null;
        }
        //me lo regresa en un vector de 1

        Simbolo s = (Simbolo) Globales.VarGlobales.getInstance().getAnterior().getAnterior();
        if (s instanceof Vector) {
            if (tvalor.isPrimitive(e)) {
                return ReasignarVector_Primitivo(e, val, tvalor, (Vector) obj);
            } else if (tvalor.isVector()) {
                return ReasignarVector_Vector(e, val, tvalor, (Vector) obj);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector no puede contener ese tipo de objetos ", linea, columna);
            }
        } else if (s instanceof Lista) {
            if (tvalor.isPrimitive(e)) {
                return ReasignarLista_Primitivo(e, val, tvalor);
            } else if (tvalor.isList()) {
                return ReasignarLista_Lista(e, val, tvalor);
            } else if (tvalor.isVector()) {
                return ReasignarLista_Vector(e, val, tvalor);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La lista no puede contener ese tipo de objetos ", linea, columna);
            }
        } else if (s instanceof Matrix) {
            ///se que si el anterior es matriz el resultado simpre va a ser un vector
            ///se puede modificar unicacmente ese vector
            if (tvalor.isPrimitive(e)) {
                return ReasignarMatriz_Primitivo(e,val,tvalor);
            }else if(tvalor.isVector()){
                
            }else{
                return new Errores(Errores.TipoError.SEMANTICO,"La matriz no puede contener ese tipo de objetos ", linea, columna);
            }
        }

        /*
        //da problema mejor se clona y hacemos esto distinto
        Existe la variable
        El tipo puede aceptar eso
        Agregarlo
         */
        return null;
    }

    private Object ReasignarLista_Vector(Entorno e, Object val, TipoExp t) {
        Anterior ant = (Anterior) Globales.VarGlobales.getInstance().getAnterior();
        Lista metiendo = (Lista) ant.getAnterior();
        Vector apasar = (Vector) val;
        if (apasar.getDimensiones().size() > 1) {
            return new Errores(Errores.TipoError.SEMANTICO, "La lista solo puede contener un vector en su nodo de tamanio 1 ", linea, columna);
        }
        LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().clonarListaVector(apasar.getDimensiones(), e);
        Vector nuevo = new Vector("", new TipoExp(Tipos.VECTOR), apasar.getTiposecundario(), valoresnuevos);
        metiendo.getLista().set(ant.getIndice(), nuevo);
        return null;
    }
    
    private Object ReasignarLista_Lista(Entorno e, Object val, TipoExp t) {
        Anterior ant = (Anterior) Globales.VarGlobales.getInstance().getAnterior();
        if (ant.getAcceso() == 1) {
            if (ant.getAnterior() instanceof Lista) {
                Lista metiendo = (Lista) ant.getAnterior();
                Lista apasar = (Lista) val;
                if (apasar.getLista().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La lista solo puede contener un elemento en su nodo ", linea, columna);
                }
                LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().CopiarLista(e, apasar.getLista());
                Lista nueva = new Lista(valoresnuevos, new TipoExp(Tipos.LISTA), null, "");
                metiendo.getLista().set(ant.getIndice(), nueva);
                return null;
            } else {
                //es vector tira error porque no se puede meter lista en vectores
                return new Errores(Errores.TipoError.SEMANTICO, "No se puede meter en un vector una lista", linea, columna);
            }
        } else {
            //acceso 2 no tiene restricciones
            if (ant.getAnterior() instanceof Lista) {
                Lista metiendo = (Lista) ant.getAnterior();
                Lista apasar = (Lista) val;
                LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().CopiarLista(e, apasar.getLista());
                Lista nueva = new Lista(valoresnuevos, new TipoExp(Tipos.LISTA), null, "");
                metiendo.getLista().set(ant.getIndice(), nueva);
                return null;
            } else {
                //es vector
                return new Errores(Errores.TipoError.SEMANTICO, "No se puede meter en un vector una lista", linea, columna);
            }
        }

    }
    private Object ReasignarMatriz_Primitivo(Entorno e,Object setvalor,TipoExp t){
        Anterior ant=(Anterior)Globales.VarGlobales.getInstance().getAnterior();
        Vector v=(Vector)ant.getAnterior();
        return  null;
    }
    private Object ReasignarLista_Primitivo(Entorno e, Object setvalor, TipoExp t) {
        Anterior ant = (Anterior) Globales.VarGlobales.getInstance().getAnterior();
        if (ant.getAnterior() instanceof Lista) {
            Lista l = (Lista) ant.getAnterior();
            LinkedList<Object> objetos = new LinkedList<>();
            objetos.add(new Literal(setvalor, t, linea, columna));
            l.getLista().set(ant.getIndice(), new Vector("", new TipoExp(Tipos.VECTOR), t, objetos));
            return null;
        } else if (ant.getAnterior() instanceof Vector) {
            Vector v = (Vector) ant.getAnterior();
            Literal nueva = new Literal(setvalor, t, linea, columna);
            v.getDimensiones().set(ant.getIndice(), nueva);
            TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, t);
            CastearVector(nuevot, e, v);
            return null;
        }
        return null;
    }

    private Object ReasignarVector_Primitivo(Entorno e, Object setvalor, TipoExp t, Vector v) {
        TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, t);
        ((Literal) v.getDimensiones().get(0)).valor = CastearValor(nuevot, setvalor, t);
        ((Literal) v.getDimensiones().get(0)).tipo = nuevot;
        Simbolo s = e.get(((Acceso) acc).getId().getVal());
        if ((s instanceof Vector)) {
            CastearVector(nuevot, e);
        } else {

            CastearVector(nuevot, e, (Vector) Globales.VarGlobales.getInstance().getAnterior().getAnterior());
        }
        return null;
    }

    private Object ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t, Vector v) {
        if (setvalor instanceof Vector) {
            Vector set = (Vector) setvalor;
            if (set.getDimensiones().size() == 1) {
                LinkedList<Object> listaAux = Globales.VarGlobales.getInstance().clonarListaVector(set.getDimensiones(), e);
                Literal aux = (Literal) listaAux.get(0);//--Retorna un arreglo con un elemento que es una literal
                TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, aux.getTipo(e));
                ((Literal) v.getDimensiones().get(0)).valor = CastearValor(nuevot, aux.valor, aux.getTipo(e));
                ((Literal) v.getDimensiones().get(0)).tipo = nuevot;
                Simbolo s = e.get(((Acceso) acc).getId().getVal());
                if ((s instanceof Vector)) {
                    CastearVector(nuevot, e);
                } else {

                    CastearVector(nuevot, e, (Vector) Globales.VarGlobales.getInstance().getAnterior().getAnterior());
                }

            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector solo puede contener vectores de tamanio 1", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "No se pueden meter otras estructuras en un vector", linea, columna);
        }
        return null;
    }

    private void CastearVector(TipoExp t, Entorno e) {

        Vector v = (Vector) e.get(((Acceso) acc).getId().getVal());
        Literal aux;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            aux = (Literal) v.getDimensiones().get(i);
            aux.valor = CastearValor(t, aux.getValor(e), aux.getTipo(e));
            aux.tipo = t;
        }
    }

    private void CastearVector(TipoExp t, Entorno e, Vector r) {
        Vector v = r;
        Literal aux;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            aux = (Literal) v.getDimensiones().get(i);
            aux.valor = CastearValor(t, aux.getValor(e), aux.getTipo(e));
            aux.tipo = t;
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
                default:
                    break;
            }
        }
        return null;
    }

    private TipoExp Dominante_Vector(LinkedList<Object> l, Entorno e, TipoExp tul) {
        Literal li = (Literal) l.get(0);
        if (tul.tp == Tipos.NULO || li.getTipo(e).tp == Tipos.NULO) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.STRING || li.getTipo(e).tp == Tipos.STRING) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.NUMERIC || li.getTipo(e).tp == Tipos.NUMERIC) {
            return new TipoExp(Tipos.NUMERIC);
        } else if (tul.tp == Tipos.INTEGER || li.getTipo(e).tp == Tipos.INTEGER) {
            return new TipoExp(Tipos.INTEGER);
        } else if (tul.tp == Tipos.BOOLEAN && li.getTipo(e).tp == Tipos.BOOLEAN) {
            return new TipoExp(Tipos.BOOLEAN);
        }
        return null;
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

    /**
     * @return the acc
     */
    public Expresion getAcc() {
        return acc;
    }

    /**
     * @param acc the acc to set
     */
    public void setAcc(Acceso acc) {
        this.acc = acc;
    }

    /**
     * @return the valor
     */
    public Expresion getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Expresion valor) {
        this.valor = valor;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

}

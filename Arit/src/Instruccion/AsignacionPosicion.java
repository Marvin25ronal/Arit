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
import Objetos.Array;
import Objetos.Matrix;
import Objetos.Nulo;
import Objetos.EstructuraLineal;
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
        Simbolo s = (Simbolo) obj;
        if (((Simbolo) ac.getId().getValor(e)) instanceof Matrix) {
            if (tvalor.isPrimitive(e)) {
                return ReasignarMatriz_Primitivo(e, val, tvalor, (EstructuraLineal) s);
            } else if (tvalor.isVector()) {
                return ReasignarMatriz_Vector(e, val, tvalor, (EstructuraLineal) s);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La matriz no puede contener ese tipo de elementos", linea, columna);
            }
        } else if (((Simbolo) ac.getId().getValor(e)) instanceof Array) {
            if (tvalor.isPrimitive(e) || tvalor.isVector() || tvalor.isList()) {
                return ReasignarArray(e, val, tvalor, (EstructuraLineal) s);
            }
        } else if (s.getTipo().isVector()) {
            if (tvalor.isPrimitive(e)) {
                return ReasignarVector_Primitivo(e, val, tvalor, (EstructuraLineal) obj);
            } else if (tvalor.isVector()) {
                return ReasignarVector_Vector(e, val, tvalor, (EstructuraLineal) obj);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector no puede contener ese tipo de objetos ", linea, columna);
            }
        } else if (s.getTipo().isList()) {
            if (tvalor.isPrimitive(e)) {
                return ReasignarLista_Primitivo(e, val, tvalor, (EstructuraLineal) obj);
            } else if (tvalor.isList()) {
                return ReasignarLista_Lista(e, val, tvalor, (EstructuraLineal) obj);
            } else if (tvalor.isVector()) {
                return ReasignarLista_Vector(e, val, tvalor, (EstructuraLineal) obj);
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La lista no puede contener ese tipo de objetos ", linea, columna);
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
    
    private Object ReasignarLista_Vector(Entorno e, Object val, TipoExp t, EstructuraLineal lista) {
        /*Anterior ant = (Anterior) Globales.VarGlobales.getInstance().getAnterior();
        Lista metiendo = (Lista) ant.getAnterior();
        EstructuraLineal apasar = (EstructuraLineal) val;
        if (apasar.getDimensiones().size() > 1) {
            return new Errores(Errores.TipoError.SEMANTICO, "La lista solo puede contener un vector en su nodo de tamanio 1 ", linea, columna);
        }
        LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().clonarListaVector(apasar.getDimensiones(), e);
        EstructuraLineal nuevo = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), apasar.getTiposecundario(), valoresnuevos);
        metiendo.getLista().set(ant.getIndice(), nuevo);
         */
        
        EstructuraLineal apasar = (EstructuraLineal) val;
        
        if (Globales.VarGlobales.getInstance().getAnterior().getAcceso() == 1) {
            //acceso 1
            if (apasar.getDimensiones().size() > 1) {
                return new Errores(Errores.TipoError.SEMANTICO, "La lista solo puede contener un vector en su nodo de tamanio 1", linea, columna);
            }
            EstructuraLineal lista2 = (EstructuraLineal) lista.getDimensiones().get(0);
            lista2.getDimensiones().clear();
            lista2.setTipo(new TipoExp(Tipos.VECTOR));
            lista2.setTiposecundario(new TipoExp(t.tp));
            LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().clonarListaVector(apasar.getDimensiones(), e);
            lista2.setDimensiones(valoresnuevos);
        } else {
            lista.getDimensiones().clear();
            lista.setTipo(new TipoExp(Tipos.VECTOR));
            lista.setTiposecundario(new TipoExp(t.tp));
            LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().clonarListaVector(apasar.getDimensiones(), e);
            lista.setDimensiones(valoresnuevos);
        }
        return null;
    }
    
    private Object ReasignarArray(Entorno e, Object val, TipoExp tvalor, EstructuraLineal s) {
        Acceso ac = (Acceso) acc;
        Array origen = (Array) ac.getId().getValor(e);
        //actualizar tipo o no
        if (tvalor.isList()) {
            EstructuraLineal pasando = (EstructuraLineal) val;
            s.setDimensiones(Globales.VarGlobales.getInstance().CopiarLista(e, pasando.getDimensiones()));
            s.setTipo(new TipoExp(Tipos.LISTA));
            //s.setTiposecundario(new TipoExp(pasando.getTiposecundario().tp));
        } else if (tvalor.isVector()) {
            EstructuraLineal pasando = (EstructuraLineal) val;
            s.setDimensiones(Globales.VarGlobales.getInstance().clonarListaVector(pasando.getDimensiones(), e));
            s.setTiposecundario(new TipoExp(s.getTiposecundario().tp));
            
        } else if (tvalor.isPrimitive(e)) {
            Literal nueva = new Literal(val, tvalor, linea, columna);
            LinkedList<Object> nl = new LinkedList<>();
            nl.add(nueva);
            s.setDimensiones(nl);
            s.setTiposecundario(tvalor);
        }
        return null;
    }
    
    private Object ReasignarMatriz_Primitivo(Entorno e, Object val, TipoExp t, EstructuraLineal vector) {
        Acceso ac = (Acceso) acc;
        Matrix origen = (Matrix) ac.getId().getValor(e);
        //primero tengo que ver cual es el tipo dominante 
        TipoExp dominante = TipoDominanteMatriz(origen.getTiposecundario(), t);
        for (int i = 0; i < vector.getDimensiones().size(); i++) {
            Object valor = CastearValor(dominante, val, origen.getTiposecundario());
            Literal nueva = (Literal) vector.getDimensiones().get(i);
            nueva.setValor(valor);
            nueva.setTipo(new TipoExp(dominante.tp));
            //vector.getDimensiones().set(i, nueva);
        }
        if (dominante.tp != origen.getTiposecundario().tp) {
            CastearMatriz(origen, e, dominante);
        }
        return null;
    }
    
    private Object ReasignarMatriz_Vector(Entorno e, Object val, TipoExp t, EstructuraLineal vecto) {
        Acceso ac = (Acceso) acc;
        Matrix origen = (Matrix) ac.getId().getValor(e);
        EstructuraLineal vector = (EstructuraLineal) vecto;
        EstructuraLineal metiendo = (EstructuraLineal) val;
        //si tiene un elemento no puede contener mas de un elemento el metiendo
        TipoExp dominante = TipoDominanteMatriz(origen.getTiposecundario(), metiendo.getTiposecundario());
        if (vector.getDimensiones().size() == 1) {
            //tipo 4 o tipo 1
            if (metiendo.getDimensiones().size() > 1) {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector en esa posicion no puede contener mas de un elemento ", linea, columna);
            }
            Literal l = (Literal) vector.getDimensiones().get(0);
            Literal lmetiendo = (Literal) metiendo.getDimensiones().get(0);
            l.setTipo(new TipoExp(dominante.tp));
            l.setValor(CastearValor(dominante, lmetiendo.getValor(), origen.getTiposecundario()));
            
        } else {
            //tip 2,3
            if (metiendo.getDimensiones().size() == vector.getDimensiones().size()) {
                for (int i = 0; i < metiendo.getDimensiones().size(); i++) {
                    Literal l = (Literal) vector.getDimensiones().get(i);
                    Literal lmetiendo = (Literal) metiendo.getDimensiones().get(i);
                    l.setTipo(new TipoExp(dominante.tp));
                    l.setValor(CastearValor(dominante, lmetiendo.getValor(), origen.getTiposecundario()));
                }
            } else if (metiendo.getDimensiones().size() == 1) {
                for (int i = 0; i < metiendo.getDimensiones().size(); i++) {
                    Literal l = (Literal) vector.getDimensiones().get(i);
                    Literal lmetiendo = (Literal) metiendo.getDimensiones().get(0);
                    l.setTipo(new TipoExp(dominante.tp));
                    l.setValor(CastearValor(dominante, lmetiendo.getValor(), origen.getTiposecundario()));
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector tiene que ser de tamanio 1 o del mismo tamanio de la columna fila", linea, columna);
            }
        }
        if (dominante.tp != origen.getTiposecundario().tp) {
            CastearMatriz(origen, e, dominante);
        }
        return null;
    }
    
    private void CastearMatriz(Matrix m, Entorno e, TipoExp tipo) {
        m.setTiposecundario(tipo);
        for (int i = 0; i < m.getColumna(); i++) {
            for (int j = 0; j < m.getFila(); j++) {
                EstructuraLineal v = (EstructuraLineal) m.getColumnas().get(i).get(j);
                Literal l = (Literal) v.getDimensiones().get(0);
                l.setValor(CastearValor(tipo, l.getValor(), l.getTipo()));
                l.setTipo(new TipoExp(tipo.tp));
                v.setTiposecundario(tipo);
            }
        }
    }
    
    private Object ReasignarLista_Lista(Entorno e, Object val, TipoExp t, EstructuraLineal lista) {
        Anterior ant = Globales.VarGlobales.getInstance().getAnterior();
        
        if (ant.getAcceso() == 1) {
            EstructuraLineal lista2 = (EstructuraLineal) lista.getDimensiones().get(0);
            //Lista metiendo = (Lista) ant.getAnterior();
            EstructuraLineal apasar = (EstructuraLineal) val;
            if (apasar.getDimensiones().size() > 1) {
                return new Errores(Errores.TipoError.SEMANTICO, "La lista solo puede contener un elemento en su nodo ", linea, columna);
            }
            LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().CopiarLista(e, apasar.getDimensiones());
            lista2.getDimensiones().clear();
            lista2.setTipo(new TipoExp(Tipos.LISTA));
            lista2.setDimensiones(valoresnuevos);
            //EstructuraLineal nueva = new Lista(valoresnuevos, new TipoExp(Tipos.LISTA), null, "");
            //metiendo.getLista().set(ant.getIndice(), nueva);
            return null;
            
        } else {
            //acceso 2 no tiene restricciones
            EstructuraLineal apasar = (EstructuraLineal) val;
            LinkedList<Object> valoresnuevos = Globales.VarGlobales.getInstance().CopiarLista(e, apasar.getDimensiones());
            lista.getDimensiones().clear();
            lista.setTipo(new TipoExp(Tipos.LISTA));
            lista.setDimensiones(valoresnuevos);
            return null;
            
        }
        
    }
    
    private Object ReasignarLista_Primitivo(Entorno e, Object setvalor, TipoExp t, EstructuraLineal lista) {
        if (Globales.VarGlobales.getInstance().getAnterior().getAcceso() == 1) {
            //el contenido va en una lista
            //sacar el elemento
            EstructuraLineal aux = (EstructuraLineal) lista.getDimensiones().get(0);
            aux.getDimensiones().clear();
            aux.setTipo(new TipoExp(Tipos.VECTOR));
            LinkedList<Object> objetos = new LinkedList<>();
            objetos.add(new Literal(setvalor, t, linea, columna));
            aux.setDimensiones(objetos);
            aux.setTiposecundario(new TipoExp(t.tp));
        } else {
            //el contenido es puro
            lista.getDimensiones().clear();
            lista.setTipo(new TipoExp(Tipos.VECTOR));
            LinkedList<Object> objetos = new LinkedList<>();
            objetos.add(new Literal(setvalor, t, linea, columna));
            lista.setDimensiones(objetos);
            lista.setTiposecundario(new TipoExp(t.tp));
        }
        
        return null;
    }
    
    private Object ReasignarVector_Primitivo(Entorno e, Object setvalor, TipoExp t, EstructuraLineal v) {
        TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, t);
        ((Literal) v.getDimensiones().get(0)).setValor(CastearValor(nuevot, setvalor, t));
        ((Literal) v.getDimensiones().get(0)).setTipo(nuevot);
        Simbolo s = e.get(((Acceso) acc).getId().getVal());
        if ((s.getTipo().isVector())) {
            CastearVector(nuevot, e);
        } else {
            CastearVector(nuevot, e, (EstructuraLineal) Globales.VarGlobales.getInstance().getAnterior().getAnterior());
        }
        return null;
    }
    
    private Object ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t, EstructuraLineal v) {
        if (setvalor instanceof EstructuraLineal) {
            EstructuraLineal set = (EstructuraLineal) setvalor;
            if (set.getDimensiones().size() == 1) {
                LinkedList<Object> listaAux = Globales.VarGlobales.getInstance().clonarListaVector(set.getDimensiones(), e);
                Literal aux = (Literal) listaAux.get(0);//--Retorna un arreglo con un elemento que es una literal
                TipoExp nuevot = Dominante_Vector(v.getDimensiones(), e, aux.getTipo(e));
                ((Literal) v.getDimensiones().get(0)).setValor(CastearValor(nuevot, aux.getValor(), aux.getTipo(e)));
                ((Literal) v.getDimensiones().get(0)).setTipo(nuevot);
                Simbolo s = e.get(((Acceso) acc).getId().getVal());
                if ((s instanceof EstructuraLineal)) {
                    CastearVector(nuevot, e);
                } else {
                    
                    CastearVector(nuevot, e, (EstructuraLineal) Globales.VarGlobales.getInstance().getAnterior().getAnterior());
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
        
        EstructuraLineal v = (EstructuraLineal) e.get(((Acceso) acc).getId().getVal());
        v.setTiposecundario(t);
        Literal aux;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            aux = (Literal) v.getDimensiones().get(i);
            aux.setValor(CastearValor(t, aux.getValor(e), aux.getTipo(e)));
            aux.setTipo(t);
        }
    }
    
    private void CastearVector(TipoExp t, Entorno e, EstructuraLineal r) {
        EstructuraLineal v = r;
        if (!v.getTipo().isVector()) {
            return;
        }
        Literal aux;
        for (int i = 0; i < v.getDimensiones().size(); i++) {
            aux = (Literal) v.getDimensiones().get(i);
            aux.setValor(CastearValor(t, aux.getValor(e), aux.getTipo(e)));
            aux.setTipo(t);
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
    
    private TipoExp TipoDominanteMatriz(TipoExp tul, TipoExp tiob) {
        if (tul.tp == Tipos.NULO || tiob.tp == Tipos.NULO) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.STRING || tiob.tp == Tipos.STRING) {
            return new TipoExp(Tipos.STRING);
        } else if (tul.tp == Tipos.NUMERIC || tiob.tp == Tipos.NUMERIC) {
            return new TipoExp(Tipos.NUMERIC);
        } else if (tul.tp == Tipos.INTEGER || tiob.tp == Tipos.INTEGER) {
            return new TipoExp(Tipos.INTEGER);
        } else if (tul.tp == Tipos.BOOLEAN && tiob.tp == Tipos.BOOLEAN) {
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
    
    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"Asignacion Pos \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append(acc.toDot(this.hashCode()));
        nueva.append("node").append(this.hashCode() + 1).append("[label=\"= \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        nueva.append(valor.toDot(this.hashCode()));
        return nueva.toString();
    }
    
}

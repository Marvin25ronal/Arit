/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instruccion;

import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Expresion;
import Expresion.Identificador;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
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
public class DecAsig implements Instruccion {

    private Expresion valor;
    private Identificador id;
    private int linea;
    private int columna;
    public boolean isfor;
    public TipoExp tfor;

    public DecAsig(Expresion valor, Identificador id, int linea, int columna) {
        this.valor = valor;
        this.id = id;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if (getValor() == null) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se pudo declarar", getLinea(), getColumna()));
            return null;
        }
        Object setvalor = getValor().getValor(e);
        if (setvalor instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) setvalor);
            return null;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(setvalor, e);
        if (t.tp == Tipos.NULO) {
            //cuando es nulo
            if (e.ExisteVariable(getId().getVal())) {
                isfor = e.get(id.getVal()).isfor;
                tfor = e.get(id.getVal()).tfor;
                
                ReasignarVector_Nulo(e);
            } else {
                CrearVector_Nulo(e);
            }
        } else if (t.isPrimitive(e)) {
            //se crea el arreglo con los nuevos valores
            /*
            Reglas--------------
            primero ver si no existe antes para reasignar valor
            agregarla a la tabla
             */
            if (e.ExisteVariable(getId().getVal())) {
                //se reasigna
                isfor = e.get(id.getVal()).isfor;
                tfor = e.get(id.getVal()).tfor;
                ReasignarVector_Primitivo(e, setvalor, t);
            } else {
                //arreglo nuevo
                CrearNuevoVector_Primitivo(e, setvalor, t);
            }
        } else {
            //el vector va a cambiar cuando son una lista de valores
            if (t.isVector()) {
                if (e.ExisteVariable(getId().getVal())) {
                    isfor = e.get(id.getVal()).isfor;
                    tfor = e.get(id.getVal()).tfor;
                    ReasignarVector_Vector(e, setvalor, t);
                } else {
                    CrearNuevoVector_Vector(e, setvalor, t);
                }
            } else if (t.isList()) {
                if (e.ExisteVariable(getId().getVal())) {
                    isfor = e.get(id.getVal()).isfor;
                    tfor = e.get(id.getVal()).tfor;
                    ReasignarLista_Lista(e, setvalor);
                } else {
                    CrearListaNueva(e, setvalor);
                }
            } else if (t.isMatrix()) {
                if (e.ExisteVariable(getId().getVal())) {
                    isfor = e.get(id.getVal()).isfor;
                    tfor = e.get(id.getVal()).tfor;
                    ReasignarMatriz_Matriz(e, setvalor);
                } else {
                    CrearMatrizNueva(e, setvalor);
                }
            } else if (t.isArrya()) {
                if (e.ExisteVariable(id.getVal())) {
                    isfor = e.get(id.getVal()).isfor;
                    tfor = e.get(id.getVal()).tfor;
                    ReasignarArray_Array(e, setvalor);
                } else {
                    CrearArrayNueva(e, setvalor);
                }
            }
        }
        return null;
    }

    public Object EjecutarFuncion(Entorno variables, Entorno declarar) {
        if (getValor() == null) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se pudo declarar", getLinea(), getColumna()));
            return null;
        }
        Object setvalor = getValor().getValor(variables);
        if (setvalor instanceof Errores) {
            Globales.VarGlobales.getInstance().AgregarEU((Errores) setvalor);
            return null;
        }
        TipoExp t = Globales.VarGlobales.getInstance().obtenerTipo(setvalor, variables);
        if (t.tp == Tipos.NULO) {
            //cuando es nulo
            if (variables.ExisteEnEntorno(getId().getVal())) {
                isfor=variables.get(id.getVal()).isfor;
                tfor=variables.get(id.getVal()).tfor;
                ReasignarVector_Nulo(declarar);
            } else {
                CrearVector_Nulo(declarar);
            }
        } else if (t.isPrimitive(variables)) {
            //se crea el arreglo con los nuevos valores
            /*
            Reglas--------------
            primero ver si no existe antes para reasignar valor
            agregarla a la tabla
             */
            if (variables.ExisteEnEntorno(getId().getVal())) {
                //se reasigna
                isfor=variables.get(id.getVal()).isfor;
                tfor=variables.get(id.getVal()).tfor;
                ReasignarVector_Primitivo(declarar, setvalor, t);
            } else {
                //arreglo nuevo
                CrearNuevoVector_Primitivo(declarar, setvalor, t);
            }
        } else {
            //el vector va a cambiar cuando son una lista de valores
            if (t.isVector()) {
                if (variables.ExisteEnEntorno(getId().getVal())) {
                    isfor=variables.get(id.getVal()).isfor;
                    tfor=variables.get(id.getVal()).tfor;
                    ReasignarVector_Vector(declarar, setvalor, t);
                } else {
                    CrearNuevoVector_Vector(declarar, setvalor, t);
                }
            } else if (t.isList()) {
                if (variables.ExisteEnEntorno(getId().getVal())) {
                    isfor=variables.get(id.getVal()).isfor;
                    tfor=variables.get(id.getVal()).tfor;
                    ReasignarLista_Lista(declarar, setvalor);
                } else {
                    CrearListaNueva(declarar, setvalor);
                }
            } else if (t.isMatrix()) {
                if (variables.ExisteEnEntorno(getId().getVal())) {
                    isfor=variables.get(id.getVal()).isfor;
                    tfor=variables.get(id.getVal()).tfor;
                    ReasignarMatriz_Matriz(declarar, setvalor);
                } else {
                    CrearMatrizNueva(declarar, setvalor);
                }
            } else if (t.isArrya()) {
                if (variables.ExisteEnEntorno(id.getVal())) {
                    isfor=variables.get(id.getVal()).isfor;
                    tfor=variables.get(id.getVal()).tfor;
                    ReasignarArray_Array(declarar, setvalor);
                } else {
                    CrearArrayNueva(declarar, setvalor);
                }
            }
        }
        return null;
    }

    private void ReasignarMatriz_Matriz(Entorno e, Object matriz) {
        if (isfor) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se le puede agregar este tipo de objetos a " + tfor.toString(), linea, columna));
            return;
        }
        Matrix m = (Matrix) matriz;
        LinkedList<LinkedList<Object>> valores = Globales.VarGlobales.getInstance().CopiarMatrix(e, m.getColumnas());
        Matrix nueva = new Matrix(valores, new TipoExp(Tipos.MATRIX), new TipoExp(m.getTiposecundario().tp), id.getVal(), m.getColumna(), m.getFila());
        e.Actualizar(id.getVal(), nueva);
    }

    private void ReasignarArray_Array(Entorno e, Object array) {
        if (isfor) {
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "No se le puede agregar este tipo de objetos a " + tfor.toString(), linea, columna));
            return;
        }
        Array a = (Array) array;
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().CopiarArray(e, a.getArreglo());
        Array nuevo = new Array(new TipoExp(Tipos.ARRAY), new TipoExp(a.getTiposecundario().tp), id.getVal(), lista, new LinkedList<>(a.getDimensiones()));
        e.Actualizar(id.getVal(), nuevo);
    }

    private void CrearArrayNueva(Entorno e, Object array) {
        Array arreglo = (Array) array;
        LinkedList<Object> valores = Globales.VarGlobales.getInstance().CopiarArray(e, arreglo.getArreglo());
        Array nuevo = new Array(new TipoExp(Tipos.ARRAY), arreglo.getTiposecundario(), id.getVal(), valores, new LinkedList<>(arreglo.getDimensiones()), this.linea, this.columna);
        e.add(id.getVal(), nuevo);
    }

    private void CrearMatrizNueva(Entorno e, Object matriz) {
        Matrix m = (Matrix) matriz;
        LinkedList<LinkedList<Object>> valores = Globales.VarGlobales.getInstance().CopiarMatrix(e, m.getColumnas());
        Matrix nueva = new Matrix(valores, new TipoExp(Tipos.MATRIX), new TipoExp(m.getTiposecundario().tp), id.getVal(), m.getColumna(), m.getFila(), this.linea, this.columna);
        e.add(id.getVal(), nueva);
    }

    private void ReasignarLista_Lista(Entorno e, Object lista) {
        //Lista l = (Lista) lista;
        if (isfor) {
            EstructuraLineal lfor = (EstructuraLineal) e.get(id.getVal());
            EstructuraLineal l = (EstructuraLineal) lista;
            LinkedList<Object> valores = Globales.VarGlobales.getInstance().CopiarLista(e, l.getDimensiones());
            if (tfor.isMatrix()) {
                Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos de la matriz solo pueden contener un elemento y no de tipo lista", linea, columna));
                return;
            } else if (tfor.isArrya()) {
                if (valores.size() > 1) {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos del array solo pueden tener un elemento", linea, columna));
                    return;
                }
            } else if (tfor.isVector()) {
                Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos del vector solo pueden contener un elemento y no de tipo lista", linea, columna));
                return;
            } else if (tfor.isList()) {
                if (valores.size() > 1) {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos de la lista solo pueden tener un elemento", linea, columna));
                    return;
                }
            }
            lfor.getDimensiones().clear();
            lfor.setTipo(new TipoExp(Tipos.LISTA));
            for (int i = 0; i < valores.size(); i++) {
                lfor.getDimensiones().add(valores.get(i));
            }
            e.Actualizar(id.getVal(), lfor);
            return;
        }
        EstructuraLineal l = (EstructuraLineal) lista;
        LinkedList<Object> valores = Globales.VarGlobales.getInstance().CopiarLista(e, l.getDimensiones());
        //Lista nueva = new Lista(valores, new TipoExp(Tipos.LISTA), null, id.getVal());
        EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.LISTA), null, valores);
        e.Actualizar(id.getVal(), nueva);
    }

    private void CrearListaNueva(Entorno e, Object lista) {
        //Lista l = (Lista) lista;
        EstructuraLineal l = (EstructuraLineal) lista;
        LinkedList<Object> valores = Globales.VarGlobales.getInstance().CopiarLista(e, l.getDimensiones());
        //Lista nueva = new Lista(valores, new TipoExp(Tipos.LISTA), null, id.getVal());
        EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.LISTA), null, valores, this.linea, this.columna);
        e.add(id.getVal(), nueva);
    }

    private void CrearNuevoVector_Primitivo(Entorno e, Object setvalor, TipoExp t) {
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(setvalor, t, getLinea(), getColumna());
        datos.add(nueva);
        EstructuraLineal nuevo = new EstructuraLineal(getId().getVal(), new TipoExp(Tipos.VECTOR), t, datos, this.linea, this.columna);
        e.add(getId().getVal(), nuevo);
    }

    private void CrearVector_Nulo(Entorno e) {
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(new Nulo(getLinea(), getColumna()), new TipoExp(Tipos.NULO), getLinea(), getColumna());
        datos.add(nueva);
        EstructuraLineal nuevo = new EstructuraLineal(getId().getVal(), new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), datos, this.linea, this.columna);
        e.add(getId().getVal(), nuevo);
    }

    private void ReasignarVector_Nulo(Entorno e) {
        if (isfor) {
            if (tfor.isVector()) {
                EstructuraLineal lfor = (EstructuraLineal) e.get(id.getVal());
                Literal antigua = (Literal) lfor.getDimensiones().get(0);
                antigua.setValor(new Nulo(linea, columna));
                antigua.setValor(new TipoExp(Tipos.NULO));

                //Literal nueva = new Literal(new Nulo(getLinea(), getColumna()), new TipoExp(Tipos.NULO), getLinea(), getColumna());
                //lfor.getDimensiones().clear();
                //lfor.getDimensiones().add(nueva);
                lfor.setTiposecundario(new TipoExp(Tipos.STRING));
                e.Actualizar(id.getVal(), lfor);
                return;
            }
            EstructuraLineal lfor = (EstructuraLineal) e.get(id.getVal());
            Literal nueva = new Literal(new Nulo(getLinea(), getColumna()), new TipoExp(Tipos.NULO), getLinea(), getColumna());
            lfor.getDimensiones().clear();
            lfor.getDimensiones().add(nueva);
            lfor.setTiposecundario(new TipoExp(Tipos.STRING));
            e.Actualizar(id.getVal(), lfor);
            return;
        }
        Simbolo s = e.get(getId().getVal());
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(new Nulo(getLinea(), getColumna()), new TipoExp(Tipos.NULO), getLinea(), getColumna());
        datos.add(nueva);
        EstructuraLineal nuevo = new EstructuraLineal(getId().getVal(), new TipoExp(Tipos.VECTOR), new TipoExp(Tipos.STRING), datos);
        e.Actualizar(getId().getVal(), nuevo);
    }

    private void ReasignarVector_Primitivo(Entorno e, Object setvalor, TipoExp t) {
        //Verificar si es un vector
        if (isfor) {
            if (tfor.isVector()) {
                EstructuraLineal lfor = (EstructuraLineal) e.get(id.getVal());
                Literal nueva = (Literal) lfor.getDimensiones().get(0);
                nueva.setValor(setvalor);
                nueva.setTipo(t);
                //Literal nueva = new Literal(setvalor, t, getLinea(), getColumna());
                lfor.getDimensiones().clear();
                lfor.getDimensiones().add(nueva);
                lfor.setTiposecundario(t);
                e.Actualizar(id.getVal(), lfor);
                return;
            }
            EstructuraLineal lfor = (EstructuraLineal) e.get(id.getVal());
            Literal nueva = new Literal(setvalor, t, getLinea(), getColumna());
            lfor.getDimensiones().clear();
            lfor.getDimensiones().add(nueva);
            lfor.setTiposecundario(t);
            e.Actualizar(id.getVal(), lfor);
            return;
        }
        LinkedList<Object> datos = new LinkedList<>();
        Literal nueva = new Literal(setvalor, t, getLinea(), getColumna());
        datos.add(nueva);
        EstructuraLineal v = new EstructuraLineal(getId().getVal(), new TipoExp(Tipos.VECTOR), t, datos);
        e.Actualizar(getId().getVal(), v);

    }

    private void CrearNuevoVector_Vector(Entorno e, Object setvalor, TipoExp t) {
        EstructuraLineal v = (EstructuraLineal) setvalor;
        LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
        EstructuraLineal nuevo = new EstructuraLineal(getId().getVal(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), datos, this.linea, this.columna);
        e.add(getId().getVal(), nuevo);
    }

    private void ReasignarVector_Vector(Entorno e, Object setvalor, TipoExp t) {
        //a un vector solo se le puede asignar un vector
        if (isfor) {
            EstructuraLineal lfor = (EstructuraLineal) e.get(id.getVal());
            EstructuraLineal aux = (EstructuraLineal) setvalor;
            LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(aux.getDimensiones(), e);
            if (tfor.isMatrix()) {
                if (datos.size() > 1) {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos de la matriz solo pueden contener un elemento y no de tipo lista", linea, columna));
                    return;
                }
            } else if (tfor.isArrya()) {
                if (datos.size() > 1) {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos del array solo pueden tener un elemento", linea, columna));
                    return;
                }
            } else if (tfor.isVector()) {
                if (datos.size() > 1) {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos del vector solo pueden contener un elemento y no de tipo lista", linea, columna));
                    return;
                }
            } else if (tfor.isList()) {
                if (datos.size() > 1) {
                    Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Los nodos de la lista solo pueden tener un elemento", linea, columna));
                    return;
                }
            }
            if (tfor.isVector()) {
                Literal antigua = (Literal) lfor.getDimensiones().get(0);
                Literal nueva = (Literal) datos.get(0);
                antigua.setTipo(nueva.getTipo());
                antigua.setValor(nueva.getValor());
                lfor.setTiposecundario(aux.getTiposecundario());
                e.Actualizar(id.getVal(), lfor);
                return;
            }
            lfor.getDimensiones().clear();
            lfor.setTiposecundario(aux.getTiposecundario());
            for (int i = 0; i < datos.size(); i++) {
                lfor.getDimensiones().add(datos.get(i));
            }
            e.Actualizar(id.getVal(), lfor);
            return;
        }
        EstructuraLineal aux = (EstructuraLineal) setvalor;
        LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(aux.getDimensiones(), e);
        EstructuraLineal v = new EstructuraLineal(getId().getVal(), new TipoExp(Tipos.VECTOR), aux.getTiposecundario(), datos);
        e.Actualizar(getId().getVal(), v);
    }

    @Override
    public int linea() {
        return this.getLinea();
    }

    @Override
    public int columna() {
        return this.getColumna();
    }

    private boolean isPrimitive(Entorno e) {
        if (getValor().getTipo(e).tp == Tipos.VECTOR) {
            return false;
        } else if (getValor().getTipo(e).tp == Tipos.LISTA) {
            return false;
        }
        return true;
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
        nueva.append("node").append(this.hashCode()).append("[label=\"Dec_Asign \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append(id.toDot(this.hashCode()));
        nueva.append("node").append(this.hashCode() + 1).append("[label=\"= \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        nueva.append(valor.toDot(this.hashCode()));
        return nueva.toString();
    }

}

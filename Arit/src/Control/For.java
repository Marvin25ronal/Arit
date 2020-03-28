/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import AST.Nodo;
import Entorno.Entorno;
import Entorno.Simbolo;
import Expresion.Expresion;
import Expresion.Identificador;
import Expresion.Literal;
import Expresion.TipoExp;
import Expresion.TipoExp.Tipos;
import Instruccion.DecAsig;
import Instruccion.Instruccion;
import Objetos.Array;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import Objetos.Nulo;
import Reportes.Errores;
import java.util.LinkedList;

/**
 *
 * @author marvi
 */
public class For implements Instruccion {

    Identificador id;
    Expresion exp;
    LinkedList<Nodo> sentencias;
    int linea, columna;

    public For(Identificador id, Expresion exp, LinkedList<Nodo> sentencias, int linea, int columna) {
        this.id = id;
        this.exp = exp;
        this.sentencias = sentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Simbolo variable;
        Entorno arriba = new Entorno(e);
        if (e.ExisteVariable(id.getVal())) {
            variable = (Simbolo) id.getValor(e);
        } else {
            EstructuraLineal nueva = new EstructuraLineal(null, null, linea, columna);
            arriba.add(id.getVal(), nueva);
            variable = arriba.get(id.getVal());
        }
        Object estructura = exp.getValor(e);
        if (estructura instanceof Errores) {
            return estructura;
        }
        TipoExp testructura = Globales.VarGlobales.getInstance().obtenerTipo(estructura, e);
        if (testructura.isVector()) {
            EstructuraLineal v = (EstructuraLineal) estructura;
            return ForVector(v, arriba);
        } else if (testructura.isMatrix()) {
            Matrix m = (Matrix) estructura;
            return ForMatrix(m, arriba);
        } else if (testructura.isArrya()) {
            Array a = (Array) estructura;
            return ForArray(a, arriba);
        } else if (testructura.isList()) {
            EstructuraLineal l = (EstructuraLineal) estructura;
            return ForLista(l, arriba);
        } else if (testructura.isPrimitive(arriba)) {
            EstructuraLineal v = new EstructuraLineal(testructura, estructura, linea, columna);
            return ForVector(v, arriba);
        }
        return null;
    }

    private Object ForArray(Array a, Entorno e) {
        //LinkedList<Object> lista = Globales.VarGlobales.getInstance().CopiarArray(e, a.getArreglo());
        Object t = ForArray(a.getArreglo(), e);
        Castear(e, a, new TipoExp(Tipos.ARRAY));
        return t;
    }

    private Object ForArray(LinkedList<Object> lista, Entorno e) {
        for (int i = 0; i < lista.size(); i++) {
            Object aux = lista.get(i);
            if (aux instanceof LinkedList) {
                LinkedList<Object> listaaux = (LinkedList<Object>) lista.get(i);
                Object res = ForArray(listaaux, e);
                if (res != null) {
                    //Castear(e, new TipoExp(Tipos.ARRAY));
                    return res;
                }
            } else {
                EstructuraLineal v = (EstructuraLineal) lista.get(i);
                v.isfor = true;
                v.tfor = new TipoExp(Tipos.ARRAY);
                e.Actualizar(id.getVal(), v);
                for (Nodo n : sentencias) {
                    if (n instanceof Instruccion) {

                        Object result = ((Instruccion) n).ejecutar(e);
                        if (result instanceof Errores) {
                            Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                        } else if (result instanceof Break) {

                            return 1;
                        } else if (result instanceof Continue) {
                            break;
                        } else if (result != null) {
                            //break continue y esas cosas

                            return result;
                        }
                    } else if (n instanceof Expresion) {
                        Object result = ((Expresion) n).getValor(e);
                        if (result instanceof Errores) {
                            Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                        } else if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        //Castear(e, a, new TipoExp(Tipos.ARRAY));
        return null;
    }

    private Object ForLista(EstructuraLineal l, Entorno e) {
        LinkedList<Object> lista = l.getDimensiones();
        LinkedList<Object> nuevalista = lista;
        for (int i = 0; i < nuevalista.size(); i++) {
            ((Simbolo) nuevalista.get(i)).isfor = true;
            ((Simbolo) nuevalista.get(i)).tfor = new TipoExp(Tipos.LISTA);
            e.Actualizar(id.getVal(), (Simbolo) nuevalista.get(i));
            for (Nodo n : sentencias) {
                if (n instanceof Instruccion) {
                    Object result = ((Instruccion) n).ejecutar(e);
                    if (result instanceof Errores) {
                        Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                    } else if (result instanceof Break) {
                        return null;
                    } else if (result instanceof Continue) {
                        break;
                    } else if (result != null) {
                        //break continue y esas cosas
                        return result;
                    }
                } else if (n instanceof Expresion) {
                    Object result = ((Expresion) n).getValor(e);
                    if (result instanceof Errores) {
                        Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                    } else if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private Object ForMatrix(Matrix m, Entorno e) {
        LinkedList<LinkedList<Object>> lista = m.getColumnas();
        LinkedList<LinkedList<Object>> listanueva = lista;
        for (int j = 0; j < m.getColumna(); j++) {
            for (int i = 0; i < m.getFila(); i++) {
                EstructuraLineal v = (EstructuraLineal) listanueva.get(j).get(i);
                LinkedList<Object> lvector = v.getDimensiones();
                EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), lvector);
                nueva.isfor = true;
                nueva.tfor = new TipoExp(Tipos.MATRIX);
                e.Actualizar(id.getVal(), nueva);
                for (Nodo n : sentencias) {
                    if (n instanceof Instruccion) {

                        Object result = ((Instruccion) n).ejecutar(e);
                        if (result instanceof Errores) {
                            Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                        } else if (result instanceof Break) {
                            Castear(e, m, new TipoExp(Tipos.MATRIX));
                            return null;
                        } else if (result instanceof Continue) {
                            break;
                        } else if (result != null) {
                            //break continue y esas cosas
                            Castear(e, m, new TipoExp(Tipos.MATRIX));
                            return result;
                        }
                    } else if (n instanceof Expresion) {
                        Object result = ((Expresion) n).getValor(e);
                        if (result instanceof Errores) {
                            Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                        } else if (result != null) {
                            Castear(e, m, new TipoExp(Tipos.MATRIX));
                            return result;
                        }
                    }
                }
            }
        }
        Castear(e, m, new TipoExp(Tipos.MATRIX));
        return null;
    }

    private Object ForVector(EstructuraLineal t, Entorno e) {
        LinkedList<Object> lista = t.getDimensiones();
        LinkedList<Object> listanueva = lista;
        for (int i = 0; i < listanueva.size(); i++) {
            Literal l = (Literal) listanueva.get(i);
            LinkedList<Object> lvector = new LinkedList<>();
            lvector.add(l);
            EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), l.getTipo(), lvector);
            nueva.isfor = true;
            nueva.tfor = new TipoExp(Tipos.VECTOR);
            e.Actualizar(id.getVal(), nueva);
            for (Nodo n : sentencias) {
                if (n instanceof Instruccion) {
                    Object result = ((Instruccion) n).ejecutar(e);
                    if (result instanceof Errores) {
                        Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                    } else if (result instanceof Break) {
                        Castear(e, t, new TipoExp(Tipos.VECTOR));
                        return null;
                    } else if (result instanceof Continue) {
                        break;
                    } else if (result != null) {
                        //break continue y esas cosas
                        Castear(e, t, new TipoExp(Tipos.VECTOR));
                        return result;
                    }
                } else if (n instanceof Expresion) {
                    Object result = ((Expresion) n).getValor(e);
                    if (result instanceof Errores) {
                        Globales.VarGlobales.getInstance().AgregarEU((Errores) result);
                    } else if (result != null) {
                        Castear(e, t, new TipoExp(Tipos.VECTOR));
                        return result;
                    }
                }
            }
        }
        Castear(e, t, new TipoExp(Tipos.VECTOR));
        return null;
    }

    private void Castear(Entorno e, Object valor, TipoExp vengo) {
        if (vengo.isVector()) {
            CastearVector(e, valor);
        } else if (vengo.isMatrix()) {
            CastearMatriz(e, ((Matrix) valor));
        } else if (vengo.isArrya()) {
            CastearArray(e, valor);
        }
    }

    private void CastearArray(Entorno e, Object valor) {
        Array a = (Array) valor;
        TipoExp tfinal = new TipoExp(Tipos.NULO);
        buscarTipoArray(a.getArreglo(), tfinal);
        if (tfinal != a.getTiposecundario()) {
            CastearArray(a.getArreglo(), tfinal);
            a.setTiposecundario(tfinal);
        }
    }

    private void CastearArray(LinkedList<Object> l, TipoExp t) {
        for (int i = 0; i < l.size(); i++) {
            Object obj = l.get(i);
            if (obj instanceof LinkedList) {
                CastearArray((LinkedList<Object>) obj, t);
            } else {
                EstructuraLineal est = (EstructuraLineal) obj;
                if (t.isList()) {
                    if (est.getTipo().isVector()) {
                        EstructuraLineal elemento = new EstructuraLineal("", new TipoExp(Tipos.VECTOR), est.getTiposecundario(), est.getDimensiones());
                        est.setTipo(new TipoExp(Tipos.LISTA));
                        est.getDimensiones().clear();
                        est.getDimensiones().add(elemento);
                    }
                } else {
                    Literal lit=(Literal)est.getDimensiones().get(0);
                    lit.setValor(CastearValor(t,lit.getValor(),lit.getTipo()));
                    lit.setTipo(new TipoExp(t.tp));
                    est.setTiposecundario(new TipoExp(t.tp));
                }
            }
        }
    }

    private void buscarTipoArray(LinkedList<Object> l, TipoExp t) {
        for (int i = 0; i < l.size(); i++) {
            Object obj = l.get(i);
            if (obj instanceof LinkedList) {
                buscarTipoArray((LinkedList<Object>) obj, t);
            } else {
                EstructuraLineal est = (EstructuraLineal) obj;
                if(est.getTipo().isList()){
                    t.tp=Tipos.LISTA;
                    return;
                }
                Literal lit = (Literal) est.getDimensiones().get(0);
                if (t == null) {
                    t.tp = lit.getTipo().tp;
                } else {
                    t.tp = TipoDominanteMatriz(t, lit.getTipo()).tp;
                }
            }
        }
    }

    private void CastearMatriz(Entorno e, Object valor) {
        Matrix mat = (Matrix) valor;
        LinkedList<LinkedList<Object>> m = (LinkedList<LinkedList<Object>>) mat.getColumnas();
        TipoExp tfinal = buscarTipoMAtriz(m);
        if (tfinal.tp != mat.getTiposecundario().tp) {
            for (int i = 0; i < m.size(); i++) {
                LinkedList<Object> l = m.get(i);
                for (int j = 0; j < l.size(); j++) {
                    EstructuraLineal est = (EstructuraLineal) l.get(j);
                    Literal li = (Literal) est.getDimensiones().get(0);
                    li.setValor(CastearValor(tfinal, li.getValor(), li.getTipo()));
                    li.setTipo(new TipoExp(tfinal.tp));
                    est.setTiposecundario(new TipoExp(tfinal.tp));
                }
            }
            mat.setTiposecundario(tfinal);
        }
    }

    private TipoExp buscarTipoMAtriz(LinkedList<LinkedList<Object>> datos) {
        TipoExp n = null;
        for (int i = 0; i < datos.size(); i++) {
            LinkedList<Object> l = datos.get(i);
            for (int j = 0; j < l.size(); j++) {
                EstructuraLineal est = (EstructuraLineal) l.get(j);
                Literal li = (Literal) est.getDimensiones().get(0);
                if (n == null) {
                    n = li.getTipo();
                } else {
                    n = TipoDominanteMatriz(li.getTipo(), n);
                }
            }
        }
        return n;
    }

    private void CastearVector(Entorno e, Object valor) {
        EstructuraLineal v = (EstructuraLineal) valor;
        TipoExp tfinal = buscarTipoVector(v.getDimensiones());
        if (v.getTiposecundario().tp != tfinal.tp) {
            Literal l = null;
            v.setTiposecundario(tfinal);
            for (int i = 0; i < v.getDimensiones().size(); i++) {
                l = (Literal) v.getDimensiones().get(i);
                l.setValor(CastearValor(tfinal, l.getValor(), l.getTipo()));
                l.setTipo(new TipoExp(tfinal.tp));
            }
        }
    }

    private TipoExp buscarTipoVector(LinkedList<Object> datos) {
        TipoExp n = null;
        Literal aux = null;
        for (int i = 0; i < datos.size(); i++) {
            aux = (Literal) datos.get(i);
            if (n == null) {
                n = aux.getTipo();
            } else {
                n = TipoDominanteMatriz(aux.getTipo(), n);
            }
        }
        return n;
    }

    private TipoExp TipoDominanteMatriz(TipoExp tul, TipoExp tiob) {
        if (tul.isList() || tiob.isList()) {
            return new TipoExp(Tipos.LISTA);
        } else if (tul.tp == Tipos.NULO || tiob.tp == Tipos.NULO) {
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

    @Override
    public String toDot(int padre) {
        StringBuilder nueva = new StringBuilder();
        nueva.append("node").append(this.hashCode()).append("[label=\"For \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(padre).append("->node").append(this.hashCode()).append(";\n");
        nueva.append(id.toDot(this.hashCode()));
        nueva.append(exp.toDot(this.hashCode()));
        nueva.append("node").append(this.hashCode()).append("[label=\"Cuerpo \",fontcolor=\"white\",fillcolor=\"dodgerblue4\",style=\"filled,rounded\"];\n");
        nueva.append("node").append(this.hashCode()).append("->node").append(this.hashCode() + 1).append(";\n");
        for (Nodo n : sentencias) {
            nueva.append(n.toDot(this.hashCode() + 1));
        }
        return nueva.toString();

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

}

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
import Instruccion.Instruccion;
import Objetos.Array;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
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
        LinkedList<Object> lista = Globales.VarGlobales.getInstance().CopiarArray(e, a.getArreglo());
        return ForArray(lista, e);
    }

    private Object ForArray(LinkedList<Object> lista, Entorno e) {
        for (int i = 0; i < lista.size(); i++) {
            Object aux = lista.get(i);
            if (aux instanceof LinkedList) {
                LinkedList<Object> listaaux = (LinkedList<Object>) lista.get(i);
                Object res = ForArray(listaaux, e);
                if (res != null) {
                    return res;
                }
            } else {
                EstructuraLineal v = (EstructuraLineal) lista.get(i);
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
        return null;
    }

    private Object ForLista(EstructuraLineal l, Entorno e) {
        LinkedList<Object> lista = l.getDimensiones();
        LinkedList<Object> nuevalista = Globales.VarGlobales.getInstance().CopiarLista(e, lista);
        for (int i = 0; i < nuevalista.size(); i++) {
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
        LinkedList<LinkedList<Object>> listanueva = Globales.VarGlobales.getInstance().CopiarMatrix(e, lista);
        for (int j = 0; j < m.getColumna(); j++) {
            for (int i = 0; i < m.getFila(); i++) {
                EstructuraLineal v = (EstructuraLineal) listanueva.get(j).get(i);
                LinkedList<Object> lvector = Globales.VarGlobales.getInstance().clonarListaVector(v.getDimensiones(), e);
                EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), v.getTiposecundario(), lvector);
                e.Actualizar(id.getVal(), nueva);
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
        }
        return null;
    }

    private Object ForVector(EstructuraLineal t, Entorno e) {
        LinkedList<Object> lista = t.getDimensiones();
        LinkedList<Object> listanueva = Globales.VarGlobales.getInstance().clonarListaVector(lista, e);
        for (int i = 0; i < listanueva.size(); i++) {
            Literal l = (Literal) listanueva.get(i);
            LinkedList<Object> lvector = new LinkedList<>();
            lvector.add(l);
            EstructuraLineal nueva = new EstructuraLineal(id.getVal(), new TipoExp(Tipos.VECTOR), l.getTipo(), lvector);
            e.Actualizar(id.getVal(), nueva);
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

}

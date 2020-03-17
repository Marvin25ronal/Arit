/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Literal;
import Expresion.TipoExp;
import Instruccion.Instruccion;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Stream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;

/**
 *
 * @author marvin
 */
public class Histograma implements Instruccion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Histograma(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if (dimensiones.size() > 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "Este objeto no retorna nada ", linea, columna);
        }
        if (exp.size() == 3) {
            Object objV = exp.get(0).getValor(e);
            Object objXlab = exp.get(1).getValor(e);
            Object objMain = exp.get(2).getValor(e);
            if (objV instanceof Errores) {
                return objV;
            } else if (objXlab instanceof Errores) {
                return objXlab;
            } else if (objMain instanceof Errores) {
                return objMain;
            }
            String Xlabel, Titulo;
            LinkedList<Double> V = null;
            TipoExp tV = Globales.VarGlobales.getInstance().obtenerTipo(objV, e);
            TipoExp tMain = Globales.VarGlobales.getInstance().obtenerTipo(objMain, e);
            TipoExp tXlab = Globales.VarGlobales.getInstance().obtenerTipo(objXlab, e);
            if (tXlab.isVector()) {
                EstructuraLineal aux = (EstructuraLineal) objXlab;
                if (aux.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Xlab tiene mas de un elemento", linea, columna);
                } else if (!aux.getTiposecundario().isString()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Xlab no es de tipo STRING", linea, columna);
                }
                Xlabel = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
            } else if (tXlab.isString()) {
                Xlabel = objXlab.toString();
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Xlab tiene que ser de tipo STRING", linea, columna);
            }
            if (tMain.isVector()) {
                EstructuraLineal aux = (EstructuraLineal) objMain;
                if (aux.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Main tiene mas de un elemento", linea, columna);
                } else if (!aux.getTiposecundario().isString()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Main no es de tipo STRING", linea, columna);
                }
                Titulo = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
            } else if (tMain.isString()) {
                Titulo = objMain.toString();
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Main tiene que ser de tipo STRING", linea, columna);
            }
            if (tV.isVector()) {
                EstructuraLineal auxH = (EstructuraLineal) objV;
                if (!auxH.getTiposecundario().esNumero()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector V no es de tipo NUMERIC o INTEGER", linea, columna);
                }
                V = ObtenerValores(auxH.getDimensiones(), e);

                Construir(V, Xlabel,Titulo);

            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El parametro V tiene que ser de tipo VECTOR o MATRIX", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "Cantidad de parametros incorrectos", linea, columna);
        }
        return null;

    }

    private void Construir(LinkedList<Double> datos, String titulo, String X) {
        HistogramDataset ds = new HistogramDataset();
        Collections.sort(datos);
        ds.setType(HistogramType.FREQUENCY);
        Double[] vdatos = datos.toArray(new Double[datos.size()]);
        double[] unboxed = Stream.of(vdatos).mapToDouble(Double::doubleValue).toArray();
        double minimo, maximo;
        if (unboxed.length > 1) {
            minimo = unboxed[0];
            maximo = unboxed[unboxed.length - 1];
        } else {
            minimo = unboxed[0];
            maximo = minimo + 1;
        }
        ds.addSeries(X, unboxed,(int)maximo);
        JFreeChart js = ChartFactory.createHistogram(titulo, X, null, ds, PlotOrientation.VERTICAL, true,true,false);
       
        ChartFrame f = new ChartFrame(titulo, js);
        f.setSize(1000, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private LinkedList<Double> ObtenerValores(LinkedList<Object> v, Entorno e) {
        LinkedList<Double> nueva = new LinkedList<>();
        for (int i = 0; i < v.size(); i++) {
            Literal l = (Literal) v.get(i);
            nueva.add(Double.parseDouble(l.getValor(e).toString()));
        }
        return nueva;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

    @Override
    public String toDot(int padre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

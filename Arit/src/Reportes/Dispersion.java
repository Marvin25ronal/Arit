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
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author marvi
 */
public class Dispersion implements Instruccion {

    EstructuraLineal ylim;
    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Dispersion(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna, EstructuraLineal ylim) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
        this.ylim = ylim;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Object objMat = exp.get(0).getValor(e);
        Object objMain = exp.get(1).getValor(e);
        Object objXlab = exp.get(2).getValor(e);
        Object objYlab = exp.get(3).getValor(e);
        Object objYlim = ylim;
        if (objMat instanceof Errores) {
            return objMat;
        } else if (objMain instanceof Errores) {
            return objMain;
        } else if (objXlab instanceof Errores) {
            return objXlab;
        } else if (objYlab instanceof Errores) {
            return objYlab;
        } else if (objYlim instanceof Errores) {
            return objYlim;
        }
        String Xlabel, Ylabel, Titulo;
        LinkedList<Double> V = null;
        TipoExp tMatriz = Globales.VarGlobales.getInstance().obtenerTipo(objMat, e);
        TipoExp tMain = Globales.VarGlobales.getInstance().obtenerTipo(objMain, e);
        TipoExp tXlab = Globales.VarGlobales.getInstance().obtenerTipo(objXlab, e);
        TipoExp tYlab = Globales.VarGlobales.getInstance().obtenerTipo(objYlab, e);
        TipoExp tylim = Globales.VarGlobales.getInstance().obtenerTipo(objYlim, e);
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
        if (tYlab.isVector()) {
            EstructuraLineal aux = (EstructuraLineal) objYlab;
            if (aux.getDimensiones().size() > 1) {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Ylab tiene mas de un elemento", linea, columna);
            } else if (!aux.getTiposecundario().isString()) {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Ylab no es de tipo STRING", linea, columna);
            }
            Ylabel = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
        } else if (tYlab.isString()) {
            Ylabel = objYlab.toString();
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La variable Ylab tiene que ser de tipo STRING", linea, columna);
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
        if (tylim.isVector()) {
            EstructuraLineal vylim = (EstructuraLineal) objYlim;
            if (vylim.getDimensiones().size() == 2) {
                if (vylim.getTiposecundario().esNumero()) {
                    Literal l1 = (Literal) vylim.getDimensiones().get(0);
                    Literal l2 = (Literal) vylim.getDimensiones().get(1);
                    double min = Double.parseDouble(l1.getValor(e).toString());
                    double max = Double.parseDouble(l2.getValor(e).toString());
                    if (min > max) {
                        min *= -1;
                        Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "Ymin es mas grande que ymax", linea, columna));
                    }
                    if (tMatriz.isMatrix()) {
                        Matrix mat = (Matrix) objMat;
                        if (mat.getTiposecundario().esNumero()) {
                            V = ObtenerValores(mat, e, min, max);
                            Construir(V, Titulo, Xlabel, Ylabel);
                        } else {
                            return new Errores(Errores.TipoError.SEMANTICO, "El valor de los elementos de mat no es de tipo INTEGER o NUMERIC", linea, columna);
                        }
                    } else if (tMatriz.isVector()) {
                        EstructuraLineal v = (EstructuraLineal) objMat;
                        if (v.getTiposecundario().esNumero()) {
                            V = ObtenerValores(v.getDimensiones(), e,min,max);
                            Construir(V, Titulo, Xlabel, Ylabel);
                        } else {
                            return new Errores(Errores.TipoError.SEMANTICO, "El valor de los elementos de mat no es de tipo INTEGER O NUMERIC", linea, columna);
                        }
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "El parametro mat no es de tipo MATRIX", linea, columna);
                    }

                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El valor del vector ylim no es de tipo INTEGER o NUMERIC", linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El ylim no cumple con el tamanio", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La variable tylim no es de tipo VECTOR", linea, columna);
        }
        return null;
    }

    private void Construir(LinkedList<Double> datos, String titulo, String x, String y) {
        XYSeries s = new XYSeries(titulo);
        for (int i = 0; i < datos.size(); i++) {
            s.add(i + 1, datos.get(i));
        }
        XYSeriesCollection ds = new XYSeriesCollection();
        ds.addSeries(s);
        XYDataset dataset = ds;
        JFreeChart js = ChartFactory.createXYLineChart(titulo, x, y, dataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = js.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.decode("#D2691E"));
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setLinesVisible(false);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

        js.getLegend().setFrame(BlockBorder.NONE);
        ChartFrame f = new ChartFrame(titulo, js);
        f.setSize(1000, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

    private LinkedList<Double> ObtenerValores(Matrix m, Entorno e, double min, double max) {
        LinkedList<Double> nueva = new LinkedList<>();
        for (int j = 0; j < m.getFila(); j++) {
            for (int i = 0; i < m.getColumna(); i++) {
                EstructuraLineal aux = (EstructuraLineal) m.getColumnas().get(i).get(j);
                Literal l = (Literal) aux.getDimensiones().get(0);
                double d = Double.parseDouble(l.getValor(e).toString());
                if (d >= min && d <= max) {
                    nueva.add(d);
                }
            }
        }
        return nueva;
    }

    private LinkedList<Double> ObtenerValores(LinkedList<Object> v, Entorno e, double min, double max) {
        LinkedList<Double> nueva = new LinkedList<>();
        for (int i = 0; i < v.size(); i++) {
            Literal l = (Literal) v.get(i);
            double t = Double.parseDouble(l.getValor(e).toString());
            if (t >= min && t <= max) {
                nueva.add(t);
            }
        }
        return nueva;
    }

}

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
import java.util.Random;
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
public class LinePlot implements Instruccion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public LinePlot(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        Object objV = exp.get(0).getValor(e);
        Object objType = exp.get(1).getValor(e);
        Object objXlab = exp.get(2).getValor(e);
        Object objYlab = exp.get(3).getValor(e);
        Object objMain = exp.get(4).getValor(e);
        if (objV instanceof Errores) {
            return objV;
        } else if (objType instanceof Errores) {
            return objType;
        } else if (objXlab instanceof Errores) {
            return objXlab;
        } else if (objYlab instanceof Errores) {
            return objYlab;
        } else if (objMain instanceof Errores) {
            return objMain;
        }
        String Xlabel, Ylabel, Titulo, Type;
        LinkedList<Double> V = null;
        TipoExp tV = Globales.VarGlobales.getInstance().obtenerTipo(objV, e);
        TipoExp tXlab = Globales.VarGlobales.getInstance().obtenerTipo(objXlab, e);
        TipoExp tYlab = Globales.VarGlobales.getInstance().obtenerTipo(objYlab, e);
        TipoExp tMain = Globales.VarGlobales.getInstance().obtenerTipo(objMain, e);
        TipoExp tType = Globales.VarGlobales.getInstance().obtenerTipo(objType, e);
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
        if (tType.isVector()) {
            EstructuraLineal aux = (EstructuraLineal) objType;
            if (aux.getDimensiones().size() > 1) {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Type tiene mas de un elemento", linea, columna);
            } else if (!aux.getTiposecundario().isString()) {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Type no es de tipo STRING", linea, columna);
            }
            Type = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
        } else if (tType.isString()) {
            Type = objType.toString();
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La variable Type tiene que ser de tipo STRING", linea, columna);
        }
        if (tV.isVector()) {
            EstructuraLineal auxH = (EstructuraLineal) objV;
            if (!auxH.getTiposecundario().esNumero()) {
                return new Errores(Errores.TipoError.SEMANTICO, "El vector V no es de tipo NUMERIC o INTEGER", linea, columna);
            }
            V = ObtenerValores(auxH.getDimensiones(), e);

            Construir(V, Type, Xlabel, Ylabel, Titulo);

        } else if (tV.isMatrix()) {
            Matrix auxH = (Matrix) objV;
            if(auxH.getTiposecundario().esNumero()){
                V=ObtenerValores(auxH, e);
                Construir(V, Type,Xlabel, Ylabel, Titulo);
            }else{
                return new Errores(Errores.TipoError.SEMANTICO, "El contenido de la matriz tiene que ser INTEGER o NUMERIC", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "El parametro V tiene que ser de tipo VECTOR o MATRIX", linea, columna);
        }
        return null;
    }

    private LinkedList<Double> ObtenerValores(LinkedList<Object> v, Entorno e) {
        LinkedList<Double> nueva = new LinkedList<>();
        for (int i = 0; i < v.size(); i++) {
            Literal l = (Literal) v.get(i);
            nueva.add(Double.parseDouble(l.getValor(e).toString()));
        }
        return nueva;
    }

    private LinkedList<Double> ObtenerValores(Matrix m, Entorno e) {
        LinkedList<Double> nueva = new LinkedList<>();
        for (int j = 0; j < m.getFila(); j++) {
            for (int i = 0; i < m.getColumna(); i++) {
                EstructuraLineal aux = (EstructuraLineal) m.getColumnas().get(i).get(j);
                Literal l = (Literal) aux.getDimensiones().get(0);
                nueva.add(Double.parseDouble(l.getValor(e).toString()));
            }
        }
        return nueva;
    }

    private void Construir(LinkedList<Double> datos, String Type, String X, String Y, String titulo) {
        XYSeries s = new XYSeries(titulo);
        for (int i = 0; i < datos.size(); i++) {
            s.add(i + 1, datos.get(i));
        }
        XYSeriesCollection ds = new XYSeriesCollection();
        ds.addSeries(s);
        XYDataset dataset = ds;
        JFreeChart js = ChartFactory.createXYLineChart(titulo, X, Y, dataset, PlotOrientation.VERTICAL, true, true, false);

        if (Type.equalsIgnoreCase("p")) {
            XYPlot plot = js.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.decode("#D2691E"));
            renderer.setSeriesStroke(0, new BasicStroke(1.0f));
            renderer.setLinesVisible(false);
            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);

            plot.setRangeGridlinesVisible(false);
            plot.setRangeGridlinePaint(Color.BLACK);

            plot.setDomainGridlinesVisible(false);
            plot.setDomainGridlinePaint(Color.BLACK);

            js.getLegend().setFrame(BlockBorder.NONE);
        } else if (Type.equalsIgnoreCase("i")) {
            //se queda normal
            XYPlot plot = js.getXYPlot();

            plot.setBackgroundPaint(Color.white);

            plot.setRangeGridlinesVisible(false);
            plot.setRangeGridlinePaint(Color.BLACK);

            plot.setDomainGridlinesVisible(false);
            plot.setDomainGridlinePaint(Color.BLACK);

            js.getLegend().setFrame(BlockBorder.NONE);

        } else if (Type.equalsIgnoreCase("o")) {
            XYPlot plot = js.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.GREEN);
            renderer.setSeriesStroke(0, new BasicStroke(1.0f));
            renderer.setLinesVisible(true);
            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);

            plot.setRangeGridlinesVisible(false);
            plot.setRangeGridlinePaint(Color.BLACK);

            plot.setDomainGridlinesVisible(false);
            plot.setDomainGridlinePaint(Color.BLACK);

            js.getLegend().setFrame(BlockBorder.NONE);

        } else {
            XYPlot plot = js.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.decode("#000080"));
            renderer.setSeriesStroke(0, new BasicStroke(1.0f));
            renderer.setLinesVisible(true);
            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);

            plot.setRangeGridlinesVisible(false);
            plot.setRangeGridlinePaint(Color.BLACK);

            plot.setDomainGridlinesVisible(false);
            plot.setDomainGridlinePaint(Color.BLACK);

            js.getLegend().setFrame(BlockBorder.NONE);
        }
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

}
